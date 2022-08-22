package com.javasm.demo.service;

import com.javasm.demo.spring.Autowired;
import com.javasm.demo.spring.Component;
import com.javasm.demo.spring.Scope;

@Component
@Scope("prototype")
public class UserService {
    @Autowired
    private OrderService orderService;
    public void test(){
        System.out.println(orderService);
    }
}
