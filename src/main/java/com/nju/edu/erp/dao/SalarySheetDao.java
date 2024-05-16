package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.po.PurchaseSheetPO;
import com.nju.edu.erp.model.po.SalarySheetPO;
import com.nju.edu.erp.model.po.StaffManagePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SalarySheetDao {
    int create_salary_sheet(SalarySheetPO SalarySheetPO);//创建工资表

    int delete_by_id(String id);//基于id删除

    int updateSalary(SalarySheetPO SalarySheetPO);//更新工资表

    List<SalarySheetPO> findAll();//获取全部，前端展示用

    StaffManagePO findSalaryById(String sheet_number);//计算工资用

    SalarySheetPO findOneById(String sheet_number);

    List<SalarySheetPO> findAllByState(SalarySheetState state);

    int updateState(String sheet_number, SalarySheetState state);

    int updateStateV2(String sheet_number, SalarySheetState prevState, SalarySheetState state);
}
