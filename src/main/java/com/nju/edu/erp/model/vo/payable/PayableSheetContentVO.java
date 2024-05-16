package com.nju.edu.erp.model.vo.payable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayableSheetContentVO {
    /**
     * 自增id, 新建单据时前端传null
     */
    private Integer id;
    /**
     * 付款单id, 新建单据时前端传null
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
