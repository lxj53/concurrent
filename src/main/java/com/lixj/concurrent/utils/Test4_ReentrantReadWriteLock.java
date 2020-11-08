package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    测试读写锁
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/08
 */

@Slf4j
public class Test4_ReentrantReadWriteLock {
    // 读写锁
    ReadWriteLock lock = new ReentrantReadWriteLock();

    // 读线程操作
    public void read(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            log.info("读线程");
            // 模拟读写操作耗时
            TimeUnit.SECONDS.sleep(1);
        } finally {
            lock.unlock();
        }
    }

    // 写线程操作
    public void write(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            log.info("写线程");
            TimeUnit.SECONDS.sleep(1);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 结论：当读多写少的时候，可以使用读写锁，提高效率
     * 读锁：是共享锁，当其他线程申请读的时候可以共享
     * 写锁：排他锁，其他线程申请写时，不允许其他线程操作（读操作也不允许，避免读到的值是未写完的值）
     *
     * @param args
     */
    public static void main(String[] args) {
        Test4_ReentrantReadWriteLock reentrantReadWriteLock = new Test4_ReentrantReadWriteLock();

        // 读写锁。
        // 读锁：是共享锁，当其他线程申请读的时候可以共享
        // 写锁：排他锁，其他线程申请写时，不允许其他线程操作（读操作也不允许）
        Lock readLock = reentrantReadWriteLock.lock.readLock();
        Lock writeLock = reentrantReadWriteLock.lock.writeLock();
        // 正常的lock锁是排他锁
        Lock normalLock = new ReentrantLock();
        // 启用18个线程进行读操作
        for (int i = 0; i < 18; i++) {
            new Thread(() -> {
                try {
                    // 正常的锁：读写操作都是每隔一秒输出
                    // 读锁：18个线程可以并发的读操作，提高效率
//                    reentrantReadWriteLock.read(normalLock);
                    reentrantReadWriteLock.read(readLock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // 启用两个线程进行写操作
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    reentrantReadWriteLock.write(normalLock);
//                    reentrantReadWriteLock.write(writeLock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
