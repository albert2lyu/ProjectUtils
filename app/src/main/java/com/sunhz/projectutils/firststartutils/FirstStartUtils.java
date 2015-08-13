package com.sunhz.projectutils.firststartutils;

import android.content.Context;

import com.sunhz.projectutils.fileutils.SharePreferenceUtils;

/**
 * 用来记录当前app是否是第一次启动
 * Created by Spencer on 15/2/21.
 */
public class FirstStartUtils {

    private static final String FIRST_START_FLAG = "firstStartFlag";


    private FirstStartUtils() {
    }


    /**
     * 初始化方法，需要在application的onCreate中调用
     */
    public static void init(Context mContext) {
        SharePreferenceUtils sharePreferenceUtils = SharePreferenceUtils.getInstance(mContext.getApplicationContext());
        if (!sharePreferenceUtils.contains(FIRST_START_FLAG, FIRST_START_FLAG)) {
            sharePreferenceUtils.saveBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.FALSE);
        } else {
            sharePreferenceUtils.saveBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.TRUE);
        }
    }


    /**
     * 判断当前app是否是第一次启动
     * <p/>
     * 需要在application的OnCreate中调用FirstStartUtils.init()方法
     *
     * @return true:第一次启动,false:不是第一次启动
     */
    public static boolean checkIsFirstStart(Context mContext) {
        SharePreferenceUtils sharePreferenceUtils = SharePreferenceUtils.getInstance(mContext.getApplicationContext());
        return !sharePreferenceUtils.getBoolean(FIRST_START_FLAG, FIRST_START_FLAG);
    }

    /**
     * 重置标识
     *
     * @param mContext
     */
    public static void restFirstStart(Context mContext) {
        SharePreferenceUtils preferenceUtils = SharePreferenceUtils.getInstance(mContext);
        preferenceUtils.saveBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.TRUE);
    }

}
