package com.javasm.demo.service;

import com.javasm.demo.spring.*;

@Component
@Scope("prototype")
public class UserService implements BeanNameAware, InitializingBean {
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

    @Override
    public void afterPropertiesSet() {
        System.out.println("初始化方法执行!");
    }
}
