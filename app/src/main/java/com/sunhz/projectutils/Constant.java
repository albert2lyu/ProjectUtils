/**
 * Copyright (c) 2015, Spencer , ChinaSunHZ (www.spencer-dev.com).
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
package com.sunhz.projectutils;

/**
 * Constant pool
 * Created by Spencer (www.spencer-dev.com) on 15/2/3.
 */
public final class Constant {


    /**
     * Encryption constant
     */
    public static final class Encryption {
        /**
         * AES_UTILS_CLIENT_KEY is AES encoding  Key, Key length is 16
         */
        public static String AES_UTILS_CLIENT_KEY = "";
    }


    /**
     * Program, the time control
     */
    public static final class TimeInApplication {
        /**
         * Network timeout, default 3 seconds
         */
        public static int NET_TIMEOUT = 10000;

        /**
         * Cache invalidation time, the default one day
         */
        public static int CACHE_FAIL_TIME = 1000 * 60 * 60 * 24;
    }

    /**
     * Init project utils base constant.
     *
     * @param AESUtilsClientKey aes key , default value is "".
     * @param netTimeOut        net time out , default value is 10s.
     * @param cacheFailTime     cache fail time , default value is a day.
     */
    public static void initConstant(String AESUtilsClientKey, int netTimeOut, int cacheFailTime) {
        Encryption.AES_UTILS_CLIENT_KEY = AESUtilsClientKey;
        TimeInApplication.NET_TIMEOUT = netTimeOut;
        TimeInApplication.CACHE_FAIL_TIME = cacheFailTime;
    }

    /**
     * Init project utils net time out and cache fail time.
     *
     * @param netTimeOut    net time out , default value is 10s.
     * @param cacheFailTime cache fail time, default value is a day.
     */
    public static void initNetTimeOutAndCacheFailTime(int netTimeOut, int cacheFailTime) {
        Encryption.AES_UTILS_CLIENT_KEY = "";
        TimeInApplication.NET_TIMEOUT = netTimeOut;
        TimeInApplication.CACHE_FAIL_TIME = cacheFailTime;
    }
}