package com.nju.edu.erp.model.vo.receivable;

import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.ReceivableSheetContentPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceivableSheetVO {
    /**
     * 收款单单据编号（格式为：SKD-yyyyMMdd-xxxxx, 新建单据时前端传null
     */
    private String id;
    /**
     * 客户id（包含供应商和销售商）
     */
    private Integer customer;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 总额合计, 新建单据时前端传null
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private ReceivableSheetState state;
    /**
     * 收款单具体内容
     */
    List<ReceivableSheetContentVO> receivableSheetContent;
}
