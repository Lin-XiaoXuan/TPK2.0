package com.njustjjy.tpk2.dao;


import com.njustjjy.tpk2.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    /**add(增加)*/
    Integer addUserData(User user);
    /**update(修改)*/
    Integer updateUserByRecordNumber(User user);
    /**delete(删除)*/
//    Integer deleteUserByRecordNumber(String RecordNumber);
    /**query(查询)*/
    User queryUserByRecordNumber(String recordNumber);

    User queryUserByPhone(String phone);
}
