package com.sunhz.projectutils.fileutils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.IOException;

/**
 * SD卡相关工具类
 */
public class SDCardUtils {

    private static SDCardUtils sdCardUtils;

    private SDCardUtils() {

    }

    public synchronized static SDCardUtils getInstance() {
        if (sdCardUtils == null) {
            sdCardUtils = new SDCardUtils();
        }
        return sdCardUtils;
    }

    /**
     * 返回sd卡的路径
     *
     * @return sd卡路径
     */
    public String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 检测Sd卡是否存在
     *
     * @return true:存在,flase:不存在
     */
    public boolean checkSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 检查sd卡是否可写
     *
     * @return true:可写入,false:不可写入
     */
    public boolean isSdCardWrittenable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取sd卡的可用存储大小 剩下的空间
     *
     * @return sd卡剩余空间
     */
    public long getAvailableStorage() {
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
     * @param currentFileSize
     * @return
     * @throws Exception
     */
    public boolean isAvailableStorage(long currentFileSize) throws Exception {
        // / 检测sd卡是否存在
        if (!checkSDCard()) {
            throw new Exception("sd卡不存在");
        }
        // 检查sd卡是否可读
        if (!isSdCardWrittenable()) {
            throw new Exception("sd卡不能执行写入操作");
        }
        long avaliableSize = getAvailableStorage();
        return Float.compare(avaliableSize, currentFileSize) == 1;
    }

    /**
     * 在SD卡上创建文件
     *
     * @param fileName 要创建的文件名
     * @return 创建得到的文件
     */
    public File createSDFile(String fileName) throws IOException {
        File file = new File(getSDCardPath() + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param absoluteDirName 要创建的目录名
     * @return 创建得到的目录
     */
    public File createAbsoluteSDDir(String absoluteDirName) {
        File dir = new File(getSDCardPath(), absoluteDirName);
        dir.mkdir();
        return dir;
    }

}
