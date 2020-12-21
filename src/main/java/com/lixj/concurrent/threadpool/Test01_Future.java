package com.lixj.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.threadpool
 * Description :    测试future功能
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/21
 */

@Slf4j
public class Test01_Future {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        // 线程的任务是异步任务，任务提交后，主线程直接向后执行，无需关注子线程是否执行完毕
        // 若需要子线程的执行结果，可通过get()方法获取
        Future<String> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "测试future";
        });

        // future的get方法阻塞等待
        System.out.println(future.get());

        executorService.shutdown();
    }
}
