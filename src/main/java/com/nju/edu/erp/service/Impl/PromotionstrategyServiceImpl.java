package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.PromotionstrategyDao;
import com.nju.edu.erp.model.po.PromotionstrategySheetPO;
import com.nju.edu.erp.model.vo.PromotionstrategySheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.PromotionstrategyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Service
public class PromotionstrategyServiceImpl implements PromotionstrategyService {

    private final PromotionstrategyDao promotionstrategyDao;
    private Cutpricestrategy cutpricestrategy;
    private Discountstrategy discountstrategy;
    private Giftstrategy giftstrategy;
    private Voucherstrategy voucherstrategy;
    @Autowired
    public PromotionstrategyServiceImpl(PromotionstrategyDao promotionstrategyDao){
        this.promotionstrategyDao=promotionstrategyDao;
    }

    /**
    根据不同策略创建不同的策略销售表
    */
    @Override
    @Transactional
    public void makePromotionstrategySheet(UserVO userVO, PromotionstrategySheetVO promotionstrategyVO){
        int promotionstrategy_int = promotionstrategyVO.getPromotionstrategy();
        PromotionstrategySheetPO promotionstrategySheetPO=new PromotionstrategySheetPO();
        BeanUtils.copyProperties(promotionstrategyVO,promotionstrategySheetPO);
        if(promotionstrategy_int == 3){
            promotionstrategySheetPO.setOperator(userVO.getName());
            promotionstrategySheetPO.setDiscount(1.0);
            promotionstrategySheetPO.setGift("无");
            promotionstrategySheetPO.setPromotionstrategy(promotionstrategyVO.getPromotionstrategy());
        }else if(promotionstrategy_int == 2){
            promotionstrategySheetPO.setOperator(userVO.getName());
            promotionstrategySheetPO.setGift("无");
            promotionstrategySheetPO.setVoucherAmount(BigDecimal.ZERO);
            promotionstrategySheetPO.setPromotionstrategy(promotionstrategyVO.getPromotionstrategy());
        }else if(promotionstrategy_int == 1){
            promotionstrategySheetPO.setOperator(userVO.getName());
            promotionstrategySheetPO.setDiscount(1.0);
            promotionstrategySheetPO.setVoucherAmount(BigDecimal.ZERO);
            promotionstrategySheetPO.setPromotionstrategy(promotionstrategyVO.getPromotionstrategy());
        }else{
            promotionstrategySheetPO.setOperator(userVO.getName());
            promotionstrategySheetPO.setDiscount(1.0);
            promotionstrategySheetPO.setGift("无");
            promotionstrategySheetPO.setVoucherAmount(BigDecimal.ZERO);
            promotionstrategySheetPO.setPromotionstrategy(promotionstrategyVO.getPromotionstrategy());
        }
        promotionstrategyDao.saveSheet(promotionstrategySheetPO);
    }

    @Override
    @Transactional
    public List<PromotionstrategySheetVO> getPromotionstrategy(){
        List<PromotionstrategySheetVO> res=new ArrayList<>();
        List<PromotionstrategySheetPO> all = promotionstrategyDao.findAllSheet();
        for (PromotionstrategySheetPO po:all){
            PromotionstrategySheetVO vo = new PromotionstrategySheetVO();
            BeanUtils.copyProperties(po,vo);
            res.add(vo);
        }
        return res;
    }

}
