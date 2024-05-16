package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.StaffManageDao;
import com.nju.edu.erp.model.po.StaffManagePO;
import com.nju.edu.erp.model.vo.StaffManageVO;
import com.nju.edu.erp.service.StaffManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StaffManageServiceImpl implements StaffManageService {

    private final StaffManageDao staffManageDao;

    public StaffManageServiceImpl(StaffManageDao staffManageDao) {
        this.staffManageDao = staffManageDao;
    }

    @Override
    public void addStaff(StaffManageVO staffManageVO) { //添加员工
        List<StaffManagePO> list = staffManageDao.findAll();
        boolean isExist = false;
        for (StaffManagePO b : list) {
            if (b.getId().equals(staffManageVO.getId())) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            StaffManagePO staffManagePO = new StaffManagePO();
            BeanUtils.copyProperties(staffManageVO, staffManagePO);
            staffManageDao.createStaff(staffManagePO);
        }
    }

    @Override
    public void deleteStaff(String id) {
        staffManageDao.deleteById(id);
    }//删除员工

    @Override
    public void updateStaff(StaffManageVO staffManageVO) {//更新员工信息
        StaffManagePO staffManagePO = new StaffManagePO();
        BeanUtils.copyProperties(staffManageVO, staffManagePO);
        staffManageDao.updateStaff(staffManagePO);
    }

    @Override
    public List<StaffManageVO> getAll(){//获取全部，前端展示用
        List<StaffManageVO> list = new ArrayList<>();
        for (StaffManagePO po : staffManageDao.findAll()){
            StaffManageVO staffManageVO = new StaffManageVO();
            BeanUtils.copyProperties(po, staffManageVO);
            list.add(staffManageVO);
        }
        return list;
    }

}
