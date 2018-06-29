package com.data.shuzi.datacollector;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.util.Random;

/**
 * @author zizuo.zdh
 * @ClassName AsyncTest
 * @Description TODO
 * @Date 2018/6/28 11:35
 * @Version 1.0
 **/
public class AsyncTest  extends BaseTest{
    private Logger logger=LoggerFactory.getLogger(AsyncTest.class);
    @Test
    public void sayHello() throws InterruptedException {
        while (true){
            name();
            //Thread.sleep(1000);
        }
    }
    @Async("my-task-pool")
    public void name(){
        logger.info(Math.random()+"...");
    }
}
