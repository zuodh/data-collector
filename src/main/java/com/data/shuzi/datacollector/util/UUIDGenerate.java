package com.data.shuzi.datacollector.util;

import java.util.UUID;

/**
 * @author zizuo.zdh
 * @ClassName UUIDGenerate
 * @Description TODO
 * @Date 2018/6/27 9:35
 * @Version 1.0
 **/
public class UUIDGenerate {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
}
