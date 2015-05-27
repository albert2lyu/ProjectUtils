package com.sunhz.projectutils.netcheckutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class CheckInternetUtils {

    private Context mContext;

    private static CheckInternetUtils checkInternet;

    public CheckInternetUtils(Context mContext) {
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
        return Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    /**
     * 检测是否已经连接网络。
     *
     * @return 当连上网络时返回true, 否则返回false。
     */
    public boolean checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
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
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            TelephonyManager mTelephony = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            int netType = info.getType();
            int netSubtype = info.getSubtype();
            if (netType == ConnectivityManager.TYPE_WIFI) {// WIFI
                return NetAuthorityEnum.WifiConnect;
            } else if (netType == ConnectivityManager.TYPE_MOBILE
                    && netSubtype == TelephonyManager.NETWORK_TYPE_UMTS
                    && !mTelephony.isNetworkRoaming()) {// 3G
                return NetAuthorityEnum.Net3GComnect;
            } else {
                return NetAuthorityEnum.Net2GComnect;
            }
        }
        return NetAuthorityEnum.unNetConnect;
    }
}
