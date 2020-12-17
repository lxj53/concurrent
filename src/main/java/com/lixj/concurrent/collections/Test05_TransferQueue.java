package com.lixj.concurrent.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.collections
 * Description :    测试transferqueue
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/17
 */

@Slf4j
public class Test05_TransferQueue {
    /**
     * 总结：transferqueue确保A线程放在队列中的数据已被B线程拿到后才继续执行，
     *      如A线程将钱放在队列中，等待B线程将钱取走，当钱被取走后A才能继续执行
     *      场景2：
     *      A线程将消息放在队列中，如何确保消息被消费掉（mq提供有消息机制），若mq没有提供消息机制，
     *      可以使用transfer，当消息被拿走后，A线程才继续执行
     *
     * 区别：transfer需要其他线程取走值后才能继续执行
     *      put方法当将值放在队列后就可以直接返回
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        TransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        // 子线程先启动，然后拿主线程放在队列中的值，因此线程会自动结束，不会阻塞在transfer方法
//        new Thread(() -> {
//            try {
//                log.info(transferQueue.take());
//            } catch (InterruptedException e) {
//                log.error(e.getMessage());
//            }
//        }).start();

        // 阻塞等待有其他线程来拿值，当其他线程拿到值后，该线程才返回
        transferQueue.transfer("测试数据");

        // 区别：put方法将值放进去后就直接返回了，transfer需要等到有其他线程拿到值后才能返回
//        transferQueue.put("测试数据put方法");

        // 若其他线程写在主线程后面，由于transfer阻塞，导致子线程无法启动
//        new Thread(() -> {
//            try {
//                log.info(transferQueue.take());
//            } catch (InterruptedException e) {
//                log.error(e.getMessage());
//            }
//        }).start();

    }
}
