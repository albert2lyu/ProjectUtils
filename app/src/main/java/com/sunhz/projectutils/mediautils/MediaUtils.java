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
package com.sunhz.projectutils.mediautils;

import android.content.ContentResolver;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * 多媒体相关工具类
 * Created by Spencer (www.spencer-dev.com) on 2015/1/30.
 */
public class MediaUtils {

    private static MediaUtils mediaUtils;

    private Context mContext;

    private MediaUtils(Context mContext) {
        this.mContext = mContext;
    }

    public synchronized static MediaUtils getInstance(Context mContext) {
        if (mediaUtils == null) {
            mediaUtils = new MediaUtils(mContext.getApplicationContext());
        }
        return mediaUtils;
    }

    /**
     * 向媒体库中插入一条数据,进行媒体更新
     *
     * @param file                       待插入媒体文件在手机中的绝对路径
     * @param mediaScanCompletedCallBack 插入完成后的回调
     */
    public void mediaScan(File file, final MediaScanCompletedCallBack mediaScanCompletedCallBack) {
        MediaScannerConnection.scanFile(mContext, new String[]{file.getAbsolutePath()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        mediaScanCompletedCallBack.onScanCompleted(path, uri);
                    }
                });
    }


    /**
     * 删除文件并将其在媒体库中删除
     *
     * @param file                         文件对象
     * @param uri                          查询uri 例: 图片 MediaUtils.getImageUri();
     * @param where                        查询条件 例: 图片 MediaUtils.getImageWhere();
     * @param mediaDeleteCompletedCallBack 若不需要使用, 可传 null
     */
    public void deleteFileAndDeleteFileInMedia(File file, Uri uri, String where, MediaDeleteCompletedCallBack mediaDeleteCompletedCallBack) {
        String filePath = file.getAbsolutePath();
        file.delete();
        ContentResolver resolver = mContext.getContentResolver();
        resolver.delete(uri, where + "=?", new String[]{filePath});
        if (mediaDeleteCompletedCallBack != null) {
            mediaDeleteCompletedCallBack.onDeleteCompleted();
        }
    }

    /**
     * 获取图片uri
     *
     * @return 图片uri
     */
    public static Uri getImageUri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    /**
     * 获取视频uri
     *
     * @return 视频uri
     */
    public static Uri getVideoUri() {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }

    /**
     * 获取音频uri
     *
     * @return 音频uri
     */
    public static Uri getAudioUri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    /**
     * 获取图片where
     *
     * @return 图片where
     */
    public static String getImageWhere() {
        return MediaStore.Images.Media.DATA;
    }

    /**
     * 获取视频where
     *
     * @return 视频where
     */
    public static String getVideoWhere() {
        return MediaStore.Video.Media.DATA;
    }

    /**
     * 获取音频where
     *
     * @return 音频where
     */
    public static String getAudioWhere() {
        return MediaStore.Audio.Media.DATA;
    }

    /**
     * 插入媒体库,完成时回调
     */
    public interface MediaScanCompletedCallBack {
        void onScanCompleted(String path, Uri uri);
    }

    /**
     * 从媒体库删除, 完成时回调
     */
    public interface MediaDeleteCompletedCallBack {
        void onDeleteCompleted();
    }
}
