package com.njustjjy.tpk2.service;

import com.njustjjy.tpk2.entity.Record;
import org.springframework.stereotype.Service;

public interface IRecordService {
    /**
     *<p>登录接口</p>
     *
     * @param idCard -- String 用户身份证
     * */
    Record login(String idCard);

    /**
     * <p>注册接口</p>
     *
     * @param idCard 注册的身份证
     * @param password 密码
     * @param who 谁注册了这条信息
     * @param recordLevel 档案等级 1个人档案 2团体档案
     * @param remark 备注信息
     * */
    void register(String idCard,String password,String who,Integer recordLevel,String remark);
}
