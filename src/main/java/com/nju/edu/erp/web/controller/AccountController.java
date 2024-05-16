package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.BankAccountPO;
import com.nju.edu.erp.model.vo.BankAccountVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/addAccount")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.ADMIN})
    public Response addAccount(@RequestBody BankAccountVO bankAccountVO) {
        accountService.addAccount(bankAccountVO);
        return Response.buildSuccess(bankAccountVO);
    }

    @GetMapping("/deleteAccount")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.ADMIN})
    public Response deleteAccount(@RequestParam(value = "number") String accountNumber){
        accountService.deleteAccount(accountNumber);
        return Response.buildSuccess();
    }
    @PostMapping("/updateAccount")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.ADMIN})
    public Response updateAccount(@RequestBody BankAccountVO bankAccountVO){
        accountService.updateAccount(bankAccountVO);
        return Response.buildSuccess(bankAccountVO);
    }
    @GetMapping("/getAccount")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.ADMIN})
    public Response getAccount(@RequestParam String accountNumber){
        return Response.buildSuccess(accountService.getAccount(accountNumber));
    }

    @GetMapping("/getAll")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.ADMIN})
    public Response getAll(){
        return Response.buildSuccess(accountService.getAll());
    }

    @GetMapping("/getAlmostByNumber")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.ADMIN})
    public Response getAlmostByNumber(@RequestParam(value = "number") String accountNumber){
        return Response.buildSuccess(accountService.getAlmostByNumber(accountNumber));
    }

    @GetMapping("/getAlmostByName")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.ADMIN})
    public Response getAlmostByName(@RequestParam(value = "name") String accountName){
        return Response.buildSuccess(accountService.getAlmostByName(accountName));
    }

}
