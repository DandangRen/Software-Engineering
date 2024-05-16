package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.SalarySheetDao;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.po.PurchaseSheetContentPO;
import com.nju.edu.erp.model.po.PurchaseSheetPO;
import com.nju.edu.erp.model.po.SalarySheetPO;
import com.nju.edu.erp.model.po.StaffManagePO;
import com.nju.edu.erp.model.vo.SalarySheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetContentVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.service.SalarySheetService;
import com.nju.edu.erp.service.SalaryStrategy;
import com.nju.edu.erp.service.TaxCul;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class SalarySheetServiceImpl implements SalarySheetService {

    private final SalarySheetDao salarySheetDao;

    public SalarySheetServiceImpl(SalarySheetDao salarySheetDao) {
        this.salarySheetDao = salarySheetDao;
    }

    @Override
    public void addSalarySheet(SalarySheetVO salarySheetVO) { //添加工资表
        SalaryStrategy salaryStrategy;
        List<SalarySheetPO> list = salarySheetDao.findAll();
        boolean isExist = false;
        for (SalarySheetPO b : list) {
            if (b.getSheet_number().equals(salarySheetVO.getSheet_number())) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            SalarySheetPO salarySheetPO = new SalarySheetPO();
            TaxCul taxCul = new TaxCulImpl();
            BeanUtils.copyProperties(salarySheetVO, salarySheetPO);
            salarySheetPO.setState(SalarySheetState.PENDING_LEVEL_1);
            switch (salarySheetPO.getRole()){
                case "INVENTORY_MANAGER" :
                case "HR":
                case " FINANCIAL_STAFF":
                    salaryStrategy = new MonthlySalaryStrategy();
                    break;
                case "SALE_MANAGER":
                case "SALE_STAFF":
                    salaryStrategy = new DynamicSalaryStrategy();
                    break;
                case "GM":
                    salaryStrategy = new YearlySalaryStrategy();
                    break;
                default:
                    salaryStrategy = new MonthlySalaryStrategy();
            }
            StaffManagePO stfmpo_temp = salarySheetDao.findSalaryById(salarySheetVO.getId());
            Integer basic = stfmpo_temp.getBasic_salary();
            Integer post = stfmpo_temp.getPost_salary();
            Integer should_pay = salaryStrategy.should_pay(basic,post);
            salarySheetPO.setShould_pay(should_pay);
            salarySheetPO.setTax(taxCul.Tax(should_pay));
            salarySheetPO.setActual_pay(salarySheetPO.getShould_pay()-salarySheetPO.getTax());
            salarySheetDao.create_salary_sheet(salarySheetPO);
        }
    }

    @Override
    public void deleteById(String id) {
        salarySheetDao.delete_by_id(id);
    }//删除工资单

    @Override
    public void updateSalary(SalarySheetVO salarySheetVO) {//更新工资信息
        SalarySheetPO salarySheetPO = new SalarySheetPO();
        BeanUtils.copyProperties(salarySheetVO, salarySheetPO);
        salarySheetDao.updateSalary(salarySheetPO);
    }


    /**
     * 根据状态获取工资单(state == null 则获取所有工资单)
     *
     * @param state 工资单状态
     * @return 工资单
     */
    @Override
    public List<SalarySheetVO> getSalarySheetByState(SalarySheetState state) {
        List<SalarySheetVO> res = new ArrayList<>();
        List<SalarySheetPO> all;
        if(state == null) {
            all = salarySheetDao.findAll();
        } else {
            all = salarySheetDao.findAllByState(state);
        }
        for(SalarySheetPO po: all) {
            SalarySheetVO vo = new SalarySheetVO();
            BeanUtils.copyProperties(po, vo);
            res.add(vo);
        }
        return res;
    }

    /**
     * 根据工资单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param sheet_number 进货单id
     * @param state       工资单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String sheet_number, SalarySheetState state) {
        if(state.equals(SalarySheetState.FAILURE)) {
            SalarySheetPO salarySheet = salarySheetDao.findOneById(sheet_number);
            if(salarySheet.getState() == SalarySheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = salarySheetDao.updateState(sheet_number, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            SalarySheetState prevState;
            if (state.equals(SalarySheetState.SUCCESS)) {
                prevState = SalarySheetState.PENDING_LEVEL_2;
            } else if (state.equals(SalarySheetState.PENDING_LEVEL_2)) {
                prevState = SalarySheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = salarySheetDao.updateStateV2(sheet_number, prevState, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
        }
    }

    @Override
    public List<SalarySheetVO> getAll(){//获取全部，前端展示用
        List<SalarySheetVO> list = new ArrayList<>();
        for (SalarySheetPO po : salarySheetDao.findAll()){
            SalarySheetVO salarySheetVO = new SalarySheetVO();
            BeanUtils.copyProperties(po, salarySheetVO);
            list.add(salarySheetVO);
        }
        return list;
    }

}
