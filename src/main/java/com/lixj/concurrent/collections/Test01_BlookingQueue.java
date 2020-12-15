package com.lixj.concurrent.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.collections
 * Description :    测试阻塞队列
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/15
 */

@Slf4j
public class Test01_BlookingQueue {
    /**
     * 总结：阻塞队列，常用方法为put和take
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);

        for (int i = 0; i < 10; i++) {
            blockingQueue.add("a" + i);
        }

        // 线程会阻塞
//        blockingQueue.put("a10");
        // 线程会报错
//        blockingQueue.add("a10");
        // 方法会返回是否增加成功,不同于add方法的地方，offer不会抛出错误
        blockingQueue.offer("a10");

        log.info("阻塞队列:{}", blockingQueue);
    }
}
