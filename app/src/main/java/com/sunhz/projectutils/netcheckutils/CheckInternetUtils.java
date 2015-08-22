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
package com.sunhz.projectutils.netcheckutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * check network tool
 * Created by Spencer (www.spencer-dev.com) on 15/2/3.
 */
public class CheckInternetUtils {

    private static CheckInternetUtils checkInternet;
    private Context mContext;

    private CheckInternetUtils(Context mContext) {
        this.mContext = mContext;
    }

    public synchronized static CheckInternetUtils getInstance(Context mContext) {
        if (checkInternet == null) {
            checkInternet = new CheckInternetUtils(mContext.getApplicationContext());
        }
        return checkInternet;
    }

    /**
     * Whether flight mode
     *
     * @return true:flight mode ,false:not flight mode
     */
    public boolean isAirplaneModeOn() {
        return Settings.System.getInt(mContext.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    /**
     * Detecting whether the network is connected.
     *
     * @return connection net work : true, not connection net work : false。
     */
    public boolean checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null) && info.isAvailable();
    }

    /**
     * Analyzing the current network status
     *
     * @return NetAuthorityEnum status
     */
    public NetAuthorityEnum JudgeCurrentNetState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            TelephonyManager mTelephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            int netType = info.getType();
            int netSubtype = info.getSubtype();
            if (netType == ConnectivityManager.TYPE_WIFI) {// WIFI
                return NetAuthorityEnum.WifiConnect;
            } else if (netType == ConnectivityManager.TYPE_MOBILE && judgeNetWorkTypeIs4G(netSubtype) && !mTelephony.isNetworkRoaming()) {// 4G
                return NetAuthorityEnum.Net4GConnect;
            } else if (netType == ConnectivityManager.TYPE_MOBILE && judgeNetWorkTypeIs3G(netSubtype) && !mTelephony.isNetworkRoaming()) {// 3G
                return NetAuthorityEnum.Net3GConnect;
            } else if (netType == ConnectivityManager.TYPE_MOBILE && judgeNetWorkTypeIs2G(netSubtype) && !mTelephony.isNetworkRoaming()) { // 2G
                return NetAuthorityEnum.Net2GConnect;
            } else { // Network types can not judge
                return NetAuthorityEnum.NetworkTypeUnrecognized;
            }
        }
        return NetAuthorityEnum.unNetConnect;
    }


// Reference address : http://www.binkery.com/archives/368.html
    private boolean judgeNetWorkTypeIs2G(int netWorkTye) {
        if (TelephonyManager.NETWORK_TYPE_GPRS == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_EDGE == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_CDMA == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_1xRTT == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_IDEN == netWorkTye) {
            return true;
        } else {
            return false;
        }
    }

    private boolean judgeNetWorkTypeIs3G(int netWorkTye) {
        if (TelephonyManager.NETWORK_TYPE_UMTS == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_EVDO_0 == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_EVDO_A == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_HSDPA == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_HSUPA == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_HSPA == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_EVDO_B == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_EHRPD == netWorkTye ||
                TelephonyManager.NETWORK_TYPE_HSPAP == netWorkTye) {
            return true;
        } else {
            return false;
        }


    }

    private boolean judgeNetWorkTypeIs4G(int netWorkTye) {
        return netWorkTye == TelephonyManager.NETWORK_TYPE_LTE;
    }
}
