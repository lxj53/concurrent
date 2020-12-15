package com.lixj.concurrent.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.collections
 * Description :    测试delayQueue
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/15
 */

@Slf4j
public class Test03_DelayQueue {
    /**
     * 总结：延迟队列，在指定时间执行任务(定时任务)或者在指定时间移除对象(缓存失效的对象)，底层使用的优先级队列PriorityBlockingQueue
     * 注意：任务需要实现Delayed接口
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<MyTask> blockingQueue = new DelayQueue();
        long timeMillis = System.currentTimeMillis();
        blockingQueue.offer(new MyTask("a1", timeMillis + 1000));
        blockingQueue.offer(new MyTask("a2", timeMillis + 3000));
        blockingQueue.offer(new MyTask("a3", timeMillis + 500));
        blockingQueue.offer(new MyTask("a4", timeMillis + 2000));
        blockingQueue.offer(new MyTask("a5", timeMillis + 4000));

        for (int i = 0; i < 5; i++) {
            log.info("队列：{}", blockingQueue.take().name);
        }

    }

    /**
     * 自定义的任务，需要实现Delayed接口，需要重写getDelay和compareTo方法
     */
    static class MyTask implements Delayed {
        String name;
        long time;

        public MyTask (String name, Long time) {
            this.name = name;
            this.time = time;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            // 用于返回还剩余多少时间才执行，或者是多少时间后移除，若未到执行时间，则线程阻塞
            return unit.convert(time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            // 用于比较那些任务的优先级别是最高的，确保优先级最高的队列先被取出来
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            }
            return 0;
        }
    }
}
