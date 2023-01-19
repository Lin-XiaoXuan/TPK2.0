package com.njustjjy.tpk2.config;

import com.njustjjy.tpk2.interceptor.LoginInterceptor;
import com.njustjjy.tpk2.interceptor.ParameterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 *  WebMvcConfigurer ： SpringMvc提供的Mvc配置接口，实现这个接口的类为一个配置类
 *
 * IntereptConfig ： 用于给拦截器添加白名单和黑名单并将拦截器注册到项目中
 * 白名单：在经过拦截器的时候直接放行的请求
 * 黑名单：在经过拦截器的时候不能直接放行的请求
 * */
@Configuration //将这个类加载到Spring中并标注为配置类
//@Profile("pro") //只在生产环境生效
public class IntereptConfig implements WebMvcConfigurer {

    //登录拦截器
    HandlerInterceptor loginInterceptor = new LoginInterceptor();
    //请求参数拦截器
    HandlerInterceptor parameterInterceptor = new ParameterInterceptor();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录请求拦截器白名单
        List<String> white_login = new ArrayList<String>();
        //白名单添加静态资源
        //放行登录和注册的控制器
        //放行基本请求
        white_login.add("/record/login/**");
        white_login.add("/record/register/**");
        white_login.add("/emp/login/**");
        white_login.add("/btprice/**");

        //放行swagger文档
        white_login.add("/swagger-ui/**");
        white_login.add("/swagger-resources/**");
        white_login.add("/webjars/**");
        white_login.add("/v2/**");

        //参数拦截器白名单
        List<String> white_param = new ArrayList<String>();
        //添加放行名单
        white_param.add("/swagger-ui/**");
        white_param.add("/swagger-resources/**");
        white_param.add("/webjars/**");
        white_param.add("/v2/**");

        //注册参数拦截器
        registry.addInterceptor(parameterInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(white_param);

        //注册登录拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")     //表示需要拦截的url是什么,这里表示所有路径
                .excludePathPatterns(white_login);  //这个方法用于除去传入方法的请求，传入白名单集合
    }
}
