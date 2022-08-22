package com.javasm.demo.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) //运行时动态获取注解信息;注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
public @interface Autowired {
    String value() default "";
}
