package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.finance.BusinessOperationVO;
import com.nju.edu.erp.model.vo.finance.SalesDetailsConditionVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.payable.PayableSheetVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.receivable.ReceivableSheetVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import org.apache.ibatis.ognl.ObjectElementsAccessor;
import org.springframework.stereotype.Service;

import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;

import java.util.Date;
import java.util.List;

@Service
public interface FinanceService {

    /**
     * 制定收款单
     * @param userVO
     * @param receivableSheetVO
     */
    void makeReceivableSheet(UserVO userVO, ReceivableSheetVO receivableSheetVO);

    /**
     * 根据单据状态获取收款单
     * @param state
     * @return
     */
    List<ReceivableSheetVO> getReceivableSheetByState(ReceivableSheetState state);

    /**
     * 审批收款单据
     * @param receivableSheetId
     * @param state
     */
    void approval(UserVO user, String receivableSheetId, ReceivableSheetState state);

    /**
     * 根据收款单Id搜索收款单信息
     * @param receivableSheetId 收款单Id
     * @return 收款单全部信息
     */
    ReceivableSheetVO getReceivableSheetById(String receivableSheetId);

    /**
     * 制定付款单
     * @param userVO
     * @param payableSheetVO
     */
    void makePayableSheet(UserVO userVO, PayableSheetVO payableSheetVO);

    /**
     * 根据单据状态获取付款单
     * @param state
     * @return
     */
    List<PayableSheetVO> getPayableSheetByState(PayableSheetState state);

    /**
     * 审批付款单据
     * @param payableSheetId
     * @param state
     */
    void approval(UserVO user, String payableSheetId, PayableSheetState state);

    /**
     * 根据付款单Id搜索付款单信息
     * @param payableSheetId 付款单Id
     * @return 付款单全部信息
     */
    PayableSheetVO getPayableSheetById(String payableSheetId);


    /**
     * 根据条件查看销售明细表
     * @param  salesDetailsConditionVO 查看销售明细表前置条件
     * @return 销售明细表信息
     */
    List<SalesDetailsPO> getSalesDetails(SalesDetailsConditionVO salesDetailsConditionVO);

    List<SaleSheetVO> getXSD(BusinessCalendarPO businessCalendarPO);

    List<SaleReturnsSheetVO> getXSTHD(BusinessCalendarPO businessCalendarPO);

    List<PurchaseSheetVO> getJHD(BusinessCalendarPO businessCalendarPO);

    List<PurchaseReturnsSheetVO> getJHTHD(BusinessCalendarPO businessCalendarPO);

    List<PayableSheetVO> getFKD (BusinessCalendarPO businessCalendarPO);

    List<ReceivableSheetVO> getSKD(BusinessCalendarPO businessCalendarPO);

    List<BusinessOperationVO> getBusinessOperation(Date beginDate, Date endDate);

    void exportSalesDetailsExcelPaper();

    void exportBusinessOperationExcelPaper();

    void exportXSDExcelPaper();

    void exportXSTHDExcelPaper();

    void exportJHDExcelPaper();

    void exportJHTHDExcelPaper();

    void exportFKDExcelPaper();

    void exportSKDExcelPaper();


}
