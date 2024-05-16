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
public class SalesDetailsPO {
    ////时间（精确到天），商品名， 型号，数量，单价，总额。需要支持导出数据。
    //      result:{
    //        date:'',
    //        name:'',
    //        type:'',
    //        quantity:'',
    //        price:'',
    //        all_price:''
    //      }
    /**
     * 日期
     */
    private Date create_time;   //看看要不要改成 Date类

    /**
     * 商品名
     */
    private String name;

    /**
     *   型号
     */
    private String type;

    /**
     *  数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal unit_price;

    /**
     * 总价
     */
    private BigDecimal total_price  ;

}
