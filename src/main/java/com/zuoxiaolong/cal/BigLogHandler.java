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

package com.zuoxiaolong.cal;

import javafx.concurrent.Task;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Xiaolong Zuo
 */
public class BigLogHandler {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws IOException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("test.log");
        List<String> all = HandlerUtils.readLines(inputStream);
        int size = all.size() / 3;
        List<String> task1 = all.subList(size * 0, size * 1);
        List<String> task2 = all.subList(size * 1, size * 2);
        List<String> task3 = all.subList(size * 2, all.size());
        Future<Map<String, Integer>> future1 = (Future<Map<String, Integer>>) EXECUTOR_SERVICE.submit(new Mapper(task1));
        Future<Map<String, Integer>> future2 = (Future<Map<String, Integer>>) EXECUTOR_SERVICE.submit(new Mapper(task1));
        Future<Map<String, Integer>> future3 = (Future<Map<String, Integer>>) EXECUTOR_SERVICE.submit(new Mapper(task1));


    }

    public static class Mapper extends Task<Map<String, Integer>> {

        private List<String> lines;

        public Mapper(List<String> lines) {
            this.lines = lines;
        }

        @Override
        protected Map<String, Integer> call() throws Exception {
            return HandlerUtils.count(lines);
        }

    }

}
