package com.data.shuzi.datacollector.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zizuo.zdh
 * @ClassName DateUtil
 * @Description TODO
 * @Date 2018/6/27 10:03
 * @Version 1.0
 **/
public class DateUtil {
    public static Date formatDate(String dataStr){
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        try {
            return df.parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
