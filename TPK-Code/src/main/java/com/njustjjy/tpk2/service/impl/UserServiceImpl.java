package com.njustjjy.tpk2.service.impl;

import com.alibaba.fastjson2.JSON;
import com.njustjjy.tpk2.dao.RecordMapper;
import com.njustjjy.tpk2.dao.UserMapper;
import com.njustjjy.tpk2.entity.Record;
import com.njustjjy.tpk2.entity.User;
import com.njustjjy.tpk2.exception.*;
import com.njustjjy.tpk2.service.IUserService;
import com.njustjjy.tpk2.utils.MD5Utils;
import com.njustjjy.tpk2.utils.Redis6Utils;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.njustjjy.tpk2.utils.RedisConstants.SHORT_DATA_TIME;
import static com.njustjjy.tpk2.utils.RedisConstants.USER_KEY_PREFIX;

@Service
@Log4j2
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    Redis6Utils redis6Utils;

    @Override
    public void updatePhone(String phone, String recordNumber,String who) {
        //判断用户是否存在
        User dbResult = userMapper.queryUserByRecordNumber(recordNumber);
        if(dbResult == null || dbResult.getIsDelete() == 1){
            throw new UserNotFindException("用户信息未找到");
        }

        //判断手机号是否已经绑定
        if(dbResult.getPhone() != null){
            throw new UserBindingPhoneException("用户已经绑定了手机号");
        }

        //封装数据
        dbResult.setPhone(phone);
        dbResult.setModifiedUser(who);
        dbResult.setModifiedTime(LocalDateTime.now());

        //修改手机号
        Integer dbResult2 = userMapper.updateUserByRecordNumber(dbResult);
        //判断数据库是否存储成功
        if(dbResult2 != 1){
            throw new TPKUpdateException("修改时发生异常");
        }
        //删除redis中数据
        String redisKeys = USER_KEY_PREFIX + recordNumber;
        redisTemplate.delete(redisKeys);
        //将数据更新到redis中
        redis6Utils.set(redisKeys, JSON.toJSONString(dbResult2),SHORT_DATA_TIME);
    }

    @Override //修改耦合性过高
    public void updateUserPassword(String recordNumber, String newPass, String oldPass,String who) {
        //获取数据
        User dbResult = userMapper.queryUserByRecordNumber(recordNumber);
        //判断结果是否存在
        if(dbResult == null){
            throw new UserNotFindException("用户未查询到");
        }

        //获取盐值
        String salt = dbResult.getSalt();
        //判断盐值是否存在
        if(salt == null){
            //获取盐值
            salt = MD5Utils.getSalt();
            dbResult.setSalt(salt);
        }

        //获取盐值并检测旧的密码是否正确
        String oldSaltPass = MD5Utils.getMD5PasswordUseSalt(oldPass,salt,3);
        //判断数据库中密码是否正确
        String oldPassOnDB = dbResult.getPassword();

        //判读密码是否存在
        if(oldPassOnDB != null && !(oldPassOnDB.equals(oldSaltPass))){
            throw new WrongPasswordException("密码错误");
        }

        //封装数据
        dbResult.setPassword(MD5Utils.getMD5PasswordUseSalt(newPass,salt,3));
        dbResult.setModifiedTime(LocalDateTime.now());
        dbResult.setModifiedUser(who);

        //数据库存储数据
        Integer dbResult2 = userMapper.updateUserByRecordNumber(dbResult);
        //删除
        if(dbResult2 != 1){
            throw new TPKUpdateException("修改数据时发生异常");
        }

        //将数据更新到Redis中
        boolean redisResult = !(redis6Utils.set(USER_KEY_PREFIX + recordNumber,JSON.toJSONString(dbResult),SHORT_DATA_TIME));
        if(redisResult){
            log.warn("login() --- Redis数据更新失败");
        }
    }

    @Override
    public void addUser(User user) {
        //检查record是否存在
        String recordNumber = user.getRecordNumber();
        Record record = recordMapper.queryRecordByRecordNumber(recordNumber);

        //判断recordNumber是否存在
        if(record == null) {
            throw new RecordNotFindException("档案未找到异常");
        }

        //插入数据
        Integer dbResult3 = userMapper.addUserData(user);

        //判断返回值
        if(dbResult3 != 1){
            throw new TPKInsertException("插入数据时发生异常");
        }

        //清空redis数据
        redisTemplate.delete(USER_KEY_PREFIX + recordNumber);
    }

    @Override
    public User getUserByRecordNumber(String recordNumber) {
        //在redis中获取
        User user = userMapper.queryUserByRecordNumber(recordNumber);

        //判断是否为null
        if(user == null){
            throw new UserNotFindException("用户未查询到");
        }

        //设置空值
        user.setUid(null);
        user.setPhone(null);
        user.setPassword(null);
        user.setSalt(null);
        user.setPlace(null);
        user.setIsDelete(null);
        user.setRecordNumber(null);
        user.setModifiedUser(null);
        user.setModifiedTime(null);
        user.setCreatedUser(null);
        user.setCreatedTime(null);

        return user;
    }
}
