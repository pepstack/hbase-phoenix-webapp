package com.daikai.hbase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * @autor kevin.dai
 * @Date 2017/12/27
 *
 *  https://www.cnblogs.com/wuyun-blog/p/5679073.html
 *  https://blog.csdn.net/qq_37162911/article/details/86239107
 *  https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE
 */

@Configuration
public class PhoenixDataSource {

    @Autowired
    private Environment env;


    @Bean(name = "phoenixJdbcDataSource")
    @Qualifier("phoenixJdbcDataSource")
    public DataSource dataSource(){
        DruidDataSource ds = new DruidDataSource();

        ds.setName(env.getProperty("phoenix.name"));

        ds.setUrl(env.getProperty("phoenix.url"));
        ds.setDriverClassName(env.getProperty("phoenix.driver-class-name"));
        ds.setUsername(env.getProperty("phoenix.username"));
        ds.setPassword(env.getProperty("phoenix.password"));

        ds.setMaxActive(20);

        // https://blog.csdn.net/qq_34359363/article/details/72763491
        // 配置获取连接等待超时的时间(毫秒)
        ds.setMaxWait(1000);

        ds.setInitialSize(1);

        // 连接泄漏监测
        ds.setRemoveAbandoned(true);
        ds.setRemoveAbandonedTimeout(60); // seconds
        ds.setLogAbandoned(true);

        ds.setDefaultAutoCommit(Boolean.valueOf(env.getProperty("phoenix.default-auto-commit")));
        //DEL: ds.setConnectionProperties("phoenix.schema.isNamespaceMappingEnabled=true");
        //DEL: ds.setValidationQuery("select 1 from dual");

        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        ds.setTimeBetweenEvictionRunsMillis(6000);

        // 防止过期
        ds.setValidationQuery("SELECT 1");
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);

        return ds;
    }

    @Bean(name = "phoenixJdbcTemplate")
    @Qualifier("phoenixJdbcTemplate")
    public JdbcTemplate phoenixJdbcTemplate(@Qualifier("phoenixJdbcDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
