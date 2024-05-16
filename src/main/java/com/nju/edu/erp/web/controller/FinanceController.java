package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import com.nju.edu.erp.model.po.BusinessCalendarPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.po.SalesDetailsConditionPO;
import com.nju.edu.erp.model.vo.finance.SalesDetailsConditionVO;
import com.nju.edu.erp.model.vo.payable.PayableSheetVO;
import com.nju.edu.erp.model.vo.receivable.ReceivableSheetVO;
import com.nju.edu.erp.service.FinanceService;
import com.nju.edu.erp.service.PurchaseReturnsService;
import com.nju.edu.erp.web.Response;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/finance")
public class FinanceController {
    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    /**
     * 财务制定付款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/payable/sheet-make")
    public Response makePayableOrder(UserVO userVO, @RequestBody PayableSheetVO payableSheetVO)  {
        financeService.makePayableSheet(userVO, payableSheetVO);
        return Response.buildSuccess();
    }

    /**
     * 根据状态查看付款单
     */
    @GetMapping(value = "/payable/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) PayableSheetState state)  {
        return Response.buildSuccess(financeService.getPayableSheetByState(state));
    }


    /**
     * 总经理审批
     * @param payableSheetId 付款单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN, Role.FINANCIAL_STAFF})
    @GetMapping(value = "/payable/approval")
    public Response Approval(UserVO user,
                             @RequestParam("payableSheetId") String payableSheetId,
                             @RequestParam("state") PayableSheetState state)  {
        if(state.equals(PayableSheetState.FAILURE) || state.equals(PayableSheetState.SUCCESS) || state.equals(PayableSheetState.PENDING)) {
            financeService.approval(user, payableSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }


    /**
     * 根据付款单Id搜索收款单信息
     * @param id 付款单Id
     * @return 付款单全部信息
     */
    @GetMapping(value = "/payable/find-sheet")
    public Response findByPayableSheetId(@RequestParam(value = "id") String id)  {
        return Response.buildSuccess(financeService.getPayableSheetById(id));
    }

    /**
     * 财务制定收款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/receivable/sheet-make")
    public Response makeReceivableOrder(UserVO userVO, @RequestBody ReceivableSheetVO receivableSheetVO)  {
        financeService.makeReceivableSheet(userVO, receivableSheetVO);
        return Response.buildSuccess();
    }

    /**
     * 根据状态查看收款单
     */
    @GetMapping(value = "/receivable/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) ReceivableSheetState state)  {
        return Response.buildSuccess(financeService.getReceivableSheetByState(state));
    }


    /**
     * 总经理审批
     * @param receivableSheetId 收款单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN, Role.FINANCIAL_STAFF})
    @GetMapping(value = "/receivable/approval")
    public Response Approval(UserVO user,
                             @RequestParam("receivableSheetId") String receivableSheetId,
                             @RequestParam("state") ReceivableSheetState state)  {
        if(state.equals(ReceivableSheetState.FAILURE) || state.equals(ReceivableSheetState.SUCCESS) || state.equals(ReceivableSheetState.PENDING)) {
            financeService.approval(user, receivableSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000","操作失败"); // code可能得改一个其他的
        }
    }


    /**
     * 根据收款单Id搜索收款单信息
     * @param id 收款单Id
     * @return 收款单全部信息
     */
    @GetMapping(value = "/receivable/find-sheet")
    public Response findByReceivableSheetId(@RequestParam(value = "id") String id)  {
        return Response.buildSuccess(financeService.getReceivableSheetById(id));
    }

    @GetMapping(value="/getSalesDetails")
    public Response getSalesDetails(@RequestParam String beginDate,@RequestParam String endDate,@RequestParam String name,@RequestParam Integer supplier,@RequestParam String operator){
        SalesDetailsConditionVO salesDetailsConditionVO = new SalesDetailsConditionVO();
        salesDetailsConditionVO.setBeginDate(beginDate);
        salesDetailsConditionVO.setEndDate(endDate);
        salesDetailsConditionVO.setName(name);
        salesDetailsConditionVO.setSupplier(supplier);
        salesDetailsConditionVO.setOperator(operator);
        return Response.buildSuccess(financeService.getSalesDetails(salesDetailsConditionVO));
    }

    @GetMapping(value="/getBill")
    public Response getBill(@RequestParam String beginDate,@RequestParam String endDate,@RequestParam String type,@RequestParam Integer supplier,@RequestParam String operator){
        BusinessCalendarPO businessCalendarPO = new BusinessCalendarPO();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            businessCalendarPO.setBeginDate(dateFormat.parse(beginDate));
            businessCalendarPO.setEndDate(dateFormat.parse(endDate));
        }catch (ParseException e){
            e.printStackTrace();
        }
        businessCalendarPO.setOperator(operator);
        businessCalendarPO.setSupplier(supplier);


        if(type.equals("XSD")){
            return Response.buildSuccess(financeService.getXSD(businessCalendarPO));
        }else if(type.equals("XSTHD")){
            return Response.buildSuccess(financeService.getXSTHD(businessCalendarPO));
        }else if(type.equals("JHD")){
            return Response.buildSuccess(financeService.getJHD(businessCalendarPO));

        }else if(type.equals("JHTHD")){
            return Response.buildSuccess(financeService.getJHTHD(businessCalendarPO));

        }else if(type.equals("FKD")){
            return Response.buildSuccess(financeService.getFKD(businessCalendarPO));

        }else if(type.equals("SKD")){
            return Response.buildSuccess(financeService.getSKD(businessCalendarPO));
        }

        return Response.buildSuccess();
    }
    @GetMapping(value = "/getBusinessOperation")
    public Response getBusinessOperation(@RequestParam String beginTime,@RequestParam String endTime){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Date endDate = new Date();
        try{
            beginDate = dateFormat.parse(beginTime);
            endDate = dateFormat.parse(endTime);
        }catch(ParseException e){
            e.printStackTrace();
        }



        return Response.buildSuccess(financeService.getBusinessOperation(beginDate,endDate));
    }

    @GetMapping(value = "/exportExcelPaper")
    public Response exportExcelPaper(@RequestParam String type){
        switch (type){
            case "销售明细表":
                financeService.exportSalesDetailsExcelPaper();
                break;
            case "经营情况表":
                financeService.exportBusinessOperationExcelPaper();
                break;
            case "销售单历程表" :
                financeService.exportXSDExcelPaper();
                break;
            case "销售退货单历程表":
                financeService.exportXSTHDExcelPaper();
                break;
            case "进货单历程表":   
                financeService.exportJHDExcelPaper();
                break;
            case "进货退货单历程表":    
                financeService.exportJHTHDExcelPaper();
                break;
            case "付款单历程表":
                financeService.exportFKDExcelPaper();
                break;
            case "收款单历程表":
                financeService.exportSKDExcelPaper();
                break;
        }


        return Response.buildSuccess();
    }

}


//beginDate:'',
//        endDate:'',
//        name:'',
//        supplier:0,
//        operator:'',