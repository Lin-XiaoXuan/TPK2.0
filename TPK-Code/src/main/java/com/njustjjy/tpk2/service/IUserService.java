package com.njustjjy.tpk2.service;

import com.njustjjy.tpk2.entity.User;

public interface IUserService {
    /**
     * 更改手机号的绑定
     * @param phone --- 手机号
     * @param recordNumber -- 档案号
     * @param who --- 谁进行了修改操作
     * --- 该接口需要修改 ---
     * */
    void updatePhone(String phone,String recordNumber,String who);

    /**
     * 修改用户账户密码
     * @param recordNumber --- 修改的用户档案号
     * @param newPass --- 新密码
     * @param oldPass --- 旧密码
     * @param who --- 谁修改了这条信息
     *
     * */
    void updateUserPassword(String recordNumber,String newPass,String oldPass,String who);

    /**
     * 添加用户
     * @param user --- 用户对象
     * */
    void addUser(User user);

    /**
     * <p>获取User</p>
     * @param recordNumber
     * */
    User getUserByRecordNumber(String recordNumber);
}
