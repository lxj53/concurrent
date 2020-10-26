package com.lixj.concurrent.common;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: lixj25188
 * @Date: 2020/10/18/16:41
 * @Description: 线程常用的方法
 */
public class Test2_ThreadCommonMethod {
    public static void main(String[] args) {
        Thread myThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("子线程执行。。。" + i);
            }
        });
        myThread.start();
//        try {
//            // 主线程sleep 1000毫秒进入到阻塞状态
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // 主线程让出CPU进入到可执行状态，可能会立马被继续执行
        Thread.yield();

        for (int i = 0; i < 10; i++) {
            System.out.println("main线程执行第" + i + "次");
            if (i >= 4) {
                try {
                    // 在主线程调用其他线程的join方法，相当于主线程等待子线程执行完毕后再执行主线程，类似于方法调用
                    myThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
