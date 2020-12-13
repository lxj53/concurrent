package com.lixj.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    测试弱引用对象
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/13
 */
@Slf4j
public class Test9_2_WeakReference {

    /**
     * 结论：弱引用对象遇到垃圾回收就会被回收
     * 注意：软、弱、虚引用都需要通过get()方法获取对象
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        WeakReference<byte[]> m = new WeakReference<>(new byte[1024*1024*10]);
        System.out.println("创建弱引用对象:" + m.get());
        System.gc();
        // 暂停一秒，确保会触发垃圾回收器回收
        TimeUnit.SECONDS.sleep(1);
        // 此时get方法得到的到null，因为会被回收掉
        System.out.println("手动gc回收后:" + m.get());
    }
}
