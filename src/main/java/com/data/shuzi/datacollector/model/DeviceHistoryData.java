package com.data.shuzi.datacollector.model;

import lombok.Data;

import java.util.Date;

/**
 * @author zizuo.zdh
 * @ClassName DeviceHistoryData
 * @Description TODO
 * @Date 2018/7/4 17:19
 * @Version 1.0
 **/
@Data
public class DeviceHistoryData {
    private Long id;
    private Integer projId;
    private String projName;
    private String projAddr;
    private Double latitude;
    private Double longitude;
    private String devid;
    private String vdeviceName;
    private String dataAddress;
    private String itemid;
    private String itemname;
    private String val;
    private String specificType;
    private String readOnly;
    private String devName;
    private String quality;
    private String datatype;
    private String alias;
    private Long htime;

}
