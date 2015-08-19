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
package com.sunhz.projectutils.packageutils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 包管理工具
 * Created by Spencer (www.spencer-dev.com) on 15/2/3.
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
        if (TextUtils.isEmpty(apkFilePath)) {
            throw new IllegalArgumentException("apk file path 不能为空");
        }

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
        if (TextUtils.isEmpty(packageName)) {
            throw new IllegalArgumentException("package name 不能为空");
        }

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
     * 判断当前手机中,是否已经安装某些包名的程序
     * @param packageNames 要检查的包名数组
     *
     * @return 包名集合
     */
    public boolean judgePackageNamesInPhoneApp(String... packageNames) {
        if (packageNames == null || packageNames.length == 0) {
            throw new IllegalArgumentException("packageNames 不能为 null 或 packageNames 的 length 不能为0");
        }

        PackageManager packageManager = mContext.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        if (packageInfoList != null) {

            List<String> packageNameList = new ArrayList<String>();
            for (PackageInfo packageInfo : packageInfoList) {
                packageNameList.add(packageInfo.packageName);
            }

            for (String packageName : packageNames) {
                if (packageNameList.contains(packageName)) {
                    return true;
                }
            }
        }
        return false;
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
     * @return 如果获取失败，返回 ""(『空字符串』)
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
