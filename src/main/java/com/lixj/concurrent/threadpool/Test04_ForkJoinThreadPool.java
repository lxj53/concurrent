package com.lixj.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.threadpool
 * Description :    测试ForkJoinThreadPool
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/24
 */

@Slf4j
public class Test04_ForkJoinThreadPool {
    /**
     * 总结：forkjoin线程池简单使用案例，自己定义任务的拆分方法，通过线程池计算任务是否需要拆分任务
     *
     * threadpoolExecutor和forkjoin区别：
     * threadpoolExecutor是一个线程队列，一个任务队列，都是从一个任务队列中拿任务
     * forkjoin类似于一个线程维护一个任务队列，当前线程的任务完成后，去其他任务队列中偷任务
     * @param args
     */
    public static void main(String[] args) {
        // for循环模式计算数值累加
        long[] numbers = LongStream.rangeClosed(1, 100000000).toArray();
        long beginTime = System.currentTimeMillis();
        long num = 0;
        for(long number : numbers) {
            num += number;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("for循环计算结果：" + num + "耗时：" + (endTime-beginTime));

        // forkjoin模式计算累加，将任务拆分
        ForkJoinPool forkJoinPool = new ForkJoinPool(5);
        beginTime = System.currentTimeMillis();
        Long forkJoinNum = forkJoinPool.invoke(new SumTask(numbers, 0, numbers.length - 1));
        endTime = System.currentTimeMillis();
        System.out.println("forkjoin计算结果：" + forkJoinNum + "耗时：" + (endTime-beginTime));
        forkJoinPool.shutdown();
    }

    // 执行任务,可根据实际场景继承不同的类
    // RecursiveTask：有返回值
    // RecursiveAction：无返回值
    static class SumTask extends RecursiveTask<Long> {
        private long[] numbers;
        private int from;
        private int to;

        // 构造方法
        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            // 当需要计算的数字个数小于6时，直接采用for loop方式计算结果
            if (to - from < 6) {
                long total = 0;
                for (int i = from; i <= to; i++) {
                    total += numbers[i];
                }
                return total;
            } else {
                // 否则，把任务一分为二，递归拆分(注意此处有递归)到底拆分成多少分 需要根据具体情况而定
                int middle = (from + to) / 2;
                SumTask taskLeft = new SumTask(numbers, from, middle);
                SumTask taskRight = new SumTask(numbers, middle + 1, to);
                taskLeft.fork();
                taskRight.fork();
                return taskLeft.join() + taskRight.join();
            }
        }
    }
}
