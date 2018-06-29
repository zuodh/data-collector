package com.data.shuzi.datacollector;

import com.data.shuzi.datacollector.util.DateUtil;
import com.data.shuzi.datacollector.util.UUIDGenerate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zizuo.zdh
 * @ClassName UtilTest
 * @Description TODO
 * @Date 2018/6/27 9:36
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilTest {
    @Test
    public void test(){
        System.out.println(UUIDGenerate.getUUID());
    }
    @Test
    public void test1(){
       System.out.println(DateUtil.formatDate("2018-06-26 17:22:06.208"));
    }
    @Test
    public void test2(){
        Long htime=1530055248000L;
        long endTime = System.currentTimeMillis()/1000;
        long startTime = (endTime - 60 * 60 * 24*180)*1000;

        Date date=new Date(startTime);
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println( df.format(date));


    }
}
