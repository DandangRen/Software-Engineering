package com.nju.edu.erp.AccountTest;


import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.model.po.BankAccountPO;
import com.nju.edu.erp.model.vo.BankAccountVO;
import com.nju.edu.erp.service.Impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;


@SpringBootTest
public class AccountTests {

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    private AccountDao accountDao;

    @Test
    public void findAllTest() {
        BankAccountVO bankAccountVO1 = BankAccountVO.builder()
                .number("123456")
                .balance(BigDecimal.valueOf(0))
                .build();
        BankAccountVO bankAccountVO2 = BankAccountVO.builder()
                .number("1234567")
                .balance(BigDecimal.valueOf(0))
                .build();
        BankAccountVO bankAccountVO3 = BankAccountVO.builder()
                .number("12345678")
                .balance(BigDecimal.valueOf(0))
                .build();
        BankAccountVO bankAccountVO4 = BankAccountVO.builder()
                .number("123456")//白盒测试
                .balance(BigDecimal.valueOf(0))
                .build();
        accountService.addAccount(bankAccountVO1);
        accountService.addAccount(bankAccountVO2);
        accountService.addAccount(bankAccountVO3);
        accountService.addAccount(bankAccountVO4);
        Assertions.assertEquals(3, accountService.getAll().size());
        Assertions.assertEquals(0, accountService.getAccount("123456").getBalance().compareTo(BigDecimal.valueOf(0)));
        Assertions.assertEquals(0, accountService.getAccount("1234567").getBalance().compareTo(BigDecimal.valueOf(0)));
        //白盒测试
        Assertions.assertEquals(3, accountService.getAll().size());
        accountService.deleteAccount("12345678");
        Assertions.assertEquals(2, accountService.getAll().size());

    }


}
