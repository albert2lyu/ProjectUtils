package com.sunhz.projectutils.fileutils;

import android.content.Context;

public class SharePreferenceUtils {

    private static SharePreferenceUtils sharePreferenceUtils;

    private Context mContext;

    private SharePreferenceUtils(Context mContext) {
        this.mContext = mContext;
    }

    public synchronized static SharePreferenceUtils getInstance(Context mContext) {
        if (sharePreferenceUtils == null) {
            sharePreferenceUtils = new SharePreferenceUtils(mContext.getApplicationContext());
        }
        return sharePreferenceUtils;
    }

    public synchronized void saveLong(String spName, String key, long content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().putLong(key, content)
                .commit();
    }


    public synchronized long getLong(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getLong(key, -1);
    }

    public synchronized long getLong(String spName, String key, long defaultValue) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getLong(key, defaultValue);
    }


    public synchronized void saveInt(String spName, String key, int content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().putInt(key, content)
                .commit();
    }

    public synchronized int getInt(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getInt(key, -1);
    }

    public synchronized int getInt(String spName, String key, int defaultValue) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getInt(key, defaultValue);
    }

    public synchronized void saveBoolean(String spName, String key, boolean content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().putBoolean(key, content)
                .commit();
    }

    public synchronized boolean getBoolean(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getBoolean(key, false);
    }

    public synchronized boolean getBoolean(String spName, String key, boolean defaultValue) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getBoolean(key, defaultValue);
    }

    public synchronized void saveString(String spName, String key, String content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().putString(key, content)
                .commit();
    }

    public synchronized String getString(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getString(key, "");
    }

    public synchronized String getString(String spName, String key, String defaultValue) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getString(key, defaultValue);
    }

    public synchronized void saveFloat(String spName, String key, float content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().putFloat(key, content)
                .commit();
    }

    public synchronized float getFloat(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getFloat(key, -1);
    }

    public synchronized float getFloat(String spName, String key, float defaultValue) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getFloat(key, defaultValue);
    }

    public synchronized boolean contains(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).contains(key);
    }

    public synchronized void clear(String spName) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().clear().commit();
    }

}
