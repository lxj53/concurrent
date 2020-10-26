package com.lixj.concurrent.common;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: lixj25188
 * @Date: 2020/10/15/23:14
 * @Description: 创建线程的四种方式
 */
public class Test1_CreateThread {
    public static void main(String[] args) throws Exception {
        // 第一种方法: Thread对象的子类
        // 1.定义Thread对象的子类，并重写run方法，run方法就是线程需要完成的任务
        // 2.创建Thread子类的实例
        // 3.调用线程的start方法开启线程，不能直接调用run方法(直接调用run方法只是方法调用，无法开启新线程)
//        MyThread1 myThread = new MyThread1();
//        myThread.start();
//        Thread.sleep(1000);
//        System.out.println("主线程。。。");

        // 第二种方法：实现Runnable接口
        // 1.定义Runnable接口的实现类，并重写run方法
        // 2.创建runnable接口的实例，并用该实例作为Thread的target创建Thread对象，thread对象才是真正的线程对象
        // 3.调用Thread实例的start方法
        MyThread2 myThread2 = new MyThread2();
        Thread thread2 = new Thread(myThread2);
        thread2.start();
        Thread.sleep(1000);
        System.out.println("主线程。。。");

        // 第三种方法：实现Callable接口
        // Callable需要和Future结合使用（具体是Future接口的实现类FutureTask,FutureTask实现了获取子任务结果、判断子任务是否结束等方法）
        // 1.创建Callable接口的实现类，并重写call方法
        // 2.使用FutureTask封装Callable对象，该FutureTask对象封装了Callable对象call方法的返回值
        // 3.使用FutureTask对象作为Thread对象的Target创建并启动线程（因为FutureTask对象实现了Future和Runnable接口）
        // 4.调用FutureTask对象的get方法获取子线程的返回值
        MyThread3 myThread3 = new MyThread3();
        FutureTask<String> futureTask = new FutureTask<>(myThread3);
        // 因为FutureTask对象实现了Future和Runnable接口
        Thread thread3 = new Thread(futureTask);
        thread3.start();
        String taskResult = futureTask.get();
        System.out.println("子线程返回结果：" + taskResult);
        System.out.println("主线程。。。");

        // 第4种方法：使用线程池的方式


    }

    static class MyThread1 extends Thread {
        @Override
        public void run() {
            System.out.println("自定义线程1");
        }
    }

    static class MyThread2 implements Runnable {

        @Override
        public void run() {
            System.out.println("自定义线程2");
        }
    }

    static class MyThread3 implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "自定义线程3";
        }
    }
}
