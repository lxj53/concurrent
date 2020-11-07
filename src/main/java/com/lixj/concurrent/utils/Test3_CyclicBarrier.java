package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    测试CyclicBarrier
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/11/07
 */

@Slf4j
public class Test3_CyclicBarrier {

    /**
     * 结论：CyclicBarrier(循环栅栏)等待指定数量的线程完成后才执行后续操作
     *
     * @param args
     */
    public static void main(String[] args) {
        // 所有线程都等待完成后，才执行下一步操作
        // 第一个参数：要参与的线程数
        // 第二个参数：最后一个到达线程需要做的任务
        CyclicBarrier barrier = new CyclicBarrier(20, () -> log.info("线程{}到达，等待线程已满", Thread.currentThread().getName()));
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    log.info("线程{}到达栅栏", Thread.currentThread().getName());
                    // 表示当前线程已经到达栅栏,当指定数量的线程都达到后才能继续后续执行
                    barrier.await();
                    log.info("线程{}执行", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    // 表示栅栏已经被破坏，破坏的原因可能是其中一个线程 await() 时被中断或者超时
                    e.printStackTrace();
                }
            }, String.valueOf(i + 1)).start();
        }
    }
}
