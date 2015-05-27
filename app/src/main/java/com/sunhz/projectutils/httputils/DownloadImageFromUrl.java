package com.sunhz.projectutils.httputils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sunhz.projectutils.asynctaskutils.AsyncTaskExpand;

import java.io.IOException;
import java.io.InputStream;

/**
 * 通过url地址,下载网络图片
 * Created by Spencer on 15/2/17.
 */
public class DownloadImageFromUrl extends AsyncTaskExpand<String, Void, Bitmap> {


    private DownloadCallBack downloadCallBack;

    /**
     * 控制是否并行
     *
     * @param flag AsyncTaskExpend.PARALLEL or AsyncTaskExpend.SERIAL
     */
    public DownloadImageFromUrl(boolean flag, DownloadCallBack downloadCallBack) {
        super(flag);
        this.downloadCallBack = downloadCallBack;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String imageUrl = params[0];
        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new java.net.URL(imageUrl).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPreExecute() {
        downloadCallBack.onPreExecute();
    }


    @Override
    protected void onPostExecute(Bitmap result) {
        downloadCallBack.onPostExecute(result);
    }

    public interface DownloadCallBack {
        void onPreExecute();

        void onPostExecute(Bitmap result);
    }
}
