package com.javasm.demo.spring;

public class TestApplicationContext {
    public Class configClass;

    public TestApplicationContext(Class configClass) {
        this.configClass = configClass;
    }
    public Object getBean(String beanName){
        return null;
    }
}
