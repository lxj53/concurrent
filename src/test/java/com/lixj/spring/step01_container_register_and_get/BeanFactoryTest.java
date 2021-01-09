package com.lixj.spring.step01_container_register_and_get;

import org.junit.jupiter.api.Test;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.spring.step01
 * Description :
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2021/01/09
 */

/**
 * 缺点：bean对象是手动创建好后再注册到容器中，希望通过容器负责管理bean的创建
 * 后期我们只会提供xml配置文件或者注解，只提供类对象的全限类名，容器需要支持通过反射等方法创建对象并放在容器中
 */
class BeanFactoryTest {

    @Test
    void test() {
        // 1.初始化容器
        BeanFactory beanFactory = new BeanFactory();

        // 2.创建对象并将对象注册到容器中
        BeanDefinition beanDefinition = new BeanDefinition(new HelloWorldService());
        beanFactory.registerBeanDefinition("helloWorld", beanDefinition);

        // 3.从容器中拿对象
        HelloWorldService helloWorld = (HelloWorldService) beanFactory.getBean("helloWorld");
        helloWorld.hello();
    }
}