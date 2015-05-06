package com.sunhz.projectutils;

public final class Constance {

    /**
     * 现在使用BuildConfig来进行管理
     * 若使用eclipse,请进行手动更改.
     * 这个标志只能在android studio中使用
     * 若想在eclipse中使用,请将eclipse的自动编译关闭
     */
    public static final boolean isDebug = BuildConfig.DEBUG;

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