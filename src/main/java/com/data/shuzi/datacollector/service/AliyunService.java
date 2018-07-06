package com.data.shuzi.datacollector.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zizuo.zdh
 * @ClassName AliyunService
 * @Description TODO
 * @Date 2018/6/25 17:19
 * @Version 1.0
 **/
public interface AliyunService {
    /**
    *@author zizuo.zdh
    *@Description TODO
    *@Date 2018/6/26 14:24
    *@Param []
    *@return com.alibaba.fastjson.JSONObject
    *
    **/
     JSONObject getAliyunToken();
     /**
     *@author zizuo.zdh
     *@Description TODO
     *@Date 2018/6/26 11:47
     *@Param []
     *@return com.alibaba.fastjson.JSONObject
     *
     **/
     JSONObject getProjectAlarm();
     /**
     *@author zizuo.zdh
     *@Description 获取特定项目的实时数据
     *@Date 2018/6/26 16:37
     *@Param [projectId]
     *@return com.alibaba.fastjson.JSONObject
     *
     **/
     JSONObject getProjectCurrentItemData(String projectId);
     /**
     *@author zizuo.zdh
     *@Description 获取设备历史记录
     *@Date 2018/6/26 18:00
     *@Param [deviceId, itemId]
     *@return com.alibaba.fastjson.JSONObject
     *
     **/
     JSONObject getHistoryData(String deviceId,String itemId,Long endTime);
}
