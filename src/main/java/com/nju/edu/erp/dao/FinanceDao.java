package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.po.BusinessCalendarPO;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.SalesDetailsConditionPO;
import com.nju.edu.erp.model.po.SalesDetailsPO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.payable.PayableSheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.receivable.ReceivableSheetVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.nju.edu.erp.model.po.SaleSheetPO;
import com.nju.edu.erp.model.po.SaleReturnsSheetPO;
import com.nju.edu.erp.model.po.PurchaseSheetPO;
import com.nju.edu.erp.model.po.PurchaseReturnsSheetPO;
import com.nju.edu.erp.model.po.PayableSheetPO;
import com.nju.edu.erp.model.po.ReceivableSheetPO;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface FinanceDao {
    /**
     * 根据条件查看销售明细表
     * @param  salesDetailsConditionPO 查看销售明细表前置条件
     * @return 销售明细表信息
     */
    List<SalesDetailsPO> getSalesDetails(SalesDetailsConditionPO salesDetailsConditionPO);

    List<SaleSheetPO> getXSD(BusinessCalendarPO businessCalendarPO);

    List<SaleReturnsSheetPO> getXSTHD(BusinessCalendarPO businessCalendarPO);

    List<PurchaseSheetPO> getJHD(BusinessCalendarPO businessCalendarPO);

    List<PurchaseReturnsSheetPO> getJHTHD(BusinessCalendarPO businessCalendarPO);

    List<PayableSheetPO> getFKD (BusinessCalendarPO businessCalendarPO);

    List<ReceivableSheetPO> getSKD(BusinessCalendarPO businessCalendarPO);

    List<SaleSheetPO> getSaleSheetByDate(Date beginDate,Date endDate);

    List<SaleReturnsSheetPO>  getSaleReturnsSheetByDate(Date beginDate,Date endDate);
}
