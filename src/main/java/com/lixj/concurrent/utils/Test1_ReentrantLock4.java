package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    测试reentrantLock作用_公平锁
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/02
 */

@Slf4j
public class Test1_ReentrantLock4 {
    private static Lock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                try {
                    reentrantLock.lock();
                    log.info("第{}个线程开始执行", Thread.currentThread().getName());
                    if (finalI == 0) {
                        try {
                            TimeUnit.SECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    reentrantLock.unlock();
                }
            }, String.valueOf(i + 1));
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
            TimeUnit.MILLISECONDS.sleep(2);
        }
    }
}
