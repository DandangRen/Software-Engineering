package com.nju.edu.erp.model.po;


import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalarySheetPO {
    /**
     * 单据编号
     */
    private String sheet_number;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private SalarySheetState state;
    /**
     * 员工编号
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 岗位
     */
    private String role;
    /**
     * 银行账户号
     */
    private String account_number;
    /**
     * 应发工资
     */
    private Integer should_pay = 0;
    /**
     * 扣除税款
     */
    private Integer tax = 0;
    /**
     * 实发金额
     */
    private Integer actual_pay = 0;
}
