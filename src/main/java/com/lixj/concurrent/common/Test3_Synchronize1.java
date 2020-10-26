package com.lixj.concurrent.common;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.concurrent
 * Description :    线程同步_synchronized修饰实例方法
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/10/19
 */

@Slf4j
public class Test3_Synchronize1 {

    /**
     * 测试synchronized修饰实例方法。
     * 结论：
     * 1.若线程中的实例对象是同一对象时，可实现线程安全。
     * 2.若多个线程中的实例对象时不同的对象，但不同的对象共享同一份资源，
     * 由于不同的线程占用不同对象的锁资源，此时线程是不安全的
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // 场景1：两个线程中的线程实例是相同的对象
        SynchronizedGood synchronizedGood = new SynchronizedGood();
        Thread thread1 = new Thread(synchronizedGood);
        Thread thread2 = new Thread(synchronizedGood);
        // 两个线程启动后执行run方法中的increase，由于有关键字synchronized
        // 因此需要先获取synchronizedGood对象的锁
        // 注意：此时两个线程的实例对象是同一个对象，因此竞争的是同一个对象的锁，
        // 两个线程在运行时存在互斥（同一时刻只能一个线程执行synchronized的代码块）
        thread1.start();
        thread2.start();
        // 等待thread1线程运行结束后，再执行主线程
        thread1.join();
        thread2.join();
        int result = synchronizedGood.getResult();
        // 执行结果为20000
        log.info("相同线程对象实例，运行后结果：{}", result);

        // 场景2：两个线程中的线程实例是不同的对象
        SynchronizedGood synchronizedGood3 = new SynchronizedGood();
        SynchronizedGood synchronizedGood4 = new SynchronizedGood();
        Thread thread3 = new Thread(synchronizedGood3);
        Thread thread4 = new Thread(synchronizedGood4);
        // 注意：此时两个线程的实例对象是两个不同的对象，但静态资源是共享的
        // 因此两个线程在执行时占用的是不同对象的锁，不存在互斥，因此最后的结果可能不是20000
        thread3.start();
        thread4.start();
        thread3.join();
        thread4.join();
        int result2 = synchronizedGood3.getResult();
        // 执行结果不为20000，解决方式：将synchronized作用于静态的方法，此时需要竞争的是class对象
        // 无论创建多少实例对象，class对象只有一个，因此竞争的是同一个对象的锁资源
        log.info("不同线程对象实例，运行后结果：{}", result2);

    }

    static class SynchronizedGood implements Runnable {
        // 线程共享资源
        static int i = 0;

        // 实例方法上的锁，线程执行时需先获取实例对象的锁
        private synchronized void increase() {
            for (int j = 0; j < 10000; j++) {
                i++;
            }
        }

        @Override
        public void run() {
            increase();
        }

        /**
         * 获取到实例对象共享的静态资源
         *
         * @return
         */
        private int getResult() {
            return i;
        }
    }
}
