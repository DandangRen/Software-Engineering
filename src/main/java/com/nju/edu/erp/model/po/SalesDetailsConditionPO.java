package com.nju.edu.erp.model.po;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesDetailsConditionPO {
    //query:{//时间区间，商品名，客户，业务员，仓库
    //        beginDate:'',
    //        endDate:'',
    //        name:'',
    //        customer:'',
    //        operator:'',
    //        warehouse:''
    //      },
    /**
     * 起始日期
     */
    private Date beginDate;


    /**
     * 结束日期
     */
    private Date endDate;

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
