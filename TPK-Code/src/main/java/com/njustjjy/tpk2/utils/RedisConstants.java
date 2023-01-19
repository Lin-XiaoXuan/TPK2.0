package com.njustjjy.tpk2.utils;

public class RedisConstants {
    //record类型存储redis前缀
    public static final String RECODE_KEY_PREFIX = "record:";
    //user类型存储redis前缀
    public static final String USER_KEY_PREFIX = "user:";
    //business类型存储redis前缀
    public static final String BUSINESS_KEY_PREFIX = "business:";
    //emp数据存储redis前缀
    public static final String EMP_KEY_PREFIX = "emp:";
    //热点数据：价格表的key
    public static final String PRICE_TABLE = "btprice";
    //热点数据：emp表
    public static final String EMP_TABLE = "tb_emp";
    //emp循环队列key
    public static final String EMP_ROUND_QUEUE = "emp_round_queue";


    //关于时间的常量
    //热点数据50min
    public static final long HOT_DATA_TIME = 60 * 50;
    //长时间存储：30min
    public static final long LONG_DATA_TIME = 60 * 30;
    //短时存储缓存：5min
    public static final long SHORT_DATA_TIME = 60 * 5;
}
