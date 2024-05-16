package com.nju.edu.erp.service;

public interface SalaryStrategy {
    /**
     * 结合具体的计算策略，返回应发薪资
     * @param basic_salary 基本工资
     * @param post_salary 岗位工资
     */
    Integer should_pay(Integer basic_salary,Integer post_salary);
}
