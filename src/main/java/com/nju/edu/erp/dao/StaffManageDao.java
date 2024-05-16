package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.StaffManagePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StaffManageDao {
    int createStaff(StaffManagePO StaffMangePO);//添加员工
    int updateStaff(StaffManagePO StaffMangePO);//更新员工信息
    int deleteById(String id);//基于员工编号删除

    List<StaffManagePO> findAll();//获得全部信息，前端展示用
}
