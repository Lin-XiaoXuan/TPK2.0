package com.njustjjy.tpk2.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;


@ApiModel("API通用分页模型")
public class PageBean<T>{
    @ApiModelProperty("总条数")
    private int totalCount; //总条数
    @ApiModelProperty("当前页码")
    private int currentPage; //当前页码
    @ApiModelProperty("每页条数")
    private int rows;  //每页条数
    @ApiModelProperty("总页码页数")
    private int totalPage;  //总页码页数
    @ApiModelProperty("分页返回的结果")
    private List<T> results;   //结果集

    //这个参数用于数据库查询只能get，set要调用对应构造函数
    private Integer start;


    public PageBean(){}
    //构造函数并自动计算
    public PageBean(int currentPage,int rows,int totalCount) {
        //处理一下保证currentPage 不小于1
        this.currentPage = currentPage < 1 ? 1 : currentPage;
        this.totalCount = totalCount;
        //如果查询条数大于totalCount 则赋予默认值3，如果没有则设置rows的值
        this.rows = rows > totalCount ? 3 : rows;
        this.totalPage = PageUtils.getTotalPage(this.totalCount,this.rows);
        //这里稍微判断处理一下start计算出来的值，如果start计算出来的值小于0 则start直接为0，防止程序崩溃
        int startValue = PageUtils.getStart(this.currentPage,this.rows);
        this.start = startValue < 0 ? 0 : startValue;
    }

    //get 和set 方法

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public Integer getStart() {
        return start;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", currentPage=" + currentPage +
                ", rows=" + rows +
                ", totalPage=" + totalPage +
                ", results=" + results +
                ", start=" + start +
                '}';
    }

    //获取totalPage
    private static Integer getTotalPage(Integer count,Integer rows){
        return (count % rows == 0) ? (count/rows) : (count/rows + 1);
    }

    //获取开始条数
    private static Integer getStart(Integer currentPage,Integer rows){
        return (currentPage - 1) * rows;
    }

    public static void main(String[] args) {
        PageBean pa = new PageBean(0,11,10);
        System.out.println(pa.toString());
    }
}
