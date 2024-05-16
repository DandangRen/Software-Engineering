package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.StaffClockInPO;
import com.nju.edu.erp.model.vo.StaffClockInVO;
import com.nju.edu.erp.service.StaffClockInService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/staffClock")
public class StaffClockInController {
    private final StaffClockInService staffClockInService;

    @Autowired
    public StaffClockInController(StaffClockInService staffClockInService) {
        this.staffClockInService = staffClockInService;
    }

    @PostMapping("/addClockIn")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response add_clock_in(@RequestBody StaffClockInVO staffClockInVO) {//向签到表中添加人员
        staffClockInService.add_clock_in(staffClockInVO);
        return Response.buildSuccess(staffClockInVO);
    }

    @PostMapping("/monthlyClean")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response monthly_clean(){//每月清零初始化
        staffClockInService.monthly_clean();
        return Response.buildSuccess();
    }

    @PostMapping("/clockIn")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response clock_in(@RequestBody StaffClockInVO staffClockInVO){//签到
        staffClockInService.clock_in(staffClockInVO);
        return Response.buildSuccess(staffClockInVO);
    }

    @GetMapping("/deleteClockIn")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response deleteClockIn(@RequestParam(value = "id") String id){//基于id删除员工打卡记录
        staffClockInService.deleteById(id);
        return Response.buildSuccess();
    }

    @GetMapping("/getAllClockIn")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response getAll(){//获得全部信息，前端展示用
        return Response.buildSuccess(staffClockInService.getAll());
    }
}
