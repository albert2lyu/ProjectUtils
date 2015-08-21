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
package com.sunhz.projectutils.fileutils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.IOException;

/**
 * sd card tool
 * Created by Spencer (www.spencer-dev.com) on 15/2/21.
 */
public class SDCardUtils {


    private SDCardUtils() {

    }


    /**
     * get sd card path
     *
     * @return sd card path
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * the existence of sd card
     *
     * @return true : exist ,false : not exist
     */
    public static boolean checkSDCardIsExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * Check that the sd card can write
     *
     * @return true : can write ,false : not can write
     */
    public static boolean isSdCardWritable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * Get sd card available storage size
     *
     * @return sd card available storage size
     */
    public static long getAvailableStorage() {
        String storageDirectory = null;
        storageDirectory = getSDCardPath();
        try {
            StatFs stat = new StatFs(storageDirectory);
            return ((long) stat.getAvailableBlocks() * (long) stat.getBlockSize());
        } catch (RuntimeException ex) {
            return 0;
        }
    }

    /**
     * Determine whether the current sd card space to save the file
     *
     * @param currentFileSize current file size
     * @return Returns whether to save
     * @throws Exception save failure
     */
    public static boolean isAvailableStorage(long currentFileSize)  {
        if (!checkSDCardIsExist()) {
            return false;
        }

        if (!isSdCardWritable()) {
            return false;
        }

        long availableSize = getAvailableStorage();
        return Float.compare(availableSize, currentFileSize) == 1;
    }

    /**
     * Create a file in the SD card
     *
     * @param fileName file name
     * @return Created files
     * @throws IOException create failure
     */
    public static File createSDFile(String fileName) throws IOException {
        File file = new File(getSDCardPath() + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * Create a directory in the SD card, you can create multi-level directory together
     *
     * @param absoluteDirName Directory name to be created
     * @return Create a directory to get
     */
    public static File createAbsoluteSDDir(String absoluteDirName) {
        File dir = new File(getSDCardPath(), absoluteDirName);
        dir.mkdirs();
        return dir;
    }

}
