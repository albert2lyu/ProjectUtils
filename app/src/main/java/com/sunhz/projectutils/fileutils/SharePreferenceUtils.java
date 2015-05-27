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
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().putLong(key, content)
                .commit();
    }

    public synchronized void saveLongAppend(String spName, String key, long content) {
        mContext.getSharedPreferences(spName, Context.MODE_APPEND).edit().putLong(key, content)
                .commit();
    }

    public synchronized long getLong(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).getLong(key, -1);
    }

    public synchronized void saveInt(String spName, String key, int content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().putInt(key, content)
                .commit();
    }

    public synchronized void saveIntAppend(String spName, String key, int content) {
        mContext.getSharedPreferences(spName, Context.MODE_APPEND).edit().putInt(key, content)
                .commit();
    }

    public synchronized int getInt(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).getInt(key, -1);
    }

    public synchronized void saveBoolean(String spName, String key, boolean content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().putBoolean(key, content)
                .commit();
    }

    public synchronized void saveBooleanAppend(String spName, String key, boolean content) {
        mContext.getSharedPreferences(spName, Context.MODE_APPEND).edit().putBoolean(key, content)
                .commit();
    }

    public synchronized boolean getBoolean(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).getBoolean(key, false);
    }

    public synchronized void saveString(String spName, String key, String content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().putString(key, content)
                .commit();
    }

    public synchronized void saveStringAppend(String spName, String key, String content) {
        mContext.getSharedPreferences(spName, Context.MODE_APPEND).edit().putString(key, content)
                .commit();
    }

    public synchronized String getString(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).getString(key, "");
    }

    public synchronized void saveFloat(String spName, String key, float content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().putFloat(key, content)
                .commit();
    }

    public synchronized void saveFloatAppend(String spName, String key, float content) {
        mContext.getSharedPreferences(spName, Context.MODE_APPEND).edit().putFloat(key, content)
                .commit();
    }

    public synchronized float getFloat(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).getFloat(key, -1);
    }

    public synchronized boolean contains(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).contains(key);
    }

    public synchronized void clear(String spName) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().clear().commit();
    }

}
