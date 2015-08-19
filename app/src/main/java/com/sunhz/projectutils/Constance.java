/**
 * Copyright (c) 2015, Spencer 给立乐 (www.spencer-dev.com).
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
 * 常量池
 * Created by Spencer (www.spencer-dev.com) on 15/2/3.
 */
public final class Constance {


    /**
     * 加密相关常量
     */
    public static final class Encryption {
        /**
         * AES_UTILS_CLIENT_KEY 是AES加密的Key，Key的长度必须为16位
         */
        public final static String AES_UTILS_CLIENT_KEY = "";
    }


    /**
     * 程序中,各种时间控制
     */
    public static final class TimeInApplication {
        /**
         * 网络超时, 默认3秒
         */
        public final static int NET_TIMEOUT = 10000;

        /**
         * 缓存失效时间, 默认一天
         */
        public final static int CACHE_FAIL_TIME = 1000 * 60 * 60 * 24; // 一天
    }


}