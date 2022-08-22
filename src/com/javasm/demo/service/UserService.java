package com.javasm.demo.service;

import com.javasm.demo.spring.Autowired;
import com.javasm.demo.spring.BeanNameAware;
import com.javasm.demo.spring.Component;
import com.javasm.demo.spring.Scope;

@Component
@Scope("prototype")
public class UserService implements BeanNameAware {
    @Autowired
    private OrderService orderService;
    private String beanName;

    public void test() {
        //System.out.println(orderService);
        System.out.println(this.beanName);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
