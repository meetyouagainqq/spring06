package com.javasm.demo;

import com.javasm.demo.service.AppConfig;
import com.javasm.demo.service.UserInterface;
import com.javasm.demo.service.UserService;
import com.javasm.demo.spring.PostConstruct;
import com.javasm.demo.spring.TestApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) {
        TestApplicationContext context = new TestApplicationContext(AppConfig.class);
//        System.out.println(context.getBean("userService"));
        System.out.println(context.getBean("userService"));//底层至少有一个Map<beanName,Bean>对象
        System.out.println(context.getBean("userService"));
        System.out.println(context.getBean("userService"));
//        System.out.println(context.getBean("orderService"));
        UserService userService = (UserService) context.getBean("userService");
        userService.test();
        //bean对象初始化之前做的
    //        for (Method method : userService.getClass().getDeclaredMethods()) {
    //            if (method.isAnnotationPresent(PostConstruct.class)) {
    //                method.invoke(userService,null);
    //            }
    //        }
    }
}
