package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.model.po.PromotionstrategySheetPO;
import com.nju.edu.erp.model.vo.PromotionstrategySheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.Promotionstrategy;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

//代金券
public class Voucherstrategy implements Promotionstrategy {
    @Override
    public PromotionstrategySheetPO makePromotionstrategySheet(UserVO uservo, PromotionstrategySheetVO promotionstrategySheetVO){
        PromotionstrategySheetPO promotionstrategySheetPO=new PromotionstrategySheetPO();
        BeanUtils.copyProperties(promotionstrategySheetVO,promotionstrategySheetPO);
        promotionstrategySheetPO.setOperator(uservo.getName());
        promotionstrategySheetPO.setDiscount(1.0);
        promotionstrategySheetPO.setGift("无");
        promotionstrategySheetPO.setPromotionstrategy(promotionstrategySheetVO.getPromotionstrategy());
        return promotionstrategySheetPO;
    }
}
