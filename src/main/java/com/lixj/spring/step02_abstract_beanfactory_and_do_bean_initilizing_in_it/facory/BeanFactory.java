package com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.facory;

import com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it.BeanDefination;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.spring.step02_abstract_beanfactory_and_do_bean_initilizing_in_it
 * Description :    为了扩展性，定义为接口方法，具体实现通过子类实现
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2021/01/09
 */

public interface BeanFactory {

    // 容器提供注册和获取功能
    Object getBean (String name);

    void registerBean (String name, BeanDefination beanDefination);

}
