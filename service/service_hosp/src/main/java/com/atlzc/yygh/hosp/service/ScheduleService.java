package com.atlzc.yygh.hosp.service;

import com.atlzc.yygh.model.hosp.Schedule;
import com.atlzc.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ScheduleService {
    //上传排班接口
    void save(Map<String, Object> paramMap);
    //获取排班信息
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);
    //删除排班
    void remove(String hoscode, String hosScheduleId);
}
