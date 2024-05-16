package com.nju.edu.erp.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoVO {

    /**
     * 商品id
     */
    private String id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 商品型号
     */
    private String type;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     *  进价
     */
    private BigDecimal purchasePrice;

    /**
     *  零售价
     */
    private BigDecimal retailPrice;

    /**
     *  最近进价
     */
    private BigDecimal recentPp;

    /**
     *  最近零售价
     */
    private BigDecimal recentRp;



    public String get_id(){
        return id;
    }
    public String get_name(){
        return name;
    }
    public Integer get_categoryId(){
        return categoryId;
    }
    public String get_type(){
        return type;
    }
    public Integer get_quantity(){
        return quantity;
    }
    public BigDecimal get_purchasePrice(){
        return purchasePrice;
    }
    public BigDecimal get_retailPrice(){
        return retailPrice;
    }
    public BigDecimal get_recentPp(){
        return recentPp;
    }
    public BigDecimal get_recentRp(){
        return recentRp;
    }
}
