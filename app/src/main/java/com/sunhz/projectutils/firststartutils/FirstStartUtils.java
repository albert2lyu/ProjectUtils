/**
 * Copyright (c) 2015, Spencer , ChinaSunHZ (www.spencer-dev.com).
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
 * Record whether the current app is the first start.
 * true is first, false is not first.
 * Created by Spencer (www.spencer-dev.com) on 15/2/21.
 */
public class FirstStartUtils {

    private FirstStartUtils() {

    }

    private static final String FIRST_START_FLAG = "firstStartFlag";


    /**
     * Initialization method, you need to call in the application onCreate
     *
     * @param mContext Context
     */
    public static void init(Context mContext) {
        SharePreferenceUtils sharePreferenceUtils = SharePreferenceUtils.getInstance(mContext.getApplicationContext());
        if (!sharePreferenceUtils.contains(FIRST_START_FLAG, FIRST_START_FLAG)) {
            sharePreferenceUtils.saveBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.TRUE);
        } else {
            sharePreferenceUtils.saveBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.FALSE);
        }
    }


    /**
     * Determine whether the current app is the first time you start
     * FirstStartUtils.init() need to call in the application OnCreate
     *
     * @param mContext Context
     * @return true:first start,false:not first start
     */
    public static boolean checkIsFirstStart(Context mContext) {
        SharePreferenceUtils sharePreferenceUtils = SharePreferenceUtils.getInstance(mContext.getApplicationContext());
        return sharePreferenceUtils.getBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.TRUE);
    }

    /**
     * reset first start flag
     *
     * @param mContext Context
     */
    public static void restFirstStart(Context mContext) {
        SharePreferenceUtils preferenceUtils = SharePreferenceUtils.getInstance(mContext);
        preferenceUtils.saveBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.TRUE);
    }

    /**
     * Manually change the start times, change not first.
     *
     * @param mContext Context
     */
    public static void changeNotFirst(Context mContext) {
        SharePreferenceUtils sharePreferenceUtils = SharePreferenceUtils.getInstance(mContext);
        sharePreferenceUtils.saveBoolean(FIRST_START_FLAG, FIRST_START_FLAG, Boolean.FALSE);
    }
}

