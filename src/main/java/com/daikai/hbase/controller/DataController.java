package com.daikai.hbase.controller;

import com.daikai.hbase.service.DataService;
import com.daikai.hbase.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @autor kevin.dai
 * @Date 2017/12/26
 */

@RestController
public class DataController {

    @Autowired
    private DataService dataService;



    @GetMapping(value = "/query")
    public List<Map<String,Object>> query(){
        return dataService.query();
    }


    @GetMapping(value = "/count")
    public int count(){
        return dataService.countDept();
    }


    @GetMapping(value = "/update")
    public ResultVO update(){
        return dataService.update();
    }


    @GetMapping(value = "/add")
    public ResultVO add(){
        return dataService.add();
    }


    @GetMapping(value = "/delete")
    public ResultVO delete(){
        return dataService.delete();
    }





}
