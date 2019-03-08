package com.daikai.hbase.vo;

/**
 * @autor kevin.dai
 * @Date 2017/12/27
 */
public class ResultVO {


    boolean status;

    String message;

    public ResultVO(boolean status, String message) {
        this.status = status;
        this.message = message;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
