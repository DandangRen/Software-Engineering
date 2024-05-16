package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.BankAccountPO;
import com.nju.edu.erp.model.vo.BankAccountVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface AccountDao {
    int createOne(BankAccountPO bankAccountPO);
    int updateOne(BankAccountPO bankAccountPO);

    int deleteByNumber(String accountNumber);

    BankAccountPO findOneByNumber(String accountNumber);

    List<BankAccountPO> findAlmostByName(String accountName);

    List<BankAccountPO> findAlmostByNumber(String accountNumber);
    List<BankAccountPO> findAll();
}
