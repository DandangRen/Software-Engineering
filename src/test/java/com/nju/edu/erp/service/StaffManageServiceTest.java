package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.StaffManageDao;
import com.nju.edu.erp.model.po.StaffManagePO;
import com.nju.edu.erp.model.vo.StaffManageVO;
import com.nju.edu.erp.service.Impl.StaffManageServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class StaffManageServiceTest {//员工管理单元测试
    @Autowired
    StaffManageDao staffManageDao;
    @Autowired
    public StaffManageService staffManageService;

    @Test
    @Transactional
    @Rollback(value = true)
    public void add_test(){//测试添加员工是否成功
        StaffManageVO staffManageVO = new StaffManageVO("12138","sky666","男",
                "20000101","13333333333","SALE_MANAGER",8000,
                5000,"3","Dynamic","卡","65555555555555555");
        StaffManagePO staffManagePO = new StaffManagePO("12138","sky666","男",
                "20000101","13333333333","SALE_MANAGER",8000,
                5000,"3","Dynamic","卡","65555555555555555");
        staffManageService.addStaff(staffManageVO);
        Assertions.assertEquals(staffManagePO,staffManageDao.findAll().get(0));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void delete_test(){//测试删除员工是否成功
        StaffManageVO staffManageVO = new StaffManageVO("12138","sky666","男",
                "20000101","13333333333","SALE_MANAGER",8000,
                5000,"3","Dynamic","卡","65555555555555555");
        StaffManagePO staffManagePO = new StaffManagePO("12138","sky666","男",
                "20000101","13333333333","SALE_MANAGER",8000,
                5000,"3","Dynamic","卡","65555555555555555");
        staffManageService.addStaff(staffManageVO);
        staffManageService.deleteStaff("12138");
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void update_test(){//测试更新员工是否成功
        StaffManageVO staffManageVO1 = new StaffManageVO("12138","sky666","男",
                "20000101","13333333333","SALE_MANAGER",8000,
                5000,"3","Dynamic","卡","65555555555555555");
        StaffManageVO staffManageVO2 = new StaffManageVO("12138","sky666","男",
                "20000101","13333333333","SALE_MANAGER",99999,
                5000,"3","Dynamic","卡","65555555555555555");
        StaffManagePO staffManagePO = new StaffManagePO("12138","sky666","男",
                "20000101","13333333333","SALE_MANAGER",99999,
                5000,"3","Dynamic","卡","65555555555555555");
        staffManageService.addStaff(staffManageVO1);
        staffManageService.updateStaff(staffManageVO2);
        Assertions.assertEquals(staffManagePO,staffManageDao.findAll().get(0));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void find_test(){//测试查找全部是否成功
        StaffManageVO staffManageVO = new StaffManageVO("12138","sky666","男",
                "20000101","13333333333","SALE_MANAGER",8000,
                5000,"3","Dynamic","卡","65555555555555555");
        StaffManagePO staffManagePO = new StaffManagePO("12138","sky666","男",
                "20000101","13333333333","SALE_MANAGER",8000,
                5000,"3","Dynamic","卡","65555555555555555");
        staffManageService.addStaff(staffManageVO);
        Assertions.assertEquals(staffManagePO,staffManageDao.findAll().get(0));
    }

}
