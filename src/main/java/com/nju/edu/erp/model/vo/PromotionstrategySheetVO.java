package com.nju.edu.erp.model.vo;

import com.nju.edu.erp.enums.sheetState.PromotionstrategySheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.service.Promotionstrategy;
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
public class PromotionstrategySheetVO {
    /**
     * 促销策略单据编号
     */
    private String id;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 折让
     */
    private double discount;
    /**
     * 使用代金券总额
     */
    private BigDecimal voucherAmount;
    /**
     * 赠品
     */
    private String gift;
    /**
     * 促销策略
     */
    private int promotionstrategy;
}
