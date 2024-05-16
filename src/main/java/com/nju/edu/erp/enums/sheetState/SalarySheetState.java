package com.nju.edu.erp.enums.sheetState;

import com.nju.edu.erp.enums.BaseEnum;
import com.nju.edu.erp.model.po.SalarySheetPO;

public enum SalarySheetState implements BaseEnum<SalarySheetState, String> {

    PENDING_LEVEL_1("待一级审批"), // 待人力资源人员审批
    PENDING_LEVEL_2("待二级审批"), // 待总经理审批
    SUCCESS("审批完成"),
    FAILURE("审批失败");

    private final String value;

    SalarySheetState(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
