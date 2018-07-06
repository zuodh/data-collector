package com.data.shuzi.datacollector.event;

import com.lmax.disruptor.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zizuo.zdh
 * @ClassName DisruptorExceptionHandler
 * @Description TODO
 * @Date 2018/7/6 11:37
 * @Version 1.0
 **/
public class DisruptorExceptionHandler  implements ExceptionHandler<DataEvent> {
    private static final Logger logger=LoggerFactory.getLogger(DisruptorExceptionHandler.class);
    @Override
    public void handleEventException(Throwable throwable, long seq, DataEvent dataEvent) {
        logger.error("处理发生异常:" + throwable.getMessage()+"seq:"+seq+"data:"+dataEvent.getJsonObject().toJSONString());
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        logger.error("启动发生异常:" + throwable.getMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        logger.error("关闭发生异常:" + throwable.getMessage());
    }
}
