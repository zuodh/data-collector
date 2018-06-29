package com.data.shuzi.datacollector.hbase;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zizuo.zdh
 * @ClassName HbaseTemplate
 * @Description TODO
 * @Date 2018/6/28 17:27
 * @Version 1.0
 **/
public class HbaseTemplate implements HbaseApi{
    private static final Logger LOGGER = LoggerFactory.getLogger(HbaseTemplate.class);

    private Configuration configuration;

    private volatile Connection connection;

    public HbaseTemplate(Configuration configuration) {
        this.setConfiguration(configuration);
        Assert.notNull(configuration, " a valid configuration is required");
    }

    @Override
    public <T> T execute(String tableName, TableCallback<T> action) {
        Assert.notNull(action, "Callback object must not be null");
        Assert.notNull(tableName, "No table specified");

        StopWatch sw = new StopWatch();
        sw.start();
        Table table = null;
        try {
            table = this.getConnection().getTable(TableName.valueOf(tableName));
            return action.doInTable(table);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            if (null != table) {
                try {
                    table.close();
                    sw.stop();
                } catch (IOException e) {
                    LOGGER.error("hbase资源释放失败");
                }
            }
        }
        return null;
    }

    @Override
    public <T> List<T> find(String tableName, String family, final RowMapper<T> action) {
        Scan scan = new Scan();
        scan.setCaching(5000);
        scan.addFamily(Bytes.toBytes(family));
        return this.find(tableName, scan, action);
    }

    @Override
    public <T> List<T> find(String tableName, String family, String qualifier, final RowMapper<T> action) {
        Scan scan = new Scan();
        scan.setCaching(5000);
        scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
        return this.find(tableName, scan, action);
    }

    @Override
    public <T> List<T> find(String tableName, final Scan scan, final RowMapper<T> action) {
        return this.execute(tableName, table -> {
            int caching = scan.getCaching();
            // 如果caching未设置(默认是1)，将默认配置成5000
            if (caching == 1) {
                scan.setCaching(5000);
            }
            ResultScanner scanner = table.getScanner(scan);
            try {
                List<T> rs = new ArrayList<T>();
                int rowNum = 0;
                for (Result result : scanner) {
                    rs.add(action.mapRow(result, rowNum++));
                }
                return rs;
            } finally {
                scanner.close();
            }
        });
    }

    @Override
    public <T> T get(String tableName, String rowName, final RowMapper<T> mapper) {
        return this.get(tableName, rowName, null, null, mapper);
    }

    @Override
    public <T> T get(String tableName, String rowName, String familyName, final RowMapper<T> mapper) {
        return this.get(tableName, rowName, familyName, null, mapper);
    }

    @Override
    public <T> T get(String tableName, final String rowName, final String familyName, final String qualifier, final RowMapper<T> mapper) {
        return this.execute(tableName, table -> {
            Get get = new Get(Bytes.toBytes(rowName));
            if (StringUtils.isNotBlank(familyName)) {
                byte[] family = Bytes.toBytes(familyName);
                if (StringUtils.isNotBlank(qualifier)) {
                    get.addColumn(family, Bytes.toBytes(qualifier));
                }
                else {
                    get.addFamily(family);
                }
            }
            Result result = table.get(get);
            return mapper.mapRow(result, 0);
        });
    }

    @Override
    public void execute(String tableName, MutatorCallback action) {
        Assert.notNull(action, "Callback object must not be null");
        Assert.notNull(tableName, "No table specified");

        StopWatch sw = new StopWatch();
        sw.start();
        BufferedMutator mutator = null;
        try {
            BufferedMutatorParams mutatorParams = new BufferedMutatorParams(TableName.valueOf(tableName));
            mutator = this.getConnection().getBufferedMutator(mutatorParams.writeBufferSize(3 * 1024 * 1024));
            action.doInMutator(mutator);
        } catch (Throwable throwable) {
            sw.stop();
            throwable.printStackTrace();
        } finally {
            if (null != mutator) {
                try {
                    mutator.flush();
                    mutator.close();
                    sw.stop();
                } catch (IOException e) {
                    LOGGER.error("hbase mutator资源释放失败");
                }
            }
        }
    }

    @Override
    public void saveOrUpdate(String tableName, final Mutation mutation) {
        this.execute(tableName, mutator-> {
             mutator.mutate(mutation);
        });
    }

    @Override
    public void saveOrUpdates(String tableName, final List<Mutation> mutations) {
        this.execute(tableName, mutator-> {
                mutator.mutate(mutations);
        });
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        if (null == this.connection) {
            synchronized (this) {
                if (null == this.connection) {
                    try {
                        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(20, 50, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
                        poolExecutor.prestartCoreThread();
                        this.connection = ConnectionFactory.createConnection(configuration, poolExecutor);
                    } catch (IOException e) {
                        LOGGER.error("hbase connection资源池创建失败");
                    }
                }
            }
        }
        return this.connection;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
