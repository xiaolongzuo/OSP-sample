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

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Xiaolong Zuo
 */
public class DoubleCheckSingleton {

    private DoubleCheckSingleton() {
        System.out.println("实例已经被创建");
    }

    private static DoubleCheckSingleton instance;

    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                    return instance;
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0 ; i < 100 ;i++) {
            test();
        }
        Thread.sleep(5000);
    }

    public static void test() {
        final int count = 2;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        final CountDownLatch countDownLatch = new CountDownLatch(count);
        final Set<DoubleCheckSingleton> doubleCheckSingletons = new HashSet<DoubleCheckSingleton>();
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (BrokenBarrierException e) {
                        throw new RuntimeException(e);
                    }
                    DoubleCheckSingleton instance = DoubleCheckSingleton.getInstance();
                    synchronized (doubleCheckSingletons) {
                        doubleCheckSingletons.add(instance);
                        System.out.println(doubleCheckSingletons + "添加成功!");
                    }
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("实例个数[" + doubleCheckSingletons + "]:" + doubleCheckSingletons.size());
        instance = null;
    }

}
