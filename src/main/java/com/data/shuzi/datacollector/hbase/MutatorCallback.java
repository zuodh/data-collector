package com.data.shuzi.datacollector.hbase;

import org.apache.hadoop.hbase.client.BufferedMutator;

/**
 * @author zizuo.zdh
 * @ClassName MutatorCallback
 * @Description TODO
 * @Date 2018/6/28 17:32
 * @Version 1.0
 **/
public interface MutatorCallback {

    /**
     * 使用mutator api to update put and delete
     * @param mutator
     * @throws Throwable
     */
    void doInMutator(BufferedMutator mutator) throws Throwable;
}
