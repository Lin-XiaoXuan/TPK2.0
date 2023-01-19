package com.njustjjy.tpk2.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *档案实体类
 * -- 晓轩
 * */
@Data
public class Record extends BaseEntity implements Serializable {
    private Integer rid;
    private String idCard;
    private String number;
    private String password;
    private String salt;
    private Integer level;
    private Integer isDelete;
}
