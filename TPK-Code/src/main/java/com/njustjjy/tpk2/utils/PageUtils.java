package com.njustjjy.tpk2.utils;

import java.util.Map;

/**
 * 分页工具组
 *  用于分页的部分计算
 *  所需参数
 *      总条数 -- Counts
 *      当前页数 -- currentPage
 *      每页条数 -- rows
 *
 *
 *  返回结果集
 *      开始页码    --- start
 *      开始页数 =
 *      总条数 = Counts
 * */

//计算总共多少页： 总页数 = 总条数 / 每页条数
//判断逻辑： 如果总页数除的尽则直接为 总条数/每页条数
//  如果总页数除不尽则为 总条数/每页条数 + 1
//计算开始页数 = (当前页数 - 1) * 每页条数
//    Integer Counts,Integer currentPage,Integer rows,

//分页功能对象该对象用于计算分页所需数据
public class PageUtils {
    //获取totalPage
    public static Integer getTotalPage(Integer count,Integer rows){
        return (count % rows == 0) ? (count/rows) : (count/rows + 1);
    }

    //获取开始条数
    public static Integer getStart(Integer currentPage,Integer rows){
        return (currentPage - 1) * rows;
    }
}
