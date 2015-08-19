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
package com.sunhz.projectutils.netcheckutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * 检查网络相关类
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
     * 是否飞行模式
     *
     * @return true:飞行模式,flase:非飞行模式
     */
    public boolean isAirplaneModeOn() {
        return Settings.System.getInt(mContext.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    /**
     * 检测是否已经连接网络。
     *
     * @return 当连上网络时返回true, 否则返回false。
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
     * 判断当前网络状态
     *
     * @return NetAuthorityEnum 状态
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
            } else { // 不能判断的网络类型
                return NetAuthorityEnum.NotknowNetType;
            }
        }
        return NetAuthorityEnum.unNetConnect;
    }


// 此说明参考地址为:http://www.binkery.com/archives/368.html
// 2G
//    GPRS       2G(2.5) General Packet Radia Service 114kbps
//    EDGE       2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
//    CDMA     2G 电信 Code Division Multiple Access 码分多址
//    1xRTT      2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
//    IDEN      2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）

// 3G
//    UMTS      3G WCDMA 联通3G Universal Mobile Telecommunication System 完整的3G移动通信技术标准
//    EVDO_0   3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
//    EVDO_A  3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
//    HSDPA    3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
//    HSUPA    3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
//    HSPA      3G (分HSDPA,HSUPA) High Speed Packet Access
//    EVDO_B 3G EV-DO Rev.B 14.7Mbps 下行 3.5G
//    EHRPD  3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
//    HSPAP  3G HSPAP 比 HSDPA 快些

// 4G
//    LTE        4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G

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
