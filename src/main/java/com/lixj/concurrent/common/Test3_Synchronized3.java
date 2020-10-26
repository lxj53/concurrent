package com.lixj.concurrent.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.common
 * Description :    synchronized使用场景3：作用于同步代码块
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/10/26
 */

@Slf4j
public class Test3_Synchronized3 {

    /**
     * 测试同步代码块：避免整个方法比较大，存在耗时的方法，锁整个方法可能效率低，因此只锁需要同步的地方
     * 结论：
     * 可通过锁对象、this实例、class对象实现锁代码块
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // 场景2：两个线程中的线程实例是不同的对象 （不同点：锁的是同一个静态对象）
        SynchronizedGood synchronizedGood3 = new SynchronizedGood();
        SynchronizedGood synchronizedGood4 = new SynchronizedGood();
        Thread thread3 = new Thread(synchronizedGood3);
        Thread thread4 = new Thread(synchronizedGood4);
        thread3.start();
        thread4.start();
        thread3.join();
        thread4.join();
        int result2 = synchronizedGood3.getResult();
        // 执行结果为20000，因为此时锁的是同一个静态对象
        log.info("不同线程对象实例，运行后结果：{}", result2);
    }

    static class SynchronizedGood implements Runnable {
        // 需要锁定的对象
        static SynchronizedGood instance = new SynchronizedGood();

        // 线程共享资源
        static int i = 0;

        private void increase() throws InterruptedException {
            // 模拟耗时操作
            Thread.sleep(1000);

            // 注意：三个方法不能同时放开，因为可能两个两个执行到instance和class地方，
            // 此时两个锁对象是不同的对象，相当于同时操作共享资源
            // 1.锁对象为同一个instance对象
            synchronized (instance) {
                for (int j = 0; j < 10000; j++) {
                    i++;
                }
            }

            // 2.锁对象为当前实例
            synchronized (this) {
                for (int j = 0; j < 10000; j++) {
                    i++;
                }
            }

            // 3.锁对象为class对象
            synchronized (SynchronizedGood.class) {
                for (int j = 0; j < 10000; j++) {
                    i++;
                }
            }
        }

        @SneakyThrows
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
