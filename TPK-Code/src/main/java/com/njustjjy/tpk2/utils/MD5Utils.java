package com.njustjjy.tpk2.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * MD5Utils加密工具，用于加密字符串
 *
 * <p>MD5加密算法</p>
 *
 * @author xiaoxuan
 * @version V2.0
 * @date 2022-08-16
 * */
@Component
public class MD5Utils {
    /**
     * MD5加盐加密算法
     *
     * <p>MD5加密算法</p>
     *
     * @param number -- 加密次数
     * @param salt -- 加密盐值
     * @param str -- 需要加密数据
     *
     * @author xiaoxuan
     * @version V2.0
     * @date 2022-08-16
     * */

    public static String getMD5PasswordUseSalt(String str,String salt,Integer number){
        //md5加盐加密
        for(int i = 0; i < number;i++){
            str = DigestUtils.md5DigestAsHex((salt+str+salt).getBytes()).toUpperCase();
        }
        return str;
    }

    //获取盐值
    public static synchronized String getSalt(){
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static void main(String[] args) {
        MD5Utils md5Utils = new MD5Utils();
        System.out.println(md5Utils.getSalt());
    }
}
