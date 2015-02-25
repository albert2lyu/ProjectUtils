package com.sunhz.projectutils.packageutils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

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
            packageUtils = new PackageUtils(mContext);
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
        intent.setDataAndType(Uri.fromFile(new File(apkFilePath)),
                "application/vnd.android.package-archive");
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

    public void getAllPackageName() {
        //TODO:获取手机全部应用程序报名
    }

    public String getMyApplicationPackageName() {
        return mContext.getPackageName();
    }

}
