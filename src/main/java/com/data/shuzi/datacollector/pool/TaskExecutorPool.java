package com.data.shuzi.datacollector.pool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zizuo.zdh
 * @ClassName TaskExecutorPool
 * @Description TODO
 * @Date 2018/6/28 10:22
 * @Version 1.0
 **/
@Configuration
@EnableAsync
public class TaskExecutorPool {

    @Autowired
    TaskExecutorPoolConfig taskExecutorPoolConfig;
    @Bean("my-task-pool")
    public Executor getMyTaskThreadPool(){
        ThreadPoolTaskExecutor threadPoolExecutor=new ThreadPoolTaskExecutor();
        threadPoolExecutor.setCorePoolSize(taskExecutorPoolConfig.getCorePoolSize());
        threadPoolExecutor.setKeepAliveSeconds(taskExecutorPoolConfig.getKeepAliveSeconds());
        threadPoolExecutor.setQueueCapacity(taskExecutorPoolConfig.getQueueCapacity());
        threadPoolExecutor.setMaxPoolSize(taskExecutorPoolConfig.getMaxPoolSize());
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolExecutor.setThreadNamePrefix("my-task");
        threadPoolExecutor.initialize();
        return threadPoolExecutor;
    }
}
