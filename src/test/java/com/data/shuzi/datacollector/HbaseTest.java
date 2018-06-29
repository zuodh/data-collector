package com.data.shuzi.datacollector;

import com.data.shuzi.datacollector.hbase.HbaseTemplate;
import com.data.shuzi.datacollector.hbase.TableCallback;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zizuo.zdh
 * @ClassName HbaseTest
 * @Description TODO
 * @Date 2018/6/28 18:00
 * @Version 1.0
 **/
public class HbaseTest extends  BaseTest {
    @Autowired
    HbaseTemplate hbaseTemplate;
    @Test
    public void test(){
        //hbaseTemplate
    }

}
