package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.service.SalaryStrategy;

public class YearlySalaryStrategy implements SalaryStrategy {
    public Integer should_pay(Integer basic_salary, Integer post_salary) {
        return (basic_salary) * 12 + post_salary;
    }
}
