package com.daikai.hbase.service.impl;

import com.daikai.hbase.service.DataService;
import com.daikai.hbase.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.sql.RowSet;
import java.util.List;
import java.util.Map;

/**
 * @autor
 */

@Service
public class DataServiceImpl  implements DataService{

    @Autowired
    @Qualifier("phoenixJdbcTemplate")
    JdbcTemplate  jdbcTemplate;

    /*
        CREATE TABLE IF NOT EXISTS ORG_DEPT_NC (ID VARCHAR(30) NOT NULL, NAME VARCHAR(30), CONSTRAINT PK PRIMARY KEY (ID));
    */

    public ResultVO add() {
        return jdbcTemplate.update("upsert into ORG_DEPT_NC (ID, NAME ) VALUES ('zhang','软件开发')") == 1?
                new ResultVO(true,"插入成功！"):new ResultVO(false,"插入失败！");
    }

    public int countDept() {
        return jdbcTemplate.queryForObject("select count(1) from ORG_DEPT_NC ",Integer.class);
    }

    public ResultVO delete() {
       return jdbcTemplate.update("delete from ORG_DEPT_NC WHERE ID = 'zhangliang' ") == 1?
                new ResultVO(true,"删除成功！"):new ResultVO(false,"删除失败！");
    }

    public ResultVO update() {
        return jdbcTemplate.update("upsert into ORG_DEPT_NC (ID,NAME ) VALUES ('wang','测试组')") == 1?
                new ResultVO(true,"更新成功！"):new ResultVO(false,"更新失败！");
    }

    public List<Map<String, Object>> query() {
        return jdbcTemplate.queryForList("select * from ORG_DEPT_NC ");
    }
}
