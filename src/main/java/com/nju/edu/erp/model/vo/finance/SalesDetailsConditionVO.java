package com.nju.edu.erp.model.vo.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesDetailsConditionVO {
    /**
     * 起始日期
     */
    private String beginDate;


    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 商品名
     */
    private String name;

    /**
     * 客户
     */
    private Integer supplier;

    /**
     * 操作员
     */
    private String operator;

}
