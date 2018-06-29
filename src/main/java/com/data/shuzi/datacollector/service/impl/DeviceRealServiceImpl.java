package com.data.shuzi.datacollector.service.impl;

import com.data.shuzi.datacollector.cache.AbstractBaseCache;
import com.data.shuzi.datacollector.dao.DeviceRealDAO;
import com.data.shuzi.datacollector.service.DeviceRealService;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zizuo.zdh
 * @ClassName DeviceRealServiceImpl
 * @Description TODO
 * @Date 2018/6/27 17:10
 * @Version 1.0
 **/
@Service
public class DeviceRealServiceImpl extends AbstractBaseCache implements DeviceRealService {
    @Autowired
    DeviceRealDAO deviceRealDAO;
    @Override
    public List<String> getProjListByDeviceReal() {
        try {
            return  (List<String>)super.getValue("prodIds");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected Object getValueWhenExpired(Object key) throws Exception {
        return deviceRealDAO.getProjListByDeviceReal();
    }
}
