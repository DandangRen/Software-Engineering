package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.StaffManagePO;
import com.nju.edu.erp.model.vo.StaffManageVO;
import com.nju.edu.erp.service.StaffManageService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/staffManage")
public class StaffManageController {
    private final StaffManageService staffManageService;

    @Autowired
    public StaffManageController(StaffManageService staffManageService) {
        this.staffManageService = staffManageService;
    }

    @PostMapping("/addStaff")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response addStaff(@RequestBody StaffManageVO staffManageVO) {//添加员工
        staffManageService.addStaff(staffManageVO);
        return Response.buildSuccess(staffManageVO);
    }

    @GetMapping("/deleteStaff")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response deleteStaff(@RequestParam(value = "id") String id){//基于id删除员工
        staffManageService.deleteStaff(id);
        return Response.buildSuccess();
    }
    @PostMapping("/updateStaff")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response updateStaff(@RequestBody StaffManageVO staffManageVO){//更新员工信息
        staffManageService.updateStaff(staffManageVO);
        return Response.buildSuccess(staffManageVO);
    }

    @GetMapping("/getAll")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response getAll(){//获得全部信息，前端展示用
        return Response.buildSuccess(staffManageService.getAll());
    }
}
