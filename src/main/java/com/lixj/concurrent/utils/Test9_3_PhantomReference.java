package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    虚引用测试
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/13
 */

@Slf4j
public class Test9_3_PhantomReference {
    /**
     * 总结：虚引用可以从队列中获取对象(类似一个标志)，当收到标志后可进行相应的操作，如回收堆外内存
     * 注意：虚引用必须要和引用队列一起使用，他的get方法永远返回null (只有框架底层人员可以获取到)
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue<byte[]> queue = new ReferenceQueue<>();

        // 虚引用必须要和引用队列一起使用，他的get方法永远返回null，只有框架底层人员可以获取到
        PhantomReference<byte[]> phantomReference = new PhantomReference<>(
                new byte[1024 * 1024 * 5], queue);

        System.out.println("虚引用:" + phantomReference.get());

        System.out.println("队列获取:" + queue.poll());

        // 调用gc,回收虚引用对象
        System.gc();

        // 等待一秒，确保虚引用对象会被回收
        TimeUnit.SECONDS.sleep(1);

        // 从队列中拿值，有值后，可以进行堆外内存操作
        System.out.println("gc后从队列获取:" + queue.poll());
    }
}
