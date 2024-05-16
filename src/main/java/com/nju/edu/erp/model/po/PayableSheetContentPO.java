package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayableSheetContentPO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 付款单id
     */
    private String payableSheetId;
    /**
     * 银行账户
     */
    private String bankName;
    /**
     * 转账金额
     */
    private BigDecimal transferAmount;
    /**
     * 备注
     */
    private String remark;
}
