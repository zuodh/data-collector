package com.data.shuzi.datacollector;

import com.data.shuzi.datacollector.service.ProjectService;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zizuo.zdh
 * @ClassName LambdaTest
 * @Description TODO
 * @Date 2018/6/26 18:08
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class LambdaTest {
    @Autowired
    ProjectService projectService;

    @Test
    public void test() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        list.forEach(e -> {
            List<String> projList = projectService.getAllProjectFromDB();
            System.out.println(Thread.currentThread().getName() + "======================" + projList.size());
        });
        stopWatch.stop();
        System.out.println("耗时多久：" + stopWatch.getTotalTimeMillis());
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        stopWatch.start();
        list.parallelStream().forEach(e -> {
            List<String> projList = projectService.getAllProjectFromDB();
            System.out.println(Thread.currentThread().getName() + "======================" + projList.size());
        });
        stopWatch.stop();
        System.out.println("耗时多久：" + stopWatch.getTotalTimeMillis());
    }

    @Test
    public void test2() {
        Integer total = 258;
        Integer perPage = 100;
        Integer count = total / perPage + 1;
        System.out.println(count);
    }

    @Test
    public void test3() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        System.out.println(list.stream().filter(e -> e % 7 == 0).count());
    }

    @Test
    public void test4() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        System.out.println(list.parallelStream().count());
    }

    @Test
    public void test5() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dataFormat.parse("2018-06-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        Calendar begin = Calendar.getInstance();
        begin.setTime(date);
        System.out.println(begin.getTime());

    }
}
