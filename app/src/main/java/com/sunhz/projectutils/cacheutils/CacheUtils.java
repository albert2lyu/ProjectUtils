package com.sunhz.projectutils.cacheutils;

import android.content.Context;
import android.text.TextUtils;

import com.sunhz.projectutils.fileutils.FileUtils;
import com.sunhz.projectutils.fileutils.SDCardUtils;
import com.sunhz.projectutils.packageutils.PackageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 数据缓存管理
 */
public class CacheUtils {

    private static final int FAIL_TIME = 1000 * 60 * 60 * 24; // 一天

    public static final Boolean CHECK_FAIL_TIME = true;

    public static final Boolean NOT_CHECK_FAIL_TIME = false;

    private static CacheUtils cacheUtils;

    private String cachePath;

    private Context mContext;

    /**
     * 如果SD卡存在,并可读写,就将缓存存储在SD卡/包名/cache/目录下.
     * <p/>
     * 如果SD卡不存在,就直接存在data/data/包名/cache/目录下
     *
     * @param mContext
     */
    private CacheUtils(Context mContext) {
        this.mContext = mContext;
        if (SDCardUtils.getInstance().checkSDCard() && SDCardUtils.getInstance().isSdCardWrittenable()) {
            cachePath = SDCardUtils.getInstance().getSDCardPath() + File.separator + PackageUtils.getInstance(mContext).getMyApplicationPackageName() + File.separator;
        } else {
            cachePath = mContext.getCacheDir().toString();
        }
        File tempFile = new File(cachePath);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
    }

    public synchronized static CacheUtils getInstance(Context mContext) {
        if (cacheUtils == null) {
            cacheUtils = new CacheUtils(mContext);
        }
        return cacheUtils;
    }

    /**
     * 检查缓存文件是否存在,如果存在并检查是否过期
     * @param cacheFileName
     * @return
     */
    public boolean checkCacheExistsAndFailTime(String cacheFileName) {
        return checkCacheExists(cacheFileName) && checkCacheFailTime(cacheFileName);
    }

    /**
     * 检查缓存是否存在
     *
     * @return
     */
    public boolean checkCacheExists(String cacheFileName) {
        return new File(cachePath + cacheFileName).exists();
    }

    /**
     * 检查缓存是否过期
     *
     * @param cacheFileName
     * @return true:还没有过期,false:已经过期
     */
    public boolean checkCacheFailTime(String cacheFileName) {
        if (checkCacheExists(cacheFileName)) {
            return true;
        }
        long lastModified = FileUtils.getInstance().getFileLastModifiedTime(new File(cachePath + cacheFileName));
        return (System.currentTimeMillis() - lastModified) < FAIL_TIME;
    }

    /**
     * 存储str缓存
     *
     * @param cacheName 缓存名
     * @param content   缓存内容
     * @throws IOException
     */
    public void saveStringCache(String cacheName, String content) throws IOException {
        check(cacheName);
        FileUtils.getInstance().write(new File(cachePath, cacheName), content);
    }

    /**
     * 获取string缓存
     *
     * @param cacheName    缓存名字
     * @param failTimeFlag 过期时间是否生效,CacheUtils.CHECK_FAIL_TIME(true):生效,CacheUtils.NOT_CHECK_FAIL_TIME(false):不生效
     * @return
     */
    public String getStringCache(String cacheName, boolean failTimeFlag) throws Exception {
        File file = new File(cachePath, cacheName);

        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在:" + file.toString());
        }

        String cacheContent = FileUtils.getInstance().read(file);

        if (TextUtils.isEmpty(cacheContent)) {
            throw new Exception("cache 为空");
        }

        if (failTimeFlag && checkCacheFailTime(cacheName)) {
            throw new Exception("cache 已经过期");
        }
        return cacheContent;
    }

    /**
     * 删除全部缓存
     */
    public void clearAllCache() {
        FileUtils.getInstance().deleteDir(new File(cachePath));
    }

    /**
     * 获取缓存大小
     */
    public String getCacheSize() {
        try {
            long size = FileUtils.getInstance().getDirectorySize(new File(cachePath));
            if (size == -1) {
                return "无缓存";
            } else {
                return FileUtils.getInstance().formetFileSize(size);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getCachePath() {
        return cachePath;
    }

    /**
     * 检查缓存文件夹是否存在,检查保存缓存的文件是否存在
     *
     * @param cacheName
     */
    private void check(String cacheName) {
        try {

            File cachePathTemp = new File(cachePath);
            if (!cachePathTemp.exists()) {
                cachePathTemp.mkdirs();
            }

            File cacheFileTemp = new File(cachePath, cacheName);
            if (!cacheFileTemp.exists()) {
                cacheFileTemp.createNewFile();
            }
        } catch (IOException e) {
        }
    }
}