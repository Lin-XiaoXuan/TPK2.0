package com.njustjjy.tpk2.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *分组实体类
 * -- 晓轩
 * */
@Data
public class Groups extends BaseEntity implements Serializable {
    private Integer gid;
    private String name;
}
