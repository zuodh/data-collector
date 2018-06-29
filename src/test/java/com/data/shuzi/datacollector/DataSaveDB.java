package com.data.shuzi.datacollector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.shuzi.datacollector.dao.DeviceRealDAO;
import com.data.shuzi.datacollector.dao.DeviceRelItemDAO;
import com.data.shuzi.datacollector.event.DataEvent;
import com.data.shuzi.datacollector.event.DataEventProducer;
import com.data.shuzi.datacollector.event.DisruptorStart;
import com.data.shuzi.datacollector.service.AliyunService;
import com.data.shuzi.datacollector.util.DateUtil;
import com.data.shuzi.datacollector.util.UUIDGenerate;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zizuo.zdh
 * @ClassName DataSaveDB
 * @Description TODO
 * @Date 2018/6/27 9:18
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSaveDB {
     @Autowired
     DeviceRealDAO deviceRealDAO;
     @Autowired
     DeviceRelItemDAO deviceRelItemDAO;
     @Autowired
     AliyunService aliyunService;
     @Before
     public void  init(){
        DisruptorStart.getInstance();
    }
      @Test
      public void test() throws IOException {
          BufferedReader br=new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\shuangliang.txt")));
          List<Object[]> params=null;
          while (br.ready()){
             String line= br.readLine();
             if(StringUtils.isEmpty(line)){
                 continue;
             }
             String[] lineAtt=line.split("->");
             Integer projId=Integer.valueOf(lineAtt[0]);
             String traceId=UUIDGenerate.getUUID();
             if(StringUtils.isEmpty(lineAtt[1])){
                 continue;
             }
             JSONObject jsonObject=JSON.parseObject(lineAtt[1]);
             String status=jsonObject.getString("status");
             if(!status.equals("100")){
                 continue;
             }
             JSONArray jsonArray=jsonObject.getJSONArray("data");
             if(jsonArray==null|jsonArray.size()==0){
                 continue;
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
          }
      }
      @Test
      public void test1() throws IOException{
          BufferedReader br=new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\shuanglianghis2222.txt")));
          List<Object[]> params=null;
          while(br.ready()){
             String line= br.readLine();
             if(StringUtils.isEmpty(line)){
                 continue;
             }
             String[] strArr=line.split("->");
             String devId=strArr[0];
             String itemId=strArr[1];
             JSONObject jsonObject=JSON.parseObject(strArr[2]);
             if(jsonObject==null){
                 continue;
             }
             JSONObject result=jsonObject.getJSONObject("result");
             if(result==null){
                  continue;
              }
              JSONArray data=result.getJSONArray("data");
              if(data==null||data.size()==0){
                  continue;
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
                  deviceRealDAO.saveDeviceItemData(params);
              }
          }
      }
      @Test
      public void test2(){
          List<Map<String,Object>> retList=deviceRelItemDAO.getDeviceAndItem();
          if(retList==null||retList.size()==0){
              return;
          }
          Disruptor<DataEvent> disruptor=DisruptorStart.getDisruptor();
          RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
          DataEventProducer producer = new DataEventProducer(ringBuffer);
          retList.forEach(e->{
                 String devId=(String)e.get("devId");
                 String itemId=(String)e.get("itemId");
                 JSONObject jsonObject=aliyunService.getHistoryData(devId,itemId);
                 if(jsonObject!=null){
                     jsonObject.put("devId",devId);
                     jsonObject.put("itemId",itemId);
                     jsonObject.put("key","history");
                     producer.onData(jsonObject);
                 }
          });
      }
}
