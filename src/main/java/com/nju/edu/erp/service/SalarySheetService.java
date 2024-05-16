package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.vo.SalarySheetVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import org.springframework.stereotype.Service;
import com.nju.edu.erp.model.po.SalarySheetPO;

import java.util.List;

@Service
public interface SalarySheetService {

    void addSalarySheet(SalarySheetVO salarySheetVO);

    void updateSalary(SalarySheetVO salarySheetVO);

    void deleteById(String id);

    /**
     * 根据状态获取工资单(state == null 则获取所有工资单)
     * @param state 工资单状态
     * @return 工资单
     */
    List<SalarySheetVO> getSalarySheetByState(SalarySheetState state);

    /**
     * 根据工资单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     * @param sheet_number 工资单id
     * @param state 工资单单修改后的状态
     */
    void approval(String sheet_number, SalarySheetState state);

    List<SalarySheetVO> getAll();

}
