package com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.facory;

import com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.BeanDefination;
import com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.HelloWorldService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.facory
 * Description :
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2021/01/09
 */

class BeanFactoryTest {
    @Test
    void test() {
        // 1.创建bean容器
        BeanFactory beanFactory = new AutowireCapableBeanFactory();

        // 2.注入bean
        BeanDefination beanDefination = new BeanDefination();
        beanDefination.setBeanClassName("com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.HelloWorldService");
        // 注册beanDefination时，会根据全限类名初始化bean对象
        beanFactory.registerBean("helloWorld", beanDefination);

        // 3.获取bean
        HelloWorldService helloWorld = (HelloWorldService) beanFactory.getBean("helloWorld");
        helloWorld.hello();
    }
}