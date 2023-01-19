package com.njustjjy.tpk2.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.njustjjy.tpk2.dao.*;
import com.njustjjy.tpk2.entity.*;
import com.njustjjy.tpk2.entity.other.BusinessTable;
import com.njustjjy.tpk2.exception.*;
import com.njustjjy.tpk2.handler.EmpWebSocketHandler;
import com.njustjjy.tpk2.service.IBusinessService;
import com.njustjjy.tpk2.utils.GenerateUtils;
import com.njustjjy.tpk2.utils.PageBean;
import com.njustjjy.tpk2.utils.PageUtils;
import com.njustjjy.tpk2.utils.Redis6Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.njustjjy.tpk2.utils.RabbitMQConstants.EXCHANG_TPK;
import static com.njustjjy.tpk2.utils.RabbitMQConstants.QUEUE_BUIN_IDENTIFY;
import static com.njustjjy.tpk2.utils.RedisConstants.*;

@Service
@Log4j2
public class BusinessServiceImpl implements IBusinessService {

    @Autowired
    RecordMapper recordMapper;
    @Autowired
    EmpMapper empMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    Redis6Utils redis6Utils;
    @Autowired
    BtpriceMapper btpriceMapper;
    @Autowired
    BusinessMapper businessMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    EmpWebSocketHandler empWebSocketHandler;


