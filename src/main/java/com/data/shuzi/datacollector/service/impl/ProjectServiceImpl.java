package com.data.shuzi.datacollector.service.impl;

import com.data.shuzi.datacollector.cache.AbstractBaseCache;
import com.data.shuzi.datacollector.dao.ProjectDAO;
import com.data.shuzi.datacollector.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zizuo.zdh
 * @ClassName ProjectServiceImpl
 * @Description TODO
 * @Date 2018/6/26 15:22
 * @Version 1.0
 **/
@Service
public class ProjectServiceImpl extends AbstractBaseCache implements ProjectService {
    @Autowired
    ProjectDAO projectDAO;
    @Override
    public List<String> getAllProject() {
        List<String> obj=null;
        try {
            obj= (List<String>)super.getValue("project");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    @Override
    protected Object getValueWhenExpired(Object key) throws Exception {
        return projectDAO.getAllProject();
    }
    @Override
    public List<String> getAllProjectFromDB() {
        return projectDAO.getAllProject();
    }

}
