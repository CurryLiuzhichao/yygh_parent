package com.atlzc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atlzc.yygh.hosp.repository.HospitalRepository;
import com.atlzc.yygh.hosp.service.HospitalService;
import com.atlzc.yygh.model.hosp.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;


    @Override
    public void save(Map<String, Object> paramMap) {
        //把参数map集合转换对象 Hospital
        Hospital hospital = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Hospital.class);

        //判断是否存在相同数据
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);

        //如果存在，进行修改
        if (hospitalExist != null){
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
        }else { //如果不存在，进行添加
            //0：未上线 1：已上线
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }

    }

    @Override
    public Hospital getByHoscode(String hoscode) {

        return hospitalRepository.getHospitalByHoscode(hoscode);
    }
}
