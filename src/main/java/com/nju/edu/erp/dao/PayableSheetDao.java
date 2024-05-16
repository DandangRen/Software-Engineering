package com.nju.edu.erp.dao;



import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import com.nju.edu.erp.model.po.PayableSheetContentPO;
import com.nju.edu.erp.model.po.PayableSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PayableSheetDao {
    /**
     * 获取最近一条付款单
     * @return 最近一条付款单
     */
    PayableSheetPO getLatest();

    /**
     * 存入一条付款单记录
     * @param toSave 一条付款单记录
     * @return 影响的行数
     */
    int save(PayableSheetPO toSave);

    /**
     * 把付款单上的具体内容存入数据库
     * @param payableSheetContent 付款单上的具体内容
     */
    void saveBatch(List<PayableSheetContentPO> payableSheetContent);

    /**
     * 返回所有付款单
     * @return 付款单列表
     */
    List<PayableSheetPO> findAll();

    /**
     * 根据state返回付款单
     * @param state 付款单状态
     * @return 付款单列表
     */
    List<PayableSheetPO> findAllByState(PayableSheetState state);

    int updateState(String payableSheetId, PayableSheetState state);

    /**
     * 更新指定ID的收款单
     * @param payableSheetPO
     */
    int updateById(PayableSheetPO payableSheetPO);

    PayableSheetPO findOneById(String payableSheetId);

    List<PayableSheetContentPO> findContentByPayableSheetId(String payableSheetId);
}
