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

    public void init() {
        SharePreferenceUtils sharePreferenceUtils = SharePreferenceUtils.getInstance(mContext);
        if (!sharePreferenceUtils.contains(FIRST_START_FLAG, FIRST_START_FLAG)) {
            sharePreferenceUtils.saveBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.FALSE);
        } else {
            sharePreferenceUtils.saveBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.TRUE);
        }
    }


    /**
     * 判断当前app是否是第一次启动
     * <p/>
     * 需要在application的OnCreate中调用init方法
     *
     * @return true:第一次启动,false:不是第一次启动
     */
    public boolean checkIsFirstStart() {
        SharePreferenceUtils sharePreferenceUtils = SharePreferenceUtils.getInstance(mContext);
        return !sharePreferenceUtils.getBoolean(FIRST_START_FLAG, FIRST_START_FLAG);
    }

}
