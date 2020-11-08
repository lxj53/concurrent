package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    测试线程之间的通信
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/08
 */

@Slf4j
public class Test6_Exchanger {

    // 定义交换的容器
    static Exchanger<String> exchanger = new Exchanger<>();

    /**
     * 结论：exchanger用于线程间通信交换数据，exchange()方法是阻塞方法
     *
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() -> {
            String s = "T1";
            try {
                // 此处线程会阻塞，直到有其他线程来交换，将交换后的值返回
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("线程{}输出{}", Thread.currentThread().getName(), s);
        }, "t1").start();

        new Thread(() -> {
            String s = "T2";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("线程{}输出{}", Thread.currentThread().getName(), s);
        }, "t2").start();
    }
}
