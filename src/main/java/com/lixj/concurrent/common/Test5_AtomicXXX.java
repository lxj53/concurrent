package com.lixj.concurrent.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.common
 * Description :    测试java提供的原子操作类
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/01
 */

@Slf4j
public class Test5_AtomicXXX {
    // 定义使用方法
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 结论：atomicXXX封装了常用的操作（通过Unsafe类使用CAS操作实现），避免开发人员手动加锁
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // 线程开始自增数量
        new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                atomicInteger.incrementAndGet();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                atomicInteger.incrementAndGet();
            }
        }).start();

        TimeUnit.SECONDS.sleep(3);
        log.info("atomicInteger = {}", atomicInteger);
    }
}
