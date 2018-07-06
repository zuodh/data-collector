package com.data.shuzi.datacollector.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zizuo.zdh
 * @ClassName CountFactory
 * @Description TODO
 * @Date 2018/7/6 17:39
 * @Version 1.0
 **/
public class CountFactory {
    private static AtomicInteger atomicInteger = new AtomicInteger();

    private CountFactory() {

    }

    public static Integer getInc() {
        return atomicInteger.getAndIncrement();
    }

    public static Integer get() {
        return atomicInteger.get();
    }
}
