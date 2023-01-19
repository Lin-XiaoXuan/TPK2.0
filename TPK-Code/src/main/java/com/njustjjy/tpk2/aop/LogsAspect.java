package com.njustjjy.tpk2.aop;

import com.alibaba.fastjson2.JSON;
import com.njustjjy.tpk2.dao.BusinessMapper;
import com.njustjjy.tpk2.dao.UserMapper;
import com.njustjjy.tpk2.entity.Business;
import com.njustjjy.tpk2.entity.Record;
import com.njustjjy.tpk2.entity.User;
import com.njustjjy.tpk2.utils.HttpUtils;
import com.njustjjy.tpk2.utils.Redis6Utils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.web.session.HttpServletSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.njustjjy.tpk2.utils.RedisConstants.*;

@Component
@Aspect
@Log4j2
public class LogsAspect {

    /**
     *控制器参数日志
     * */
    @Before("execution(* com.njustjjy.tpk2.controller.*.*(..)))")
    public void ContollerRequestInfoLog(JoinPoint joinPoint) throws Throwable{
        //Servlet容器中获取请求参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求内容
        log.info("============请求内容============");
        log.info("REQUEST_IP:" + HttpUtils.getIpAddr(request));
        log.info("REQUEST_URL:" + request.getRequestURL().toString());
        log.info("REQUEST_METHOD:" + request.getMethod());
        //获取参数列表
//        Enumeration<String> enu = request.getParameterNames();
//        while(!enu.hasMoreElements()){
//            String name = (String) enu.nextElement();
//            log.info("name" + name + ":" + "value" + ":" + request.getParameter(name));
//        }
    }

    /**
     *控制器参数日志
     * */
    @AfterReturning(returning = "ret",value = "execution(* com.njustjjy.tpk2.controller.*.*(..))")
    public void ContollerResponseInfoLog(Object ret) throws Throwable{
        //  处理完请求，返回内容
        log.info("RESPONSE : " + ret);
        log.info("============================");
    }
}
