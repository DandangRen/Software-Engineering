package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.vo.CustomerVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
    * 根据客户类型查找客户
     * @param type 客户类型
    */
    @GetMapping("/findByType")
    public Response findByType(@RequestParam CustomerType type) {
        return Response.buildSuccess(customerService.getCustomersByType(type));
    }

    /**
     * 更新客户数据
     * @param customerPO 客户
     */
    @PostMapping("/updateCustomer")
    public Response updateCustomer(@RequestBody CustomerPO customerPO){
        customerService.updateCustomer(customerPO);
        return Response.buildSuccess(customerPO);
    }

    /**
     * 建立客户对象
     * @param customerPO 客户
     */
    @PostMapping("/createCustomer")
    public Response createCustomer(@RequestBody CustomerPO customerPO){
        customerService.createCustomer(customerPO);
        return Response.buildSuccess(customerPO);
    }

    /**
     * 删除客户对象
     * @param id 客户id
     */
    @GetMapping("/deleteCustomer")
    public Response deleteCustomer(@RequestParam Integer id){
        customerService.deleteCustomer(id);
        return Response.buildSuccess();
    }

}
