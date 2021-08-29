package com.atguigu.yygh.cmn.controller;

import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.model.cmn.Dict;
import com.atguigu.yygh.common.result.Result;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api("数据字典接口")
@RestController
@CrossOrigin
@RequestMapping("/admin/cmn/dict")
public class DictController {
    @Autowired
    private DictService dictService;
    @ApiOperation(value = "导出数据excel")
    @GetMapping("/exportDate")
    public void exportDate(HttpServletResponse response) throws IOException {
      dictService.exportDictData(response);
    }
    @ApiOperation(value = "导出数据excel")
    @PostMapping("/importData")
    public Result importDate(MultipartFile file){
        dictService.importDictData(file);
        return Result.ok();
    }
    @ApiOperation(value = "根据id查询数据下面的子数据")
    @GetMapping("/findChildData/{id}")
    public Result getListById(@PathVariable Long id){
       List<Dict> list= dictService.findChildData(id);
        return Result.ok(list);
    }
}
