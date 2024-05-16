package com.nju.edu.erp.model.vo.warehouse;

import com.nju.edu.erp.model.vo.ProductInfoVO;
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
public class WarehouseCountingVO {
    /**
     * 库存id
     */
    private Integer id;

    /**
     * 商品编号
     */
    private ProductInfoVO product;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 进价
     */
    private BigDecimal purchasePrice;

    /**
     * 批次
     */
    private Integer batchId;

    /**
     * 出厂日期
     */
    private Date productionDate;




    public Integer get_id(){
        return id;
    }
    public ProductInfoVO get_product(){
        return product;
    }
    public Integer get_quantity(){
        return quantity;
    }
    public BigDecimal get_purchasePrice(){
        return purchasePrice;
    }
    public Integer get_batchId(){
        return batchId;
    }
    public Date get_productionDate(){
        return productionDate;
    }
}
