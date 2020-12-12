package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    测试ThreadLocal
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/07
 */

@Slf4j
public class Test9_ThreadLocal {

    /**
     * 总结：threadlocal中的变量实际是存储到了每个线程对象的threadLocals属性中（属性是个map对象，key为threadlocal对象，value为存入的值），因此可以说每个线程中的变量是各自独享的
     * 注意：threadlocal可能存在内存泄漏，每次使用完需要手动remove
     * 内存泄漏原因：1、若threadlocal对象一直有强引用存在，则垃圾回收器就一直无法回收map中的对象
     *            2、map中的value一直是强引用，如果不手动remove调，垃圾回收器没法回收value对象
     * @param args
     */
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            // 因为引用的是静态属性，因此两处的threadlocal对象是同一个对象
            System.out.println("thread1:" + Test9_StaticThreadLocal.threadLocal);
            try {
                // 当前线程2秒后从threadlocal中获取值
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("线程1获取：{}", Test9_StaticThreadLocal.get());
        });
        thread1.start();
        
        log.info("启动第二个线程");

        Thread thread2 = new Thread(() -> {
            System.out.println("thread2" + Test9_StaticThreadLocal.threadLocal);
            try {
                // 线程1秒后向threadlocal中设置值
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Test9_StaticThreadLocal.set("thread2");

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 线程2在三秒后获取本地缓存变量中的值
            log.info("线程2获取：{}", Test9_StaticThreadLocal.get());
        });
        thread2.start();
    }
}
