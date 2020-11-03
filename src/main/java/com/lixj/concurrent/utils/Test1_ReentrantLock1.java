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
 * Description :    测试ReentrantLock作用_同synchronized一样，是可重入的
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/01
 */

@Slf4j
public class Test1_ReentrantLock1 {
    private int count = 0;

    private Lock reentrantLock = new ReentrantLock();

    // 每隔一秒打印一次，两个线程同时启动，判断第二个线程是否可以在中间输出
    private void threadRun() {
        // notice-lixj
        // Lock必须写在try finally中，需要手动释放锁
        try {
            // 类似于synchronized
            reentrantLock.lock();
            for (int i = 0; i < 10; i++) {
                count++;
                log.info("threadRun开始执行。。。{}", count);
                TimeUnit.SECONDS.sleep(1);

                // 执行的过程中，调用相同有锁的代码，判断是否可以执行
                if (i == 2) {
                    threadRun2();
                }

            }
        } catch (Exception e) {
            log.error("报错：{}", e);
        } finally {
            reentrantLock.unlock();
        }
    }

    private void threadRun2() {
        try {
            reentrantLock.lock();
            log.info("threadRun2开始执行。。。");
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * 结论：reentrantLock是可重入的（同一个线程）
     * 若是两个不同的线程，则需要等另外一个线程释放后，才能获取到锁对象
     *
     * @param args
     */
    public static void main(String[] args) {
        Test1_ReentrantLock1 test1_reentrantLock1 = new Test1_ReentrantLock1();
        Thread thread1 = new Thread(test1_reentrantLock1::threadRun);
        thread1.start();

        // 不同的线程，需要等第一个线程释放锁后才能执行（线程1和线程2都需要锁住对象）
        // 即需要等线程1输出结束后才能输出线程2，可查看输出日志中线程的名称
        Thread thread2 = new Thread(test1_reentrantLock1::threadRun2);
        thread2.start();

    }
}
