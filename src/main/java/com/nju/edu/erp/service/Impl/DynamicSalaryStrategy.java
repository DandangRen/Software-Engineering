package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.service.SalaryStrategy;

public class DynamicSalaryStrategy implements SalaryStrategy {
    public Integer should_pay(Integer basic_salary, Integer post_salary) {
        return basic_salary + post_salary;
    }
}
