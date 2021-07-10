package com.atlzc.yygh.hosp.service;

import com.atlzc.yygh.model.hosp.Hospital;
import com.atlzc.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface HospitalService {
    //上传医院接口
    void save(Map<String, Object> paramMap);
    //方法实现根据医院编号查询
    Hospital getByHoscode(String hoscode);
    //医院列表
    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);
    //更新医院上线状态
    void updateStatus(String id, Integer status);
    //获取医院详情
    Map<String, Object> getHospById(String id);
    //获取医院名称
    String getHospName(String hoscode);
}
