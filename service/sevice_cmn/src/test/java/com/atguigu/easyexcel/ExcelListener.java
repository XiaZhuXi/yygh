package com.atguigu.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class ExcelListener extends AnalysisEventListener<UserDao> {
    //一行一行的读取excel内容从第二行开始读取
    @Override
    public void invoke(UserDao userDao, AnalysisContext analysisContext) {
        System.out.println(userDao);
    }
    //读取之后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
