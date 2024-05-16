package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.BankAccountVO;
import org.springframework.stereotype.Service;
import com.nju.edu.erp.model.po.BankAccountPO;

import java.util.List;

@Service
public interface AccountService {

    void addAccount(BankAccountVO bankAccountVO);

    void deleteAccount(String accountNumber);

    void updateAccount(BankAccountVO bankAccountVO);

    BankAccountVO getAccount(String accountNumber);

    List<BankAccountVO> getAlmostByName(String accountName);

    List<BankAccountVO> getAlmostByNumber(String accountNumber);

    List<BankAccountVO> getAll();
}
