package com.data.shuzi.datacollector.model;

import lombok.Data;

import java.util.Date;

/**
 * @author zizuo.zdh
 * @ClassName DeviceRealData
 * @Description TODO
 * @Date 2018/6/27 9:31
 * @Version 1.0
 **/
@Data
public class DeviceRealData {
    private String devid;
    private String val;
    private String vdeviceName;
    private String itemid;
    private String dataAddress;
    private String datatype;
    private String itemname;
    private String alias;
    private Date htime;
    private String readOnly;
    private String devName;
    private String quality;
    private String traceId;
    private Integer projId;
}
