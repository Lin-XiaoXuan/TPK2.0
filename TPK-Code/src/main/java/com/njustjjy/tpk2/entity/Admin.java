package com.njustjjy.tpk2.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *管理员实体类
 * -- 晓轩
 * */
@Data
public class Admin extends BaseEntity implements Serializable {
    private Integer aid;
    private String number;
    private String password;
    private String salt;
    private Integer authority;
    private Integer eid;
    private LocalDateTime loginTime;
    private String loginUser;
}
