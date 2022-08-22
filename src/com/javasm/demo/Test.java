package com.javasm.demo;

import com.javasm.demo.service.AppConfig;
import com.javasm.demo.spring.TestApplicationContext;

public class Test {
    public static void main(String[] args) {
        TestApplicationContext context = new TestApplicationContext(AppConfig.class);
        System.out.println(context.getBean("userService"));
        System.out.println(context.getBean("userService"));
        System.out.println(context.getBean("userService"));
        System.out.println(context.getBean("userService"));
    }
}
