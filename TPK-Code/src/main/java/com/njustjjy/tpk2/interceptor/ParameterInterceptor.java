package com.njustjjy.tpk2.interceptor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.njustjjy.tpk2.exception.IllegalDataException;
import com.njustjjy.tpk2.utils.JsonResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

@Log4j2
public class ParameterInterceptor implements HandlerInterceptor {
    @Override
    //在处理请求之前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取参数并进行转换
        Map attribute = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

         //获取所有的key
        Set<String> keys = attribute.keySet();

        //设置返回数据格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        //校验所有的参数
        for(String key : keys){
            //如果返回true则拦截器开启拦截
            if(paramVerify(key,attribute.get(key).toString(),response)) return false;
        }

        //如果没有拦截则开启拦截器放行
        return true;
    }

    //参数校验器
    public boolean paramVerify(String name,String value,HttpServletResponse response) throws IOException {
        //创建开关
        boolean flag = false;

        //存储value长度
        Integer len = value.length();

        //判断参数如果参数有不合法开关开启
        if(name.equals("idCard")){
            //如果value为bull则跳过验证，后续要用到idcard时需要在Contoller层验证
//            System.out.println(value);
//            if(value.equals("null")){
//                flag = false;
//            }
//            else{
                flag =  len != 18 ? true : false;
//            }
        }
        else if(name.equals("businessNumber")){
            flag = len != 15 ? true : false;
            log.info("businessNumber--"+flag);
            log.info("businessNumber--" + value);
        }
        else if(name.equals("empNumber")){
            flag = len != 8 ? true : false;
        }
        else if(name.equals("password") || name.equals("newPass") || name.equals("oldPass") ){
            flag = (len < 6 || len > 12) ? true : false;
        }
        else if(name.equals("who")){
            flag = (len > 30) ? true : false;
            log.info("who -->" + flag);
        }
        else if(name.equals("parent") || name.equals("codes") || name.equals("businessType")){
            flag = len <= 0 || len > 5 ? true : false;
        }
        else if(name.equals("remark")){
            flag = (len > 225) ? true : false;
        }
        else if(name.equals("winNumber")){
            try{
                //窗口编号不能小于0
                Integer IntValue = Integer.parseInt(value);
                flag = (IntValue < 0) ? true : false;
            }catch(NumberFormatException e){
                flag = true;
            }
        }
        else if(name.equals("recordLevel") || name.equals("way") || name.equals("condition_1")){
            try{
                Integer IntValue = Integer.parseInt(value);
                flag = (IntValue != 1 && IntValue != 2) ? true : false;
            }catch (NumberFormatException e){
                flag = true;
            }
        }
        else if(name.equals("businessProgress")){
            try{
                Integer IntValue = Integer.parseInt(value);
                flag = (IntValue < 0 || IntValue > 4) ? true : false;
            }catch (NumberFormatException e){
                flag = true;
            }

            log.info("businessProgress -->" + flag);
        }

        //开关开启后返回参数不合法异常
        if(flag){
            log.info("调用了拦截器，请求被拦截了");
            PrintWriter out = null;
            JsonResult json = new JsonResult<>();
            json.setState(5001);
            json.setMessage("不合法的数据");

            out = response.getWriter();
            out.append(JSONObject.toJSONString(json));
            return true;
        }

        //如果开关没有开启则返回false
        return false;
    }
}
