package com.njustjjy.tpk2.service;

import com.njustjjy.tpk2.entity.Emp;


public interface IEmpService {
    /**
     * 查询用户信息
     * @param empNumber
     * */
    Emp login(String empNumber,Integer winNumber);
}
