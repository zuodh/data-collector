package com.data.shuzi.datacollector.hbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
/**
 * @author zizuo.zdh
 * @ClassName HbaseAutoConfiguration
 * @Description TODO
 * @Date 2018/6/28 17:24
 * @Version 1.0
 **/
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HbaseProperties.class)
@ConditionalOnClass(HbaseTemplate.class)
public class HbaseAutoConfiguration {
    private static final String HBASE_QUORUM = "hbase.zookeeper.quorum";
    private static final String HBASE_ROOTDIR = "hbase.rootdir";
    private static final String HBASE_ZNODE_PARENT = "zookeeper.znode.parent";
    @Autowired
    private HbaseProperties hbaseProperties;
    @Bean
    @ConditionalOnMissingBean(HbaseTemplate.class)
    public HbaseTemplate hbaseTemplate() {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set(HBASE_QUORUM, this.hbaseProperties.getQuorum());
        configuration.set(HBASE_ROOTDIR, hbaseProperties.getRootDir());
        configuration.set(HBASE_ZNODE_PARENT, hbaseProperties.getNodeParent());
        return new HbaseTemplate(configuration);
    }
}
