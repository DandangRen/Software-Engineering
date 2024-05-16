package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.StaffClockInVO;
import com.nju.edu.erp.model.vo.StaffManageVO;
import org.springframework.stereotype.Service;
import com.nju.edu.erp.model.po.StaffClockInPO;

import java.util.List;

@Service
public interface StaffClockInService {
    void add_clock_in(StaffClockInVO staffClockInVO);

    void monthly_clean();

    void clock_in(StaffClockInVO staffClockInVO);

    void deleteById(String id);

    List<StaffClockInVO> getAll();

}
