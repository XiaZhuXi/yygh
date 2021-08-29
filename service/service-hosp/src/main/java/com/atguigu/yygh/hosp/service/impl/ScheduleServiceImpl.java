package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.common.model.hosp.Department;
import com.atguigu.yygh.common.model.hosp.Schedule;
import com.atguigu.yygh.common.vo.hosp.ScheduleQueryVo;
import com.atguigu.yygh.hosp.repository.ScheduleRepository;
import com.atguigu.yygh.hosp.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    //添加排班
    @Override
    public void save(Map<String, Object> paramMap) {
        //把参数的map集合转换对象 Hospital
        String s = JSONObject.toJSONString(paramMap);
        //插入数据
        Schedule schedule = JSONObject.parseObject(s, Schedule.class);
        //判断是否存在相同的数据
        Schedule scheduleExist = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(), schedule.getHosScheduleId());
        if(scheduleExist != null) {
            scheduleExist.setUpdateTime(new Date());
            scheduleExist.setIsDeleted(0);
            scheduleRepository.save(scheduleExist);
        }else{
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            scheduleRepository.save(schedule);
        }

    }
    //查询排班
    @Override
    public Page<Schedule> selectPage(int page, int limit, ScheduleQueryVo scheduleQueryVo) {
        //创建Schedule对象将ScheduleVo转换成Schedule对象
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo,schedule);
        //创建pageable对象，设置当前页与每页记录数
        //设置当前页默认值
        Pageable pageable = PageRequest.of(page-1, limit);
        //创建Example对象
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();
       Example example=Example.of(schedule,exampleMatcher);
        Page<Schedule> all = scheduleRepository.findAll(example, pageable);
        return all;
    }
    //删除排班
    @Override
    public void remove(String hoscode, String hosScheduleId) {
        //根据医院编号部门编号查询
       Schedule schedule= scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode,hosScheduleId);
      if (schedule!=null) scheduleRepository.deleteById(schedule.getId());
    }

}
