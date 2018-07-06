package com.data.shuzi.datacollector;

import com.alibaba.fastjson.JSONObject;
import com.data.shuzi.datacollector.service.AliyunService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @author zizuo.zdh
 * @ClassName HistoryDataExecutor
 * @Description TODO
 * @Date 2018/7/4 14:54
 * @Version 1.0
 **/
public class HistoryDataExecutor implements Runnable{

    private String devId;
    private String itemId;
    private AliyunService aliyunService;
    private BufferedWriter bufferedWriter;
    public HistoryDataExecutor(AliyunService aliyunService,String devId, String itemId,BufferedWriter bufferedWriter){
        this.devId=devId;
        this.itemId=itemId;
        this.aliyunService=aliyunService;
        this.bufferedWriter=bufferedWriter;
    }
    @Override
    public void run()   {
        System.out.println("the current thread is:"+Thread.currentThread().getName());
        SimpleDateFormat dataFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dataFormat.parse("2018-06-24 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar begin = Calendar.getInstance();
        begin.setTime(date);
        System.out.println("the current thread is:" + begin.getTime());

        for (int i = 0; i < 168; i++) {
            System.out.println("the current thread is:" + i);
            JSONObject jsonObject1 = aliyunService.getHistoryData(devId, itemId, begin.getTime().getTime() / 1000);
            if (jsonObject1 == null) continue;
            System.out.println("the current thread is:" + jsonObject1);
            try {
                bufferedWriter.write("devId"+"->"+"itemId"+"->"+jsonObject1.toString());
                bufferedWriter.write("\n");
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            begin.add(Calendar.HOUR, 1);
        }

    }
}
