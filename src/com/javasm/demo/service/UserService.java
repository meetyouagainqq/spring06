package com.javasm.demo.service;

import com.javasm.demo.spring.*;

@Component
public class UserService implements UserInterface {
    @Autowired
    private OrderService orderService;
    private User admin;

    @PostConstruct
    public void a() {
     //mysql-->管理员信息--->User对象--->admin
    }

    @Override
    public void test() {
        System.out.println("123 proxy");
    }


}
