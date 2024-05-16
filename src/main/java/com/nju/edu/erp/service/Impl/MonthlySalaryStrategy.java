package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.service.SalaryStrategy;

public class MonthlySalaryStrategy implements SalaryStrategy {
    @Override
    public Integer should_pay(Integer basic_salary, Integer post_salary) {
        return basic_salary;
    }
}
