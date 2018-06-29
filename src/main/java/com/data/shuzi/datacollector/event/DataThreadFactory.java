package com.data.shuzi.datacollector.event;

import java.util.concurrent.ThreadFactory;

/**
 * @author zizuo.zdh
 * @ClassName DataThreadFactory
 * @Description TODO
 * @Date 2018/6/27 15:00
 * @Version 1.0
 **/
public class DataThreadFactory implements ThreadFactory {
    private int count;
    private String prefix;
    public DataThreadFactory(String prefix){
        this.prefix=prefix;
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread thread=new Thread(r,prefix+"-"+count);
        count++;
        return thread;
    }
}
