package com.njustjjy.tpk2.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class LocalDataTimeUtils {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.print(now.getDayOfMonth());
    }
}
