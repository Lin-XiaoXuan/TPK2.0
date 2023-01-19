package com.njustjjy.tpk2.aop;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.njustjjy.tpk2.dao.BusinessMapper;
import com.njustjjy.tpk2.dao.UserMapper;
import com.njustjjy.tpk2.entity.Business;
import com.njustjjy.tpk2.entity.Record;
import com.njustjjy.tpk2.entity.User;
import com.njustjjy.tpk2.exception.TPKInsertException;
import com.njustjjy.tpk2.exception.TPKUpdateException;
import io.lettuce.core.RedisException;
import org.aspectj.lang.JoinPoint;
import org.springframework.amqp.core.Message;
import com.njustjjy.tpk2.exception.LoginExpiredException;
import com.njustjjy.tpk2.utils.Redis6Utils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.njustjjy.tpk2.utils.RedisConstants.*;
import static com.njustjjy.tpk2.utils.RedisConstants.HOT_DATA_TIME;

@Aspect
@Log4j2
@Component
public class LoadAspect {

    @Autowired
    BusinessMapper businessMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    Redis6Utils redis6Utils;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    HttpSession session;

    

    /**
     * 在用户的登录操作返回数据后热加载必要的数据到Redis
     *
     * */
    @AfterReturning(returning = "record")
    @Pointcut("execution(* com.njustjjy.tpk2.service.IRecordService.login(..))")
    public void userHotLoad(Record record){
        //将数据存入Redis
        //1.获取recordNumber
        String recordNumber = record.getNumber();

        //热加载Business数据
        hotLoadBusiness(recordNumber);

        //热加载User的数据存入Redis
        User user = userMapper.queryUserByRecordNumber(recordNumber);
        //判断数据是否在，不存在则进行加载到redis
        if(user != null){
            redis6Utils.set(USER_KEY_PREFIX + recordNumber, JSON.toJSONString(user),SHORT_DATA_TIME);
        }
    }


    /**
     * 监听用户重新加载record的对应Business方法
     *
     *
     * */
    @Before("execution(* com.njustjjy.tpk2.service.IBusinessService.repealBusiness(..))" + "||" +
            "execution(* com.njustjjy.tpk2.service.IBusinessService.updateBusinessByNumber(..))")
    public void loadBusiness(){
        //Servlet容器中获取请求参数，并获取session对象
        Object recordNumber = session.getAttribute("recordNumber");
        //判断是否获取数据，如果session中有内容则进行重新加载对应的Business
        if(recordNumber != null){
            //如果获取数据则进行热加载
            hotLoadBusiness(recordNumber.toString());
        }
    }

//    /**
//     * 在业务分配完业务员后自动清空并存刷新redis数据
//     *
//     * */
////    @After(value="execution(* com.njustjjy.tpk2.listener.BuinListener.onMessage(..))")
//    public void afterBuinListener(JoinPoint point) throws Throwable {
//        //获取参数
//        Message message = (Message) point.getArgs()[0];
//        String messageBody = new String(message.getBody());
//        Business business = null;
//        try{
//            business = JSONObject.parseObject(messageBody,Business.class);
//        }catch (JSONException e){
//            throw new TPKUpdateException("修改Redis信息时发生异常");
//        }
//        //热更新
//        hotLoadBusiness(business.getRecordNumber());
//    }


    /**
     * 封装对应的热加载Business的方法
     *
     * */
     public void hotLoadBusiness(String recordNumber){
        //2.访问Mapper层接口查询数据
        List<Business> business = businessMapper.queryAllBusinessByRecordNumber(recordNumber);
        //3.判断数据
        if(business.size() != 0) {
            //将数据更新到Redis中
            Set<ZSetOperations.TypedTuple> sets = new HashSet<>();
            //封装数据
            for(Business bus : business){
                sets.add(ZSetOperations.TypedTuple.of(JSON.toJSONString(bus),(bus.getBid().doubleValue())));
            }
            //存储数据
            redis6Utils.zAddSet(BUSINESS_KEY_PREFIX + recordNumber,sets,HOT_DATA_TIME);
        }
    }
}
