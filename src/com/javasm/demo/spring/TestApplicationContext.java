package com.javasm.demo.spring;

import com.javasm.demo.service.UserService;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TestApplicationContext {
    public Class configClass;
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> singletonMap = new ConcurrentHashMap<>();
    private ArrayList<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public TestApplicationContext(Class configClass) {
        //第一步 扫描配置类

        this.configClass = configClass;
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            //获取扫描注解的相关信息
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            //获取的注解值为扫描路径
            String path = componentScanAnnotation.value(); //路径 com.javasm.demo
            path = path.replace(".", "/"); //com/javasm/demo
            ClassLoader classLoader = TestApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile()); //既能表示一个对象也能表示一个目录 ，故封装成为一个File对象
            //System.out.println(file);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    String fileName = f.getAbsolutePath();
                    //System.out.println(fileName);
                    if (fileName.endsWith(".class")) {
                        String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                        // className com.javasm.demo.service.UserService
                        className = className.replace("\\", ".");
                        //System.out.println(className);
                        try {
                            Class<?> loadClass = classLoader.loadClass(className);
                            if (loadClass.isAnnotationPresent(Component.class)) {
                                if (BeanPostProcessor.class.isAssignableFrom(loadClass)) {
                                    BeanPostProcessor instance = (BeanPostProcessor) loadClass.newInstance();
                                    beanPostProcessorList.add(instance);
                                }


                                Component component = loadClass.getAnnotation(Component.class);
                                String beanName = component.value();
                                //当component注解的名称为空时
                                if ("".equals(beanName)) {
                                    beanName = Introspector.decapitalize(loadClass.getSimpleName());
                                }
                                //证明是一个bean;同时说明程序员生成了一个bean
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(loadClass);
                                if (loadClass.isAnnotationPresent(Scope.class)) {
                                    Scope scopeAnnotation = loadClass.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scopeAnnotation.value());
                                } else {
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinitionMap.put(beanName, beanDefinition);

                            }
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                Object bean = createBean(beanName, beanDefinition);
                singletonMap.put(beanName, bean);
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class classType = beanDefinition.getType(); //当前创建bean的类
        try {
            Object instance = classType.getConstructor().newInstance();
            //实现属性的依赖注入
            for (Field field : classType.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    field.set(instance, getBean(field.getName()));
                }
            }
            if (instance instanceof BeanNameAware) {
                //spring将某个东西告诉当前的bean
                ((BeanNameAware) instance).setBeanName(beanName);
            }
            //
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
               beanPostProcessor.postProcessBeforeInitialization(beanName,instance);
            }
            if (instance instanceof InitializingBean) {
                //调用初始化方法
                ((InitializingBean) instance).afterPropertiesSet();
            }
            //BeanPostProcessor 后置处理器
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
               beanPostProcessor.postProcessAfterInitialization(beanName,instance);
            }
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException();
        } else {
            String scope = beanDefinition.getScope();
            if (scope.equals("singleton")) {
                Object bean = singletonMap.get(beanName);
                if (bean == null) {
                    Object bean1 = createBean(beanName, beanDefinition);
                    singletonMap.put(beanName, bean1);
                }
                return bean;
            } else {
                //多例
                return createBean(beanName, beanDefinition);
            }
        }
    }
}
