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
package com.sunhz.projectutils.mediautils;

import android.content.ContentResolver;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Multimedia Tools
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
     * Insert a row into the media library and media updates
     *
     * @param file                       file
     * @param mediaScanCompletedCallBack  insert completion callback
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
     * Delete the file and delete it in the media library
     *
     * @param file                         file
     * @param uri                          Query uri Example: Photo MediaUtils.getImageUri ();
     * @param where                        Query conditions of Example: Photo MediaUtils.getImageWhere ();
     * @param mediaDeleteCompletedCallBack If you do not use, you can pass null
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
     * get image uri
     *
     * @return image uri
     */
    public static Uri getImageUri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    /**
     * get video uri
     *
     * @return video uri
     */
    public static Uri getVideoUri() {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }

    /**
     * get audio uri
     *
     * @return audio uri
     */
    public static Uri getAudioUri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    /**
     * get image where
     *
     * @return image where
     */
    public static String getImageWhere() {
        return MediaStore.Images.Media.DATA;
    }

    /**
     * get video where
     *
     * @return video where
     */
    public static String getVideoWhere() {
        return MediaStore.Video.Media.DATA;
    }

    /**
     * get audio where
     *
     * @return audio where
     */
    public static String getAudioWhere() {
        return MediaStore.Audio.Media.DATA;
    }

    /**
     * Insert a media library, upon completion callback
     */
    public interface MediaScanCompletedCallBack {
        void onScanCompleted(String path, Uri uri);
    }

    /**
     * Remove from the media library, the completion callback
     */
    public interface MediaDeleteCompletedCallBack {
        void onDeleteCompleted();
    }
}
