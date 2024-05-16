package com.nju.edu.erp.model.vo.receivable;

import com.nju.edu.erp.model.po.BankAccountPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceivableSheetContentVO {
    /**
     * 自增id, 新建单据时前端传null
     */
    private Integer id;
    /**
     * 收款单id, 新建单据时前端传null
     */
    private String receivableSheetId;
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
