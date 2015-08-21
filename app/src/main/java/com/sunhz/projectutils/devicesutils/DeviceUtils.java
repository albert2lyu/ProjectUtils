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
package com.sunhz.projectutils.devicesutils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * get device info
 * Created by Spencer (www.spencer-dev.com) on 15/2/25.
 */
public class DeviceUtils {

    private DeviceUtils() {
    }


    /**
     * get device ID
     *
     * @param mContext Context
     * @return deviceID (system version 5.0 or more get ANDROID_ID , system version  5.0 or less get Device Id
     */
    public static String getDeviceID(Context mContext) {
        String deviceId = ((TelephonyManager) mContext.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId == null) {
            deviceId = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }


    /**
     * get device name
     *
     * @return device name
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * get sdk level
     *
     * @return sdk level
     */
    public static int getDeviceSDKLevel() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * get Device Chip Type
     *
     * @return Device Chip Type
     */
    public static String getDeviceBoard() {
        return Build.BOARD;
    }

    /**
     * Get system customization Suppliers
     *
     * @return system customization Suppliers
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }


    /**
     * get CPU ABI
     *
     * @return get CPU ABI
     */
    public static String getCPU_ABI() {
        return Build.CPU_ABI;
    }


    /**
     * get rom version,such as Samsung note3 n9008 acquired hlte (hlte generic version, hltekor for the Korean version)
     * may be different for each mobile phone
     *
     * @return rom version
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * Get internal version number
     *
     * @return internal version number
     */
    public static String getDeviceDisplay() {
        return Build.DISPLAY;
    }

    /**
     * get fingerprint collection device, the device containing a variety of information
     *
     * @return fingerprint collection device, the device containing a variety of information
     */
    public static String getDeviceFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * get compile the host system
     *
     * @return compile the host system
     */
    public static String getDeviceHost() {
        return Build.HOST;
    }

    /**
     * get Revised version of the system
     *
     * @return Revised version of the system
     */
    public static String getID() {
        return Build.ID;
    }

    /**
     * get hardware manufacturers
     *
     * @return hardware manufacturers
     */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }


}
