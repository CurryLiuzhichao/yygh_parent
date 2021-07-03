package com.atlzc.yygh.hosp.service;

import com.atlzc.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {
    //上传医院接口
    void save(Map<String, Object> paramMap);
    //方法实现根据医院编号查询
    Hospital getByHoscode(String hoscode);
}
