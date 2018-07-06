package com.data.shuzi.datacollector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.shuzi.datacollector.dao.DeviceRealDAO;
import com.data.shuzi.datacollector.dao.DeviceRelItemDAO;
import com.data.shuzi.datacollector.event.DataEvent;
import com.data.shuzi.datacollector.event.DataEventProducer;
import com.data.shuzi.datacollector.event.DisruptorStart;
import com.data.shuzi.datacollector.model.DeviceHistoryData;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public void init() {
        DisruptorStart.getInstance();
    }

    @Test
    public void test() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\shuangliang.txt")));
        List<Object[]> params = null;
        while (br.ready()) {
            String line = br.readLine();
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            String[] lineAtt = line.split("->");
            Integer projId = Integer.valueOf(lineAtt[0]);
            String traceId = UUIDGenerate.getUUID();
            if (StringUtils.isEmpty(lineAtt[1])) {
                continue;
            }
            JSONObject jsonObject = JSON.parseObject(lineAtt[1]);
            String status = jsonObject.getString("status");
            if (!status.equals("100")) {
                continue;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray == null | jsonArray.size() == 0) {
                continue;
            }
            params = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject devObj = jsonArray.getJSONObject(i);
                if (devObj == null) {
                    continue;
                }
                /**devid,val,vdeviceName,itemid,dataAddress,datatype,itemname,alias,htime,readOnly,devName,quality,traceId,projId*/
                params.add(new Object[]{devObj.getString("devid"), devObj.getString("val"), devObj.getString("vdeviceName"), devObj.getString("itemid"),
                        devObj.getString("dataAddress"), devObj.getString("datatype"), devObj.getString("itemname"), devObj.getString("alias"), DateUtil.formatDate(devObj.getString("htime")),
                        devObj.getString("readOnly"), devObj.getString("devName"), devObj.getString("quality"), traceId, projId});
            }
            deviceRealDAO.saveDeviceRealData(params);
        }
    }

    @Test
    public void test1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\shuanglianghis2222.txt")));
        List<Object[]> params = null;
        while (br.ready()) {
            String line = br.readLine();
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            String[] strArr = line.split("->");
            String devId = strArr[0];
            String itemId = strArr[1];
            JSONObject jsonObject = JSON.parseObject(strArr[2]);
            if (jsonObject == null) {
                continue;
            }
            JSONObject result = jsonObject.getJSONObject("result");
            if (result == null) {
                continue;
            }
            JSONArray data = result.getJSONArray("data");
            if (data == null || data.size() == 0) {
                continue;
            }
            params = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                JSONObject obj = data.getJSONObject(i);
                if (obj == null) {
                    continue;
                }
                Double val = obj.getDouble("val");
                Long htime = obj.getLong("htime") / 1000;
                params.add(new Object[]{devId, itemId, val, htime});
                deviceRealDAO.saveDeviceItemData(params);
            }
        }
    }

    @Test
    public void test2() {
        List<Map<String, Object>> retList = deviceRelItemDAO.getDeviceAndItem();
        if (retList == null || retList.size() == 0) {
            return;
        }
        Disruptor<DataEvent> disruptor = DisruptorStart.getDisruptor();
        RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
        DataEventProducer producer = new DataEventProducer(ringBuffer);
        retList.forEach(e -> {
            String devId = (String) e.get("devId");
            String itemId = (String) e.get("itemId");
            JSONObject jsonObject = aliyunService.getHistoryData(devId, itemId, System.currentTimeMillis() / 1000);
            if (jsonObject != null) {
                jsonObject.put("devId", devId);
                jsonObject.put("itemId", itemId);
                jsonObject.put("key", "history");
                producer.onData(jsonObject);
            }
        });
    }

    @Test
    public void test3() throws IOException {
        List<Object[]> list=new ArrayList<>();
        BufferedReader projBr = new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\project.txt")));
        BufferedReader deviceBr = new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\realData.txt")));
        Map<String,JSONArray> deviceMap=new HashMap<>();
        while (deviceBr.ready()) {
            String line = deviceBr.readLine();
            String[] arrt=line.split("->");
            JSONObject jsonObject = JSON.parseObject(arrt[1]);
            if (jsonObject == null) {
                continue;
            }
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            deviceMap.put(arrt[0],jsonArray);
        }

        while (projBr.ready()) {
            String line = projBr.readLine();
            JSONObject jsonObject = JSON.parseObject(line);
            if (jsonObject == null) {
                continue;
            }
            Integer projId = jsonObject.getInteger("id");
            JSONArray jsonArray=deviceMap.get(String.valueOf(projId));
            if(jsonArray==null||jsonArray.size()==0){
                continue;
            }
            for (int i=0;i<jsonArray.size();i++){
                DeviceHistoryData deviceHistoryData=new DeviceHistoryData();
                deviceHistoryData.setProjAddr(jsonObject.getString("address"));
                deviceHistoryData.setProjName(jsonObject.getString("name"));
                deviceHistoryData.setLatitude(jsonObject.getDouble("latitude"));
                deviceHistoryData.setLongitude(jsonObject.getDouble("longitude"));
                deviceHistoryData.setProjId(projId);
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                if(jsonArray==null){
                    continue;
                }
                deviceHistoryData.setAlias(jsonObject1.getString("alias"));
                deviceHistoryData.setDataAddress(jsonObject1.getString("dataAddress"));
                deviceHistoryData.setDevid(jsonObject1.getString("devid"));
                deviceHistoryData.setItemid(jsonObject1.getString("itemid"));
                deviceHistoryData.setItemname(jsonObject1.getString("itemname"));
                deviceHistoryData.setReadOnly(jsonObject1.getString("readOnly"));
                deviceHistoryData.setSpecificType(jsonObject1.getString("specificType"));
                deviceHistoryData.setVdeviceName(jsonObject1.getString("vdeviceName"));
                deviceHistoryData.setDevName(jsonObject1.getString("devName"));
                deviceHistoryData.setQuality(jsonObject1.getString("quality"));
                deviceHistoryData.setDatatype(jsonObject1.getString("datatype"));
                deviceHistoryData.setVal("1");
                deviceHistoryData.setHtime(System.currentTimeMillis());
                list.add(new Object[]{deviceHistoryData.getProjId(),deviceHistoryData.getProjName(),deviceHistoryData.getProjAddr(),deviceHistoryData.getLatitude(),deviceHistoryData.getLongitude(),
                        deviceHistoryData.getDevid(),deviceHistoryData.getVdeviceName(),deviceHistoryData.getDataAddress(),deviceHistoryData.getItemid(),deviceHistoryData.getItemname(),deviceHistoryData.getVal(),
                        deviceHistoryData.getSpecificType(),deviceHistoryData.getReadOnly(),deviceHistoryData.getDevName(),deviceHistoryData.getQuality(),deviceHistoryData.getDatatype(),deviceHistoryData.getAlias(),deviceHistoryData.getHtime()/1000});
                deviceRealDAO.saveHistoryData(list,100);
            }
        }
    }
    @Test
    public void test4() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\zizuo.zdh\\Desktop\\history_load.txt")));

        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dataFormat.parse("2018-07-04 14:00:00");
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        Calendar begin = Calendar.getInstance();
        begin.setTime(date);
        Long startTime=begin.getTime().getTime();
        Long endTime= (startTime/1000 + 60 * 60 * 1)*1000;
        List<Map<String, Object>> retList = deviceRelItemDAO.getDeviceAndItem();
        retList.forEach(e->{
            String devId=(String)e.get("devId");
            String itemId=(String)e.get("itemId");
            try {
                bufferedWriter.write("26e68fc7-d277-495a-a92e-43eb1707ee55,"+"1,"+"1000,"+devId+","+itemId+","+startTime+","+endTime);
                bufferedWriter.write("\n");
                bufferedWriter.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
        bufferedWriter.close();
    }
}
