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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/4/3.
 */
public class Singleton {

    private Singleton(){}

    private static Singleton instance;

    public static Singleton getInstance(){
        if(instance == null){
            try {
                Thread.sleep(10);
                instance = new Singleton();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static void main(String[] args) throws  Exception {
        final List<Singleton> list = Collections.synchronizedList(new ArrayList<Singleton>());
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    list.add(Singleton.getInstance());
                }
            });
            t.start();

        }
        Thread.sleep(1000);
        System.out.println(list);
    }
}
