package com.atlzc.yygh.hosp.controller;

import com.atlzc.yygh.common.result.Result;
import com.atlzc.yygh.hosp.service.HospitalService;
import com.atlzc.yygh.model.hosp.Hospital;
import com.atlzc.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "医院管理接口")
@RestController
@RequestMapping("/admin/hosp/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    //医院列表
    @ApiOperation(value = "获取分页列表")
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Integer page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Integer limit,
                        @ApiParam(name = "hospitalQueryVo", value = "查询对象", required = false)
                                    HospitalQueryVo hospitalQueryVo
    ) {
        Page<Hospital> selectHospPage = hospitalService.selectHospPage(page,limit,hospitalQueryVo);
        return Result.ok(selectHospPage);
    }

    //更新医院上线状态
    @ApiOperation(value = "更新上线状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result lock(
            @ApiParam(name = "id", value = "医院id", required = true)
            @PathVariable("id") String id,
            @ApiParam(name = "status", value = "状态（0：未上线 1：已上线）", required = true)
            @PathVariable("status") Integer status){
        hospitalService.updateStatus(id, status);
        return Result.ok();
    }

    //获取医院详情
    @ApiOperation(value = "获取医院详情")
    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail(
            @ApiParam(name = "id", value = "医院id", required = true)
            @PathVariable String id) {
        Map<String, Object> map = hospitalService.getHospById(id);
        return Result.ok(map);
    }

}
