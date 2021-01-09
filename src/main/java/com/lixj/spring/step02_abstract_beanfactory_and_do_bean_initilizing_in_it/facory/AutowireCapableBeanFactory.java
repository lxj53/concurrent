package com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.facory;

import com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.BeanDefination;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.facory
 * Description :    自动注入的bean容器
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2021/01/09
 */

public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object doCreateBean(BeanDefination beanDefination) {
        try {
            // 通过反射的方式创建bean对象
            Object bean = beanDefination.getBeanClass().newInstance();
            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
