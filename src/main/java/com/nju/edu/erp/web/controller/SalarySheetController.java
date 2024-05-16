package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.vo.SalarySheetVO;
import com.nju.edu.erp.service.SalarySheetService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/salarySheet")
public class SalarySheetController {
    private final SalarySheetService salarySheetService;

    @Autowired
    public SalarySheetController(SalarySheetService salarySheetService) {
        this.salarySheetService = salarySheetService;
    }

    @PostMapping("/addSalarySheet")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response addSalarySheet(@RequestBody SalarySheetVO salarySheetVO) {//添加工资单
       salarySheetService.addSalarySheet(salarySheetVO);
        return Response.buildSuccess(salarySheetVO);
    }

    @GetMapping("/deleteSalarySheet")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response deleteById(@RequestParam(value = "id") String id){//基于id删除员工工资单
        salarySheetService.deleteById(id);
        return Response.buildSuccess();
    }
    @PostMapping("/updateSalary")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response updateSalary(@RequestBody SalarySheetVO salarySheetVO){//更新工资单信息
        salarySheetService.updateSalary(salarySheetVO);
        return Response.buildSuccess(salarySheetVO);
    }

    @GetMapping("/getAll")
    @Authorized(roles = {Role.HR, Role.ADMIN})
    public Response getAll(){//获得全部信息，前端展示用
        return Response.buildSuccess(salarySheetService.getAll());
    }

    /**
     * 人力资源人员审批
     * @param sheet_number 工资单id
     * @param state 修改后的状态("审批失败"/"待二级审批")
     */
    @GetMapping(value = "/first-approval")
    @Authorized (roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response firstApproval(@RequestParam("sheet_number") String sheet_number,
                                  @RequestParam("state") SalarySheetState state)  {
        if(state.equals(SalarySheetState.FAILURE) || state.equals(SalarySheetState.PENDING_LEVEL_2)) {
            salarySheetService.approval(sheet_number, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 总经理审批
     * @param sheet_number 工资单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("sheet_number") String sheet_number,
                                   @RequestParam("state") SalarySheetState state)  {
        if(state.equals(SalarySheetState.FAILURE) || state.equals(SalarySheetState.SUCCESS)) {
            salarySheetService.approval(sheet_number, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 根据状态查看工资单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) SalarySheetState state)  {
        return Response.buildSuccess(salarySheetService.getSalarySheetByState(state));
    }
}
