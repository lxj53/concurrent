package com.lixj.concurrent.common;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.concurrent
 * Description :    线程同步_synchronized
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/10/19
 */

public class Test3_synchronize {
    private static int count;
    private static Object object = new Object();

    public static void main(String[] args) {
        // 需要先获取到锁对象
        synchronized (object) {
            count++;
            System.out.println(count);
        }
    }
}
