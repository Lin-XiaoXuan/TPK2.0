package com.njustjjy.tpk2.dao;

import com.njustjjy.tpk2.entity.Record;
import com.njustjjy.tpk2.utils.GenerateUtils;
import com.njustjjy.tpk2.utils.MD5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RecordMapperTest {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    GenerateUtils generateUtils;
    @Autowired
    MD5Utils md5Utils;

    @Test
    public void addRecordTest(){
        Record record = new Record();
        //获取盐值
        String salt = md5Utils.getSalt();

        record.setIdCard("340823200310135617");
        record.setNumber(generateUtils.getRecordID("340823200310135617"));
        record.setSalt(salt);
        record.setLevel(1);
        record.setPassword(md5Utils.getMD5PasswordUseSalt("1234",salt,3));
        record.setCreatedTime(LocalDateTime.now());
        record.setCreatedUser("管理员-晓轩");
        recordMapper.addRecord(record);
    }

    @Test
    public void queryRecordByidCardTest(){
        System.out.println(recordMapper.queryRecordByidCard("340823200310135617"));
    }

    @Test
    public void queryRecordByRecordNumberTest(){
        System.out.println(recordMapper.queryRecordByRecordNumber(""));
    }


}
