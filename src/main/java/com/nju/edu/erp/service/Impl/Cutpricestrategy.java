package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.model.po.PromotionstrategySheetPO;
import com.nju.edu.erp.model.vo.PromotionstrategySheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.Promotionstrategy;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

//降价
public class Cutpricestrategy implements Promotionstrategy {
    @Override
    public PromotionstrategySheetPO makePromotionstrategySheet(UserVO userVO, PromotionstrategySheetVO promotionstrategySheetVO){
        PromotionstrategySheetPO promotionstrategySheetPO=new PromotionstrategySheetPO();
        BeanUtils.copyProperties(promotionstrategySheetVO,promotionstrategySheetPO);
        promotionstrategySheetPO.setOperator(userVO.getName());
        promotionstrategySheetPO.setDiscount(1.0);
        promotionstrategySheetPO.setGift("无");
        promotionstrategySheetPO.setVoucherAmount(BigDecimal.ZERO);
        promotionstrategySheetPO.setPromotionstrategy(promotionstrategySheetVO.getPromotionstrategy());
        return promotionstrategySheetPO;

    }
}
