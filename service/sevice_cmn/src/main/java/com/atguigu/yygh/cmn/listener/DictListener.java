package com.atguigu.yygh.cmn.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.yygh.cmn.mapper.DictSetMapper;
import com.atguigu.yygh.common.model.cmn.Dict;
import com.atguigu.yygh.common.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;


public class DictListener extends AnalysisEventListener<DictEeVo> {
private DictSetMapper dictSetMapper;
public DictListener (DictSetMapper dictSetMapper){
    this.dictSetMapper=dictSetMapper;
}
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        //调用方法添加数据库
        Dict dict=new Dict();
        dict.setIsDeleted(0);
        BeanUtils.copyProperties(dictEeVo,dict);
        dictSetMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
