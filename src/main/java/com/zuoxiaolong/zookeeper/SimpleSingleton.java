/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zuoxiaolong.zookeeper;

/**
 * @author Xiaolong Zuo
 */
//饿汉
//对象消耗资源不大的话,可以用这个
public class SimpleSingleton {

    private SimpleSingleton() {
        System.out.println("实例已经被创建");
    }

    private static SimpleSingleton instance = new SimpleSingleton();

    public static SimpleSingleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(1);
    }

}
