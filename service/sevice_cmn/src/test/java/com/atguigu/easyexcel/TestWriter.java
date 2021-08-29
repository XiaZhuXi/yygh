package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;

public class TestWriter {
    public static void main(String[] args) {
        ArrayList<UserDao> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserDao userDao = new UserDao();
            userDao.setId(i);
            userDao.setName("tom"+i);
            list.add(userDao);
        }
        //设置写入文件路径
        String fileName="E:\\person_demo\\excel\\user.xlsx";
        //调用方法实现写操作
        EasyExcel.write(fileName,UserDao.class).sheet("用户信息").doWrite(list);
    }
}
