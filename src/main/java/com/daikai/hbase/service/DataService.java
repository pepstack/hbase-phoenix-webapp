package com.daikai.hbase.service;

import com.daikai.hbase.vo.ResultVO;

import java.util.List;
import java.util.Map;

/**
 * @autor kevin.dai
 * @Date 2017/12/27
 */
public interface DataService {

    ResultVO add();

    ResultVO delete();

    ResultVO update();


    List<Map<String, Object>> query();


    int countDept();

}
