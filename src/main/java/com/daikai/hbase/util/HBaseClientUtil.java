package com.daikai.hbase.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor kevin.dai
 * @Date 2018/10/10
 */
public class HBaseClientUtil {

    private static Logger logger  = Logger.getLogger(HBaseClientUtil.class);

    private  static  Configuration configuration;



    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort","2182");
        configuration.set("hbase.zookeeper.quorum","localhost");
    }




    /**
     * 新建表(包含删除功能)
     * @Date: 13:59 2018/10/10
     */
    public static void createTable(String tableName,String[] columnStrs){
        Connection connection = null;
        Admin admin = null;
        try {
            connection =  ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
            if(admin.tableExists(TableName.valueOf(tableName))){
                admin.disableTable(TableName.valueOf(tableName));
                admin.deleteTable(TableName.valueOf(tableName));
                logger.info("表已经存在，先删除后创建");
            }
            HTableDescriptor htable = new HTableDescriptor(TableName.valueOf(tableName));
            for(String columnStr:columnStrs){
                htable.addFamily(new HColumnDescriptor(columnStr));
            }
            admin.createTable(htable);
        }catch (IOException exception){
            exception.printStackTrace();
        }finally {
            try {
                if (admin != null) {
                    admin.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }



    /**
     * 插入数据
     * @Date: 16:02 2018/10/10
     */
    public static void insertData(String tableName){
        Connection connection = null;
        try {
            connection =  ConnectionFactory.createConnection(configuration);
            Table table  = connection.getTable(TableName.valueOf(tableName));
            Put put = new Put("00004".getBytes());
            put.addColumn("personInfo".getBytes(),"name".getBytes(),"daikai".getBytes());
            put.addColumn("personInfo".getBytes(),"age".getBytes(),"23".getBytes());
            put.addColumn("personInfo".getBytes(),"sex".getBytes(),"male".getBytes());
            table.put(put);
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }



    /**
     * 根据rowkey删除某行
     * @Date: 16:08 2018/10/10
     */
    public static void deleteRow(String tableName,String rowkey){
        Connection connection = null;
        try {
            connection =  ConnectionFactory.createConnection(configuration);
            Table table  = connection.getTable(TableName.valueOf(tableName));
            Delete delete = new Delete(rowkey.getBytes());
            table.delete(delete);
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }


    /**
     * 查询
     * @Date: 13:57 2018/1/11
     */
    public static void queryAll(String tableName){
        try {
            Connection connection = ConnectionFactory.createConnection(configuration);
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            scan.setCaching(200);
            scan.setCacheBlocks(false);
            ResultScanner scanner = table.getScanner(scan);
            for (Result result:scanner){
                String s =   Bytes.toString(result.getValue(Bytes.toBytes("personInfo"),Bytes.toBytes("name"))) ;
                System.err.println(s);

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    /**
     * 指定查询条件
     * @Date: 14:06 2018/10/11
     */
    public static void queryByCondition(String tableName){
        try {
            Connection connection = ConnectionFactory.createConnection(configuration);
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            Filter filter  = new SingleColumnValueFilter(Bytes.toBytes("personInfo"),Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL,Bytes.toBytes("qianjiang"));
            scan.setFilter(filter);
            ResultScanner scanner = table.getScanner(scan);
            for (Result result:scanner){
                String s =   Bytes.toString(result.getValue(Bytes.toBytes("personInfo"),Bytes.toBytes("name"))) ;
                System.err.println(s);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * 结果输出
     * @Date: 10:10 2018/10/10
     */
    private static Map<String, Object> result2Map(Result result) {
        Map<String, Object> ret = new HashMap<String, Object>();
        if (result != null && result.listCells() != null) {
            for (Cell cell : result.listCells()) {
                String key = Bytes.toString(CellUtil.cloneQualifier(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(key + " => " + value);
                ret.put(key, value);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Long time1 = System.currentTimeMillis();
       // String[] strs = {"personInfo"};
       // HBaseClientUtil.createTable("daikai_test",strs);
       //HBaseClientUtil.deleteRow("daikai_test","00001");
        HBaseClientUtil.queryAll("daikai_test");
       // HBaseClientUtil.insertData("daikai_test");
        Long time2 = System.currentTimeMillis();
        logger.info("==========执行结束,耗时:"+(time2-time1)/1000+"s============");
    }


}
