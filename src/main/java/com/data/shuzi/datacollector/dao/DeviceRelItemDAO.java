package com.data.shuzi.datacollector.dao;

import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zizuo.zdh
 * @ClassName DeviceRelItemDAO
 * @Description TODO
 * @Date 2018/6/27 20:42
 * @Version 1.0
 **/
@Repository
public class DeviceRelItemDAO {
    @Autowired
    public JdbcTemplate jdbcTemplate;
    public List<Map<String,Object>> getDeviceAndItem(){
        return jdbcTemplate.queryForList("select devId,itemId from tr_device_item");
    }
}
