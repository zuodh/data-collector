package com.data.shuzi.datacollector.task;

import com.alibaba.fastjson.JSONObject;
import com.data.shuzi.datacollector.event.DataEvent;
import com.data.shuzi.datacollector.event.DataEventProducer;
import com.data.shuzi.datacollector.event.DisruptorStart;
import com.data.shuzi.datacollector.service.AliyunService;
import com.data.shuzi.datacollector.service.DeviceRealService;
import com.data.shuzi.datacollector.service.ProjectService;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author zizuo.zdh
 * @ClassName RemoteDataTask
 * @Description 双良远程数据接口定时任务
 * @Date 2018/6/26 14:46
 * @Version 1.0
 **/
@Component
public class RemoteDataTask {
    @Autowired
    AliyunService aliyunService;
    @Autowired
    ProjectService projectService;
    @Autowired
    DeviceRealService deviceRealService;
    private static final Logger logger=LoggerFactory.getLogger(RemoteDataTask.class);

   // @Scheduled(cron="0 1/2 * * * ? ")
    public void loadRealData(){
       logger.info("实时任务开始执行");
        List<String> projList=deviceRealService.getProjListByDeviceReal();
        if(projList==null&&projList.size()==0){
            return ;
        }
        Disruptor<DataEvent>  disruptor=DisruptorStart.getDisruptor();
        RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
        DataEventProducer producer = new DataEventProducer(ringBuffer);
        projList.forEach(e->{
            JSONObject currentItemData= aliyunService.getProjectCurrentItemData(String.valueOf(e));
            if(currentItemData==null){
                return;
            }
            currentItemData.put("projId",e);
            producer.onData(currentItemData);
        });
       logger.info("实时任务结束执行");
    }

}
