package com.lixj.concurrent.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.collections
 * Description :    测试优先级的队列
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/15
 */

@Slf4j
public class Test02_PriorityBlockingQueue {
    /**
     * 总结：阻塞的优先级队列，取出时按照排序的顺序取出，不基于存入的顺序，底层是最小堆结构
     * @param args
     */
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new PriorityBlockingQueue<>();
        blockingQueue.add("j");
        blockingQueue.add("e");
        blockingQueue.add("a");
        blockingQueue.add("t");

        // 取出，结果是排好顺序的队列
        for (int i = 0; i < 4; i++) {
            log.info("优先级队列:{}", blockingQueue.poll());
        }
    }
}
