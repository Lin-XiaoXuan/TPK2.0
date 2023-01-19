package com.njustjjy.tpk2.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.njustjjy.tpk2.dao.RecordMapper;
import com.njustjjy.tpk2.dao.UserMapper;
import com.njustjjy.tpk2.entity.Record;
import com.njustjjy.tpk2.entity.User;
import com.njustjjy.tpk2.exception.*;
import com.njustjjy.tpk2.service.IRecordService;
import com.njustjjy.tpk2.service.IUserService;
import com.njustjjy.tpk2.utils.GenerateUtils;
import com.njustjjy.tpk2.utils.HttpUtils;
import com.njustjjy.tpk2.utils.MD5Utils;
import com.njustjjy.tpk2.utils.Redis6Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.njustjjy.tpk2.utils.RedisConstants.*;

@Service
@Log4j2
public class RecordServiceImpl implements IRecordService {

    @Autowired
    Redis6Utils redis6Utils;
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    IUserService userService;
    @Autowired
    RedisTemplate redisTemplate;


    @Value("${TPK.adultLimit}")
    private String adultLimit;
    @Value("${TPK.portURL}")
    private String portURL;

    @Override
    public Record login(String idCard) {
        //生成redis通用key
        String rediskey = RECODE_KEY_PREFIX + idCard;

        //创建一个返回数据的封装对象，节省传输开销
        Record data = new Record();

        //查询redis数据是否存在如果存在则返回对应数据
        Object redisResult = redis6Utils.get(rediskey);
        if(redisResult != null){
            Record redisResult2 = JSON.parseObject(redisResult.toString(),Record.class);
            //封装数据并返回
            data.setRid(redisResult2.getRid());
            data.setNumber(redisResult2.getNumber());
            return data;
        }

        //如果redis不存在则查询对应数据并存入reids
        //查询用户信息是否存在
        Record dbResult = recordMapper.queryRecordByidCard(idCard);

        //判断record是否存在
        if(dbResult == null || dbResult.getIsDelete() == 1){
            throw new IdcardNotRegisterException("身份证未注册异常");
        }

        //将查询出来的数据全部存入redis
        String json = JSON.toJSONString(dbResult);
        if(!(redis6Utils.set(rediskey,json,SHORT_DATA_TIME))){
            log.warn("login() --- Redis数据更新失败");
        }

        //封装数据并返回
        data.setRid(dbResult.getRid());
        data.setNumber(dbResult.getNumber());

        return data;
    }


    @Override
    public void register(String idCard, String password, String who, Integer recordLevel,String remark) {
        //记录注册时间
        LocalDateTime createTime = LocalDateTime.now();

        /*--------第一段判断：年龄判断--------*/
        //获取年龄
        Integer age = GenerateUtils.getOld(idCard);
        //年龄限制判断
        if(age < 18 && Integer.valueOf(adultLimit) == 0){
            throw new IdcardAstrictException("年龄未满18周岁");
        }

        /*--------第二部分：判断Record是否被注册--------*/
        //先去数据库查询对应的Record数据是否存在
        Record dbResult = recordMapper.queryRecordByidCard(idCard);

        //自动生成新的档案号,并判断档案号是否被注册,如果被注册则重新生成
        String recordNumber = "";
        while(true){
            recordNumber = GenerateUtils.getRecordID(idCard);
            if(recordMapper.queryRecordByRecordNumber(recordNumber) == null){
                break;
            }
        }

        //判断身份证是否被注册
        if(dbResult != null){
            throw new IdcardHaveRecordException("身份证已经被注册");
        }

        /*--------第三部分：发送Http请求idCard分析接口--------*/
        //拼接请求地址
        String url = portURL + "/idcard/query/" + idCard;
        //发送请求，获取JSON对象
        JSONObject json = null;
        try {
            json = HttpUtils.getJson(url);
        } catch (IOException e) {
            throw new TPKHttpConnectException("接口服务器连接异常");
        }

        //获取回复的代码
        Integer resultCode = (Integer) json.get("state");

        //如果为4001,表示为啥找到对应idCard，表示idCard错误
        if (resultCode == 4001){
            throw new IdcardNotFindException("身份证不存在异常");
        }
        /* ... */


        /*--------第四部分：生成新的Record数据--------*/
        //生成Record密码盐值
        String recordSalt = MD5Utils.getSalt();
        //生成md5加密后密码,加密三次
        String md5Pass = MD5Utils.getMD5PasswordUseSalt(password,recordSalt,3);

        //查生层Redis对应的key
        String redisKey = RECODE_KEY_PREFIX + recordNumber;

        //拼接who字段,如果为null则表示为用户端
        if(who == null){
            who = "用户" + recordNumber;
        }

        //封装档案对象
        Record record = new Record();
        record.setIdCard(idCard);
        record.setNumber(recordNumber);
        record.setPassword(md5Pass);
        record.setSalt(recordSalt);
        record.setLevel(recordLevel);
        record.setCreatedTime(createTime);
        record.setCreatedUser(who);
        record.setRemark(remark);

        //数据库存储操作
        Integer dbResult2 = recordMapper.addRecord(record);

        //判断返回值
        if(dbResult2 != 1){
            throw new TPKInsertException("插入数据时发生异常");
        }

        //删除缓存数据
        redisTemplate.delete(redisKey);

        /*--------第五部分：生成User数据--------*/

        //封装用户信息对象
        //剥离Json数据返回的data信息
        JSONObject data = (JSONObject)json.get("data");

        //封装User对象
        User userdata = new User();
        userdata.setName(data.get("name").toString());
        userdata.setGender((Integer)data.get("gender"));
        userdata.setAge(age);
        userdata.setPlace(data.get("province").toString()
                + data.get("city").toString()
                + data.get("district").toString());
        userdata.setRecordNumber(recordNumber);
        userdata.setCreatedTime(createTime);
        userdata.setCreatedUser(who);

        //调用添加用户操作
        userService.addUser(userdata);
    }
}
