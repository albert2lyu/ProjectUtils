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
package com.sunhz.projectutils.httputils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sunhz.projectutils.Constant;
import com.sunhz.projectutils.asynctaskutils.AsyncTaskExpand;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * By url address, download a image on network
 * Created by Spencer (www.spencer-dev.com) on 15/2/17.
 */
public class DownloadImageFromUrl extends AsyncTaskExpand<String, Void, Bitmap> {


    private DownloadCallBack downloadCallBack;

    /**
     * Control parallel, serial
     *
     * @param flag             AsyncTaskExpend.PARALLEL or AsyncTaskExpend.SERIAL
     * @param downloadCallBack After the download is complete the call back
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
            bitmap = BitmapFactory.decodeStream(getNetImage(imageUrl));
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

    /**
     * By url address, download a image on network
     *
     * @param imgUrl network image address
     * @return inputStream inputStream
     * @throws IOException get failure
     */
    public  static InputStream getNetImage(String imgUrl) throws IOException {
        URL url = new URL(imgUrl);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setConnectTimeout(Constant.TimeInApplication.NET_TIMEOUT);
        urlConnection.setReadTimeout(Constant.TimeInApplication.NET_TIMEOUT);
        InputStream inputStream = urlConnection.getInputStream();
        return inputStream;
    }
}
