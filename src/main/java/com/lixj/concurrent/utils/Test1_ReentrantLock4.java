package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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

    // 创建锁的时候，增加是否是公平锁的标志，默认是非公平锁
    private static Lock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5000];

        CountDownLatch countDownLatch = new CountDownLatch(1);

        AtomicInteger atomicInteger = new AtomicInteger(0);

        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                try {
                    reentrantLock.lock();
                    log.info("第{}个线程开始执行", Thread.currentThread().getName());
                    if (Integer.parseInt(Thread.currentThread().getName()) != atomicInteger.incrementAndGet()) {
                        log.error("####################出现非公平现象:{}", Thread.currentThread().getName());
                    }
//                        countDownLatch.await();
                    if (finalI == 0) {
                        TimeUnit.SECONDS.sleep(20);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }, String.valueOf(i + 1));
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
            // 依次启动线程，确保线程是按照顺序启动的
            TimeUnit.MILLISECONDS.sleep(2);
        }
        log.info("主线程睡眠一会，再叫醒第一个线程");
//            TimeUnit.SECONDS.sleep(2);
//            countDownLatch.countDown();
    }
}
