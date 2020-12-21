package com.lixj.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.threadpool
 * Description :    测试FutureTask
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/21
 */

@Slf4j
public class Test02_FuthreTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // 定义一个任务
        Callable<String> callable = () -> {
            return "测试Future";
        };
        // 任务和Future是分开的
        Future<String> future = executorService.submit(callable);
        System.out.println(future.get());

        // 优点：既是一个任务，又是一个future，因其整合了Runnable、Future接口
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            return "测试FutureTask";
        });
        // 此处无需通过返回值获取子线程的结果，直接通过futureTask获取即可
        executorService.submit(futureTask);
        System.out.println(futureTask.get());

        executorService.shutdown();
    }
}
