package com.data.shuzi.datacollector.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.shuzi.datacollector.dao.DeviceRealDAO;
import com.data.shuzi.datacollector.util.DateUtil;
import com.data.shuzi.datacollector.util.SpringUtils;
import com.data.shuzi.datacollector.util.UUIDGenerate;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zizuo.zdh
 * @ClassName DataEventHandler
 * @Description TODO
 * @Date 2018/6/27 14:28
 * @Version 1.0
 **/
public class DataEventHandler implements EventHandler<DataEvent> {
    private static final Logger logger=LoggerFactory.getLogger(DataEventHandler.class);
    @Override
    public void onEvent(DataEvent dataEvent, long l, boolean b) throws Exception {
        JSONObject jsonObject=dataEvent.getJsonObject();
        logger.info("Event: " + jsonObject);
        if(jsonObject==null){
            return;
        }
        String key=jsonObject.getString("key");
        if(("history").equals(key)){
            saveHistoryDataDB(jsonObject);
        }else {
            saveDataDB(jsonObject);
        }
    }
    public void saveHistoryDataDB(JSONObject jsonObject) {
        logger.info("saveHistoryDataDB start");
        DeviceRealDAO deviceRealDAO=(DeviceRealDAO)SpringUtils.getBeans("deviceRealDAO");
        List<Object[]> params=null;
        String devId=jsonObject.getString("devId");
        String itemId=jsonObject.getString("itemId");
        JSONObject result=jsonObject.getJSONObject("result");
        if(result==null){
            return;
        }
        JSONArray data=result.getJSONArray("data");
        if(data==null||data.size()==0){
            return;
        }
        params=new ArrayList<>();
        for(int i=0;i<data.size();i++){
            JSONObject obj=data.getJSONObject(i);
            if(obj==null){
                continue;
            }
            Double val=obj.getDouble("val");
            Long htime=obj.getLong("htime")/1000;
            params.add(new Object[]{devId,itemId,val,htime});
        }
        deviceRealDAO.saveDeviceItemData(params);
        logger.info("saveHistoryDataDB end");

    }
    public void saveDataDB(JSONObject jsonObject){
        logger.info("saveDataDB  start");
        DeviceRealDAO deviceRealDAO=(DeviceRealDAO)SpringUtils.getBeans("deviceRealDAO");
        List<Object[]> params=null;
        String status=jsonObject.getString("status");
        if(!status.equals("100")){
            return;
        }
        String projId=jsonObject.getString("projId");
        String traceId=UUIDGenerate.getUUID();
        JSONArray jsonArray=jsonObject.getJSONArray("data");
        if(jsonArray==null|jsonArray.size()==0){
            return;
        }
        params=new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject devObj=jsonArray.getJSONObject(i);
            if(devObj==null){
                continue;
            }
            /**devid,val,vdeviceName,itemid,dataAddress,datatype,itemname,alias,htime,readOnly,devName,quality,traceId,projId*/
            params.add(new Object[]{devObj.getString("devid"),devObj.getString("val"),devObj.getString("vdeviceName"),devObj.getString("itemid"),
                    devObj.getString("dataAddress"),devObj.getString("datatype"),devObj.getString("itemname"),devObj.getString("alias"),DateUtil.formatDate(devObj.getString("htime")),
                    devObj.getString("readOnly"),devObj.getString("devName"),devObj.getString("quality"),traceId,projId});
        }
        deviceRealDAO.saveDeviceRealData(params);
        logger.info("saveDataDB end");
    }
}
