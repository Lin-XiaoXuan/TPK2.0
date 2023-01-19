package com.njustjjy.tpk2.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *用户实体类
 * -- 晓轩
 * */
@Data
public class User extends BaseEntity implements Serializable {
    private Integer uid;
    private String phone;
    private String password;
    private String salt;
    private String name;
    private Integer gender;
    private Integer age;
    private String place;
    private Integer isDelete;
    private String recordNumber;
}
