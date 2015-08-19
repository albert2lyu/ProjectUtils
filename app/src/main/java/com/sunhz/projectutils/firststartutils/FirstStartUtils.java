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
package com.sunhz.projectutils.firststartutils;

import android.content.Context;

import com.sunhz.projectutils.fileutils.SharePreferenceUtils;

/**
 * 用来记录当前app是否是第一次启动
 * Created by Spencer (www.spencer-dev.com) on 15/2/21.
 */
public class FirstStartUtils {

    private FirstStartUtils() {

    }

    private static final String FIRST_START_FLAG = "firstStartFlag";


    /**
     * 初始化方法，需要在application的onCreate中调用
     *
     * @param mContext Context
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
     * 需要在application的OnCreate中调用FirstStartUtils.init()方法
     *
     * @param mContext Context
     * @return true:第一次启动,false:不是第一次启动* @return
     */
    public static boolean checkIsFirstStart(Context mContext) {
        SharePreferenceUtils sharePreferenceUtils = SharePreferenceUtils.getInstance(mContext.getApplicationContext());
        return !sharePreferenceUtils.getBoolean(FIRST_START_FLAG, FIRST_START_FLAG);
    }

    /**
     * 重置标识
     *
     * @param mContext Context
     */
    public static void restFirstStart(Context mContext) {
        SharePreferenceUtils preferenceUtils = SharePreferenceUtils.getInstance(mContext);
        preferenceUtils.saveBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.TRUE);
    }

}
