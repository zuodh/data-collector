package com.data.shuzi.datacollector.task;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import java.util.concurrent.Executor;

/**
 * @author zizuo.zdh
 * @ClassName ScheduleConfig
 * @Description TODO
 * @Date 2018/6/26 15:04
 * @Version 1.0
 **/
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer,AsyncConfigurer {

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler getTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler=new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        threadPoolTaskScheduler.setThreadNamePrefix("task-");
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskScheduler;
    }
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        TaskScheduler taskScheduler=getTaskScheduler();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }
    @Override
    public Executor getAsyncExecutor() {
        Executor  taskScheduler=getTaskScheduler();
        return taskScheduler;
    }

    /**
     * The {@link AsyncUncaughtExceptionHandler} instance to be used
     * when an exception is thrown during an asynchronous method execution
     * with {@code void} return type.
     */
    @Override
    public  AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