    @Override
    public void userAddBusiness(String recordNumber,String businessType,String phone,String remark, Double money,Integer way,String who) {
        //判断recordNumber是否存在
        Record dbResult = recordMapper.queryRecordByRecordNumber(recordNumber);
        if(dbResult == null || dbResult.getIsDelete() == 1){
            throw new RecordNotFindException("未查询到对应档案");
        }

        //判断业务类型是否存在
        Btprice dbResult2 = btpriceMapper.queryBtpriceByCodes(businessType);
        if(dbResult2 == null){
            throw new BtpriceNotFindException("未发现对应的类型");
        }
        //价格
        Double price = dbResult2.getPrice();

        //判断价格是否匹配
        if(!(price.equals(money))){
            throw new PriceNotMutchException("价格不匹配异常");
        }

        //生成业务id
        String number = GenerateUtils.getBusiness();

        //存储数据
        //封装business信息
        Business business = new Business();
        business.setRecordNumber(dbResult.getNumber());
        business.setNumbers(number);
        business.setType(businessType);
        business.setPhone(phone);
        business.setWay(way);
        business.setProgress(1);
        business.setMoney(money);
        business.setCreatedTime(LocalDateTime.now());
        business.setCreatedUser(who);
        business.setRemark(remark);

        //分配业务员
        try {
            empWebSocketHandler.allocationEmp(business);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //将申请的数据放入RabbitMQ
//        String json = JSON.toJSONString(business);
//        rabbitTemplate.convertAndSend(EXCHANG_TPK,QUEUE_BUIN_IDENTIFY,json);
    }

    @Override
    @Deprecated
    public void addBusiness(String evidence,Integer type,Integer way,String remark,String who,Integer condition) {
        /**第一步：判断数据是否存在*/
        //按照rid取出数据
        Record dbResult = null;

        //判断情况
        if(condition == 2){
            dbResult = recordMapper.queryRecordByidCard(evidence);
        }
        else{
            dbResult = recordMapper.queryRecordByRecordNumber(evidence);
        }

        //判断数据是否存在
        if(dbResult == null || dbResult.getIsDelete() == 1){
            throw new RecordNotFindException("未查询到对应档案");
        }

        //在Redis中尝试取出价格表
        Map<Object,Object> priceTable = redis6Utils.getHashEntries(PRICE_TABLE);

        //金额初始化
        Double money = 0.00;
        //判断集合中个是否有数据BUG money无法获取
        if(!(priceTable.isEmpty())){
            //存在则转换
            String str = JSON.toJSONString(priceTable.get(type));
            //转化为对象
            Btprice redisPrice = JSONObject.parseObject(str,Btprice.class);
            System.out.println("Btprice:" + redisPrice);
            //设置值
            money = redisPrice.getPrice();
        }else{
            //不存在则后端取用数据更新进Redis
            List<Btprice> dbResult2 = btpriceMapper.queryAllBusinessType();
            if(dbResult2.size() != 0){
                for(Btprice btprice : dbResult2){
                    priceTable.put(btprice.getPid(),btprice);
                    //赋值
                    money = (btprice.getPid() == type) ? btprice.getPrice() : money;
                }
            }
            else{
                //如果没有取出数据
                throw new TPKRuntimeException("取出价格表时发生异常");
            }
            //将价格表数据存入Redis
            redis6Utils.putAll(PRICE_TABLE,priceTable,HOT_DATA_TIME);
        }

        //生成业务id
//        String number = GenerateUtils.getBusiness(type);

        //封装business信息
        Business business = new Business();
        business.setRecordNumber(dbResult.getNumber());
//        business.setNumbers(number);
//        business.setType(type);
        business.setWay(way);
        business.setProgress(1);
        business.setMoney(money);
        business.setCreatedTime(LocalDateTime.now());
        business.setCreatedUser(who);
        business.setRemark(remark);

        //数据库添加内容
//        Integer dbResult2 = businessMapper.addBusiness(business);
//        if(dbResult2 != 1){
//            throw new TPKInsertException("插入数据时发生异常");
//        }

        //将数据发送到RabbitMQ
        String json = JSON.toJSONString(business);
        rabbitTemplate.convertAndSend(EXCHANG_TPK,QUEUE_BUIN_IDENTIFY,json);
    }

    @Override
    @Deprecated
    public PageBean<Business> queryLimitBusiness(String recordNumber, Integer condition, Integer currentPage, Integer rows) {
        //查询总条数
        Integer totalCount = businessMapper.queryCountBusinessByRecordNumber(recordNumber);
        //计算总页码页数
        Integer totalPage = PageUtils.getTotalPage(totalCount,rows);
        //获取开始条数
        Integer start = PageUtils.getStart(currentPage,rows);

        //redis查询对应数据

        //获取查询结果集
        List<Business> result = businessMapper.queryLimitBusinessforUser(recordNumber,start,rows);
        //封装结果集
        PageBean pageBean = new PageBean();
        pageBean.setTotalCount(totalCount);
        pageBean.setCurrentPage(currentPage);
        pageBean.setRows(rows);
        pageBean.setTotalPage(totalPage);
        pageBean.setResults(result);

        //封装结果并返回
        return pageBean;
    }

    @Override
    public PageBean<Business> queryLimitBusinessByRecordNumberforUser(String recordNumber, Integer currentPage, Integer rows) {
        //查询
        Record dbResult = recordMapper.queryRecordByRecordNumber(recordNumber);
        if(dbResult == null){
            throw new RecordNotFindException("档案不存在");
        }

        //查询总条数
        Integer totalCount = businessMapper.queryCountBusinessByRecordNumber(recordNumber);
        //创建对象
        PageBean pageBean = new PageBean(currentPage,rows,totalCount);
        //获取查询结果集
        List<Business> result = businessMapper.queryLimitBusinessforUser(recordNumber,(pageBean.getStart()),rows);
        //封装结果集到PageBean
        pageBean.setResults(result);

        //封装结果并返回
        return pageBean;
    }

    @Override
    public PageBean<Business> queryLimitBusinessByEmpNumber(String empNumber,Integer currentPage, Integer rows) {
        //判断Emp是否存在
        Emp dbResult1 = empMapper.queryEmpByEmpNumber(empNumber);
        if(dbResult1 == null){
            throw new EmpNotFindException("业务员不存在");
        }

        //封装PageUtils对象
        //1、查询总条数
       Integer totalCount = businessMapper.queryCountBusinessByEmpNumber(empNumber);

       PageBean pageBean = new PageBean(currentPage,rows,totalCount);

       //查询结果集
       List<Business> result = businessMapper.queryLimitBusinessByEmpNumber(empNumber,(pageBean.getStart()),rows);

       //设置结果集
       pageBean.setResults(result);

        return pageBean;
    }

    @Override
    public List<Business> queryBusinessByEmpNumber(String empNumber) {
        //判断Emp是否存在
        Emp dbResult1 = empMapper.queryEmpByEmpNumber(empNumber);
        if(dbResult1 == null){
            throw new EmpNotFindException("业务员不存在");
        }

        //查询结果集
        List<Business> dbResult2 = businessMapper.queryBusinessByEmpNumber(empNumber);

        return dbResult2;
    }


    @Override
    public void repealBusiness(String businessNumber,String who) {
        //查询对应的Business是否存在
        Business dbResult = businessMapper.queryBusinessByNumber(businessNumber);

        //判断是否存在
        if(dbResult == null || dbResult.getIsDelete() == 1){
            throw new BusinessNotFindException("对应数据不存在");
        }

        //获取业务号
        String recordNumber = dbResult.getRecordNumber();

        //判断是否已经被撤销
        if(dbResult.getProgress() == 0){
            throw new BusinessisRepealException("业务已经撤销");
        }

        //封装要修改的数据
        dbResult.setProgress(0);
        dbResult.setModifiedUser(who);
        dbResult.setModifiedTime(LocalDateTime.now());

        //进行删除操作
        Integer result2 = businessMapper.updateBusinessByNumber(dbResult);

        //判断是否删除成功
        if(result2 != 1){
            throw new TPKUpdateException("更改数据时发生未知异常");
        }
        //删除库中信息
        redisTemplate.delete(BUSINESS_KEY_PREFIX + recordNumber);
    }

    @Override
    public void updateBusinessByNumber(Business business,String who) {
        //查询对应number是否存在
        String businessNumber = business.getNumbers();
        Business dbResult = businessMapper.queryBusinessByNumber(businessNumber);

        //判断是否存在
        if(dbResult == null || dbResult.getIsDelete() == 1){
            throw new BusinessNotFindException("业务信息未找到");
        }

        //获取busType
        String busType = business.getType();
        //判断业务类型
        busType = busType != null ? busType : dbResult.getType();

        //在Redis中尝试取出价格表
        Map<Object,Object> priceTable = redis6Utils.getHashEntries(PRICE_TABLE);

        //金额初始化
        Double money = 0.00;
        //判断集合中个是否有数据
        if(!(priceTable.isEmpty())){
            //存在则转换
            String str = JSON.toJSONString(priceTable.get(busType).toString());
            //转化为对象
            Btprice redisPrice = JSONObject.parseObject(str,Btprice.class);
            //设置值
            money = redisPrice.getPrice();
        }else{
            //不存在则后端取用数据更新进Redis
            List<Btprice> dbResult2 = btpriceMapper.queryAllBusinessType();
            if(dbResult2.size() != 0){
                for(Btprice btprice : dbResult2){
                    priceTable.put(btprice.getPid(),btprice);
//                    money = (btprice.getPid() == busType) ? btprice.getPrice() : null;
                }
            }
            else{
                //如果没有取出数据
                throw new TPKRuntimeException("取出价格表时发生异常");
            }
            //将数据存入Redis
            redis6Utils.putAll(PRICE_TABLE,priceTable,HOT_DATA_TIME);
        }

        //设置属性
        dbResult.setType(busType);
        dbResult.setMoney(money);
        dbResult.setRemark(business.getRemark());
        dbResult.setModifiedTime(LocalDateTime.now());
        dbResult.setModifiedUser(who);

        //设置属性
        Integer dbResult3 = businessMapper.updateBusinessByNumber(dbResult);
        if(dbResult3 != 1){
            throw new TPKUpdateException("修改信息时发生未知异常");
        }

        String recordNumber = dbResult.getRecordNumber();

        //删除数据库内容
        redisTemplate.delete(BUSINESS_KEY_PREFIX + recordNumber);
    }

    @Override
    public void updateBusinessProgree(String businessNumber,Integer businessProgress, String who) {
        //查询数据
        Business dbResult = businessMapper.queryBusinessByNumber(businessNumber);

//        判断查询结果
        if(dbResult == null || dbResult.getIsDelete() == 1){
            throw new BusinessNotFindException("业务未查询到");
        }

        //如果进度与需要修改的进度相同则方法返回
        if (dbResult.getProgress() == businessProgress){
            return;
        }

       //修改操作
       Integer dbResult2 =  businessMapper.updateBusinessProgressByNumber(dbResult.getNumbers(),businessProgress,who,LocalDateTime.now());

       //判断是否正确
        if(dbResult2 != 1){
            throw new TPKUpdateException("修改数据时发生异常");
        }
    }

    @Override
    public Business queryBusinessByNumber(String businessNumber) {
        Business dbResult = businessMapper.queryBusinessByNumber(businessNumber);

        //判断
        if(dbResult == null){
            throw new BusinessNotFindException("未找到对应的业务");
        }
        //返回数据
       return dbResult;
    }

    @Override
    public BusinessTable queryBusinessTableByBusinessNumber(String businessNumber) {
        //先查询对应的Business
        Business dbResult =  businessMapper.queryBusinessByNumber(businessNumber);
        //判断查询到的数据是否存在
        if(dbResult == null){
            throw new BusinessNotFindException("业务未查询到");
        }

        //提取数据库中获取的档案信息
        String dbRecordNumber = dbResult.getRecordNumber();

        //根据查询出来的数据来返回对应结果
        User dbResult2 = userMapper.queryUserByRecordNumber(dbRecordNumber);
        //判断对饮用户信息是否存在
        if(dbResult2 == null){
            throw new UserNotFindException("用户信息未查询到");
        }

        //根据businessTypeNumber查询
        Btprice dbResult3 = btpriceMapper.queryBtpriceByCodes(dbResult.getType());
        if(dbResult3 == null){
            throw new BtpriceNotFindException("无法匹配对应类型");
        }

        //封装数据模型
        BusinessTable data = new BusinessTable();
        data.setName(dbResult2.getName());
        data.setAge(dbResult2.getAge());
        data.setRecordNumber(dbRecordNumber);
        data.setBusinessNumbers(businessNumber);
        data.setPhone(dbResult.getPhone());
        data.setBusinessTypeName(dbResult3.getName());
        data.setBusinessTypeNumber(dbResult3.getCodes());
        data.setGender(dbResult2.getGender());
        data.setCreatedTime(dbResult.getCreatedTime());
        data.setRemark(dbResult.getRemark());
        data.setExecuteTime(dbResult.getExecuteTime());
        data.setMoney(dbResult.getMoney());

        return data;
    }


    //抽离的私有化方法，减少代码的耦合性
    private static List<Business> filterResult(List<Business> list,int condition){
        //检查是否有数据
        if(list == null || list.size() == 0){
            return null;
        }

        //创建结果集
        List<Business> results = new ArrayList<>();

        //这是个记录作用的对象
        Integer progress;
        //情况判断
        if(condition == 1){
            //如果为 1 --> 将progress == 1 或者 2 数据显示
            for(Business business : list){
               //判断progress的值，如果这个值小于2 或者不等于0 则满足condition == 1的条件，将其存入结果集
               progress = business.getProgress();
               if(progress <= 2 && progress != 0){
                   results.add(business);
               }
            }
        }

        return results;
    }
}
