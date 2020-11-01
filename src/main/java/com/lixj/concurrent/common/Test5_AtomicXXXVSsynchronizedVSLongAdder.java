package com.lixj.concurrent.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.common
 * Description :    atomic、synchronized、longadder性能比较
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/01
 */

@Slf4j
public class Test5_AtomicXXXVSsynchronizedVSLongAdder {
    private static long count1 = 0L;
    private static AtomicLong count2 = new AtomicLong(0);
    private static LongAdder count3 = new LongAdder();

    /**
     * 结论： longAdder > atomic > synchronized
     * 原因：synchronized：需要申请锁
     * atomic：高并发意味着CAS的失败几率更高，重试次数更多，越多线程重试，CAS失败几率又越高，变成恶性循环
     * longAdder:低并发时只用对一个块进行cas操作，和atomic一样
     * 高并发时，对多个段进行cas,降低了单个value的热度，进行分段更新，同时可增加段的数量
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];
        // synchronized实现自增
        Object lock = new Object();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    synchronized (lock) {
                        count1++;
                    }
                }
            });
        }
        long start = System.currentTimeMillis();
        // 启动线程并等待线程结束
        for (Thread myThread : threads) {
            myThread.start();
        }
        for (Thread myThread : threads) {
            myThread.join();
        }
        long end = System.currentTimeMillis();
        log.info("synchronized结果：{} 耗时：{}", count1, end - start);

        // atomic实现自增
        log.info("----------------------------------");
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    count2.incrementAndGet();
                }
            });
        }
        start = System.currentTimeMillis();
        for (Thread myThread : threads) {
            myThread.start();
        }
        for (Thread myThread : threads) {
            myThread.join();
        }
        end = System.currentTimeMillis();
        log.info("atomic结果：{} 耗时：{}", count2, end - start);

        // longAdder实现自增
        log.info("----------------------------------");
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    count3.increment();
                }
            });
        }
        start = System.currentTimeMillis();

        for (Thread myThread : threads) {
            myThread.start();
        }

        for (Thread myThread : threads) {
            myThread.join();
        }
        end = System.currentTimeMillis();
        log.info("longAdder结果：{} 耗时：{}", count3, end - start);
    }


}
