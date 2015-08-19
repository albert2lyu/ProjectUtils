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
package com.sunhz.projectutils.devicesutils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * 获取设备信息
 * Created by Spencer (www.spencer-dev.com) on 15/2/25.
 */
public class DeviceUtils {

    private DeviceUtils() {
    }


    /**
     * 获取deviceID
     *
     * @param mContext Context
     * @return deviceID, system version 大于 5.0 获取 ANDROID_ID , system version 小于 5.0 获取 DeviceId
     */
    public static  String getDeviceID(Context mContext) {
        String deviceId = ((TelephonyManager) mContext.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId == null) {
            deviceId = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    /**
     * 获取系统类型
     *
     * @return 固定返回『android』
     */
    public static  String getDevicePlant() {
        return "android";
    }

    /**
     * 获取设备型号
     *
     * @return 设备型号
     */
    public static  String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取sdk level
     *
     * @return sdk level
     */
    public static  int getDeviceOS() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机主板类型
     *
     * @return 手机主板类型
     */
    public static  String getBoard() {
        return Build.BOARD;
    }

    /**
     * 获取android系统定制商
     *
     * @return 系统定制商
     */
    public static  String getBrand() {
        return Build.BRAND;
    }


    /**
     * 获取CPU指令集
     *
     * @return CPU指令集
     */
    public static  String getCPU_ABI() {
        return Build.CPU_ABI;
    }


    /**
     * 获取设备参数
     *
     * @return 设备参数
     */
    public static  String getDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取显示屏参数
     *
     * @return 显示屏参数
     */
    public static  String getDisplay() {
        return Build.DISPLAY;
    }

    /**
     * 获取硬件名称
     *
     * @return 硬件名称
     */
    public static  String getFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * 获取HOST
     *
     * @return HOST
     */
    public static  String getHost() {
        return Build.HOST;
    }

    /**
     * 获取修订版本
     *
     * @return 修订版本
     */
    public static  String getID() {
        return Build.ID;
    }

    /**
     * 获取硬件制造商
     *
     * @return 硬件制造商
     */
    public static  String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取手机制造商
     *
     * @return 手机制造商
     */
    public static  String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取描述build的标签
     *
     * @return 描述build的标签
     */
    public static  String getTags() {
        return Build.TAGS;
    }

    /**
     * 获取builder类型
     *
     * @return builder类型
     */
    public static  String getType() {
        return Build.TYPE;
    }
}
