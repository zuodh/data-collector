package com.data.shuzi.datacollector;

import com.alibaba.fastjson.JSONArray;
import com.data.shuzi.datacollector.service.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zizuo.zdh
 * @ClassName ProjectServiceImplTest
 * @Description TODO
 * @Date 2018/6/26 15:28
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceImplTest {
    @Autowired
    ProjectService projectService;
    @Test
    public void test(){
        List<String> list=projectService.getAllProject();
        System.out.println(JSONArray.toJSONString(list));
    }
}
