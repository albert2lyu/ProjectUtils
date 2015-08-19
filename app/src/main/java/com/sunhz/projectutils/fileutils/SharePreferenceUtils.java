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
package com.sunhz.projectutils.fileutils;

import android.content.Context;

/**
 * SharePreferenceUtils
 * Created by Spencer (www.spencer-dev.com) on 15/2/21.
 */
public class SharePreferenceUtils {

    private SharePreferenceUtils() {

    }


    private static SharePreferenceUtils sharePreferenceUtils;

    private Context mContext;

    private SharePreferenceUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static SharePreferenceUtils getInstance(Context mContext) {
        if (sharePreferenceUtils == null) {
            sharePreferenceUtils = new SharePreferenceUtils(mContext.getApplicationContext());
        }
        return sharePreferenceUtils;
    }

    public void saveLong(String spName, String key, long content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().putLong(key, content)
                .commit();
    }


    public long getLong(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getLong(key, -1);
    }

    public long getLong(String spName, String key, long defaultValue) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getLong(key, defaultValue);
    }


    public void saveInt(String spName, String key, int content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().putInt(key, content)
                .commit();
    }

    public int getInt(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getInt(key, -1);
    }

    public int getInt(String spName, String key, int defaultValue) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getInt(key, defaultValue);
    }

    public void saveBoolean(String spName, String key, boolean content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().putBoolean(key, content)
                .commit();
    }

    public boolean getBoolean(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getBoolean(key, false);
    }

    public boolean getBoolean(String spName, String key, boolean defaultValue) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getBoolean(key, defaultValue);
    }

    public void saveString(String spName, String key, String content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().putString(key, content)
                .commit();
    }

    public String getString(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getString(key, "");
    }

    public String getString(String spName, String key, String defaultValue) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getString(key, defaultValue);
    }

    public void saveFloat(String spName, String key, float content) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().putFloat(key, content)
                .commit();
    }

    public float getFloat(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getFloat(key, -1);
    }

    public float getFloat(String spName, String key, float defaultValue) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).getFloat(key, defaultValue);
    }

    public boolean contains(String spName, String key) {
        return mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).contains(key);
    }

    public void clear(String spName) {
        mContext.getSharedPreferences(spName, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS).edit().clear().commit();
    }

}
