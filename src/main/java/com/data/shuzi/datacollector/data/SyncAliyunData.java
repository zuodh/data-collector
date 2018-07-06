package com.data.shuzi.datacollector.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.shuzi.datacollector.DataCollectorApplication;
import com.data.shuzi.datacollector.event.DataEvent;
import com.data.shuzi.datacollector.event.DataEventProducer;
import com.data.shuzi.datacollector.event.DisruptorStart;
import com.data.shuzi.datacollector.service.AliyunService;
import com.data.shuzi.datacollector.util.SpringUtils;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zizuo.zdh
 * @ClassName SyncAliyunData
 * @Description TODO
 * @Date 2018/7/6 15:39
 * @Version 1.0
 **/
public class SyncAliyunData {
    private final static Logger logger=LoggerFactory.getLogger(DataCollectorApplication.class);

    public static void loadData() throws IOException {
        AliyunService aliyunService=(AliyunService)SpringUtils.getBeans("aliyunService");
        int count=0;
        Disruptor<DataEvent> disruptor=DisruptorStart.getDisruptor();
        RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
        DataEventProducer producer = new DataEventProducer(ringBuffer);
        mointor(ringBuffer);
        BufferedReader projBr = new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\project.txt")));
        BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\realData.txt")));
        Map<String,JSONArray> deviceMap=new HashMap<>();
        while (br.ready()) {
            String line = br.readLine();
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
            JSONArray jsonArray = deviceMap.get(String.valueOf(projId));
            if (jsonArray == null || jsonArray.size() == 0) {
                continue;
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json1 = jsonArray.getJSONObject(i);
                String devId = json1.getString("devid");
                String itemId = json1.getString("itemid");

                SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = null;
                try {
                    date = dataFormat.parse("2018-07-05 00:00:00");
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                Calendar begin = Calendar.getInstance();
                begin.setTime(date);
                for (int k = 0; k < 24; k++) {
                    JSONObject jsonObject1 = aliyunService.getHistoryData(devId, itemId, begin.getTime().getTime() / 1000);
                    jsonObject1.put("address",jsonObject.getString("address"));
                    jsonObject1.put("name",jsonObject.getString("name"));
                    jsonObject1.put("latitude",jsonObject.getDouble("latitude"));
                    jsonObject1.put("longitude",jsonObject.getDouble("longitude"));
                    jsonObject1.put("projId",projId);
                    jsonObject1.put("alias",json1.getString("alias"));
                    jsonObject1.put("dataAddress",json1.getString("dataAddress"));
                    jsonObject1.put("devid",json1.getString("devid"));
                    jsonObject1.put("itemid",json1.getString("itemid"));
                    jsonObject1.put("itemname",json1.getString("itemname"));
                    jsonObject1.put("readOnly",json1.getString("readOnly"));
                    jsonObject1.put("specificType",json1.getString("specificType"));
                    jsonObject1.put("vdeviceName",json1.getString("vdeviceName"));
                    jsonObject1.put("devName",json1.getString("devName"));
                    jsonObject1.put("quality",json1.getString("quality"));
                    jsonObject1.put("datatype",json1.getString("datatype"));
                    producer.onData(jsonObject1);
                    count++;
                    System.out.println("device history count:"+count);
                    begin.add(Calendar.HOUR, 1);
                }
            }
        }
    }
    public static void mointor(RingBuffer<DataEvent> ringBuffer){
        ScheduledExecutorService executorService=new ScheduledThreadPoolExecutor(1, r -> {
            Thread thread=new Thread(r);
            thread.setDaemon(true);
            thread.setName("disruptor mointor thread");
            return thread;
        });
        executorService.scheduleAtFixedRate(() -> logger.info("the current capacity:"+ringBuffer.remainingCapacity()),10, 5,TimeUnit.SECONDS);

    }
}
