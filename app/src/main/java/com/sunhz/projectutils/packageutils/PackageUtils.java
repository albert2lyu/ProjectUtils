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
 * package manager tool
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
     * install local apk
     *
     * @param apkFilePath local apk file path
     */
    public void install(String apkFilePath) {
        if (TextUtils.isEmpty(apkFilePath)) {
            throw new IllegalArgumentException("apk file path Can not null");
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkFilePath)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    /**
     * uninstall local apk
     *
     * @param packageName local apk package name
     */
    public void unInstall(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            throw new IllegalArgumentException("package name Can not null");
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * get current phone all application package name
     *
     * @return package name list
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
     * Analyzing the current phone, whether or not already installed some packages the names of the
     * @param packageNames package array
     *
     * @return Has been installed return true, is not installed return false
     */
    public boolean judgePackageNamesInPhoneApp(String... packageNames) {
        if (packageNames == null || packageNames.length == 0) {
            throw new IllegalArgumentException("packageNames Can not null or packageNames  length not 0");
        }

        PackageManager packageManager = mContext.getPackageManager();
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
     * get current app package name
     *
     * @return current app package name
     */
    public String getMyApplicationPackageName() {
        return mContext.getPackageName();
    }

    /**
     * get current app version name
     *
     * @return if get failure return ""
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
     * get current app version code
     *
     * @return if get failure,return -1
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
