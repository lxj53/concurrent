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
 * Description :    reentrantLock作用_tryLock使用
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/02
 */

@Slf4j
public class Test1_ReentrantLock2 {

    private Lock reentrantLock = new ReentrantLock();

    private void threadRun1() {
        boolean lock = false;
        try {
            // 不带时间属性的tryLock,直接获取锁对象，然后将是否获取到锁对象的标志返回
            lock = reentrantLock.tryLock();
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                log.info("线程1输出：{}", i);
            }
        } catch (InterruptedException e) {
            log.error("中断异常：{}", e);
        } finally {
            if (lock) {
                reentrantLock.unlock();
                log.info("线程1进行解锁操作");
            }
        }
    }

    private void threadRun2() {
        boolean lock = false;
        try {
            // notice-lixj
            // 尝试去获取锁对象，指定时间内若没获取到，则自动返回未获取到锁的标志
            // 然后后续代码自动执行
            lock = reentrantLock.tryLock(2, TimeUnit.SECONDS);
            // notice-lixj 注意
            // 最差的结果也是在2秒后执行（不管有没有获取到锁都会执行）
            for (int i = 0; i < 10; i++) {
                log.info("线程2输出：{}", i);
            }
        } catch (InterruptedException e) {
            log.error("中断异常：{}", e);
        } finally {
            if (lock) {
                reentrantLock.unlock();
                log.info("线程2进行解锁操作");
            } else {
                log.info("线程2没有获取到锁");
            }
        }
    }

    /**
     * 结论：tryLock 在指定时间内尝试去获取锁，然后返回有没有锁定的标志
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Test1_ReentrantLock2 test2_reentrantLock2 = new Test1_ReentrantLock2();
        Thread thread = new Thread(test2_reentrantLock2::threadRun1);
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread thread2 = new Thread(test2_reentrantLock2::threadRun2);
        thread2.start();
    }

}
