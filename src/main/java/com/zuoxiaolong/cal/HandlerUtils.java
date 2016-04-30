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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xiaolong Zuo
 */
public class HandlerUtils {

    private HandlerUtils() {}

    private static final String DEBUG_KEY = "debug";
    private static final String INFO_KEY = "info";
    private static final String WARN_KEY = "warn";
    private static final String ERROR_KEY = "error";

    public static List<String> readLines(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines;
    }

    public static Map<String, Integer> count(List<String> lines) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (String line : lines) {
            if (line.toLowerCase().contains(DEBUG_KEY)) {
                Integer currentCount = (result.get(DEBUG_KEY) == null ? 0 : result.get(DEBUG_KEY));
                result.put(DEBUG_KEY, currentCount + 1);
            } else if (line.toLowerCase().contains(INFO_KEY)) {
                Integer currentCount = (result.get(INFO_KEY) == null ? 0 : result.get(INFO_KEY));
                result.put(INFO_KEY, currentCount + 1);
            } else if (line.toLowerCase().contains(WARN_KEY)) {
                Integer currentCount = (result.get(WARN_KEY) == null ? 0 : result.get(WARN_KEY));
                result.put(WARN_KEY, currentCount + 1);
            } else if (line.toLowerCase().contains(ERROR_KEY)) {
                Integer currentCount = (result.get(ERROR_KEY) == null ? 0 : result.get(ERROR_KEY));
                result.put(ERROR_KEY, currentCount + 1);
            } else {
                throw new RuntimeException("unknown log level.");
            }
        }
        return result;
    }

    public static void printResult(Map<String, Integer> map) {
        System.out.println("debug的日志个数为:" + map.get(DEBUG_KEY));
        System.out.println("info的日志个数为:" + map.get(INFO_KEY));
        System.out.println("warn的日志个数为:" + map.get(WARN_KEY));
        System.out.println("error的日志个数为:" + map.get(ERROR_KEY));
    }

}
