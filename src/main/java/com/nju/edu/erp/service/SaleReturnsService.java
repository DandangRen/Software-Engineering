package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SaleReturnsService {
    @Transactional
    void makeSaleReturnsSheet(UserVO userVO, SaleReturnsSheetVO saleReturnsSheetVO);

    List<SaleReturnsSheetVO> getSaleReturnsSheetByState(SaleReturnsSheetState state);

    @Transactional
    void approval(String saleReturnsSheetId, SaleReturnsSheetState state);

    SaleReturnsSheetVO getSaleReturnsSheetByStateId(String Id);

}
