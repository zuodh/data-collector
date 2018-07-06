package com.data.shuzi.datacollector.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.shuzi.datacollector.cache.AbstractBaseCache;
import com.data.shuzi.datacollector.common.HttpApi;
import com.data.shuzi.datacollector.common.RemoteHttpConstants;
import com.data.shuzi.datacollector.service.AliyunService;
import com.data.shuzi.datacollector.util.RemoteHttpClient;
import org.asynchttpclient.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zizuo.zdh
 * @ClassName AliyunServiceImpl
 * @Description TODO
 * @Date 2018/6/25 17:19
 * @Version 1.0
 **/
@Service("aliyunService")
public class AliyunServiceImpl extends AbstractBaseCache implements AliyunService {
    @Override
    public JSONObject getAliyunToken() {
        JSONObject jsonObject=null;
        try {
            jsonObject=(JSONObject)super.getValue("aliyun");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public JSONObject getProjectAlarm() {
        String url=RemoteHttpConstants.PROJECT_URL+HttpApi.PROJECT_ALARM;
        JSONObject jsonObject=this.getAliyunToken();
        List<Param> params=new ArrayList<Param>();
        if(jsonObject!=null) {
            params.add(new Param("token",jsonObject.getString("data")));
            System.out.println(jsonObject.getString("token=================================="+"data"));
        }
        String result=RemoteHttpClient.get(url,params);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        JSONObject  retObject=JSON.parseObject(result);
        return retObject;
    }

    @Override
    public JSONObject getProjectCurrentItemData(String projectId) {
        String url=RemoteHttpConstants.TOKNE_URL+HttpApi.PROJECT_CURRENT_ITEM_DATA;
        JSONObject jsonObject=this.getAliyunToken();
        List<Param> params=new ArrayList<Param>();
        if(jsonObject!=null) {
            params.add(new Param("token",jsonObject.getString("data")));
            params.add(new Param("projectID",String.valueOf(projectId)));

        }
        String result=RemoteHttpClient.get(url,params);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        JSONObject  retObject=JSON.parseObject(result);
        return retObject;
    }

    @Override
    public JSONObject getHistoryData(String deviceId, String itemId,Long endTime) {
        JSONObject jsonObject= getHistoryData(deviceId, itemId,1,endTime);
        if(jsonObject==null){
            return jsonObject;
        }
        JSONObject result=jsonObject.getJSONObject("result");
        if(result==null){
            return result;
        }
        JSONObject pageInfo=result.getJSONObject("pageInfo");
        if(pageInfo==null){
            return result;
        }
        Integer total=pageInfo.getInteger("total");
        Integer perPage=pageInfo.getInteger("perPage");
        Integer count=total/perPage+1;
        JSONArray dataArr=result.getJSONArray("data");
        for(int i=2;i<=count;i++){
           JSONObject innerJsonObj=getHistoryData(deviceId,itemId,i,endTime);
           if(innerJsonObj==null){
               continue;
           }
           JSONObject innerResult=innerJsonObj.getJSONObject("result");
           if(innerResult==null){
                continue;
           }
           JSONArray innerDataArr =innerResult.getJSONArray("data");
           if(innerDataArr==null||innerDataArr.size()==0){
               continue;
           }
           for (int j=0;j<innerDataArr.size();j++){
               JSONObject jsonObject1=innerDataArr.getJSONObject(j);
               if(jsonObject1!=null){
                   dataArr.add(jsonObject1);
               }
           }

        }
        return jsonObject;
    }

    private JSONObject getHistoryData(String deviceId, String itemId,Integer page,Long startTime){
        String url=RemoteHttpConstants.TOKNE_URL+HttpApi.PROJECT_HISTORY_DATA;
        JSONObject jsonObject=this.getAliyunToken();
        List<Param> params=new ArrayList<>();
        /**转换为秒*/
        //Long endTime=System.currentTimeMillis()/1000;
        /**相减后还需要转换为毫秒*/
        Long endTime= (startTime + 60 * 60 * 1)*1000;
        if(jsonObject!=null) {
            params.add(new Param("token",jsonObject.getString("data")));
            params.add(new Param("page",String.valueOf(page)));
            params.add(new Param("perPage",String.valueOf(1000)));
            params.add(new Param("deviceId",deviceId));
            params.add(new Param("itemId",itemId));
            params.add(new Param("startTime",String.valueOf(startTime*1000)));
            params.add(new Param("endTime",String.valueOf(endTime)));
        }
        String result=RemoteHttpClient.get(url,params);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        JSONObject  retObject=JSON.parseObject(result);
        return retObject;
    }

    /**
    *@author zizuo.zdh
    *@Description 获取相关数据
    *@Date 2018/6/25 17:49
    *@Param [key]
    *@return java.lang.Object
    *
    **/
    @Override
    public Object getValueWhenExpired(Object key) throws Exception {
        String url=RemoteHttpConstants.TOKNE_URL+HttpApi.USER_TOKEN;
        Map<String,Object> params=new HashMap<>(4);
        params.put("tenantEname",RemoteHttpConstants.TENANTENAME);
        params.put("name",RemoteHttpConstants.NAME);
        params.put("password",RemoteHttpConstants.PASSWORD);
        params.put("hash",RemoteHttpConstants.HASH);
        String userToken=RemoteHttpClient.post(url,params);
        if(StringUtils.isEmpty(userToken)){
            return null;
         }
        JSONObject jsonObject=JSON.parseObject(userToken);
        return jsonObject;
    }
}
