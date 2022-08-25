package com.javasm.demo.service;

import com.javasm.demo.spring.*;

@Component
public class UserService implements UserInterface {
    @Autowired
    private OrderService orderService;


    @Override
    public void test() {
        System.out.println("123 proxy");
    }



}
