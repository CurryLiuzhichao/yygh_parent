package com.atlzc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atlzc.yygh.hosp.repository.DepartmentRepository;
import com.atlzc.yygh.hosp.service.DepartmentService;
import com.atlzc.yygh.model.hosp.Department;
import com.atlzc.yygh.vo.hosp.DepartmentQueryVo;

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

    //上传科室接口
    @Override
    public void save(Map<String, Object> paramMap) {
        Department department = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Department.class);

        Department targetDepartment = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());

        if (null != targetDepartment) {
            //copy不为null的值，该方法为自定义方法
//            BeanUtils.copyBean(department, targetDepartment, Department.class);
            targetDepartment.setCreateTime(new Date());
            targetDepartment.setIsDeleted(0);
            departmentRepository.save(targetDepartment);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);

        }
    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        //创建Pageable对象，设置当前页和每页记录数
        Pageable pageable = PageRequest.of(page - 1, limit);
        //创建Example对象
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);

        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        Example<Department> example = Example.of(department, matcher);

        Page<Department> all = departmentRepository.findAll(example,pageable);

        return all;
    }

    //删除科室信息
    @Override
    public void remove(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(null != department) {
            //departmentRepository.delete(department);
            departmentRepository.deleteById(department.getId());
        }

    }
}

