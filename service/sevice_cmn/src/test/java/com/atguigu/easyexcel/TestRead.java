package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

public class TestRead {
    public static void main(String[] args) {
        //读取文件路径
        String fileName="E:\\person_demo\\excel\\user.xlsx";
        //调用方法读取操作
        EasyExcel.read(fileName,UserDao.class,new ExcelListener()).sheet().doRead();
    }
}
