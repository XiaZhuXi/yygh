package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.common.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.extension.service.IService;

public interface HospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hoscode);
}