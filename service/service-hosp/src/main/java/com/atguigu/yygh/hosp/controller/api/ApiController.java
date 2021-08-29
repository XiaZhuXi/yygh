package com.atguigu.yygh.hosp.controller.api;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.model.hosp.Department;
import com.atguigu.yygh.common.model.hosp.Hospital;
import com.atguigu.yygh.common.model.hosp.Schedule;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.common.utils.CheckMD5;
import com.atguigu.yygh.common.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.common.vo.hosp.ScheduleQueryVo;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalSetService hosSetService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ScheduleService scheduleService;


    //查询医院
    @ApiOperation(value = "查询医院")
    @PostMapping("hospital/show")
    public Result showHospital(HttpServletRequest request) {
        //获取传递过来的医院信息
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> parameterMap = HttpRequestHelper.switchMap(map);
        //根据传递的医院编号查询数据库，查询签名
        String hoscode = (String) parameterMap.get("hoscode");
        String signKey = hosSetService.getSignKey(hoscode);
        //签名比对
        CheckMD5.checkKey(parameterMap, signKey, "sign");
        //调用service的根据编号查询医院方法
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }

    //上传医院接口
    @ApiOperation(value = "上传医院接口")
    @PostMapping("/saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        //获取传递过来的医院信息
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> parameterMap = HttpRequestHelper.switchMap(map);

        //根据传递的医院编号查询数据库，查询签名
        String hoscode = (String) parameterMap.get("hoscode");
        String signKey = hosSetService.getSignKey(hoscode);
        //签名比对
        CheckMD5.checkKey(parameterMap, signKey, "sign");
        //传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoDataString = (String) parameterMap.get("logoData");
        if (StringUtils.checkValNotNull(logoDataString)) {
            String logoData = logoDataString.replaceAll(" ", "+");
            parameterMap.put("logoData", logoData);
        }
        hospitalService.save(parameterMap);
        return Result.ok();
    }

    //查询科室
    @ApiOperation(value = "获取分页列表")
    @PostMapping("department/list")
    public Result department(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String) paramMap.get("hoscode");
        //非必填
        String depcode = (String) paramMap.get("depcode");
        int page = StringUtils.checkValNull(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.checkValNull(paramMap.get("limit")) ? 10 : Integer.parseInt((String) paramMap.get("limit"));

        if (StringUtils.checkValNull(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        CheckMD5.checkKey(paramMap, hosSetService.getSignKey(hoscode), "sign");

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        departmentQueryVo.setDepcode(depcode);
        Page<Department> pageModel = departmentService.selectPage(page, limit, departmentQueryVo);
        return Result.ok(pageModel);
    }

    //上传科室接口
    @ApiOperation(value = "上传科室")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        //获取传递过来的医院信息
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        //根据传递的医院编号查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hosSetService.getSignKey(hoscode);
        //签名比对
        CheckMD5.checkKey(paramMap, signKey, "sign");
        //调用departmentService的方法
        departmentService.saveDepartment(paramMap);
        return Result.ok();
    }

    @ApiOperation(value = "删除科室")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String) paramMap.get("hoscode");
        //必填
        String depcode = (String) paramMap.get("depcode");
        if (StringUtils.checkValNull(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        CheckMD5.checkKey(paramMap, hosSetService.getSignKey(hoscode), "sign");

        departmentService.remove(hoscode, depcode);
        return Result.ok();
    }

    @ApiOperation(value = "上传排班")
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String) paramMap.get("hoscode");
        if (StringUtils.checkValNull(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        CheckMD5.checkKey(paramMap, hosSetService.getSignKey(hoscode), "sign");

        scheduleService.save(paramMap);
        return Result.ok();
    }

    @ApiOperation(value = "获取排班分页列表")
    @PostMapping("schedule/list")
    public Result schedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String) paramMap.get("hoscode");
        //非必填
        String depcode = (String) paramMap.get("depcode");
        int page = StringUtils.checkValNull(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.checkValNull(paramMap.get("limit")) ? 10 : Integer.parseInt((String) paramMap.get("limit"));

        if (StringUtils.checkValNull(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        CheckMD5.checkKey(paramMap, hosSetService.getSignKey(hoscode), "sign");

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        Page<Schedule> pageModel = scheduleService.selectPage(page, limit, scheduleQueryVo);
        return Result.ok(pageModel);
    }
    @ApiOperation(value = "删除科室")
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        //必填
        String hosScheduleId = (String)paramMap.get("hosScheduleId");
        if(StringUtils.checkValNull(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        CheckMD5.checkKey(paramMap, hosSetService.getSignKey(hoscode), "sign");
        scheduleService.remove(hoscode, hosScheduleId);
        return Result.ok();
    }
}
