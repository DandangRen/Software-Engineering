package com.nju.edu.erp.service;

import com.nju.edu.erp.model.po.PromotionstrategySheetPO;
import com.nju.edu.erp.model.vo.PromotionstrategySheetVO;
import com.nju.edu.erp.model.vo.UserVO;

public interface Promotionstrategy {
    PromotionstrategySheetPO makePromotionstrategySheet(UserVO uservo, PromotionstrategySheetVO promotionstrategySheetVO);
}
