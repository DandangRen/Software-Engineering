package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.FinanceDao;
import com.nju.edu.erp.dao.PayableSheetDao;
import com.nju.edu.erp.dao.ReceivableSheetDao;
import com.nju.edu.erp.enums.sheetState.PayableSheetState;
import com.nju.edu.erp.enums.sheetState.ReceivableSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.BankAccountVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.BusinessOperationVO;
import com.nju.edu.erp.model.vo.finance.SalesDetailsConditionVO;
import com.nju.edu.erp.model.vo.payable.PayableSheetContentVO;
import com.nju.edu.erp.model.vo.payable.PayableSheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.receivable.ReceivableSheetContentVO;
import com.nju.edu.erp.model.vo.receivable.ReceivableSheetVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.service.*;
import com.nju.edu.erp.utils.IdGenerator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSException;
import com.nju.edu.erp.model.vo.Sale.SaleSheetContentVO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.filechooser.FileSystemView;

@Service
public class FinanceServiceImpl implements FinanceService {

    private final ReceivableSheetDao receivableSheetDao;

    private final CustomerService customerService;

    private final AccountService accountService;

    private final PayableSheetDao payableSheetDao;

    private final FinanceDao financeDao;

    private final SaleServiceImpl saleService;

    private final SaleReturnsServiceImpl saleReturnsService;

    private final PurchaseService purchaseService;

    private final PurchaseReturnsService purchaseReturnsService;

    private final ProductService productService;

    private List<SalesDetailsPO> res_SalesDetails = new ArrayList<>();

    private List<BusinessOperationVO> res_BusinessOperation = new ArrayList<>();

    private List<SaleReturnsSheetVO> res_SaleReturnsSheetVO = new ArrayList<>();

    private List<SaleSheetVO> res_SaleSheetVO = new ArrayList<>();

    private List<PurchaseSheetVO> res_PurchaseSheetVO = new ArrayList<>();

    private List<PurchaseReturnsSheetVO> res_PurchaseReturnsSheetVO = new ArrayList<>();

    private List<PayableSheetVO> res_PayableSheetVO = new ArrayList<>();

    private List<ReceivableSheetVO> res_ReceivableSheetVO = new ArrayList<>();

    private Date beginDate0;

    private Date endDate0;

    public FinanceServiceImpl(ReceivableSheetDao receivableSheetDao, CustomerService customerService, AccountService accountService, PayableSheetDao payableSheetDao, FinanceDao financeDao, SaleServiceImpl saleService,SaleReturnsServiceImpl saleReturnsService,PurchaseService purchaseService,PurchaseReturnsService purchaseReturnsService,ProductService productService) {
        this.receivableSheetDao = receivableSheetDao;
        this.customerService = customerService;
        this.accountService = accountService;
        this.payableSheetDao = payableSheetDao;
        this.financeDao = financeDao;
        this.saleService = saleService;
        this.saleReturnsService = saleReturnsService;
        this.purchaseService = purchaseService;
        this.purchaseReturnsService=purchaseReturnsService;
        this.productService = productService;
    }


