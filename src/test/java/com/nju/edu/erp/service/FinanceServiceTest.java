package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.PayableSheetDao;
import com.nju.edu.erp.dao.ReceivableSheetDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.BankAccountVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.SalesDetailsConditionVO;
import com.nju.edu.erp.model.vo.payable.PayableSheetContentVO;
import com.nju.edu.erp.model.vo.payable.PayableSheetVO;
import com.nju.edu.erp.model.vo.receivable.ReceivableSheetContentVO;
import com.nju.edu.erp.model.vo.receivable.ReceivableSheetVO;
import com.nju.edu.erp.utils.IdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class FinanceServiceTest { // 该测试为集成测试，需要用到数据库，请大家连给定的测试数据库进行测试

    @Autowired
    ReceivableSheetDao receivableSheetDao;

    @Autowired
    PayableSheetDao payableSheetDao;

    @Autowired
    CustomerService customerService;

    @Autowired
    FinanceService financeService;

    @Autowired
    AccountService accountService;

    @Test
    public void FinanceServiceTest(){
        if(financeService==null){
            System.out.println("service也是空的");
        }else{
            System.out.println("service不是空的");
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void makeReceivableSheet() { // 测试收款单是否生成成功
        UserVO userVO = UserVO.builder()
                .name("jn")
                .role(Role.FINANCIAL_STAFF)
                .build();

        List<ReceivableSheetContentVO> receivableSheetContentVOS = new ArrayList<>();
        receivableSheetContentVOS.add(ReceivableSheetContentVO.builder()
                .bankName("123456")
                .transferAmount(BigDecimal.valueOf(1000))
                .remark("TEST1")
                .build());
        receivableSheetContentVOS.add(ReceivableSheetContentVO.builder()
                .bankName("123456")
                .transferAmount(BigDecimal.valueOf(2000))
                .remark("TEST2")
                .build());
        ReceivableSheetVO receivableSheetVO = ReceivableSheetVO.builder()
                .receivableSheetContent(receivableSheetContentVOS)
                .customer(1)
                .build();
        ReceivableSheetPO prevSheet = receivableSheetDao.getLatest();
        String realSheetId = IdGenerator.generateSheetId(prevSheet == null ? null : prevSheet.getId(), "SKD");

        financeService.makeReceivableSheet(userVO, receivableSheetVO);
        ReceivableSheetPO latestSheet = receivableSheetDao.getLatest();
        Assertions.assertNotNull(latestSheet);
        Assertions.assertEquals(realSheetId, latestSheet.getId());
        Assertions.assertEquals(ReceivableSheetState.DRAFT, latestSheet.getState());
        Assertions.assertEquals(1, latestSheet.getCustomer());
        Assertions.assertEquals(0, latestSheet.getTotalAmount().compareTo(BigDecimal.valueOf(3000)));
        Assertions.assertEquals("jn", latestSheet.getOperator());

        String sheetId = latestSheet.getId();
        Assertions.assertNotNull(sheetId);
        List<ReceivableSheetContentPO> content = receivableSheetDao.findContentByReceivableSheetId(sheetId);
        content.sort(Comparator.comparing(ReceivableSheetContentPO::getId));
        Assertions.assertEquals(2, content.size());
        Assertions.assertEquals("123456", content.get(0).getBankName());
        Assertions.assertEquals(0, content.get(0).getTransferAmount().compareTo(BigDecimal.valueOf(1000)));
        Assertions.assertEquals("123456", content.get(1).getBankName());
        Assertions.assertEquals(0, content.get(1).getTransferAmount().compareTo(BigDecimal.valueOf(2000)));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void getReceivableSheetByState() { // 测试按照状态获取收款单及其content是否成功

        List<ReceivableSheetVO> receivableSheetByState = financeService.getReceivableSheetByState(ReceivableSheetState.SUCCESS);
        Assertions.assertNotNull(receivableSheetByState);
        Assertions.assertEquals(1, receivableSheetByState.size());
        ReceivableSheetVO sheet1 = receivableSheetByState.get(0);
        Assertions.assertNotNull(sheet1);
        Assertions.assertEquals("SKD-20220702-00000", sheet1.getId());
        Assertions.assertEquals(2, sheet1.getCustomer());
        Assertions.assertEquals("jn", sheet1.getOperator());
        Assertions.assertEquals(0, sheet1.getTotalAmount().compareTo(BigDecimal.valueOf(3000)));

        List<ReceivableSheetContentVO> sheet1Content = sheet1.getReceivableSheetContent();
        Assertions.assertNotNull(sheet1Content);
        Assertions.assertEquals(2, sheet1Content.size());
        sheet1Content.sort(Comparator.comparing(ReceivableSheetContentVO::getId));
        Assertions.assertEquals("123456", sheet1Content.get(0).getBankName());
        Assertions.assertEquals(0, sheet1Content.get(0).getTransferAmount().compareTo(BigDecimal.valueOf(1000.00)));
        Assertions.assertEquals("123456", sheet1Content.get(1).getBankName());
        Assertions.assertEquals(0, sheet1Content.get(1).getTransferAmount().compareTo(BigDecimal.valueOf(2000.00)));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void receivable_approval_failed() { // 测试审批失败
        UserVO userVO = UserVO.builder()
                .name("jn")
                .role(Role.FINANCIAL_STAFF)
                .build();
        financeService.approval(userVO, "SKD-20220702-00000", ReceivableSheetState.FAILURE);
        ReceivableSheetPO sheet = receivableSheetDao.findOneById("SKD-20220702-00000");
        Assertions.assertEquals(ReceivableSheetState.FAILURE, sheet.getState());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void receivable_approval_succeed() { // 测试审批完成
        // 审批成功之后需要进行
        // 1. 修改单据状态
        // 2. 更新客户信息
        // 3. 更新银行账户
        UserVO userVO = UserVO.builder()
                .name("jn")
                .role(Role.FINANCIAL_STAFF)
                .build();
        financeService.approval(userVO,"SKD-20220702-00000", ReceivableSheetState.SUCCESS);
        ReceivableSheetPO sheet = receivableSheetDao.findOneById("SKD-20220702-00000");
        Assertions.assertEquals(ReceivableSheetState.SUCCESS,sheet.getState());

        CustomerPO customerPO = customerService.findCustomerById(sheet.getCustomer());
        Assertions.assertEquals(0, customerPO.getReceivable().compareTo(BigDecimal.valueOf(4428400)));

        BankAccountVO bankAccountVO = accountService.getAccount("123456");
        Assertions.assertEquals(0, bankAccountVO.getBalance().compareTo(BigDecimal.valueOf(3000)));

        financeService.approval(userVO,"SKD-20220703-00000", ReceivableSheetState.SUCCESS);
        ReceivableSheetPO sheet1 = receivableSheetDao.findOneById("SKD-20220703-00000");
        Assertions.assertEquals(ReceivableSheetState.SUCCESS,sheet.getState());

        CustomerPO customerPO1 = customerService.findCustomerById(sheet1.getCustomer());
        Assertions.assertEquals(0, customerPO1.getPayable().compareTo(BigDecimal.valueOf(6700000)));
        BankAccountVO bankAccountVO1 = accountService.getAccount("1234567");
        Assertions.assertEquals(0, bankAccountVO1.getBalance().compareTo(BigDecimal.valueOf(200000)));

    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void makePayableSheet() { // 测试付款单是否生成成功
        UserVO userVO = UserVO.builder()
                .name("jn")
                .role(Role.FINANCIAL_STAFF)
                .build();

        List<PayableSheetContentVO> payableSheetContentVOS = new ArrayList<>();
        payableSheetContentVOS.add(PayableSheetContentVO.builder()
                .bankName("123456")
                .transferAmount(BigDecimal.valueOf(1000))
                .remark("TEST1")
                .build());
        payableSheetContentVOS.add(PayableSheetContentVO.builder()
                .bankName("123456")
                .transferAmount(BigDecimal.valueOf(2000))
                .remark("TEST2")
                .build());
        PayableSheetVO payableSheetVO = PayableSheetVO.builder()
                .payableSheetContent(payableSheetContentVOS)
                .customer(1)
                .build();
        PayableSheetPO prevSheet = payableSheetDao.getLatest();
        String realSheetId = IdGenerator.generateSheetId(prevSheet == null ? null : prevSheet.getId(), "FKD");

        financeService.makePayableSheet(userVO, payableSheetVO);
        PayableSheetPO latestSheet = payableSheetDao.getLatest();
        Assertions.assertNotNull(latestSheet);
        Assertions.assertEquals(realSheetId, latestSheet.getId());
        Assertions.assertEquals(PayableSheetState.DRAFT, latestSheet.getState());
        Assertions.assertEquals(1, latestSheet.getCustomer());
        Assertions.assertEquals(0, latestSheet.getTotalAmount().compareTo(BigDecimal.valueOf(3000)));
        Assertions.assertEquals("jn", latestSheet.getOperator());

        String sheetId = latestSheet.getId();
        Assertions.assertNotNull(sheetId);
        List<PayableSheetContentPO> content = payableSheetDao.findContentByPayableSheetId(sheetId);
        content.sort(Comparator.comparing(PayableSheetContentPO::getId));
        Assertions.assertEquals(2, content.size());
        Assertions.assertEquals("123456", content.get(0).getBankName());
        Assertions.assertEquals(0, content.get(0).getTransferAmount().compareTo(BigDecimal.valueOf(1000)));
        Assertions.assertEquals("123456", content.get(1).getBankName());
        Assertions.assertEquals(0, content.get(1).getTransferAmount().compareTo(BigDecimal.valueOf(2000)));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void getPaySheetByState() { // 测试按照状态获取收款单及其content是否成功

        List<PayableSheetVO> payableSheetByState = financeService.getPayableSheetByState(PayableSheetState.SUCCESS);
        Assertions.assertNotNull(payableSheetByState);
        Assertions.assertEquals(1, payableSheetByState.size());
        PayableSheetVO sheet1 = payableSheetByState.get(0);
        Assertions.assertNotNull(sheet1);
        Assertions.assertEquals("FKD-20220703-00000", sheet1.getId());
        Assertions.assertEquals(2, sheet1.getCustomer());
        Assertions.assertEquals("jn", sheet1.getOperator());
        Assertions.assertEquals(0, sheet1.getTotalAmount().compareTo(BigDecimal.valueOf(4000)));

        List<PayableSheetContentVO> sheet1Content = sheet1.getPayableSheetContent();
        Assertions.assertNotNull(sheet1Content);
        Assertions.assertEquals(2, sheet1Content.size());
        sheet1Content.sort(Comparator.comparing(PayableSheetContentVO::getId));
        Assertions.assertEquals("123456", sheet1Content.get(0).getBankName());
        Assertions.assertEquals(0, sheet1Content.get(0).getTransferAmount().compareTo(BigDecimal.valueOf(1000.00)));
        Assertions.assertEquals("1234567", sheet1Content.get(1).getBankName());
        Assertions.assertEquals(0, sheet1Content.get(1).getTransferAmount().compareTo(BigDecimal.valueOf(3000.00)));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void pay_approval_failed() { // 测试审批失败
        UserVO userVO = UserVO.builder()
                .name("jn")
                .role(Role.FINANCIAL_STAFF)
                .build();
        financeService.approval(userVO, "FKD-20220703-00000", PayableSheetState.FAILURE);
        PayableSheetPO sheet = payableSheetDao.findOneById("FKD-20220703-00000");
        Assertions.assertEquals(PayableSheetState.FAILURE, sheet.getState());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void pay_approval_succeed() { // 测试审批完成
        // 审批成功之后需要进行
        // 1. 修改单据状态
        // 2. 更新客户信息
        // 3. 更新银行账户
        UserVO userVO = UserVO.builder()
                .name("jn")
                .role(Role.FINANCIAL_STAFF)
                .build();
        financeService.approval(userVO,"FKD-20220703-00001", PayableSheetState.SUCCESS);
        PayableSheetPO sheet = payableSheetDao.findOneById("FKD-20220703-00001");
        Assertions.assertEquals(PayableSheetState.SUCCESS,sheet.getState());

        CustomerPO customerPO = customerService.findCustomerById(sheet.getCustomer());
        Assertions.assertEquals(0, customerPO.getPayable().compareTo(BigDecimal.valueOf(6200000)));

        BankAccountVO bankAccountVO = accountService.getAccount("123456");
        Assertions.assertEquals(0, bankAccountVO.getBalance().compareTo(BigDecimal.valueOf(-300000)));

        financeService.approval(userVO,"FKD-20220703-00000", PayableSheetState.SUCCESS);
        PayableSheetPO sheet1 = payableSheetDao.findOneById("FKD-20220703-00000");
        Assertions.assertEquals(PayableSheetState.SUCCESS,sheet.getState());

        CustomerPO customerPO1 = customerService.findCustomerById(sheet1.getCustomer());
        Assertions.assertEquals(0, customerPO1.getReceivable().compareTo(BigDecimal.valueOf(4435400)));
        BankAccountVO bankAccountVO1 = accountService.getAccount("1234567");
        Assertions.assertEquals(0, bankAccountVO1.getBalance().compareTo(BigDecimal.valueOf(-3000)));

    }
    @Test
    @Transactional
    @Rollback(value = true)
    public void test_SalesDetails(){
        SalesDetailsConditionVO salesDetailsConditionVO = new SalesDetailsConditionVO();
        salesDetailsConditionVO.setOperator("xiaoshoujingli");
        salesDetailsConditionVO.setName("戴尔电脑");
        salesDetailsConditionVO.setSupplier(2);
        salesDetailsConditionVO.setEndDate("2022-05-30 23:46:12");
        salesDetailsConditionVO.setBeginDate("2022-05-10 23:46:12");
        List<SalesDetailsPO> list = financeService.getSalesDetails(salesDetailsConditionVO);
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void test_BusinessCalendar(){
        BusinessCalendarPO businessCalendarPO = new BusinessCalendarPO();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            businessCalendarPO.setBeginDate(dateFormat.parse("2022-05-10 23:46:12"));
            businessCalendarPO.setEndDate(dateFormat.parse("2022-05-30 23:46:12"));
        }catch (ParseException e){
            e.printStackTrace();
        }
        businessCalendarPO.setOperator("xiaoshoujingli");
        businessCalendarPO.setSupplier(2);

        financeService.getJHTHD(businessCalendarPO);
    }
    @Test
    @Transactional
    @Rollback(value = true)
    public void test_getBusinessOperation(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Date endDate = new Date();
        try{
            beginDate = dateFormat.parse("2022-05-23 00:00:00");
            endDate = dateFormat.parse("2022-05-24 00:00:00");
        }catch(ParseException e){
            e.printStackTrace();
        }
        financeService.getBusinessOperation(beginDate,endDate);
    }
}