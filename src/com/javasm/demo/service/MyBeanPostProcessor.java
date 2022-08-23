package com.javasm.demo.service;

import com.javasm.demo.spring.BeanPostProcessor;
import com.javasm.demo.spring.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if (beanName.equals("userService")){
            System.out.println("123 before");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
           if ( beanName.equals("userService")){
               System.out.println("123 after");
           }
           return bean;
    }
}
