package com.atlzc.yygh.cmn.client;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 数据字典API接口
 */
@FeignClient("service-cmn")
@Repository
public interface DictFeignClient {

    //根据Dictcode和value查询
    @ApiOperation(value = "获取数据字典名称")
    @GetMapping(value = "/admin/cmn/dict/getName/{parentDictCode}/{value}")
    public String getName(
            @ApiParam(name = "parentDictCode", value = "上级编码", required = true)
            @PathVariable("parentDictCode") String parentDictCode,

            @ApiParam(name = "value", value = "值", required = true)
            @PathVariable("value") String value);

    //根据value查询
    @ApiOperation(value = "获取数据字典名称")
    @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "Long", paramType = "path")
    @GetMapping(value = "/admin/cmn/dict/getName/{value}")
    public String getName(
            @ApiParam(name = "value", value = "值", required = true)
            @PathVariable("value") String value);

}
