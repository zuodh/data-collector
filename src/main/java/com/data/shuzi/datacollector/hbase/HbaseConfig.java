package com.data.shuzi.datacollector.hbase;

import org.apache.hadoop.conf.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

/**
 * @author zizuo.zdh
 * @ClassName HbaseConfig
 * @Description TODO
 * @Date 2018/6/29 14:33
 * @Version 1.0
 **/
@org.springframework.context.annotation.Configuration
public class HbaseConfig {
    @Bean
    public HbaseTemplate getHbaseTemplate(){
        HbaseTemplate hbaseTemplate=new HbaseTemplate();
        Configuration configuration=new Configuration();
        configuration.addResource("classpath:hbase-site.xml");
        hbaseTemplate.setConfiguration(configuration);
        hbaseTemplate.setAutoFlush(false);

        return hbaseTemplate;
    }

}
