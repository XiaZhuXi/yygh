package com.atguigu.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.Valid;

@Data
public class UserDao {
    @ExcelProperty(value = "用户的编号",index = 0)
    private int id;
    @ExcelProperty(value = "用户名称",index = 1)
    private String name;
}
