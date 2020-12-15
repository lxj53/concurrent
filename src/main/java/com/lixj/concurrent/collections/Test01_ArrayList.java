package com.lixj.concurrent.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述
 * ProductName :    Hundsun UF30
 * ProjectName :    适当性
 * Package :        com.lixj.concurrent.collections
 * Description :    测试arrayList
 *
 * @author :        12092
 * @version :       1.0
 * UpdateRemark :
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 * @date :          2020/12/15
 */

@Slf4j
public class Test01_ArrayList {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("test");


        list.forEach(e -> log.info("list中的元素：{}", e));
    }
}
