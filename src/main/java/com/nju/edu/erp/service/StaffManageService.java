package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.StaffManageVO;
import org.springframework.stereotype.Service;
import com.nju.edu.erp.model.po.StaffManagePO;

import java.util.List;

@Service
public interface StaffManageService {

    void addStaff(StaffManageVO staffManageVO);

    void deleteStaff(String id);

    void updateStaff(StaffManageVO staffManageVO);

    List<StaffManageVO> getAll();

}
