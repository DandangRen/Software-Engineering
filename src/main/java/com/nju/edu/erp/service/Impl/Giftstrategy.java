package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.model.po.PromotionstrategySheetPO;
import com.nju.edu.erp.model.vo.PromotionstrategySheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.Promotionstrategy;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

//赠品

public class Giftstrategy implements Promotionstrategy {
    @Override
    public PromotionstrategySheetPO makePromotionstrategySheet(UserVO uservo, PromotionstrategySheetVO promotionstrategySheetVO){
        PromotionstrategySheetPO promotionstrategySheetPO=new PromotionstrategySheetPO();
        BeanUtils.copyProperties(promotionstrategySheetVO,promotionstrategySheetPO);
        promotionstrategySheetPO.setOperator(uservo.getName());
        promotionstrategySheetPO.setDiscount(1.0);
        promotionstrategySheetPO.setVoucherAmount(BigDecimal.ZERO);

        promotionstrategySheetPO.setPromotionstrategy(promotionstrategySheetVO.getPromotionstrategy());
        return promotionstrategySheetPO;
    }
}
