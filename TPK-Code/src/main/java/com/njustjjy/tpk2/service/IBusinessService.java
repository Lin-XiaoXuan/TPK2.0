package com.njustjjy.tpk2.service;

import com.njustjjy.tpk2.entity.Business;
import com.njustjjy.tpk2.entity.other.BusinessTable;
import com.njustjjy.tpk2.utils.PageBean;

import java.io.IOException;
import java.util.List;

public interface IBusinessService {
    /**
     * <p>用户业务申请接口</p>
     *
     *
     * @param recordNumber
     * @param businessType
     * @param remark
     * @param money
     * @param way
     * @param who
     * */
    void userAddBusiness(String recordNumber,String businessType, String phone,String remark, Double money,Integer way,String who);

    /**
     * <p>业务申请接口</p>
     *
     * @param evidence -- String evidence -- 搜索凭据，
     *                 if condition == 1 : 使用recordNumber直接查询
     *                 elif condition == 2 : 使用idCard查询
     * @param businessType -- Integer 业务类型
     * @param way -- 业务发起途径
     * @param who -- 谁发起了这个业务
     * */
    @Deprecated
    void addBusiness(String evidence,Integer businessType,Integer way,String remark,String who,Integer condition);

    /**
     * <p>查询与自己相关的业务</p>
     * @param recordNumber --- String 所查询的recordNumber
     * @param condition --- Integer 情况 1 --- 查询未撤销的业务  2：查询已经删除的业务
     * @param currentPage --- Integer 当前页
     * @param rows --- Integer 每页条数
     *
     * */
    @Deprecated
    PageBean<Business> queryLimitBusiness(String recordNumber, Integer condition, Integer currentPage, Integer rows);

    /**
     * <p>用户查询数据接口</p>
     * @param recordNumber --- String 所查询的recordNumber
     * @param currentPage --- Integer 当前页
     * @param rows --- Integer 每页条数
     *
     * @return PageBean
     * */
    PageBean<Business> queryLimitBusinessByRecordNumberforUser(String recordNumber,Integer currentPage,Integer rows);

    /**
     * <p>根据业务员ID分页查询Business的业务</p>
     * @param empNumber --- String 业务员的编号
     * 该接口只查询 待进行和进行中的业务
     *
     * @return PageBean
     * */
    PageBean<Business> queryLimitBusinessByEmpNumber(String empNumber,Integer currentPage, Integer rows);

    /**
     * <p>根据业务员ID分页查询Business的业务</p>
     * @param empNumber --- String 业务员的编号
     * 该接口只查询 待进行和进行中的业务
     *
     * @return PageBean
     * */
    List<Business> queryBusinessByEmpNumber(String empNumber);

    /**
     * 用户撤销业务逻辑
     * @param businessNumber --- String  业务号
     *
     * */
    void repealBusiness(String businessNumber,String who);

    /**
     * 用户修改业务接口
     * @param business --- Business 业务信息
     * @param who --- String 是谁修改了这条信息
     * */
    void updateBusinessByNumber(Business business,String who);


    /**
     * 修改业务进度
     * @param businessProgress --- Integer 业务进度
     * @param who --- String 是谁修改了这条信息
     * */
    void updateBusinessProgree(String businessNumber,Integer businessProgress,String who);

    /**
     * 查询Business
     * @param businessNumber --- Integer 业务号
     * */
    Business queryBusinessByNumber(String businessNumber);

    /**
     * 用于查询并返回BusinessTable数据模型
     *
     *
     * */
    BusinessTable queryBusinessTableByBusinessNumber(String businesNumber);
}
