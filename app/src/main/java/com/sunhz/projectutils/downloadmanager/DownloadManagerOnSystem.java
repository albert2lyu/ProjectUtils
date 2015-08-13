package com.sunhz.projectutils.downloadmanager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

/**
 * 系统自带的DownloadManager进行下载
 * Created by Spencer on 15/2/4.
 */
public class DownloadManagerOnSystem {


    /*
    simple demo
    DownloadManagerOnSystem.getInstance(mContext).startDownloadFile("http://www.xxxx.com/xx/xxxx.apk", "car.apk", "application/vnd.android.package-archive", new DownloadManagerOnSystem.DownloadCompleteCallBack() {
        @Override
        public void downloadCompleteCallBak(Context context, Intent intent) {
            long lo = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (DownloadManagerOnSystem.getInstance(context).getLastDownloadID() == lo) {
                PackageUtils.getInstance(context).install(DownloadManagerOnSystem.getInstance(context).getDownloadPath(DownloadManagerOnSystem.getInstance(context).getLastDownloadID()));
            }
        }
    });

    在activity销毁的时候调用unRegisterReceiver
    */


    private static DownloadManagerOnSystem downloadManagerOnSystem;
    private static boolean regBrodcastReceiverFlag = false; // 用来判断是否已经注册过广播
    private Context mContext;
    private DownloadCompleteCallBack downloadCompleteCallBack;
    private long lastDownloadID;
    private BroadcastReceiver downloadBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            downloadCompleteCallBack.downloadCompleteCallBak(context, intent);
        }
    };


    private DownloadManagerOnSystem(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 使用时,要执行startDownloadFile,也要在界面销毁或下载完成时,执行endDownloadFile操作
     *
     * @param mContext Context
     * @return DownloadManagerOnSystem
     */
    public static DownloadManagerOnSystem getInstance(Context mContext) {
        if (downloadManagerOnSystem == null) {
            downloadManagerOnSystem = new DownloadManagerOnSystem(mContext.getApplicationContext());
        }
        return downloadManagerOnSystem;
    }

    /**
     * 直接进行下载文件操作.并打开系统的notification(有进度条)
     *
     * @param url                      下载文件链接地址
     * @param fileName                 文件名
     * @param mimeType                 文件类型
     * @param downloadCompleteCallBack 下载完成时的回调
     */
    public void startDownloadFile(String url, String fileName, String mimeType, DownloadCompleteCallBack downloadCompleteCallBack) {

        this.downloadCompleteCallBack = downloadCompleteCallBack;

        Uri uri = Uri.parse(url);

        DownloadManager dowanloadmanagerService = getDownloadManager();
        lastDownloadID = dowanloadmanagerService.enqueue(new DownloadManager.Request(uri)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                .setAllowedOverRoaming(false)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                .setMimeType(mimeType));
        regBrodcastReceiverFlag = true;
        mContext.registerReceiver(downloadBroadcastReceiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public long getLastDownloadID() {
        return lastDownloadID;
    }


    public void unRegisterReceiver() {
        if (regBrodcastReceiverFlag) {
            mContext.unregisterReceiver(downloadBroadcastReceiver);
            regBrodcastReceiverFlag = false;
        }
    }

    public DownloadManager getDownloadManager() {
        return (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public String getDownloadPath(long id) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);

        Cursor cursor = DownloadManagerOnSystem.getInstance(mContext).getDownloadManager().query(query);

        int columnCount = cursor.getColumnCount();
        String path = null;
        while (cursor.moveToNext()) {
            for (int j = 0; j < columnCount; j++) {
                String columnName = cursor.getColumnName(j);
                String string = cursor.getString(j);
                if (columnName.equals("local_filename")) {
                    path = string;
                }
// 打印所有的列属性
//                if (string != null) {
//                    System.out.println(columnName + ": " + string);
//                } else {
//                    System.out.println(columnName + ": null");
//                }
            }
        }
        cursor.close();
        return path;
    }

    public interface DownloadCompleteCallBack {
        void downloadCompleteCallBak(Context context, Intent intent);
    }

}
