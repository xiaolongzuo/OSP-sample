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

package com.zuoxiaolong.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

/**
 * @author Xiaolong Zuo
 * @since 16/1/8 22:25
 */
public class CuratorFrameWorkTest {

    @Test
    public void create() throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181,localhost:3181,localhost:4181", new RetryOneTime(1000));
        curatorFramework.start();
        System.out.println(curatorFramework.create().forPath("/curator"));
        Stat stat = curatorFramework.checkExists().forPath("/curator");
        System.out.println(stat);
    }

    @Test
    public void delete() throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181,localhost:3181,localhost:4181", new RetryOneTime(1000));
        curatorFramework.start();
        System.out.println(curatorFramework.delete().forPath("/curator"));
        Stat stat = curatorFramework.checkExists().forPath("/curator");
        System.out.println(stat);
    }

}
