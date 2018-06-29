package com.data.shuzi.datacollector;

import com.alibaba.fastjson.JSONObject;
import com.data.shuzi.datacollector.cache.AbstractBaseCache;
import com.data.shuzi.datacollector.service.AliyunService;
import com.data.shuzi.datacollector.service.impl.AliyunServiceImpl;
import com.data.shuzi.datacollector.util.SpringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zizuo.zdh
 * @ClassName CacheTest
 * @Description TODO
 * @Date 2018/6/25 17:53
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest extends AbstractBaseCache {
    @Before
    public void init(){

    }
    @Test
    public void test1() throws Exception {
        Object value=super.getValue("hello");
        System.out.println(value);
         value=super.getValue("hello");
        System.out.println(value);

    }


    @Override
    protected Object getValueWhenExpired(Object key) throws Exception {
        Student student=new Student();
        student.setId(1);
        student.setName("java");
        return student;
    }

}
