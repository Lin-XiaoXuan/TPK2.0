package com.njustjjy.tpk2.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.njustjjy.tpk2.utils.JsonResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/** 定义一个接口
 *
 * HandlerInterceptor ：SpringMVC中拦截器接口类
 * --实现这个接口的都是被认为是一个拦截器，用于拦截一些请求
 * */
@Log4j2
public class LoginInterceptor implements HandlerInterceptor {
    /**在调用所有处理请求方法之前被自动调用
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器（url+Contoller：映射）
     * @return boolean
     *         为true ：表示放行请求
     *         为false；表示不放行
     * @throws
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       /**
        * 登录拦截原理
        *   1、检测全局session对象中是否有UID，如果有放行，没有则重构定向到主页面
        * */
       //获取全局的session对象
       HttpSession session = request.getSession();
       //获取uid对应的对象，判断获取的对象是否为空，如果为空则重定向并返回false，如果不为空返回true
       Object rid = session.getAttribute("rid");
       Object eid = session.getAttribute("eid");

       //设置返回数据格式
       response.setCharacterEncoding("UTF-8");
       response.setContentType("application/json; charset=utf-8");

       //判断session中数据，两个数据都为null时表示
       if(rid == null && eid == null){
           PrintWriter out = null;
           JsonResult json = new JsonResult<>();
           json.setState(5002);
           json.setMessage("请先登录！！！");

           out = response.getWriter();
           out.append(JSONObject.toJSONString(json));

           //拦截器拦截
           return false;
       }

       //请求合法继续放行
       return true;
    }
}
