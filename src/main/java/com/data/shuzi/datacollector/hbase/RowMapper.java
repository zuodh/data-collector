package com.data.shuzi.datacollector.hbase;

import org.apache.hadoop.hbase.client.Result;

/**
 * @author zizuo.zdh
 * @ClassName RowMapper
 * @Description TODO
 * @Date 2018/6/28 17:32
 * @Version 1.0
 **/
public interface RowMapper<T> {
    T mapRow(Result result, int rowNum) throws Exception;
}
