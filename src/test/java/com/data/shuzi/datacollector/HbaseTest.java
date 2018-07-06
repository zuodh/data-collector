package com.data.shuzi.datacollector;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;

import java.io.IOException;
import java.util.List;

/**
 * @author zizuo.zdh
 * @ClassName HbaseTest
 * @Description TODO
 * @Date 2018/6/28 18:00
 * @Version 1.0
 **/
public class HbaseTest extends BaseTest {
    @Autowired
    HbaseTemplate hbaseTemplate;

    @Test
    public void test() {
        //hbaseTemplate
//        hbaseTemplate.put("t1", "r4", "f1", " ", "java".getBytes());

        hbaseTemplate.get("t1", "r1","f1:", new RowMapper<Object>() {

            @Override
            public Object mapRow(Result result, int i) throws Exception {
                List<Cell> cells=result.listCells();
                for(Cell cell:cells){
                    // 获取值
                    String value = Bytes.toString(cell.getValueArray(),
                            cell.getValueOffset(), cell.getValueLength());
                    // 获取列簇
                    String family = Bytes.toString(cell.getFamilyArray(),
                            cell.getFamilyOffset(), cell.getFamilyLength());
                    // 获取列
                    String quali = Bytes.toString(cell.getQualifierArray(),
                            cell.getQualifierOffset());
                    System.out.println("value:"+value+"family:"+family+"quali:"+quali);

                }
                return null;
            }
        });
        //hbaseTemplate.put("t1","r1","f1","1000","java".getBytes());
    }

    @Test
    public void test1() throws IOException {
        Configuration hbaseConfig = HBaseConfiguration.create();
       // String zkQuorum = "192.168.43.223";
        hbaseConfig.addResource("classpath:hbase-site.xml");
        //hbaseConfig.set(HConstants.ZOOKEEPER_QUORUM, zkQuorum);
        Connection connection = ConnectionFactory.createConnection(hbaseConfig);
        Table table = connection.getTable(TableName.valueOf("t1"));
//        Put put=new Put("r3".getBytes());
//        put.addColumn("f1".getBytes(),"".getBytes(),"php".getBytes());
//        table.put(put);
        ResultScanner scanner = table.getScanner(new Scan());
        for(Result res: scanner){
            String clom=Bytes.toString(res.getRow());
            System.out.println(clom);
         //   System.out.println(Bytes.toString(res.getValue("f1".getBytes(),"".getBytes())));
           for(Cell cell:res.listCells()){
               String kv=Bytes.toString(cell.getFamilyArray());
              System.out.println(kv);
             //  System.out.println(Bytes.toString(cell.getQualifierArray()));
           }
        }
    }

}
