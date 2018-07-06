package com.data.shuzi.datacollector;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author zizuo.zdh
 * @ClassName DeviceRealESInfo
 * @Description TODO
 * @Date 2018/7/3 14:45
 * @Version 1.0
 **/
@Data
@Document(indexName = "mytest",type="device")
public class DeviceRealESInfo {
    @Id
    @Field(type=FieldType.Keyword)
    private String devid;
    @Field(type=FieldType.Keyword)
    private String val;
    @Field(type=FieldType.Object)
    private String vdeviceName;
//    @Field(type=FieldType.Keyword)
//    private String itemid;
    @Field(type=FieldType.Keyword)
    private String dataAddress;
    @Field(type=FieldType.Keyword)
    private String datatype;
//    @Field(type=FieldType.Keyword)
//    private String itemname;
//    @Field(type=FieldType.Text)
//    private String alias;
    @Field(type=FieldType.Date)
    private Date htime;
    @Field(type=FieldType.Keyword)
    private String readOnly;
    @Field(type=FieldType.Keyword)
    private String devName;
    @Field(type=FieldType.Keyword)
    private String quality;
    @Field(type=FieldType.Keyword)
    private String traceId;
    @Field(type=FieldType.Integer)
    private Integer projId;
}
