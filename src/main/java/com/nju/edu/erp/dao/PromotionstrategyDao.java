package com.nju.edu.erp.dao;


import com.nju.edu.erp.enums.sheetState.PromotionstrategySheetState;

import com.nju.edu.erp.model.po.PromotionstrategySheetPO;

import com.nju.edu.erp.model.po.ReceivableSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface PromotionstrategyDao {


    /**
     * 存入一条促销策略单记录

     */
    int saveSheet(PromotionstrategySheetPO toSave);


    /**
     * 查找所有促销策略单
     */
    List<PromotionstrategySheetPO> findAllSheet();

    /**
     * 查找指定id的促销策略单

     */
    PromotionstrategySheetPO findSheetById(String id);


}
