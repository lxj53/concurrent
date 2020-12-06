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
 * Description :    查看aqs底层原理
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/05
 */

@Slf4j
public class Test8_AQS {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock(Boolean.TRUE);
        try {
            // 在此处打断点，查看代码流程
            lock.lock();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.info("并发报错", e);
        } finally {
            lock.unlock();
        }
    }
}
