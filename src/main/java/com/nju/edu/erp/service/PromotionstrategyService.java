package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PromotionstrategySheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.vo.PromotionstrategySheetVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PromotionstrategyService {
    /**
     * 制定促销策略单
     */
    void makePromotionstrategySheet(UserVO userVO, PromotionstrategySheetVO promotionstrategyVO);

    /**
     * 获取所有促销策略
     */
    List<PromotionstrategySheetVO> getPromotionstrategy();
}
