package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.StaffClockInDao;
import com.nju.edu.erp.model.po.StaffClockInPO;
import com.nju.edu.erp.model.po.StaffManagePO;
import com.nju.edu.erp.model.vo.StaffClockInVO;
import com.nju.edu.erp.model.vo.StaffManageVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class StaffClockInServiceTest {//员工打卡单元测试
    @Autowired
    StaffClockInDao staffClockInDao;
    @Autowired
    public StaffClockInService staffClockInService;

    @Test
    @Transactional
    @Rollback(value = true)
    public void add_test(){//测试添加打卡是否成功
        StaffClockInVO staffClockInVO = new StaffClockInVO("12138","sky666","2022-07-09",
                2);
        StaffClockInPO staffClockInPO = new StaffClockInPO("12138","sky666","2022-07-09",
                2);
        staffClockInService.add_clock_in(staffClockInVO);
        Assertions.assertEquals(staffClockInPO,staffClockInDao.findAll().get(0));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void delete_test(){//测试删除打卡是否成功
        StaffClockInVO staffClockInVO = new StaffClockInVO("12138","sky666","20220709",
                5);
        StaffClockInPO staffClockInPO = new StaffClockInPO("12138","sky666","2022-07-09",
                5);
        staffClockInService.add_clock_in(staffClockInVO);
        staffClockInService.deleteById("12138");
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void clock_in_test(){//测试员工打卡是否成功
        StaffClockInVO staffClockInVO = new StaffClockInVO("12138","sky666","20220709",
                5);
        StaffClockInPO staffClockInPO = new StaffClockInPO("12138","sky666","2022-07-10",
                6);
        staffClockInService.add_clock_in(staffClockInVO);
        staffClockInService.clock_in(staffClockInVO);
        Assertions.assertEquals(staffClockInPO,staffClockInDao.findAll().get(0));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void find_test(){//测试查找全部是否成功
        StaffClockInVO staffClockInVO = new StaffClockInVO("12138","sky666","2022-07-09",
                2);
        StaffClockInPO staffClockInPO = new StaffClockInPO("12138","sky666","2022-07-09",
                2);
        staffClockInService.add_clock_in(staffClockInVO);
        Assertions.assertEquals(staffClockInPO,staffClockInDao.findAll().get(0));
    }

}
