package com.data.shuzi.datacollector.event;

import com.lmax.disruptor.EventFactory;

/**
 * @author zizuo.zdh
 * @ClassName DataEventFactory
 * @Description TODO
 * @Date 2018/6/27 14:27
 * @Version 1.0
 **/
public class DataEventFactory implements EventFactory<DataEvent> {
    @Override
    public DataEvent newInstance() {
        return new DataEvent();
    }
}
