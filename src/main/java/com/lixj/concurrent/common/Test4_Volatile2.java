package com.lixj.concurrent.common;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.common
 * Description :    测试volatile作用_禁止指令重排序
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/01
 */

@Slf4j
public class Test4_Volatile2 {

    // 实例对象的引用
    private static volatile Test4_Volatile2 INSTANCE = null;

    // 私有的构建方法，禁止外部创建
    private Test4_Volatile2() {

    }

    // 提供单例对象实现方法，外部通过此方法获取实例对象
    public Test4_Volatile2 getINSTANCE() {
        // 2 这一层判断增加的原因：不加也是可以的，但是增加的好处：先判断实例是否存在，存在则返回
        // 若不加，调用方法则会直接到synchronized关键字，需要先获取锁资源，然后再判断实例是否存在，效率低
        if (INSTANCE == null) {
            // 1 增加synchronized关键字，保证线程安全
            synchronized (this) {
                // 核心逻辑：如果实例引用为空，则创建实例对象
                if (INSTANCE == null) {
                    // 3 增加volatile作用：创建对象的指令对应三个，申请内存空间（对象属性是默认值）、对象初始化（对属性赋值）、将内存地址返回
                    // 不增加volatile，可能会将申请内存空间、返回地址的指令排在对象初始化指令前面，导致若两个线程同时进来，
                    // 第1线程进行到此时进行重排序，未初始化后就已返回，第2线程在最外层判断时，INSTANCE此时已为非空
                    // 即第2线程拿到了未初始化完成的对象，因此需要增加volatile关键字
                    INSTANCE = new Test4_Volatile2();
                }
            }
        }
        return INSTANCE;
    }
}
