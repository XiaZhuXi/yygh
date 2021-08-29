package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.common.model.hosp.Department;
import com.atguigu.yygh.common.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.hosp.repository.DepartmentRepository;
import com.atguigu.yygh.hosp.service.DepartmentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void saveDepartment(Map<String, Object> paramMap) {
        //把参数的map集合转换对象 Hospital
        String s = JSONObject.toJSONString(paramMap);
        //插入数据
        Department department = JSONObject.parseObject(s, Department.class);
        //判断是否存在相同的数据
        Department departmentExist = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());
        if(departmentExist != null) {
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        }else{
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> selectPage(int page, int limit, DepartmentQueryVo departmentQueryVo) {
      //创建Department对象将DepartmentVo转换成Department对象
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo,department);
        //创建pageable对象，设置当前页与每页记录数
        //设置当前页默认值 0
        Pageable pageable = PageRequest.of(page - 1, limit);
        //创建Example对象
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase(true).withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Department> example=Example.of(department,exampleMatcher);

       Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        //根据医院编号部门编号查询
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department!=null){
            //调用方法删除
            departmentRepository.deleteById(department.getId());
        }
    }
}
