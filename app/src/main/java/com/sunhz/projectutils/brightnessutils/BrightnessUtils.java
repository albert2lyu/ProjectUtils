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
package com.sunhz.projectutils.brightnessutils;


import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.WindowManager;

/**
 * 改变评论亮度工具
 * link : http://apps.hi.baidu.com/share/detail/53623456
 * Created by Spencer on 9/28/15.
 */
public class BrightnessUtils {


    private static boolean isAuto;
    private static int currentBrightness;

    /**
     * 快速设置屏幕亮度
     * 1，获取当前屏幕亮度
     * 2，保存当前屏幕亮度
     * 3，判断是否自动亮度
     * 4，设置新的屏幕亮度
     *
     * @param resolver
     * @param brightness
     */
    public static void quickSetBrightness(Activity mActivity, ContentResolver resolver, int brightness) {
        isAuto = isAutoBrightness(resolver);
        if (isAuto) {
            stopAutoBrightness(mActivity);
        } else {
            currentBrightness = getScreenBrightness(mActivity);
        }
        saveBrightness(resolver, brightness);
    }

    /**
     * 快速还原屏幕亮度
     * 1，判断是否自动亮度
     * 2，还原亮度设置
     *
     * @param mActivity
     * @param resolver
     */
    public static void quickResetBrightness(Activity mActivity, ContentResolver resolver) {
        if (isAuto) {
            startAutoBrightness(mActivity);
        } else {
            saveBrightness(resolver, currentBrightness);
        }
    }

    /**
     * 判断是否开启了自动亮度调节
     */
    public static boolean isAutoBrightness(ContentResolver aContentResolver) {
        boolean automaticBrightness = false;

        try {
            automaticBrightness = Settings.System.getInt(aContentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }
        return automaticBrightness;
    }

    /**
     * 获取屏幕的亮度
     */
    public static int getScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;

        ContentResolver resolver = activity.getContentResolver();

        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }


    /**
     * 设置亮度
     */
    public static void setBrightness(Activity activity, int brightness) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        activity.getWindow().setAttributes(lp);
    }


    /**
     * 停止自动亮度调节
     */
    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 开启亮度自动调节 *
     *
     * @param activity
     */
    public static void startAutoBrightness(Activity activity) {

        Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

    }


    /**
     * 保存亮度设置状态
     */
    public static void saveBrightness(ContentResolver resolver, int brightness) {
        Uri uri = android.provider.Settings.System.getUriFor("screen_brightness");
        android.provider.Settings.System.putInt(resolver, "screen_brightness", brightness);
        resolver.notifyChange(uri, null);
    }
}
