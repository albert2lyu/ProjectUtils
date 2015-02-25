package com.sunhz.projectutils.devicesutils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * 获取设备信息
 * Created by Spencer on 15/2/25.
 */
public class DeviceUtils {

    private static DeviceUtils deviceUtils;


    private DeviceUtils() {
    }

    public static synchronized DeviceUtils getInstance() {
        if (deviceUtils == null) {
            deviceUtils = new DeviceUtils();
        }
        return deviceUtils;
    }

    public String getDeviceID(Context mContext) {
        return ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    public String getDevicePlant() {
        return "android";
    }

    public String getDeviceName() {
        return Build.MODEL;
    }

    public int getDeviceOS() {
        return Build.VERSION.SDK_INT;
    }


}
