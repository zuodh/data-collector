package com.data.shuzi.datacollector.hbase;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zizuo.zdh
 * @ClassName HbaseProperties
 * @Description TODO
 * @Date 2018/6/28 17:23
 * @Version 1.0
 **/
@Data
@ConfigurationProperties("zookeeper")
public class HbaseProperties {
    private String quorum;

    private String rootDir;

    private String nodeParent;
}
