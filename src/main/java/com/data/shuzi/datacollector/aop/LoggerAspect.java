package com.data.shuzi.datacollector.aop;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.shuzi.datacollector.common.CodeEnum;
import com.data.shuzi.datacollector.common.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author zizuo.zdh
 * @ClassName LoggerAspect
 * @Description TODO
 * @Date 2018/6/25 14:47
 * @Version 1.0
 **/
@Component
@Aspect
public class LoggerAspect {
    private final static Logger logger=LoggerFactory.getLogger(LoggerAspect.class);
    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void logger() {
    }

    @Around(value = "logger()")
    public Object around(ProceedingJoinPoint point) {
        //当前拦截的类和方法：
        Class clazz = point.getTarget().getClass();
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        logger.info("类名：" + clazz.getSimpleName() + ",方法名称:" + method.getName() + ",参数:" + Arrays.toString(point.getArgs()));
        Object result = null;
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            result = point.proceed();
        } catch (Throwable e) {
            logger.error("类名：" + clazz.getSimpleName() + ",方法名称:" + method.getName() + "异常:" + e);
            return Result.isFail(CodeEnum.OK.getCode(),e.getMessage());
        } finally {
            stopWatch.stop();
        }
        logger.info("类名：" + clazz.getSimpleName() + ",方法名称:" + method.getName() + ",耗时：" + stopWatch.getTotalTimeMillis() + "ms");
        //返回通知
        return result;
    }
}
