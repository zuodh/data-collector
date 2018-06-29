package com.data.shuzi.datacollector.pool;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zizuo.zdh
 * @ClassName TaskThreadPoolConfig
 * @Description TODO
 * @Date 2018/6/28 10:23
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "task")
public class TaskExecutorPoolConfig {
    private int corePoolSize;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    private int maxPoolSize;
    private int keepAliveSeconds;
    private int queueCapacity;
}
