package com.nju.edu.erp.model.po;

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
public class BusinessCalendarPO {
    /**
     * 起始日期
     */
    private Date beginDate;


    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 客户
     */
    private Integer supplier;

    /**
     * 操作员
     */
    private String operator;

}
