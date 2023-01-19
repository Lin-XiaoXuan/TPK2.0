package com.njustjjy.tpk2.utils;

import io.lettuce.core.RedisException;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class Redis6Utils {
    @Autowired
    RedisTemplate redisTemplate;

    //BoundValueOperations工厂类
    private BoundValueOperations getboundValueOperations(String key){
        return redisTemplate.boundValueOps(key);
    }
    //BoundHashOperations工厂
    private BoundHashOperations getboundHashOperations(String key){
        return redisTemplate.boundHashOps(key);
    }
    //BoundZSetOperations工厂
    private BoundZSetOperations getboundZSetOperations(String key){return redisTemplate.boundZSetOps(key);}
    //
    private BoundListOperations getboundListOperations(String key){
        return redisTemplate.boundListOps(key);
    }

    /*--------------------------------------基本操作-----------------------------------*/
    /**
     * 将值放入缓存
     * 增加操作未出现异常则返回true 否则为false
     *
     * */
    public Boolean set(String key,Object value){
        try{
            getboundValueOperations(key).set(value);
        }catch (RedisException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 将值放入缓存并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) -1为无期限
     * @return true成功 false 失败
     */
    public Boolean set(String key, Object value,long time) {
        try{
            if (time > 0) {
                getboundValueOperations(key).set(value, time, TimeUnit.SECONDS);
            }
            else
            {
                getboundValueOperations(key).set(value);
            }
        }catch (RedisException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除操作
     * 删除对应的redis数据，如果key为null则返回null否则返回获取的数据
     *
     * */
    public String delete(String key){
        return key == null ? null : getboundValueOperations(key).getAndDelete().toString();
    }

    /**
     * 查询操作
     * 查询对应数据，key为null返回null否则返回false
     *
     * */
    public Object get(String key){
        return key == null ? null : getboundValueOperations(key).get();
    }

    //-----------------------------------hash操作-----------------------------------


    /**
     * 将值放入缓存并设置时间
     *
     * @param key   键
     * @param maps 所有数据，map的可以为 Redis 的 faild
     * @param time 过期时间 -- 单位秒
     * 插入操作未产生异常则返回true否则为false
     */
    public Boolean putAll(String key, Map<Object, Object> maps,long time) {
        try{
            getboundHashOperations(key).putAll(maps);
            getboundHashOperations(key).expire(time,TimeUnit.SECONDS);
        }catch (RedisException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Boolean putAll(String key,Map<Object,Object> maps){
        try{
            getboundHashOperations(key).putAll(maps);
        }catch (RedisException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取 key 下的 所有  faild 和 value
     *
     * @param key 键
     * @return
     */
    public Map<Object, Object> getHashEntries(String key) {
        return getboundHashOperations(key).entries();
    }

    /**
     * 获取 key 下指定的faild值
     *
     * @param key 键
     * @return
     */
    public Object getHashEntries(String key,String faild) {
        //获取HashMap
        Map<Object,Object> map = getboundHashOperations(key).entries();
        Object result =  map.get(faild);

        //判断数据是否存在
        if(result != null){
            return result;
        }

        return null;
    }

    /**
     * 删除指定 hash 的 faild
     *
     * @param key
     * @param faild
     * @return 删除成功的 数量
     */
    public Long deleteHash(String key,String... faild){
       return key != null ? getboundHashOperations(key).delete(faild) : null;
    }


    /**
     * 验证指定 key 下 有没有指定的 faild
     *
     * @param key
     * @param faild
     * @return
     */
    public boolean isHaveFaild(String key, String faild) {
        return getboundHashOperations(key).hasKey(faild);
    }





    //-----------------------------------zset操作-----------------------------------

    /**
     * 添加单个数据
     *
     * @param key 键
     * @param value 值
     * @param score zset中的分数
     *
     * @return 没有异常返回true，有异常返回false
     */
    public Boolean zAdd(String key,Object value,double score){
        try{
            getboundZSetOperations(key).add(value,score);
        }catch (RedisException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     *添加集合
     *
     * @param key 键
     * @param value 值
     *
     * @return 没有异常返回true，有异常返回false
     */
    public Boolean zAddSet(String key, Set<ZSetOperations.TypedTuple> value,long time){
        BoundZSetOperations bzso = getboundZSetOperations(key);
        try{
            bzso.add(value);
            bzso.expire(time,TimeUnit.SECONDS);
        }catch (RedisException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    //-----------------------------------list操作-----------------------------------
    /**
     * 在List左边添加元素值
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean leftPush(String key,Object value){
        try{
            getboundListOperations(key).leftPush(value);
        }catch (RedisException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 从右侧吐出并获取一个值(吐出：取出并移除)
     *
     * @param key -- 列表键
     * @return
     */
    public Object rightPop(String key){
        return key != null ? getboundListOperations(key).rightPop() : null;
    }

}
