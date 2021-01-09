package com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it
 * Description :    bean的增强类型，扩展了元数据等信息
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2021/01/09
 */

public class BeanDefination {

    private Object bean;

    private Class beanClass;

    private String beanClassName;

    // 提供空参构造器，用于创建空的对象，然后设置对象的全限类名。或者提供有全限类名的构造器
    public BeanDefination() {
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;

        try {
            Class<?> beanClass = Class.forName(beanClassName);
            this.beanClass = beanClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
