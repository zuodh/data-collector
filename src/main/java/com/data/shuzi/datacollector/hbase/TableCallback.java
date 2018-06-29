package com.data.shuzi.datacollector.hbase;

import org.apache.hadoop.hbase.client.Table;

/**
 * @author zizuo.zdh
 * @ClassName TableCallback
 * @Description TODO
 * @Date 2018/6/28 17:32
 * @Version 1.0
 **/
public interface TableCallback<T> {
    T doInTable(Table table) throws Throwable;
}
