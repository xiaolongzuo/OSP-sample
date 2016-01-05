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

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * 这是一个用zookeeper实现queue的示例.
 *
 * @author Xiaolong Zuo
 * @since 16/1/5 01:30
 *
 * @see com.zuoxiaolong.zookeeper.QueueProducerTest
 * @see com.zuoxiaolong.zookeeper.QueueConsumerTest
 */
public class Queue extends SyncPrimitive {

    //初始化时建立一个序列的根节点,name可以看作是queue的名称.
    public Queue(String address, String name)
            throws KeeperException, InterruptedException, IOException {
        super(address);
        this.root = name;
        if (zk != null) {
            Stat s = zk.exists(root, false);
            if (s == null) {
                zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }
    }

    //生产者调用的方法
    boolean produce(int i) throws KeeperException, InterruptedException {
        ByteBuffer b = ByteBuffer.allocate(4);
        byte[] value;

        // 将消息的值设置为i
        b.putInt(i);
        value = b.array();
        //在根节点下建立一个节点,相当于在queue当中插入一个消息
        String result = zk.create(root + "/element", value, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("result:" + result);
        return true;
    }

    int consume() throws KeeperException, InterruptedException {
        Stat stat = null;

        // Get the first element available
        while (true) {
            synchronized (mutex) {
                List<String> list = zk.getChildren(root, true);
                System.out.println("size:" + list.size());
                if (list.isEmpty()) {
                    System.out.println("Going to wait");
                    mutex.wait();
                } else {
                    Integer min = new Integer(list.get(0).substring(7));
                    String id = list.get(0);
                    for (String s : list) {
                        System.out.println("queue's elements : " + s);
                        Integer tempValue = new Integer(s.substring(7));
                        if (tempValue < min) id = s;
                    }
                    byte[] b = zk.getData(root + "/" + id, false, stat);
                    zk.delete(root + "/" + id, 0);
                    ByteBuffer buffer = ByteBuffer.wrap(b);
                    int value = buffer.getInt();
                    System.out.println("element has been consumed: " + root + "/" + id + "  the value is:" + value);
                }
            }
        }
    }

}
