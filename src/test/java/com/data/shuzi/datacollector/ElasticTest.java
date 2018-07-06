package com.data.shuzi.datacollector;

import com.data.shuzi.datacollector.util.UUIDGenerate;
import org.elasticsearch.index.query.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.elasticsearch.index.query.SpanQueryBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * @author zizuo.zdh
 * @ClassName ElasticTest
 * @Description TODO
 * @Date 2018/7/3 14:08
 * @Version 1.0
 **/
public class ElasticTest extends BaseTest {
    private static final Logger logger=LoggerFactory.getLogger(ElasticTest.class);
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Before
    public void init(){
        if (elasticsearchTemplate.indexExists("mytest")) {
            elasticsearchTemplate.deleteIndex("mytest");
        }
        elasticsearchTemplate.createIndex("mytest");

        elasticsearchTemplate.putMapping(DeviceRealESInfo.class);

    }
    @Test
    public void test(){
        List<DeviceRealESInfo> taskInfoList=new ArrayList<>();
        for(int i=0;i<1000;i++) {
            DeviceRealESInfo deviceRealESInfo = new DeviceRealESInfo();
            deviceRealESInfo.setDevid("100"+i);
            deviceRealESInfo.setHtime(new Date());
            deviceRealESInfo.setDevName("dev-name");
            deviceRealESInfo.setVal(String.valueOf(i));
            deviceRealESInfo.setDataAddress("无锡"+i);
            deviceRealESInfo.setDatatype("String");
            deviceRealESInfo.setReadOnly("true");
            deviceRealESInfo.setProjId(i);
            deviceRealESInfo.setQuality("true");
            deviceRealESInfo.setTraceId(UUIDGenerate.getUUID());
            taskInfoList.add(deviceRealESInfo);
        }
        insertOrUpdateTaskInfo(taskInfoList);
    }
    @Test
    public void test1(){
        Pageable pageable= PageRequest.of(1,10);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery("无锡")).withPageable(pageable).build();
       List<DeviceRealESInfo> result=elasticsearchTemplate.queryForList(searchQuery, DeviceRealESInfo.class);
        result.stream().forEach(e->{
            System.out.println(e.getDevid()+"-"+e.getProjId());
        });
    }
    public boolean update(List<DeviceRealESInfo> taskInfoList) {
        List<IndexQuery> queries = new ArrayList<IndexQuery>();
        for (DeviceRealESInfo realESInfo : taskInfoList) {
            IndexQuery indexQuery = new IndexQueryBuilder().withId(realESInfo.getDevid()).withObject(realESInfo).build();
            queries.add(indexQuery);
        }
        elasticsearchTemplate.bulkIndex(queries);
        return true;
    }

    public boolean insertOrUpdateTaskInfo(List<DeviceRealESInfo> taskInfoList) {
        List<IndexQuery> queries = new ArrayList<>();
        for (DeviceRealESInfo taskInfo : taskInfoList) {
            IndexQuery indexQuery = new IndexQueryBuilder().withId(taskInfo.getDevid()).withObject(taskInfo).build();
            queries.add(indexQuery);
        }
        elasticsearchTemplate.bulkIndex(queries);
        return true;
    }


    public boolean insertOrUpdateTaskInfo(DeviceRealESInfo taskInfo) {
        try {
            IndexQuery indexQuery = new IndexQueryBuilder().withId(taskInfo.getDevid()).withObject(taskInfo).build();
            elasticsearchTemplate.index(indexQuery);
            return true;
        } catch (Exception e) {
            logger.error("insert or update task info error.", e);
            return false;
        }
    }
}
