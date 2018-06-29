package com.data.shuzi.datacollector;

import com.alibaba.fastjson.JSONObject;
import com.data.shuzi.datacollector.service.AliyunService;
import com.data.shuzi.datacollector.service.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author zizuo.zdh
 * @ClassName DataCollectorTest
 * @Description TODO
 * @Date 2018/6/26 17:10
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataCollectorTest {
    @Autowired
    AliyunService aliyunService;
    @Autowired
    ProjectService projectService;
    @Test
    public void test() throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter(new File("C:\\Users\\zizuo.zdh\\Desktop\\shuangliang.txt")));
        List<String> projectIds=projectService.getAllProject();
        projectIds.forEach(e->{
            JSONObject jsonObject= aliyunService.getProjectCurrentItemData(e);
            try {
                bw.write(e+"->"+jsonObject.toString());
                bw.write("\n");
                bw.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        bw.close();
    }

    @Test
    public void test1() throws IOException,InterruptedException {
        BufferedWriter bw=new BufferedWriter(new FileWriter(new File("C:\\Users\\zizuo.zdh\\Desktop\\shuangliang.txt")));
        for(int i=950;i<2000;i++) {
            JSONObject jsonObject = aliyunService.getProjectCurrentItemData(String.valueOf(i));
            if(jsonObject!=null) {
                bw.write(i + "->" + jsonObject.toString());
                bw.write("\n");
                bw.flush();
            }
            Thread.sleep(5000);
        }
    }
}
