package com.data.shuzi.datacollector.event;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadFactory;

/**
 * @author zizuo.zdh
 * @ClassName DisruptorStart
 * @Description TODO
 * @Date 2018/6/27 14:39
 * @Version 1.0
 **/
public class DisruptorStart {
    private static final int bufferSize = 1024;
    private static final DisruptorStart disruptorStart=new DisruptorStart();
    private static  Disruptor<DataEvent> disruptor=null;
    private DisruptorStart(){

    }
    private static void init(){
        DataEventFactory factory = new DataEventFactory();
        ThreadFactory threadFactory=new DataThreadFactory("disruptor");
        disruptor = new Disruptor<>(factory, bufferSize,threadFactory,ProducerType.MULTI, new BlockingWaitStrategy());
        disruptor.handleEventsWith(new DataEventHandler());
        disruptor.setDefaultExceptionHandler(new DisruptorExceptionHandler());
        disruptor.start();
    }

    public synchronized  static DisruptorStart getInstance(){
        if(disruptor==null){
            init();
        }
        return disruptorStart;
    }
    public  static Disruptor<DataEvent> getDisruptor(){
        return disruptor;
    }
}
