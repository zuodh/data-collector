package com.data.shuzi.datacollector.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zizuo.zdh
 * @ClassName ProjectDAO
 * @Description TODO
 * @Date 2018/6/26 15:22
 * @Version 1.0
 **/
@Repository
public class ProjectDAO {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    public List<String> getAllProject() {
        String sql="select id from project";
        return jdbcTemplate.queryForList(sql,String.class);
    }
}
