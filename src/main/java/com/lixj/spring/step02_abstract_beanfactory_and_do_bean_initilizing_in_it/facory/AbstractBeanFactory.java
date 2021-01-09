package com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.facory;

import com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.BeanDefination;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.facory
 * Description :    抽象的bean容器
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2021/01/09
 */

/**
 * 获取bean和注册bean的逻辑基本都一样，因此提供抽象类，
 * 暴露一个抽象的创建对象的方法，子类只需要关注如何创建对象即可，便于扩展
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    Map<String, BeanDefination> beanDefinationMap = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String name) {
        return beanDefinationMap.get(name).getBean();
    }

    @Override
    public void registerBean(String name, BeanDefination beanDefination) {
        // 在容器中创建bean对象，具体怎么创建，看具体的实现类
        Object bean = doCreateBean(beanDefination);
        beanDefination.setBean(bean);
        beanDefinationMap.put(name, beanDefination);
    }

    protected abstract Object doCreateBean(BeanDefination beanDefination);
}
