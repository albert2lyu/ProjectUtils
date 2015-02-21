package com.sunhz.projectutils.fileutils;

import android.content.Context;

public class SharePreferenceUtils {

    private static SharePreferenceUtils sharePreferenceUtils;

    private Context mContext;

    private SharePreferenceUtils(Context mContext) {
        this.mContext = mContext;
    }

    public synchronized static SharePreferenceUtils getInstance(Context mContext) {
        if (sharePreferenceUtils != null) {
            sharePreferenceUtils = new SharePreferenceUtils(mContext);
        }
        return sharePreferenceUtils;
    }

    public void saveLong(String spName, String key, long content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().putLong(key, content)
                .commit();
    }

    public long getLong(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).getLong(key, -1);
    }

    public void saveInt(String spName, String key, int content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().putLong(key, content)
                .commit();
    }

    public int getInt(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).getInt(key, -1);
    }

    public void saveBoolean(String spName, String key, boolean content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().putBoolean(key, content)
                .commit();
    }

    public boolean getBoolean(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).getBoolean(key, false);
    }

    public void saveString(String spName, String key, String content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().putString(key, content)
                .commit();
    }

    public String getString(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).getString(key, "");
    }

    public void saveFloat(String spName, String key, float content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).edit().putFloat(key, content)
                .commit();
    }

    public float getFloat(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE).getFloat(key, -1);
    }

    public boolean contains(String spName,String key){
        return mContext.getSharedPreferences(spName,Context.MODE_PRIVATE).contains(key);
    }

}
