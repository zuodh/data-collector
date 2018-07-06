package com.data.shuzi.datacollector.pool;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author zizuo.zdh
 * @ClassName ThreadPool
 * @Description TODO
 * @Date 2018/7/6 14:38
 * @Version 1.0
 **/
public class ThreadPool {
    private static  BlockingQueue queue = new ArrayBlockingQueue(1000);
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 16, 60, TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());


    public static void executor(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }
}
