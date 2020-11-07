package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

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
    private Lock reentrantLock = new ReentrantLock(true);

    /**
     * 用于检测是否是公平锁，若是正常锁，应该是交替输出，可通过声明公平非公平对比试验结果
     */
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                reentrantLock.lock();
                log.info("线程{}得到锁", Thread.currentThread().getName());
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    /**
     * 结论：创建Lock锁的时候，增加是否是公平锁的标志入参，默认是非公平锁（synchronized默认也是非公平锁）
     * 原理：
     * 非公平锁：锁自旋后会进入到block队列，新线程需要获取锁的时候可能正在自旋，自旋时可能直接先获取到锁，
     * 即使没自旋的线程，block队列中线程获取到锁的概率也要取决于调度器
     * 公平锁：需要获取锁对象时，先看block队列中是否有等待队列，有的话直接进入等待队列，没有可以自旋。
     * 当其他线程释放锁后，将锁对象赋予block中最先等待的线程
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) {
        Test1_ReentrantLock4 test1_reentrantLock4 = new Test1_ReentrantLock4();
        Thread t1 = new Thread(test1_reentrantLock4::run);
        Thread t2 = new Thread(test1_reentrantLock4::run);
        // t1和t2线程启动后，轮流去争取锁对象
        t1.start();
        t2.start();
    }
}
