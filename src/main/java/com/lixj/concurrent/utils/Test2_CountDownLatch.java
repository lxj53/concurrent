package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    测试CountDownLatch
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/07
 */

@Slf4j
public class Test2_CountDownLatch {

    /**
     * 结论：CountDownLatch使用场景：可等待其他线程都运行结束后才执行
     * 即使用lacth.await()方法需等门栓数为0才能放行
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];
        // 创建门栓
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                log.info("线程{}进行countdown操作", Thread.currentThread().getName());
                // 子线程对门栓进行减操作
                latch.countDown();
            }, String.valueOf(i + 1));
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        // 等待门栓数为0后才能放行
        latch.await();

        log.info("主线程结束");
    }
}
