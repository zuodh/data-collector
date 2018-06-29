package com.data.shuzi.datacollector.dao;

import com.data.shuzi.datacollector.model.DeviceRealData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zizuo.zdh
 * @ClassName DeviceRealDAo
 * @Description TODO
 * @Date 2018/6/27 10:15
 * @Version 1.0
 **/
@Repository
@DependsOn("springUtils")
public class DeviceRealDAO {
    private Logger logger=LoggerFactory.getLogger(DeviceRealDAO.class);
    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Async("my-task-pool")
    public void saveDeviceRealData(List<Object[]> paramList){
        logger.info("saveDeviceRealData batch executor start"+paramList.size());
        String sql="insert into tb_device_real_data(devid,val,vdeviceName,itemid,dataAddress,datatype,itemname,alias,htime,readOnly,devName,quality,traceId,projId) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, paramList);
    }

    public List<Map<String,Object>> queryProjectAndItem() {
        return jdbcTemplate.queryForList("select devid,itemId from tb_device_real_data");
    }
    @Async("my-task-pool")
    public void saveDeviceItemData(List<Object[]> params) {
        logger.info("saveDeviceItemData batch executor start"+params.size());
        String sql="insert into tb_device_item(devId,itemId,val,htime,createtime) values(?,?,?,from_unixtime(?),now())";
        jdbcTemplate.batchUpdate(sql, params);
    }

    public List<String> getProjListByDeviceReal() {
        String sql="select DISTINCT projId from tb_device_real_data";
        return jdbcTemplate.queryForList(sql,String.class);
    }
}
