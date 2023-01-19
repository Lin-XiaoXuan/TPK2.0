package com.njustjjy.tpk2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.njustjjy.tpk2.dao")
@SpringBootApplication
public class TpkApplication {
    public static void main(String[] args) {
//        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(TpkApplication.class, args);
    }

}
