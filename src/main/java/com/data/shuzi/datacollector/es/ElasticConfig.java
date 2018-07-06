package com.data.shuzi.datacollector.es;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zizuo.zdh
 * @ClassName ElasticConfig
 * @Description TODO
 * @Date 2018/7/3 13:54
 * @Version 1.0
 **/
@Configuration
public class ElasticConfig {
    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(){
        TransportClient client=null;
        Settings settings = Settings.builder().put("cluster.name", "es").build();
        try {
             client=new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ElasticsearchTemplate elasticsearchTemplate=new ElasticsearchTemplate(client);
        return elasticsearchTemplate;
    }

}
