package com.njustjjy.tpk2.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *员工类实体类
 * -- 晓轩
 * */
@Data
public class Emp extends BaseEntity implements Serializable {
    private Integer eid;
    private String number;
    private String name;
    private String idCard;
    private Integer age;
    private Integer gender;
    private Double salary;
    private Integer gid;
}
