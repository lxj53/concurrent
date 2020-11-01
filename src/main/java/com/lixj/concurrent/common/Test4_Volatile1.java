package com.lixj.concurrent.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.common
 * Description :    测试volatile作用_线程可见性
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/01
 */
@Slf4j
public class Test4_Volatile1 {
    // 多个线程共享的资源，增加了volatile关键字的，字段被修改后可被其他线程立马看到
    private static volatile boolean runFlag = true;

    /**
     * 结论：volatile关键字具有线程可见性
     * 增加volatile关键字，字段修改后，可被其他线程立马可见，若没关键字，则不会立马可见，会一直空循环
     * 不加：不会输出结束日志信息
     * 增加：会输出结束日志信息
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // 线程1判断runFlag标志，是否输出结束日志
        new Thread(() -> {
            while (runFlag) {

            }
            log.info("myThread线程运行结束。。。");
        }, "myThread").start();

        // 用于线程1完成启动
        TimeUnit.SECONDS.sleep(2);

        // 修改共享变量，判断线程1是否可见
        new Thread(() -> {
            log.info("myThread2线程启动");
            runFlag = false;
            log.info("runFlag被修改成false");
        }, "myThread2").start();
    }
}
