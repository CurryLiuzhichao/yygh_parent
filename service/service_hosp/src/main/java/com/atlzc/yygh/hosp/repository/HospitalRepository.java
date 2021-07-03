package com.atlzc.yygh.hosp.repository;

import com.atlzc.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HospitalRepository extends MongoRepository<Hospital,String> {
    //判断是否存在数据
    Hospital getHospitalByHoscode(String hoscode);
}
