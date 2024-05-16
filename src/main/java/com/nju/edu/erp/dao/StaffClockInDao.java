package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.StaffClockInPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StaffClockInDao {
    int create_clock_in(StaffClockInPO StaffClockInPO);//创建签到表

    int monthly_clean();//每月清零初始化

    int clock_in(StaffClockInPO StaffClockInPO);//签到

    int deleteById(String id);//基于员工编号删除

    List<StaffClockInPO> findAll();//获取全部，前端展示用
}
