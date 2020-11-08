package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    测试信号量
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/08
 */

@Slf4j
public class Test5_Semaphore {

    // 信号量，入参为准许的数量
    Semaphore semaphore = new Semaphore(1);

    public void run() {
        try {
            // 获取准许，获取到后，信号量减1，当信号量为0时，线程在此阻塞
            semaphore.acquire();
            log.info("线程{}开始执行", Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(1);
            log.info("线程{}执行结束", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 信号量释放时，信号量自增，需放到finally中，确保会执行到
            semaphore.release();
        }
    }

    /**
     * 结论：当时限流的场景时可以使用semaphore（同时允许几个线程并发操作）
     *
     * @param args
     */
    public static void main(String[] args) {
        Test5_Semaphore test5_semaphore = new Test5_Semaphore();
        // 启动两个线程，可通过信号量为1和2，查看输出区别
        // 信号量为1：另外一个线程需要等第一个线程释放掉后才能执行，因其会阻塞到acquire()方法
        // 信号量为2：两个线程可以并发执行，两个线程都可以acquire到
        new Thread(() -> test5_semaphore.run()).start();
        new Thread(() -> test5_semaphore.run()).start();
    }
}
