package com.data.shuzi.datacollector.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zizuo.zdh
 * @ClassName AbstractBaseCache
 * @Description TODO
 * @Date 2018/6/25 17:30
 * @Version 1.0
 **/
@Data
public abstract class AbstractBaseCache<K,V> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractBaseCache.class);

    /**缓存自动刷新周期*/
    protected int refreshDuration = 10;
    /**缓存刷新周期时间格式*/
    protected TimeUnit refreshTimeunit = TimeUnit.MINUTES;
    /**缓存过期时间（可选择）*/
    protected int expireDuration = -1;
    /**缓存刷新周期时间格式*/
    protected TimeUnit expireTimeunit = TimeUnit.HOURS;
    /**缓存最大容量*/
    protected int maxSize = 10000;
    /**数据刷新线程池*/
    protected static ListeningExecutorService refreshPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(2));

    private LoadingCache<K, V> cache = null;
    /**
    *@author zizuo.zdh
    *@Description TODO
    *@Date 2018/6/28 14:46
    *@Param [key]
    *@return V
    *
    **/
    protected abstract V getValueWhenExpired(K key) throws Exception;

    public V getValue(K key) throws Exception {
        try {
            return getCache().get(key);
        } catch (Exception e) {
            logger.error("从内存缓存中获取内容时发生异常，key: " + key, e);
            throw e;
        }
    }
    public void clearAll(){
        this.getCache().invalidateAll();
    }
    
    private synchronized LoadingCache<K, V> getCache() {
        if(cache == null){
            CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().maximumSize(maxSize);
            if(refreshDuration > 0) {
                cacheBuilder = cacheBuilder.refreshAfterWrite(refreshDuration, refreshTimeunit);
            }
            if(expireDuration > 0) {
                cacheBuilder = cacheBuilder.expireAfterWrite(expireDuration, expireTimeunit);
            }
            cache = cacheBuilder.build(new CacheLoader<K, V>() {
                @Override
                public V load(K key) throws Exception {
                    return getValueWhenExpired(key);
                }
                @Override
                public ListenableFuture<V> reload(final K key,V oldValue)  {
                    return refreshPool.submit(() -> getValueWhenExpired(key));
                }
            } );
        }
        return cache;
    }

}
