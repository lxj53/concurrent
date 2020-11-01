package com.lixj.concurrent.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.common
 * Description :    测试volatile作用_不具备原子性
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/01
 */

@Slf4j
public class Test4_Volatile3 {
    private static /*volatile*/ int count = 0;

    /**
     * 结论：volatile只能保证线程可见性，不能保证原子性
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                // 需要加锁同步，count字段使用volatile字段修饰，只能保证线程读到的值是最新值，
                // 可能两个线程读到相同的值，然后都加一，此时相当于加了一次
                synchronized (Test4_Volatile3.class) {
                    count++;
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                synchronized (Test4_Volatile3.class) {
                    count++;
                }
            }
        });
        t2.start();

        // 等待线程执行结束
        TimeUnit.SECONDS.sleep(2);
        log.info("count = {}", count);
    }
}
