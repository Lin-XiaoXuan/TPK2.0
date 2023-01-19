package com.njustjjy.tpk2.dao;

import com.njustjjy.tpk2.entity.Business;
import com.njustjjy.tpk2.entity.other.BusinessTable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BusinessMapper {
    /**add(增加)*/
    Integer addBusiness(Business business);
    /**update(修改)*/
    Integer updateIsDeleteByNumber(String number,Integer isDelete);

    Integer updateBusinessProgressByNumber(String numbers, Integer progress, String modifiedUser, LocalDateTime modifiedTime);

    Integer updateBusinessByNumber(Business business);
    /**delete(删除)*/

    /**query(查询)*/
    List<Business> queryLimitBusinessforUser(String recordNumber,Integer start,Integer rows);

    List<Business> queryLimitBusinessByEmpNumber(String empNumber,Integer start,Integer rows);

    List<Business> queryBusinessByEmpNumber(String empNumber);

    Integer queryCountBusinessByRecordNumber(String recordNumber);

    Integer queryCountBusinessByEmpNumber(String empNumber);

    Business queryBusinessByNumber(String number);

    List<Business> queryAllBusinessByRecordNumber(String recordNumber);

    /**
     * 左外链接
     * */
//    BusinessTable queryBusinessAndUserMixDataBybusinessNumber(String businessNumber);
}
