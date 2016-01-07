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

package com.zuoxiaolong.quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * quartz的简单示例
 *
 * @author Xiaolong Zuo
 * @since 16/1/8 02:22
 */
public class QuartzTest {

    @Test
    public void test() throws SchedulerException, InterruptedException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).usingJobData("scheduleJob", 1).withIdentity("task_" + 1,"group_" + 1).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("task_" + 1, "group_" + 1).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
        scheduler.scheduleJob(jobDetail, trigger);
        Thread.sleep(100000);
        scheduler.shutdown();
    }

    public static class MyJob implements Job {

        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("--------------------------------");
        }

    }

}
