package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PromotionstrategySheetState;
import com.nju.edu.erp.model.vo.PromotionstrategySheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.PromotionstrategyService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/promotion")
public class PromotionstrategyController {
    private final PromotionstrategyService promotionstrategyService;


    @Autowired
    public PromotionstrategyController(PromotionstrategyService promotionstrategyService) {
        this.promotionstrategyService = promotionstrategyService;
    }

    /**
     * 总经理制定促销策略单
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makePurchaseOrder(UserVO userVO, @RequestBody PromotionstrategySheetVO promotionstrategyVO)  {
        promotionstrategyService.makePromotionstrategySheet(userVO, promotionstrategyVO);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/sheet-show")
    public Response showSheet(){
        return Response.buildSuccess(promotionstrategyService.getPromotionstrategy());
    }
}
