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
public class BankAccountPO {
    /**
     * 账户卡号
     */
    private String number;
    /**
     * 账户名称
     */
    private String name;
    /**
     * 余额
     */
    private BigDecimal balance;
}
