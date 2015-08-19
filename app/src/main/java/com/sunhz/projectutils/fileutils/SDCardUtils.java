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
package com.sunhz.projectutils.fileutils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.IOException;

/**
 * SD卡相关工具类
 * Created by Spencer (www.spencer-dev.com) on 15/2/21.
 */
public class SDCardUtils {


    private SDCardUtils() {

    }


    /**
     * 返回sd卡的路径
     *
     * @return sd卡路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 检测Sd卡是否存在
     *
     * @return true:存在,false:不存在
     */
    public static boolean checkSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 检查sd卡是否可写
     *
     * @return true:可写入,false:不可写入
     */
    public static boolean isSdCardWrittenable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取sd卡的可用存储大小 剩下的空间
     *
     * @return sd卡剩余空间
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
     * 判断当前的sd空间是否可保存该文件
     *
     * @param currentFileSize 当前文件大小
     * @return 返回是否可保存
     * @throws Exception 保存失败
     */
    public static boolean isAvailableStorage(long currentFileSize) throws Exception {
        // / 检测sd卡是否存在
        if (!checkSDCard()) {
            throw new Exception("sd卡不存在");
        }
        // 检查sd卡是否可读
        if (!isSdCardWrittenable()) {
            throw new Exception("sd卡不能执行写入操作");
        }
        long availableSize = getAvailableStorage();
        return Float.compare(availableSize, currentFileSize) == 1;
    }

    /**
     * 在SD卡上创建文件
     *
     * @param fileName 要创建的文件名
     * @return 创建得到的文件
     * @throws IOException 创建失败
     */
    public static File createSDFile(String fileName) throws IOException {
        File file = new File(getSDCardPath() + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录,可多层级目录一起创建
     *
     * @param absoluteDirName 要创建的目录名
     * @return 创建得到的目录
     */
    public static File createAbsoluteSDDir(String absoluteDirName) {
        File dir = new File(getSDCardPath(), absoluteDirName);
        dir.mkdirs();
        return dir;
    }

}
