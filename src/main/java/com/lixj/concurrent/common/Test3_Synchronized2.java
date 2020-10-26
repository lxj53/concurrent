package com.lixj.concurrent.common;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.common
 * Description :    synchronized使用方式2：修饰静态的方法
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/10/26
 */

@Slf4j
public class Test3_Synchronized2 {

    /**
     * 测试synchronized修饰静态的方法
     * 结论：
     * 由于class对象只有一个，因此即使多个线程要执行的target对象是不同对象，也可以实现线程安全
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        // 场景2：两个线程中的线程实例是不同的对象 （不同点：对象中现在synchronized修饰的是静态方法）
        SynchronizedGood synchronizedGood3 = new SynchronizedGood();
        SynchronizedGood synchronizedGood4 = new SynchronizedGood();
        Thread thread3 = new Thread(synchronizedGood3);
        Thread thread4 = new Thread(synchronizedGood4);
        thread3.start();
        thread4.start();
        thread3.join();
        thread4.join();
        int result2 = synchronizedGood3.getResult();
        // 执行结果为20000，因为此时锁的是class对象的锁，class对象只有一个
        log.info("不同线程对象实例，运行后结果：{}", result2);
    }

    static class SynchronizedGood implements Runnable {
        // 线程共享资源
        static int i = 0;

        // 静态方法上的锁，锁的是class对象，也就是SynchronizedGood对应的class对象
        private static synchronized void increase() {
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
