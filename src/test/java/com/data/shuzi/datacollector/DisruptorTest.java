package com.data.shuzi.datacollector;

import com.alibaba.fastjson.JSONObject;
import com.data.shuzi.datacollector.event.DataEvent;
import com.data.shuzi.datacollector.event.DataEventProducer;
import com.data.shuzi.datacollector.event.DisruptorStart;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zizuo.zdh
 * @ClassName DisruptorTest
 * @Description TODO
 * @Date 2018/6/27 16:27
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DisruptorTest {
    @Before
    public void  init(){
        DisruptorStart.getInstance();
    }
    @Test
    public void test(){
        Disruptor<DataEvent> disruptor=DisruptorStart.getDisruptor();
        Assert.assertNotNull(disruptor);
        RingBuffer<DataEvent> ringBuffer = disruptor.getRingBuffer();
        DataEventProducer producer = new DataEventProducer(ringBuffer);
        for(int i=0;i<100000;i++) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("key-"+i,i);
            producer.onData(jsonObject);
        }
    }
}
