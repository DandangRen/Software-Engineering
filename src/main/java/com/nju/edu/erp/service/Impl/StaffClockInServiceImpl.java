package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.StaffClockInDao;
import com.nju.edu.erp.model.po.StaffClockInPO;
import com.nju.edu.erp.model.vo.StaffClockInVO;
import com.nju.edu.erp.service.StaffClockInService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StaffClockInServiceImpl implements StaffClockInService {
    private final StaffClockInDao staffClockInDao;

    public StaffClockInServiceImpl(StaffClockInDao staffClockInDao) {
        this.staffClockInDao = staffClockInDao;
    }

    @Override
    public void add_clock_in(StaffClockInVO staffClockInVO) {//向签到表中添加
        List<StaffClockInPO> list = staffClockInDao.findAll();
        boolean isExist = false;
        for (StaffClockInPO b : list) {
            if (b.getName().equals(staffClockInVO.getName())) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            StaffClockInPO staffClockInPO = new StaffClockInPO();
            BeanUtils.copyProperties(staffClockInVO, staffClockInPO);
            staffClockInDao.create_clock_in(staffClockInPO);
        }
    }

    @Override
    public void monthly_clean() {//每月清零初始化
        staffClockInDao.monthly_clean();
    }

    @Override
    public void deleteById(String id) {
        staffClockInDao.deleteById(id);
    }//删除员工打卡记录

    @Override
    public void clock_in(StaffClockInVO staffClockInVO) {//签到
        StaffClockInPO staffClockInPO = new StaffClockInPO();
        BeanUtils.copyProperties(staffClockInVO, staffClockInPO);
        staffClockInDao.clock_in(staffClockInPO);
    }

    @Override
    public List<StaffClockInVO> getAll(){//获得全部信息，前端展示用
        List<StaffClockInVO> list = new ArrayList<>();
        for (StaffClockInPO po : staffClockInDao.findAll()){
            StaffClockInVO staffClockInVO = new StaffClockInVO();
            BeanUtils.copyProperties(po, staffClockInVO);
            list.add(staffClockInVO);
        }
        return list;
    }

}
