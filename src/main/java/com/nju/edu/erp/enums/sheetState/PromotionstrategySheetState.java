package com.nju.edu.erp.enums.sheetState;

import com.nju.edu.erp.enums.BaseEnum;
//促销策略状态审批
public enum PromotionstrategySheetState implements BaseEnum<PromotionstrategySheetState, String> {

    PENDING("待审批"), // 待总经理审批
    SUCCESS("审批完成"),
    FAILURE("审批失败");

    private final String value;

    PromotionstrategySheetState(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}