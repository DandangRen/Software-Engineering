package com.nju.edu.erp.dao;



import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import com.nju.edu.erp.model.po.ReceivableSheetContentPO;
import com.nju.edu.erp.model.po.ReceivableSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ReceivableSheetDao {
    /**
     * 获取最近一条收款单
     * @return 最近一条进货单
     */
    ReceivableSheetPO getLatest();

    /**
     * 存入一条收款单记录
     * @param toSave 一条收款单记录
     * @return 影响的行数
     */
    int save(ReceivableSheetPO toSave);

    /**
     * 把收款单上的具体内容存入数据库
     * @param receivableSheetContent 收款单上的具体内容
     */
    void saveBatch(List<ReceivableSheetContentPO> receivableSheetContent);

    /**
     * 返回所有收款单
     * @return 收款单列表
     */
    List<ReceivableSheetPO> findAll();

    /**
     * 根据state返回收款单
     * @param state 收款单状态
     * @return 收款单列表
     */
    List<ReceivableSheetPO> findAllByState(ReceivableSheetState state);

    int updateState(String receivableSheetId, ReceivableSheetState state);

    /**
     * 更新指定ID的收款单
     * @param receivableSheetPO
     */
    int updateById(ReceivableSheetPO receivableSheetPO);

    ReceivableSheetPO findOneById(String receivableSheetId);

    List<ReceivableSheetContentPO> findContentByReceivableSheetId(String receivableSheetId);
}
