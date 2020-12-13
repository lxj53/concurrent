package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    测试软引用
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/13
 */

@Slf4j
public class Test9_1_SoftReference {

    /**
     * 结论：软引用的对象在内存不够用时，会进行自动回收
     * 案例前提：需要手动指定堆内存大小 -Xms25m -Xmx25m
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // 创建10M的对象
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);
        System.out.println("创建软引用对象:" + m.get());
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("手动gc后:" + m.get());
        // 再创建10M的内存大小(应该是直接进入了老年代，导致老年代大小不够用，后期可通过jstat指令查看)
        // 导致内存不够用，回收软引用的对象
        byte[] bytesNew = new byte[1024*1024*10];
        System.out.println("创建大对象后:" + m.get());
    }

}
