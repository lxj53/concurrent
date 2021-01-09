package com.lixj.spring.step01_container_register_and_get;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.spring.step01
 * Description :    bean的容器，可以从中获取到bean对象
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2021/01/09
 */

public class BeanFactory {

    // map类型的容器，key为bean的id，value为bean类型的包装类型
    Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 从容器中获取bean对象
     *
     * @param name
     * @return
     */
    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }

    /**
     * 向容器中注册bean相关信息
     *
     * @param name
     * @param beanDefinition
     */
    public void registerBeanDefinition (String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

}
