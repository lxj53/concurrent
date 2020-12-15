package com.lixj.concurrent.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.collections
 * Description :    测试SynchronousQueue队列
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/15
 */

@Slf4j
public class Test04_SynchronousQueue {
    /**
     * 总结：SynchronousQueue容量为0，用于线程间通信，类似于Exchanger
     * 注意：不能直接往里面add(因队列容量为0)，只能阻塞的put和take(类似于不经过队列)
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException{
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                // 线程阻塞等待
                blockingQueue.take();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }, "consumer").start();
        // 线程阻塞的往里面放，因其容量是0，因此只能等其他线程take,
        // 两个线程直接交换数据，中间的队列类似于无效
        blockingQueue.put("put message");

        // 不能直接往里面add，因其容量是0
//        blockingQueue.add("add message");

        log.info("容量：{}", blockingQueue.size());
    }
}
