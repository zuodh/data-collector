package com.data.shuzi.datacollector.event;

import com.alibaba.fastjson.JSONObject;
import com.lmax.disruptor.RingBuffer;


/**
 * @author zizuo.zdh
 * @ClassName DataEventProducerWithTranslator
 * @Description TODO
 * @Date 2018/6/27 14:31
 * @Version 1.0
 **/
public class DataEventProducer{
    private final RingBuffer<DataEvent> ringBuffer;

    public  DataEventProducer(RingBuffer<DataEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void onData(JSONObject bb)
    {
        long sequence = ringBuffer.next();
        DataEvent dataEvent = ringBuffer.get(sequence);
        dataEvent.setJsonObject(bb);
        ringBuffer.publish(sequence);
    }
}
