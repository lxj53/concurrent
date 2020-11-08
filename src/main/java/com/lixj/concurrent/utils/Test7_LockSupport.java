package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    测试LockSupport
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/08
 */

@Slf4j
public class Test7_LockSupport {

    /**
     * 结论：LockSupport可阻塞线程和唤醒指定的线程，以前只能通过先获取到锁对象，然后wait 或者 await
     * 区别：
     * 1. wait 和 await方法需要先获取到锁（需要在锁的代码块中调用），park方法不需要获取锁
     * 2. unpark方法可以唤醒指定的线程，而notify/notifyAll不能唤醒指定的线程，只能唤醒阻塞队列中的随机线程/所有线程
     * 3.unpark方法可以先与park方法方法执行（可以提前抵消一次park操作），而notify不能先于wait方法执行
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (i == 5) {
                    // 线程阻塞，等待其他线程唤醒
                    LockSupport.park();
                }
                log.info(String.valueOf(i));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

        // 主线程唤醒t线程
        LockSupport.unpark(t);
    }
}
