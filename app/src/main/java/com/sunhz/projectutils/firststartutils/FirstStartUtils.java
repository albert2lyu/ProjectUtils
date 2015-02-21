package com.sunhz.projectutils.firststartutils;

import android.content.Context;

import com.sunhz.projectutils.fileutils.SharePreferenceUtils;

/**
 * 用来记录当前app是否是第一次启动
 * Created by Spencer on 15/2/21.
 */
public class FirstStartUtils {

    private static final String FIRST_START_FLAG = "firstStartFlag";

    private static FirstStartUtils firstStartUtils;

    private Context mContext;

    private FirstStartUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static FirstStartUtils getInstance(Context mContext) {
        if (firstStartUtils == null) {
            firstStartUtils = new FirstStartUtils(mContext);
        }
        return firstStartUtils;
    }

    /**
     * 判断当前app是否是第一次启动
     *
     * @return true:第一次启动,false:不是第一次启动
     */
    public boolean checkIsFirstStart() {
        SharePreferenceUtils sharePreferenceUtils = SharePreferenceUtils.getInstance(mContext);
        boolean result = !sharePreferenceUtils.getBoolean(FIRST_START_FLAG, FIRST_START_FLAG);

        if (result) {
            sharePreferenceUtils.saveBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.TRUE);
        }

        return result;
    }

}
