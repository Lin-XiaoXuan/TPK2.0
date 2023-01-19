package com.njustjjy.tpk2.dao;

import com.njustjjy.tpk2.entity.Emp;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpMapper {
    /**
     * empNumber 用户id号
     *
     * */
    Emp queryEmpByEmpNumber(String empNumber);
}
