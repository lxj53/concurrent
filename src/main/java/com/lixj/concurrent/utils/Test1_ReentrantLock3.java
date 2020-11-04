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
 * Description :    测试lockInterupptibly功能
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/02
 */

@Slf4j
public class Test1_ReentrantLock3 {

    private static Lock lock = new ReentrantLock();

    /**
     * 结论：查看lock和lockInterruptibly的区别
     * lock.lock(); 必须等持有锁对象的线程做完事情，其他等待的线程才可以做事情。而且中途不能退出。
     * lock.lockInterruptibly(); 也必须是等待持有锁对象的线程做完事情，其他线程才能做事情，但中途可以退出。（在等待的过程的可以随时被打断）
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // 线程1先获取锁，然后开始睡眠
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                TimeUnit.SECONDS.sleep(4);
                log.info("线程1正常输出");
            } catch (InterruptedException e) {
                log.error("线程1并发报错:", e);
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        // 主线程等待，确保线程1已经启动并且拿到锁
        TimeUnit.MILLISECONDS.sleep(50);

        // 线程2开始启动
        Thread t2 = new Thread(() -> {
            try {
//                lock.lock();
                // 使用该方法，可以随时响应中断标志，若使用lock方法，只有在获取到锁后才能响应中断信息
                lock.lockInterruptibly();
                TimeUnit.SECONDS.sleep(2);
                log.info("线程2打断后的输出");
            } catch (InterruptedException e) {
                log.error("线程2并发报错:", e);
            } finally {
                try {
                    lock.unlock();
                    log.info("线程2正常释放锁");
                } catch (Exception e) {
                    log.info("可能没获取到锁：", e);
                }
            }
        });
        t2.start();

        log.info("1秒后打断线程2");
        TimeUnit.SECONDS.sleep(1);

        // 1秒后打断线程2
        t2.interrupt();
    }

}
