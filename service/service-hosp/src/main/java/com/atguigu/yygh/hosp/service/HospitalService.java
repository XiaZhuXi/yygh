package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.common.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {
    void save(Map<String, Object> parameterMap);

    Hospital getByHoscode(String hoscode);

}
