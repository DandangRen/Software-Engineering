package com.nju.edu.erp.model.po;


import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayableSheetPO {
    //单据编号（FKD-yyyyMMdd-xxxxx），
    //客户（同时包含供应商和销售商），操作员（当前登录用户），转账列表，总额汇总
    /**
     * 付款单单据编号（格式为：FKD-yyyyMMdd-xxxxx
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
     * 总额合计
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态
     */
    private PayableSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
