package com.atguigu.yygh.cmn.service;

import com.atguigu.yygh.common.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface DictService extends IService<Dict> {

//根据id查询数据下面的子数据
    List<Dict> findChildData(Long id);

    void exportDictData(HttpServletResponse response) throws IOException;

    void importDictData(MultipartFile file);
}
