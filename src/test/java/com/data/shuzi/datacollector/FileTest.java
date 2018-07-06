package com.data.shuzi.datacollector;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zizuo.zdh
 * @ClassName FileTest
 * @Description TODO
 * @Date 2018/6/26 22:08
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileTest {
    @Test
    public void test() throws IOException {
        List<String> ret=new ArrayList();
        BufferedReader bufferedReader=new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\shuangliang.txtbak")));
        while(bufferedReader.ready()){
            String readLine=bufferedReader.readLine();
            if(readLine.indexOf("\"status\":\"100\"")!=-1){
                String[] strArr=readLine.split("->");
                ret.add(strArr[0]);
            }
        }
        System.out.println(JSONObject.toJSONString(ret));
    }
    @Test
    public void test1() throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\shuanglianghis2222.txt")));
        BufferedWriter bw=new BufferedWriter(new FileWriter(new File("C:\\Users\\zizuo.zdh\\Desktop\\deviceId.txt")));
        while (br.ready()){
           String line= br.readLine();
           String[] arr=line.split("->");
            bw.write(arr[0]+"->"+arr[1]+"\n");
            bw.flush();
        }
        bw.close();

    }
    @Test
    public void test2() throws IOException {
        Set<String> set=new HashSet<>();
        BufferedReader br=new BufferedReader(new FileReader(new File("C:\\Users\\zizuo.zdh\\Desktop\\historyData.txt")));
        while (br.ready()){
            String line= br.readLine();
            String[] arr=line.split("->");
            if(!set.contains(arr[0]+"->"+arr[1])){
                set.add(arr[0]+"->"+arr[1]);
                System.out.println(arr[0]+"->"+arr[1]);

            }
        }
    }
}
