package com.lixj.concurrent.utils;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.utils
 * Description :    静态的threadlocals
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/10
 */

public class Test9_StaticThreadLocal {
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void set(String str) {
        threadLocal.set(str);
    }

    public static String get() {
        return threadLocal.get();
    }

    public void remove() {
        threadLocal.remove();
    }
}
