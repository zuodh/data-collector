package com.data.shuzi.datacollector.service;

import java.util.List;

/**
 * @author zizuo.zdh
 * @ClassName ProjectService
 * @Description TODO
 * @Date 2018/6/26 15:21
 * @Version 1.0
 **/
public interface ProjectService {
    public List<String> getAllProject();
    public List<String> getAllProjectFromDB();
}
