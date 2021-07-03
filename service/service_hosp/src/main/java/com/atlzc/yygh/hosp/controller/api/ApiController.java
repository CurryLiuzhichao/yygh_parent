package com.atlzc.yygh.hosp.controller.api;

import com.atlzc.yygh.common.exception.YyghException;
import com.atlzc.yygh.common.helper.HttpRequestHelper;
import com.atlzc.yygh.common.result.Result;
import com.atlzc.yygh.common.result.ResultCodeEnum;
import com.atlzc.yygh.common.utils.MD5;
import com.atlzc.yygh.hosp.service.DepartmentService;
import com.atlzc.yygh.hosp.service.HospitalService;
import com.atlzc.yygh.hosp.service.HospitalSetService;
import com.atlzc.yygh.hosp.service.ScheduleService;
import com.atlzc.yygh.model.hosp.Department;
import com.atlzc.yygh.model.hosp.Hospital;
import com.atlzc.yygh.model.hosp.Schedule;
import com.atlzc.yygh.vo.hosp.DepartmentQueryVo;

import com.atlzc.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api(tags = "医院管理API接口")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    //删除排班
    @ApiOperation(value = "删除科室")
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        //必填
        String hosScheduleId = (String)paramMap.get("hosScheduleId");
        if(StringUtils.isEmpty(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.remove(hoscode, hosScheduleId);
        return Result.ok();
    }


    //获取排班信息
    @ApiOperation(value = "获取排班分页列表")
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        //非必填
        String depcode = (String)paramMap.get("depcode");
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));

        if(StringUtils.isEmpty(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page , limit, scheduleQueryVo);
        return Result.ok(pageModel);
    }


    //上传排班接口
    @ApiOperation(value = "上传排班")
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        if(StringUtils.isEmpty(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.save(paramMap);
        return Result.ok();

    }

    //删除科室接口
    @ApiOperation(value = "删除科室")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());

        String hoscode = (String)paramMap.get("hoscode");
        //必填
        String depcode = (String)paramMap.get("depcode");
        //签名校验
        if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.remove(hoscode, depcode);
        return Result.ok();
    }

        //查询科室
    @ApiOperation(value = "获取分页列表")
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String) paramMap.get("hoscode");

        String depcode = (String)paramMap.get("depcode");
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));



        //签名校验
        if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }


        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        departmentQueryVo.setDepcode(depcode);
        Page<Department> pageModel = departmentService.findPageDepartment(page, limit, departmentQueryVo);
        return Result.ok(pageModel);


    }

    //上传科室接口
    @ApiOperation(value = "上传科室信息")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hoscode = (String) paramMap.get("hoscode");

        //1、获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");

        //2、根据传递过来的医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3、把数据库查到的签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        //4、判断签名是否一致
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //调用service方法
        departmentService.save(paramMap);
        return Result.ok();
    }

    //查询医院接口
    @ApiOperation(value = "获取医院信息")
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        String hoscode = (String) paramMap.get("hoscode");

        //1、获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");

        //2、根据传递过来的医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3、把数据库查到的签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        //4、判断签名是否一致
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //调用service 方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }

    //上传医院接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        //获取传递过来医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //1、获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");

        //2、根据传递过来的医院编码，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3、把数据库查到的签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        //4、判断签名是否一致
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoDataString = (String) paramMap.get("logoData");
        String logoData = logoDataString.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);

        //调用service的方法
        hospitalService.save(paramMap);
        return Result.ok();
    }


}
