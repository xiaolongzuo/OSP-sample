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

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 *
 * 这是一个基础类,实现了watcher接口,当有事件发生的时候,会唤醒等待在mutex上的线程.
 *
 * @author Xiaolong Zuo
 * @since 16/1/5 00:30
 */
public class SyncPrimitive implements Watcher {

    static ZooKeeper zk = null;
    static final Object mutex = new Object();

    String root;

    SyncPrimitive(String address)
            throws KeeperException, IOException {
        if(zk == null){
            System.out.println("Starting ZK:");
            zk = new ZooKeeper(address, 3000, this);
            System.out.println("Finished starting ZK: " + zk);
        }
    }

    public void process(WatchedEvent event) {
        synchronized (mutex) {
            mutex.notify();
        }
    }

}