    @Override
    public void makeReceivableSheet(UserVO userVO, ReceivableSheetVO receivableSheetVO) {
        // 需要持久化收款单（ReceivableSheet）和收款单content（ReceivableSheetContent），其中总价计算需要在后端进行
        // 需要的service和dao层相关方法未实现
        ReceivableSheetPO receivableSheetPO = new ReceivableSheetPO();
        BeanUtils.copyProperties(receivableSheetVO, receivableSheetPO);
        // 此处根据制定单据人员确定操作员
        receivableSheetPO.setCreateTime(new Date());
        receivableSheetPO.setOperator(userVO.getName());
        ReceivableSheetPO latest = receivableSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "SKD");
        receivableSheetPO.setId(id);
        receivableSheetPO.setState(ReceivableSheetState.DRAFT);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<ReceivableSheetContentPO> rContentPOList = new ArrayList<>();
        for(ReceivableSheetContentVO content : receivableSheetVO.getReceivableSheetContent()) {
            ReceivableSheetContentPO rContentPO = new ReceivableSheetContentPO();
            BeanUtils.copyProperties(content,rContentPO);
            rContentPO.setReceivableSheetId(id);
            BigDecimal transferAmount = rContentPO.getTransferAmount();
            rContentPOList.add(rContentPO);
            totalAmount = totalAmount.add(transferAmount);
        }
        receivableSheetPO.setTotalAmount(totalAmount);
        receivableSheetDao.saveBatch(rContentPOList);
        receivableSheetDao.save(receivableSheetPO);
    }

    @Override
    public List<ReceivableSheetVO> getReceivableSheetByState(ReceivableSheetState state) {
        // 根据单据状态获取收款单（注意：VO包含ReceivableSheetContent）
        // 依赖的dao层部分方法未提供，需要自己实现
        List<ReceivableSheetVO> res = new ArrayList<>();
        List<ReceivableSheetPO> all;
        if(state == null) {
            all = receivableSheetDao.findAll();
        } else {
            all = receivableSheetDao.findAllByState(state);
        }
        for(ReceivableSheetPO po: all) {
            ReceivableSheetVO vo = new ReceivableSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<ReceivableSheetContentPO> alll = receivableSheetDao.findContentByReceivableSheetId(po.getId());
            List<ReceivableSheetContentVO> vos = new ArrayList<>();
            for (ReceivableSheetContentPO p : alll) {
                ReceivableSheetContentVO v = new ReceivableSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setReceivableSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 审批收款单(财务人员进行确认/总经理进行审批)
     *
     * @param receivableSheetId 收款单id
     * @param state 收款单修改后的状态(state == "待审批"/"审批失败"/"审批完成")
     */
    @Override
    public void approval(UserVO user, String receivableSheetId, ReceivableSheetState state) {
        ReceivableSheetPO receivableSheetPO = receivableSheetDao.findOneById(receivableSheetId);
        receivableSheetPO.setOperator(user.getName());
        receivableSheetPO.setState(state);
        receivableSheetDao.updateById(receivableSheetPO);
        if (state.equals(ReceivableSheetState.SUCCESS)){
            //更改客户应收
            CustomerPO customerPO = customerService.findCustomerById(receivableSheetPO.getCustomer());
            BigDecimal receivable = customerPO.getReceivable();//应收
            BigDecimal total = receivableSheetPO.getTotalAmount();//转账总额
            if (receivable.compareTo(total) >= 0){
                customerPO.setReceivable(receivable.subtract(total));//更改应收
            }else {//转账金额大于应收，应收清零，更改应付(待定)
                customerPO.setReceivable(BigDecimal.valueOf(0));
                customerPO.setPayable(total.subtract(receivable).add(customerPO.getPayable()));
            }
            customerService.updateCustomer(customerPO);

            //修改银行账户
            List<ReceivableSheetContentPO> content = receivableSheetDao.findContentByReceivableSheetId(receivableSheetId);
            for (ReceivableSheetContentPO r : content){
                String bankName = r.getBankName();
                BankAccountVO bankAccountVO = accountService.getAccount(bankName);
                bankAccountVO.setBalance(bankAccountVO.getBalance().add(r.getTransferAmount()));
                accountService.updateAccount(bankAccountVO);
            }
        }
    }

    @Override
    public ReceivableSheetVO getReceivableSheetById(String receivableSheetId) {
        ReceivableSheetPO receivableSheetPO = receivableSheetDao.findOneById(receivableSheetId);
        if(receivableSheetPO == null) return null;
        List<ReceivableSheetContentPO> contentPO = receivableSheetDao.findContentByReceivableSheetId(receivableSheetId);
        ReceivableSheetVO rVO = new ReceivableSheetVO();
        BeanUtils.copyProperties(receivableSheetPO, rVO);
        List<ReceivableSheetContentVO> receivableSheetContentVOList = new ArrayList<>();
        for (ReceivableSheetContentPO content:
                contentPO) {
            ReceivableSheetContentVO rContentVO = new ReceivableSheetContentVO();
            BeanUtils.copyProperties(content, rContentVO);
            receivableSheetContentVOList.add(rContentVO);
        }
        rVO.setReceivableSheetContent(receivableSheetContentVOList);
        return rVO;
    }

    @Override
    public void makePayableSheet(UserVO userVO, PayableSheetVO payableSheetVO) {
        // 需要持久化付款单（PayableSheet）和付款单content（PayableSheetContent），其中总价计算需要在后端进行
        // 需要的service和dao层相关方法未实现
        PayableSheetPO payableSheetPO = new PayableSheetPO();
        BeanUtils.copyProperties(payableSheetVO, payableSheetPO);
        // 此处根据制定单据人员确定操作员
        payableSheetPO.setCreateTime(new Date());
        payableSheetPO.setOperator(userVO.getName());
        PayableSheetPO latest = payableSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "FKD");
        payableSheetPO.setId(id);
        payableSheetPO.setState(PayableSheetState.DRAFT);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<PayableSheetContentPO> pContentPOList = new ArrayList<>();
        for(PayableSheetContentVO content : payableSheetVO.getPayableSheetContent()) {
            PayableSheetContentPO pContentPO = new PayableSheetContentPO();
            BeanUtils.copyProperties(content, pContentPO);
            pContentPO.setPayableSheetId(id);
            BigDecimal transferAmount = pContentPO.getTransferAmount();
            pContentPOList.add(pContentPO);
            totalAmount = totalAmount.add(transferAmount);
        }
        payableSheetPO.setTotalAmount(totalAmount);
        payableSheetDao.saveBatch(pContentPOList);
        payableSheetDao.save(payableSheetPO);
    }

    @Override
    public List<PayableSheetVO> getPayableSheetByState(PayableSheetState state) {
        // 根据单据状态获取付款单（注意：VO包含PayableSheetContent）
        // 依赖的dao层部分方法未提供，需要自己实现
        List<PayableSheetVO> res = new ArrayList<>();
        List<PayableSheetPO> all;
        if(state == null) {
            all = payableSheetDao.findAll();
        } else {
            all = payableSheetDao.findAllByState(state);
        }
        for(PayableSheetPO po: all) {
            PayableSheetVO vo = new PayableSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<PayableSheetContentPO> alll = payableSheetDao.findContentByPayableSheetId(po.getId());
            List<PayableSheetContentVO> vos = new ArrayList<>();
            for (PayableSheetContentPO p : alll) {
                PayableSheetContentVO v = new PayableSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setPayableSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 审批付款单(财务人员进行确认/总经理进行审批)
     *
     * @param payableSheetId 付款单id
     * @param state 付款单修改后的状态(state == "待审批"/"审批失败"/"审批完成")
     */
    @Override
    public void approval(UserVO user, String payableSheetId, PayableSheetState state) {
        PayableSheetPO payableSheetPO = payableSheetDao.findOneById(payableSheetId);
        payableSheetPO.setOperator(user.getName());
        payableSheetPO.setState(state);
        payableSheetDao.updateById(payableSheetPO);
        if (state.equals(PayableSheetState.SUCCESS)){
            //更改客户应付
            CustomerPO customerPO = customerService.findCustomerById(payableSheetPO.getCustomer());
            BigDecimal payable = customerPO.getPayable();//应付
            BigDecimal total = payableSheetPO.getTotalAmount();//转账总额
            if (payable.compareTo(total) >= 0){
                customerPO.setPayable(payable.subtract(total));//更改应付
            }else {//转账金额大于应付，应付清零，更改应收(待定)
                customerPO.setPayable(BigDecimal.valueOf(0));
                customerPO.setReceivable((total.subtract(payable).add(customerPO.getReceivable())));
            }
            customerService.updateCustomer(customerPO);

            //修改银行账户
            List<PayableSheetContentPO> content = payableSheetDao.findContentByPayableSheetId(payableSheetId);
            for (PayableSheetContentPO p : content){
                String bankName = p.getBankName();
                BankAccountVO bankAccountVO = accountService.getAccount(bankName);
                bankAccountVO.setBalance(bankAccountVO.getBalance().subtract(p.getTransferAmount()));
                accountService.updateAccount(bankAccountVO);
            }
        }
    }

    @Override
    public PayableSheetVO getPayableSheetById(String payableSheetId) {
        PayableSheetPO payableSheetPO = payableSheetDao.findOneById(payableSheetId);
        if(payableSheetPO == null) return null;
        List<PayableSheetContentPO> contentPO = payableSheetDao.findContentByPayableSheetId(payableSheetId);
        PayableSheetVO pVO = new PayableSheetVO();
        BeanUtils.copyProperties(payableSheetPO, pVO);
        List<PayableSheetContentVO> payableSheetContentVOList = new ArrayList<>();
        for (PayableSheetContentPO content:
                contentPO) {
            PayableSheetContentVO pContentVO = new PayableSheetContentVO();
            BeanUtils.copyProperties(content, pContentVO);
            payableSheetContentVOList.add(pContentVO);
        }
        pVO.setPayableSheetContent(payableSheetContentVOList);
        return pVO;
    }


    @Override
    public List<SalesDetailsPO> getSalesDetails(SalesDetailsConditionVO salesDetailsConditionVO){
        SalesDetailsConditionPO salesDetailsConditionPO = new SalesDetailsConditionPO();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            salesDetailsConditionPO.setBeginDate(dateFormat.parse(salesDetailsConditionVO.getBeginDate()));
            salesDetailsConditionPO.setEndDate(dateFormat.parse(salesDetailsConditionVO.getEndDate()));
        }catch (ParseException e){
            e.printStackTrace();
        }
        salesDetailsConditionPO.setSupplier(salesDetailsConditionVO.getSupplier());
        salesDetailsConditionPO.setName(salesDetailsConditionVO.getName());
        salesDetailsConditionPO.setOperator(salesDetailsConditionVO.getOperator());

        List<SalesDetailsPO> list = this.financeDao.getSalesDetails(salesDetailsConditionPO);
        System.out.println(list);
        res_SalesDetails = list;

        return list;

    }

    @Override
    public List<SaleSheetVO> getXSD(BusinessCalendarPO businessCalendarPO){
        List<SaleSheetVO> res = new ArrayList<>();
        List<SaleSheetPO> all = financeDao.getXSD(businessCalendarPO);

        for(SaleSheetPO po :all){
            SaleSheetVO vo = saleService.getSaleSheetById(po.getId());
            res.add(vo);

        }
        res_SaleSheetVO = res;
        return res;
    }

    @Override
     public List<SaleReturnsSheetVO> getXSTHD(BusinessCalendarPO businessCalendarPO){
        List<SaleReturnsSheetVO> res = new ArrayList<>();
        List<SaleReturnsSheetPO> all = financeDao.getXSTHD(businessCalendarPO);

        for(SaleReturnsSheetPO po:all){
            SaleReturnsSheetVO vo = saleReturnsService.getSaleReturnsSheetByStateId(po.getId());
            res.add(vo);
        }

        res_SaleReturnsSheetVO = res;
        return res;

     }


    @Override
    public List<PurchaseSheetVO> getJHD(BusinessCalendarPO businessCalendarPO){
        List<PurchaseSheetVO> res = new ArrayList<>();
        List<PurchaseSheetPO> all = financeDao.getJHD(businessCalendarPO);

        for(PurchaseSheetPO po :all){
            PurchaseSheetVO vo = purchaseService.getPurchaseSheetById(po.getId());
            res.add(vo);
        }

        res_PurchaseSheetVO = res;
        return res;
    }

    @Override
    public List<PurchaseReturnsSheetVO> getJHTHD(BusinessCalendarPO businessCalendarPO){
        List<PurchaseReturnsSheetVO> res = new ArrayList<>();
        List<PurchaseReturnsSheetPO> all = financeDao.getJHTHD(businessCalendarPO);

        for(PurchaseReturnsSheetPO po :all){
            PurchaseReturnsSheetVO vo = purchaseReturnsService.getPurchaseReturnsSheetById(po.getId());
            res.add(vo);
        }

        res_PurchaseReturnsSheetVO = res;
        return res;
    }

    @Override
    public List<PayableSheetVO> getFKD(BusinessCalendarPO businessCalendarPO){
        List<PayableSheetVO> res = new ArrayList<>();
        List<PayableSheetPO> all = financeDao.getFKD(businessCalendarPO);

        for(PayableSheetPO po:all){
            PayableSheetVO vo = getPayableSheetById(po.getId());
            res.add(vo);
        }

        res_PayableSheetVO = res;
        return res;
    }
    @Override
    public List<ReceivableSheetVO> getSKD(BusinessCalendarPO businessCalendarPO){
        List<ReceivableSheetVO> res = new ArrayList<>();
        List<ReceivableSheetPO> all = financeDao.getSKD(businessCalendarPO);

        for(ReceivableSheetPO po:all){
            ReceivableSheetVO vo = getReceivableSheetById(po.getId());
            res.add(vo);
        }

        res_ReceivableSheetVO = res;
         return res;
    }

    @Override
    public List<BusinessOperationVO> getBusinessOperation(Date beginDate,Date endDate){
        beginDate0 = beginDate;
        endDate0 = endDate;

        List<BusinessOperationVO> res = new ArrayList<>();
        BusinessOperationVO vo = new BusinessOperationVO();
        vo.setIncome(0);
        vo.setDiscount(0);
        vo.setExpenditure(0);
        vo.setProfit(0);

        List<SaleSheetPO> income = financeDao.getSaleSheetByDate(beginDate,endDate);

        for(SaleSheetPO po:income){
            vo.setIncome(vo.getIncome()+po.getFinalAmount().intValue());
            int raw=po.getRawTotalAmount().intValue();
            int fi = po.getFinalAmount().intValue();
            vo.setDiscount(vo.getDiscount() + raw - fi);
            SaleSheetVO vo1 = saleService.getSaleSheetById(po.getId());
            List<SaleSheetContentVO> expenditure = vo1.getSaleSheetContent();
            for(SaleSheetContentVO vo2:expenditure){
                vo.setExpenditure(vo.getExpenditure()+vo2.getQuantity()*productService.getOneProductByPid(vo2.getPid()).get_purchasePrice().intValue());
            }

        }
        List<SaleReturnsSheetPO> saleReturn = financeDao.getSaleReturnsSheetByDate(beginDate,endDate);
        for(SaleReturnsSheetPO po :saleReturn){
            int money = po.getTotalAmount().intValue();
            double dis = saleService.getSaleSheetById(po.getSaleSheetId()).getDiscount().doubleValue();
            
            vo.setIncome(vo.getIncome()-money);
            int money1 = (int)(money/dis)-money;
            vo.setDiscount(vo.getDiscount()-money1);

            SaleReturnsSheetVO vo1 = saleReturnsService.getSaleReturnsSheetByStateId(po.getId());
            List<SaleReturnsSheetContentVO> list = vo1.getSaleReturnsSheetContent();
            for(SaleReturnsSheetContentVO vo2:list){
                vo.setExpenditure(vo.getExpenditure()- vo2.getQuantity()*productService.getOneProductByPid(vo2.getPid()).get_purchasePrice().intValue());
            }
        }


        vo.setProfit(vo.getIncome()-vo.getExpenditure());
        res.add(vo);

        res_BusinessOperation = res;
        return res;


    }

    @Override
    public void exportSalesDetailsExcelPaper() {
        List<SalesDetailsPO> list = res_SalesDetails;
        //标题
        String[] title = {"时间","名称","型号","数量","单价","总额"};
        //创建一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表sheet
        XSSFSheet sheet = workbook.createSheet();
        // 创建第一行
        XSSFRow rowTitle = sheet.createRow(0);
        // 创建表头
        for (int i = 0; i < title.length; i++) {
            rowTitle.createCell(i).setCellValue(title[i]);
        }
        XSSFRow rows;
        XSSFCell cells;
        for(int i=0;i<list.size();i++){
            //在这个sheet页⾥创建⼀⾏
            rows = sheet.createRow(i + 2);
            //给该⾏数据赋值
            for (int j = 0; j < title.length; j++) {
                cells = rows.createCell(j);
                String value = "";
                switch (j){
                    case 0:
                        value = list.get(i).getCreate_time().toString();
                        System.out.println("时间为："+value);
                        break;
                    case 1:
                        value = list.get(i).getName();
                        break;
                    case 2:
                        value = list.get(i).getType();
                        break;
                    case 3:
                        value = list.get(i).getQuantity().toString();
                        break;
                    case 4:
                        value = list.get(i).getUnit_price().toString();
                        break;
                    case 5:
                        value = list.get(i).getTotal_price().toString();
                        break;

                }
                cells.setCellValue(value);

            }

        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String filename = "销售明细表";
        File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        //String path = System.getProperty("user.dir") + "\\" + filename + dateFormat.format(date) + ".xlsx";
        String path = desktopPath + "\\" + filename + dateFormat.format(date) + ".xlsx";
        System.out.println("Excel⽂件输出路径: "+path);
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exportBusinessOperationExcelPaper(){
        List<BusinessOperationVO> list = res_BusinessOperation;
        //标题
        String[] title = {"起始时间","结束时间","总收入","折让","总支出","总利润"};
        //创建一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表sheet
        XSSFSheet sheet = workbook.createSheet();
        // 创建第一行
        XSSFRow rowTitle = sheet.createRow(0);
        // 创建表头
        for (int i = 0; i < title.length; i++) {
            rowTitle.createCell(i).setCellValue(title[i]);
        }
        XSSFRow rows;
        XSSFCell cells;
        for(int i=0;i<list.size();i++){
            //在这个sheet页⾥创建⼀⾏
            rows = sheet.createRow(i + 2);
            //给该⾏数据赋值
            for (int j = 0; j < title.length; j++) {
                cells = rows.createCell(j);
                String value = "";
                switch (j){
                    case 0:
                        value = beginDate0.toString();
                        break;
                    case 1:
                        value = endDate0.toString();
                        break;
                    case 2:
                        value = list.get(i).getIncome().toString();
                        break;
                    case 3:
                        value = list.get(i).getDiscount().toString();
                        break;
                    case 4:
                        value = list.get(i).getExpenditure().toString();
                        break;
                    case 5:
                        value = list.get(i).getProfit().toString();
                        break;


                }
                cells.setCellValue(value);

            }

        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String filename = "经营情况表";
        File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        //String path = System.getProperty("user.dir") + "\\" + filename + dateFormat.format(date) + ".xlsx";
        String path = desktopPath + "\\" + filename + dateFormat.format(date) + ".xlsx";
        System.out.println("Excel⽂件输出路径: "+path);
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void exportXSDExcelPaper(){
        List<SaleSheetVO> list = res_SaleSheetVO;
        //标题
        String[] title = {"id","supplier","操作员","业务员","折让前总金额","折让后总金额","折扣","使用代金券总额","备注"};
        //创建一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表sheet
        XSSFSheet sheet = workbook.createSheet();
        // 创建第一行
        XSSFRow rowTitle = sheet.createRow(0);
        // 创建表头
        for (int i = 0; i < title.length; i++) {
            rowTitle.createCell(i).setCellValue(title[i]);
        }
        XSSFRow rows;
        XSSFCell cells;
        for(int i=0;i<list.size();i++){
            //在这个sheet页⾥创建⼀⾏
            rows = sheet.createRow(i + 2);
            //给该⾏数据赋值
            for (int j = 0; j < title.length; j++) {
                cells = rows.createCell(j);
                String value = "";
                switch (j){
                    case 0:
                        value = list.get(i).getId();
                        break;
                    case 1:
                        value = list.get(i).getSupplier().toString();
                        break;
                    case 2:
                        value = list.get(i).getOperator();
                        break;
                    case 3:
                        value = list.get(i).getSalesman();
                        break;
                    case 4:
                        value = list.get(i).getRawTotalAmount().toString();
                        break;
                    case 5:
                        value = list.get(i).getFinalAmount().toString();
                        break;
                    case 6:
                        value = list.get(i).getDiscount().toString();
                        break;
                    case 7:
                        value = list.get(i).getVoucherAmount().toString();
                        break;
                    case 8:
                        value = list.get(i).getRemark();
                        break;

                }
                cells.setCellValue(value);

            }

        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String filename = "经营历程表";
        File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        //String path = System.getProperty("user.dir") + "\\" + filename + dateFormat.format(date) + ".xlsx";
        String path = desktopPath + "\\" + filename + dateFormat.format(date) + ".xlsx";
        System.out.println("Excel⽂件输出路径: "+path);
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void exportXSTHDExcelPaper(){
        List<SaleReturnsSheetVO> list = res_SaleReturnsSheetVO;
        System.out.println(res_SaleReturnsSheetVO);
        //标题
        String[] title = {"id","关联的销售单id","操作员","总额合计","备注"};
        //创建一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表sheet
        XSSFSheet sheet = workbook.createSheet();
        // 创建第一行
        XSSFRow rowTitle = sheet.createRow(0);
        // 创建表头
        for (int i = 0; i < title.length; i++) {
            rowTitle.createCell(i).setCellValue(title[i]);
        }
        XSSFRow rows;
        XSSFCell cells;
        for(int i=0;i<list.size();i++){
            //在这个sheet页⾥创建⼀⾏
            rows = sheet.createRow(i + 2);
            //给该⾏数据赋值
            for (int j = 0; j < title.length; j++) {
                cells = rows.createCell(j);
                String value = "";
                switch (j){
                    case 0:
                        value = list.get(i).getId();
                        break;
                    case 1:
                        value = list.get(i).getSaleSheetId();
                        break;
                    case 2:
                        value = list.get(i).getOperator();
                        break;
                    case 3:
                        value = list.get(i).getTotalAmount().toString();
                        break;
                    case 4:
                        value = list.get(i).getRemark();
                        break;


                }
                cells.setCellValue(value);

            }

        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String filename = "经营历程表";
        File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        //String path = System.getProperty("user.dir") + "\\" + filename + dateFormat.format(date) + ".xlsx";
        String path = desktopPath + "\\" + filename + dateFormat.format(date) + ".xlsx";
        System.out.println("Excel⽂件输出路径: "+path);
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void exportJHDExcelPaper(){
        List<PurchaseSheetVO> list = res_PurchaseSheetVO;
        //标题
        String[] title = {"id","进货商id","操作员","总额合计","备注"};
        //创建一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表sheet
        XSSFSheet sheet = workbook.createSheet();
        // 创建第一行
        XSSFRow rowTitle = sheet.createRow(0);
        // 创建表头
        for (int i = 0; i < title.length; i++) {
            rowTitle.createCell(i).setCellValue(title[i]);
        }
        XSSFRow rows;
        XSSFCell cells;
        for(int i=0;i<list.size();i++){
            //在这个sheet页⾥创建⼀⾏
            rows = sheet.createRow(i + 2);
            //给该⾏数据赋值
            for (int j = 0; j < title.length; j++) {
                cells = rows.createCell(j);
                String value = "";
                switch (j){
                    case 0:
                        value = list.get(i).getId();
                        break;
                    case 1:
                        value = list.get(i).getSupplier().toString();
                        break;
                    case 2:
                        value = list.get(i).getOperator();
                        break;
                    case 3:
                        value = list.get(i).getTotalAmount().toString();
                        break;
                    case 4:
                        value = list.get(i).getRemark();
                        break;


                }
                cells.setCellValue(value);

            }

        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String filename = "经营历程表";
        File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        //String path = System.getProperty("user.dir") + "\\" + filename + dateFormat.format(date) + ".xlsx";
        String path = desktopPath + "\\" + filename + dateFormat.format(date) + ".xlsx";
        System.out.println("Excel⽂件输出路径: "+path);
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportJHTHDExcelPaper(){
        List<PurchaseReturnsSheetVO> list = res_PurchaseReturnsSheetVO;
        //标题
        String[] title = {"id","关联的进货单id","操作员","总额合计","备注"};
        //创建一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表sheet
        XSSFSheet sheet = workbook.createSheet();
        // 创建第一行
        XSSFRow rowTitle = sheet.createRow(0);
        // 创建表头
        for (int i = 0; i < title.length; i++) {
            rowTitle.createCell(i).setCellValue(title[i]);
        }
        XSSFRow rows;
        XSSFCell cells;
        for(int i=0;i<list.size();i++){
            //在这个sheet页⾥创建⼀⾏
            rows = sheet.createRow(i + 2);
            //给该⾏数据赋值
            for (int j = 0; j < title.length; j++) {
                cells = rows.createCell(j);
                String value = "";
                switch (j){
                    case 0:
                        value = list.get(i).getId();
                        break;
                    case 1:
                        value = list.get(i).getPurchaseSheetId();
                        break;
                    case 2:
                        value = list.get(i).getOperator();
                        break;
                    case 3:
                        value = list.get(i).getTotalAmount().toString();
                        break;
                    case 4:
                        value = list.get(i).getRemark();
                        break;


                }
                cells.setCellValue(value);

            }

        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String filename = "经营历程表";
        File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        //String path = System.getProperty("user.dir") + "\\" + filename + dateFormat.format(date) + ".xlsx";
        String path = desktopPath + "\\" + filename + dateFormat.format(date) + ".xlsx";
        System.out.println("Excel⽂件输出路径: "+path);
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportFKDExcelPaper(){
        List<PayableSheetVO> list = res_PayableSheetVO;
        //标题
        String[] title = {"id","客户id","操作员","总额合计"};
        //创建一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表sheet
        XSSFSheet sheet = workbook.createSheet();
        // 创建第一行
        XSSFRow rowTitle = sheet.createRow(0);
        // 创建表头
        for (int i = 0; i < title.length; i++) {
        rowTitle.createCell(i).setCellValue(title[i]);
        }
        XSSFRow rows;
        XSSFCell cells;
        for(int i=0;i<list.size();i++){
        //在这个sheet页⾥创建⼀⾏
        rows = sheet.createRow(i + 2);
        //给该⾏数据赋值
        for (int j = 0; j < title.length; j++) {
        cells = rows.createCell(j);
        String value = "";
        switch (j){
        case 0:
        value = list.get(i).getId();
        break;
        case 1:
        value = list.get(i).getCustomer().toString();
        break;
        case 2:
        value = list.get(i).getOperator();
        break;
        case 3:
        value = list.get(i).getTotalAmount().toString();
        break;




        }
        cells.setCellValue(value);

        }

        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String filename = "经营历程表";
        File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        //String path = System.getProperty("user.dir") + "\\" + filename + dateFormat.format(date) + ".xlsx";
        String path = desktopPath + "\\" + filename + dateFormat.format(date) + ".xlsx";
        System.out.println("Excel⽂件输出路径: "+path);
        try {
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        workbook.close();
        fileOutputStream.close();
        } catch (IOException e) {
        e.printStackTrace();
        }

    }
    @Override
    public void exportSKDExcelPaper(){
        List<ReceivableSheetVO> list = res_ReceivableSheetVO;
        //标题
        String[] title = {"id","客户id","操作员","总额合计"};
        //创建一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表sheet
        XSSFSheet sheet = workbook.createSheet();
        // 创建第一行
        XSSFRow rowTitle = sheet.createRow(0);
        // 创建表头
        for (int i = 0; i < title.length; i++) {
            rowTitle.createCell(i).setCellValue(title[i]);
        }
        XSSFRow rows;
        XSSFCell cells;
        for(int i=0;i<list.size();i++){
            //在这个sheet页⾥创建⼀⾏
            rows = sheet.createRow(i + 2);
            //给该⾏数据赋值
            for (int j = 0; j < title.length; j++) {
                cells = rows.createCell(j);
                String value = "";
                switch (j){
                    case 0:
                        value = list.get(i).getId();
                        break;
                    case 1:
                        value = list.get(i).getCustomer().toString();
                        break;
                    case 2:
                        value = list.get(i).getOperator();
                        break;
                    case 3:
                        value = list.get(i).getTotalAmount().toString();
                        break;



                }
                cells.setCellValue(value);

            }

        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String filename = "经营历程表";
        File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        //String path = System.getProperty("user.dir") + "\\" + filename + dateFormat.format(date) + ".xlsx";
        String path = desktopPath + "\\" + filename + dateFormat.format(date) + ".xlsx";
        System.out.println("Excel⽂件输出路径: "+path);
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
