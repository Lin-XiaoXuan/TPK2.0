package com.njustjjy.tpk2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
public class RedisConfig {
    //url地址
    private String host = "139.159.250.100";
    //端口号
    private Integer port = 6379;
    //密码
    private String password = "Lin6280178";
    //数据库号
    private Integer database = 0;

    //配置最大连接数
    private Integer maxTotal = 100;
    //最大能够保持idel状态的对象数
    private Integer maxIdle = 100;
    //设置最小空闲数
    private Integer minIdle = 10;
    //最大连接数
    private Integer maxWait = 2000;

    //配置jedis工厂类
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        //配置reids数据库或者redis集群
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(host);
        standaloneConfiguration.setPort(port);
        standaloneConfiguration.setPassword(password);
        standaloneConfiguration.setDatabase(database);

        //配置Jedis连接池
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWait(Duration.ofDays(maxWait));

        //最新版本要是用JedisClientConfiguration来设置连接池
        //JedisClientConfiguration默认设置了读取和连接超时 默认2秒
        JedisClientConfiguration jedisClientConfiguration =
                JedisClientConfiguration
                        .builder()
                        .usePooling()
                        .poolConfig(jedisPoolConfig)
                        .build();

        return  new JedisConnectionFactory(standaloneConfiguration,jedisClientConfiguration);
    }

    //redis模板类
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        //配置jedis工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //更改序列化器，推荐实际开发中使用jackson进行序列化器
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));
        return redisTemplate;
    }
}
