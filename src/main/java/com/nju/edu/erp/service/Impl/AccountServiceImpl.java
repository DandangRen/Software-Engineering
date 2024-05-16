package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.model.po.BankAccountPO;
import com.nju.edu.erp.model.vo.BankAccountVO;
import com.nju.edu.erp.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void addAccount(BankAccountVO bankAccountVO) {
        List<BankAccountPO> list = accountDao.findAll();
        boolean isExist = false;
        for (BankAccountPO b : list) {
            if (b.getNumber().equals(bankAccountVO.getNumber())) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            BankAccountPO bankAccountPO = new BankAccountPO();
            BeanUtils.copyProperties(bankAccountVO, bankAccountPO);
            accountDao.createOne(bankAccountPO);
        }
    }

    @Override
    public void deleteAccount(String accountNumber) {
        accountDao.deleteByNumber(accountNumber);
    }

    @Override
    public void updateAccount(BankAccountVO bankAccountVO) {
        BankAccountPO bankAccountPO = new BankAccountPO();
        BeanUtils.copyProperties(bankAccountVO, bankAccountPO);
        accountDao.updateOne(bankAccountPO);
    }

    @Override
    public BankAccountVO getAccount(String accountNumber) {
        BankAccountPO bankAccountPO = accountDao.findOneByNumber(accountNumber);
        BankAccountVO bankAccountVO = new BankAccountVO();
        BeanUtils.copyProperties(bankAccountPO, bankAccountVO);
        return bankAccountVO;
    }

    @Override
    public List<BankAccountVO> getAlmostByName(String accountName) {
        List<BankAccountVO> list = new ArrayList<>();
        for (BankAccountPO po : accountDao.findAlmostByName(accountName)){
            BankAccountVO bankAccountVO = new BankAccountVO();
            BeanUtils.copyProperties(po, bankAccountVO);
            list.add(bankAccountVO);
        }
        return list;
    }

    @Override
    public List<BankAccountVO> getAlmostByNumber(String accountNumber) {
        List<BankAccountVO> list = new ArrayList<>();
        for (BankAccountPO po : accountDao.findAlmostByNumber(accountNumber)){
            BankAccountVO bankAccountVO = new BankAccountVO();
            BeanUtils.copyProperties(po, bankAccountVO);
            list.add(bankAccountVO);
        }
        return list;
    }

    @Override
    public List<BankAccountVO> getAll(){
        List<BankAccountVO> list = new ArrayList<>();
        for (BankAccountPO po : accountDao.findAll()){
            BankAccountVO bankAccountVO = new BankAccountVO();
            BeanUtils.copyProperties(po, bankAccountVO);
            list.add(bankAccountVO);
        }
        return list;
    };
}
