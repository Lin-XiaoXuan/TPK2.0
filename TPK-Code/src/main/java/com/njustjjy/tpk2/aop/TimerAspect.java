package com.njustjjy.tpk2.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2
public class TimerAspect {
    /**
     * 用于计算业务层耗时 ms
     *
     * ProceedingJoinPoint :表示连接点，目标方法的对象
     * Around: 环绕通知
     * */
    @Around("execution(* com.njustjjy.tpk2.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //获取开始时间戳
        long start = System.currentTimeMillis();
        //调用目标方法
        Object result = pjp.proceed();
        //获取开结束时间戳
        long end = System.currentTimeMillis();
        log.info("业务耗时：" + (end - start) + "ms");
        return result;
    }
}
