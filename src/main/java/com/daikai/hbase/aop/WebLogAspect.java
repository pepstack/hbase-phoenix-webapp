package com.daikai.hbase.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * @autor kevin.dai
 * @Date 2017/12/28
 */


@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);


    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");


    @Pointcut("execution(* com.daikai.hbase.controller..*.*(..))")
    public void LogTime(){

    }




    @Before("LogTime()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null){
            HttpServletRequest httpServletRequest = attributes.getRequest();
            logger.info("当前请求地址:"+httpServletRequest.getRequestURL().toString());
            Long time = System.currentTimeMillis();
            String time1 = simpleDateFormat.format(time);
            logger.info("请求开始时间:"+time1);
        }

    }



    @AfterReturning(pointcut = "LogTime()")
    public void doAfter(){
        Long time = System.currentTimeMillis();
        String time1 = simpleDateFormat.format(time);
        logger.info("请求结束时间:"+time1);
    }


}
