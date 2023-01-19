package com.njustjjy.tpk2.service.impl;


import com.alibaba.fastjson2.JSON;
import com.njustjjy.tpk2.dao.EmpMapper;
import com.njustjjy.tpk2.dao.RecordMapper;
import com.njustjjy.tpk2.entity.Emp;
import com.njustjjy.tpk2.exception.EmpNotFindException;
import com.njustjjy.tpk2.exception.NoAuthorityException;
import com.njustjjy.tpk2.service.IEmpService;
import com.njustjjy.tpk2.utils.Redis6Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.njustjjy.tpk2.utils.RedisConstants.*;

@Service
@Log4j2
public class EmpServiceImpl implements IEmpService {

    @Autowired
    Redis6Utils redis6Utils;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    EmpMapper empMapper;
    @Autowired
    RecordMapper recordMapper;

    @Value("${TPK.authority.EmpGroup}")
    Integer authority;

    @Override
    public Emp login(String empNumber,Integer winNumber) {
        //创建结果集
        Emp emp = new Emp();

        //在Redis中取用数据
        Object redisResult = redis6Utils.getHashEntries(EMP_TABLE,EMP_KEY_PREFIX + empNumber);
        if(redisResult != null){
            Emp redisEmp = JSON.parseObject(redisResult.toString(),Emp.class);
            //判断权限
            if(redisEmp.getGid() != authority){
                throw new NoAuthorityException("该用户没有权限");
            }

            //封装对应数据
            emp.setEid(redisEmp.getEid());
            emp.setNumber(redisEmp.getNumber());
            emp.setName(redisEmp.getName());
            emp.setGid(redisEmp.getGid());
            emp.setGender(redisEmp.getGid());

            //返回
            return emp;
        }

        //如果不存在则在db中查询
        Emp dbResult = empMapper.queryEmpByEmpNumber(empNumber);
        //判断信息是否存在
        if(dbResult == null){
            throw new EmpNotFindException("对应数据未查询到");
        }

        //判断权限
        if(dbResult.getGid() != authority){
            throw new NoAuthorityException("该用户没有权限");
        }

        //封装对应数据
        emp.setEid(dbResult.getEid());
        emp.setNumber(dbResult.getNumber());
        emp.setName(dbResult.getName());
        emp.setGid(dbResult.getGid());

        //存入Redis
        String json = JSON.toJSONString(emp);
        //创建hash表
        Map<Object,Object> maps = new HashMap<Object,Object>();
        maps.put(EMP_KEY_PREFIX + emp.getNumber(),json);

        //存储数据到redis
        boolean logFlag = redis6Utils.putAll(EMP_TABLE,maps);
        if(!logFlag){
            log.warn("redis存储失败");
        }

        return emp;
    }
}
