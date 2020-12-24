package com.lixj.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.threadpool
 * Description :    测试ThreadpoolExecutor
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/24
 */

@Slf4j
public class Test03_ThreadPoolExecutor {
    /**
     * 总结：线程池的几个常用参数
     * corePoolSize: 核心线程池大小。默认线程池创建后，线程池中线程数量为0，当有任务来时，就创建一个线程执行任务，当线程池中线程达到corePoolSize后，将任务丢到队列中
     * maximumPoolSize: 线程池中线程最大数量
     * keepAliveTime: 线程空闲多久后会被回收终止。默认线程池中线程大于corePoolSize后，线程空闲keepAliveTime被回收，直至线程数量不超过corePoolSize数量
     * unit: keepAliveTime的时间单位
     * workQueue: 阻塞队列，用于存储任务
     * threadFactory: 线程工厂，用于创建线程，阿里要求一般需要重写，因为线程名称需要自定义，便于查找问题
     * handler: 拒绝策略，当任务队列满后，并且线程已经到达最大线程数量，如何处理被拒绝的任务，默认带了四个拒绝策略，
     *          但一般需要重写拒绝策略，可将被拒绝的任务写到数据库或者kafka
     *
     * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
     * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
     * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
     * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
     *
     * @param args
     */
    public static void main(String[] args) {
        // 线程工厂和拒绝策略使用的默认的
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(512), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        // 自定义拒绝策略
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(2, 4, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(512), Executors.defaultThreadFactory(), ((r, executor) -> {
            try {
                // 自定义拒绝策略，由blockingqueue的offer改成put阻塞方法
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                log.error("线程被打断");
            }
        }));

        // 创建常用线程的方法，但是阿里不推荐使用，避免使用的任务队列造成OOM内存溢出
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

        // 本质是个forkJoin线程池
        ExecutorService executorService = Executors.newWorkStealingPool();
    }
}
