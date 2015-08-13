package com.sunhz.projectutils.packageutils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 包管理工具
 * Created by Spencer on 15/2/3.
 */
public class PackageUtils {

    private static PackageUtils packageUtils;

    private Context mContext;

    private PackageUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static PackageUtils getInstance(Context mContext) {
        if (packageUtils == null) {
            packageUtils = new PackageUtils(mContext.getApplicationContext());
        }
        return packageUtils;
    }

    /**
     * 安装本地apk
     *
     * @param apkFilePath 本地apk路径
     */
    public void install(String apkFilePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkFilePath)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    /**
     * 卸载本地apk
     *
     * @param packageName 本地apk包名
     */
    public void unInstall(String packageName) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 获取应用全部包名
     *
     * @return 包名集合
     */
    public List<String> getAllPackageName() {
        PackageManager packageManager = mContext.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        List<String> packageNameList = new ArrayList<String>();
        for (PackageInfo packageInfo : packageInfoList) {
            packageNameList.add(packageInfo.packageName);
        }
        return packageNameList;
    }

    /**
     * 获取当前app包名
     *
     * @return 当前app包名
     */
    public String getMyApplicationPackageName() {
        return mContext.getPackageName();
    }

    /**
     * 获取当前app的版本名
     *
     * @return 如果获取失败，返回『空字符串』
     */
    public String getMyApplicationVersionName() {
        try {
            return mContext.getPackageManager().getPackageInfo(getMyApplicationPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前app的版本号
     *
     * @return 如果获取失败，返回『-1』
     */
    public int getMyApplicationVersionCode() {
        try {
            return mContext.getPackageManager().getPackageInfo(getMyApplicationPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
