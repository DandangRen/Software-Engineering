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
public class StaffManageVO {
    /**
     * 编号
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 手机
     */
    private String phone_number;
    /**
     * 工作岗位
     */
    private String role;
    /**
     * 基本工资
     */
    private Integer basic_salary;
    /**
     * 岗位工资
     */
    private Integer post_salary;
    /**
     * 岗位级别
     */
    private String post_level;
    /**
     * 薪资计算方式
     */
    private String salary_count_way;
    /**
     * 薪资方式方法
     */
    private String salary_method;
    /**
     * 工资卡账户号
     */
    private String bank_account;

}
