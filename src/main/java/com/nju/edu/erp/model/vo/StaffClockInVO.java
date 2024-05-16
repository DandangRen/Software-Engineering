package com.nju.edu.erp.model.vo;


import com.nju.edu.erp.model.vo.BankAccountVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffClockInVO {
    /**
     * 编号
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 当日日期
     */
    private String today;
    /**
     * 每月打卡次数
     */
    private Integer clock_in_count;


}
