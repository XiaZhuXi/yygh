package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.common.model.hosp.Department;
import com.atguigu.yygh.common.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;


import java.util.Map;

public interface DepartmentService {
    void saveDepartment(Map<String, Object> paramMap);

    Page<Department> selectPage(int page, int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);
}
