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
public class BusinessOperationVO {
    /**
     * 总收入
     */
    Integer income;

    /**
     * 折让
     */
    Integer discount;

    /**
     * 总支出
     */
    Integer expenditure;

    /**
     * 总利润
     */
    Integer profit;
}
