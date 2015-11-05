/**
 * Copyright (c) 2015, Spencer , ChinaSunHZ (www.spencer-dev.com).
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunhz.projectutils.cacheutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.sunhz.projectutils.Constant;
import com.sunhz.projectutils.fileutils.FileUtils;
import com.sunhz.projectutils.fileutils.SDCardUtils;
import com.sunhz.projectutils.imageutils.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * cache 管理器
 * 目录结构：（详细的目录结构请参看项目的 README.md 文件）
 * in sdcard/android/[package name]/files or 手机内存
 * -- cache folder
 * --- dataCache folder
 * --- imageCache folder
 * --- otherCache folder
 */
public class CacheUtils {

    private Context mContext;

    private CacheUtils(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    private static CacheUtils cacheUtils;

    public static CacheUtils getInstance(Context mContext) {
        if (cacheUtils == null) {
            cacheUtils = new CacheUtils(mContext);
        }
        return cacheUtils;
    }

    public void init() {
        initCacheUtils();
    }


    private static final long FAIL_TIME = Constant.TimeInApplication.CACHE_FAIL_TIME;

    private static final String ROOT_CACHE_FOLDER_NAME = "cache";
    private static final String DATA_CACHE_FOLDER_NAME = "dataCache";
    private static final String MEDIA_CACHE_FOLDER_NAME = "mediaCache";
    private static final String OTHER_CACHE_FOLDER_NAME = "otherCache";


    private File superRootCacheFile;
    private File rootCacheFile;
    private File dataCacheFile;
    private File mediaCacheFile;
    private File otherCacheFile;

    /**
     * init cache utils
     */
    private void initCacheUtils() {

        checkCacheFolderExists();

    }

    /**
     * 如果手机有 sd 卡 , 创建缓存目录到 sd card/android/[package name]/files/cache
     * 如果手机没有 sd 卡 , 创建缓存目录到 data/data/[package name]/cache
     */
    private void checkCacheFolderExists() {

        if (SDCardUtils.checkSDCardIsExist() && SDCardUtils.isSdCardWritable()) {
            superRootCacheFile = mContext.getExternalFilesDir(null);
            rootCacheFile = new File(superRootCacheFile.toString() + File.separator + ROOT_CACHE_FOLDER_NAME);
        } else {
            rootCacheFile = mContext.getCacheDir();
        }

        String rootCachePath = rootCacheFile.toString();
        checkCachePath(rootCacheFile);


        dataCacheFile = new File(rootCachePath + File.separator + DATA_CACHE_FOLDER_NAME);
        checkCachePath(dataCacheFile);

        mediaCacheFile = new File(rootCachePath + File.separator + MEDIA_CACHE_FOLDER_NAME);
        checkCachePath(mediaCacheFile);

        otherCacheFile = new File(rootCachePath + File.separator + OTHER_CACHE_FOLDER_NAME);
        checkCachePath(otherCacheFile);


    }

    /**
     * 检查缓存文件夹是否存在，如果不存在，或者路径下不是文件夹，删除重建
     *
     * @param file file
     */
    private void checkCachePath(File file) {
        if (!file.exists()) {
            file.mkdirs();
        } else if (file.exists() && !file.isDirectory()) {
            file.delete();
            file.mkdirs();
        }

    }

    /**
     * 获取 root cache file
     *
     * @return root cache file
     */
    public File getRootCacheFile() {
        return rootCacheFile;
    }

    /**
     * 获取 data cache file
     *
     * @return data cache file
     */
    public File getDataCacheFile() {
        return dataCacheFile;
    }

    /**
     * 获取 data cache path
     *
     * @return String type data cache path
     */
    public String getDataCachePath() {
        return dataCacheFile.toString();
    }

    /**
     * 获取 media cache file
     *
     * @return media cache file
     */
    public File getMediaCacheFile() {
        return mediaCacheFile;
    }

    /**
     * 获取 media cache 路径
     *
     * @return String type media cache path
     */
    public String getMediaCachePath() {
        return mediaCacheFile.toString();
    }

    /**
     * 获取 other cache 路径
     *
     * @return other cache file
     */
    public File getOtherCacheFile() {
        return otherCacheFile;
    }


    /**
     * 获取 other cache 路径
     *
     * @return String type other cache path
     */
    public String getOtherCachePath() {
        return otherCacheFile.toString();
    }

    /**
     * 检查缓存是否存在，是否过期
     *
     * @param cacheType     cache type, CacheType.XXX
     * @param cacheFileName cache file name
     * @param useExactTime  使用精确时间查询缓存是否失效：true，false：不使用精确时间查询缓存是否失效
     * @return true：存在且没失效. false：缓存不存在或缓存以失效
     */
    public boolean checkCacheExistsAndFailTime(CacheType cacheType, String cacheFileName, boolean useExactTime) {
        return checkCacheExists(cacheType, cacheFileName) && useExactTime ? checkCacheFailTimeUseExactTime(cacheType, cacheFileName) : checkCacheFailTimeNotUseExactTime(cacheType, cacheFileName);
    }

    /**
     * 检查缓存是否存在
     *
     * @param cacheType     cache type, CacheType.XXX
     * @param cacheFileName cache file name
     * @return true：存在, false：不存在
     */
    public boolean checkCacheExists(CacheType cacheType, String cacheFileName) {

        switch (cacheType) {
            case DataCache:
                return new File(dataCacheFile, cacheFileName).exists();
            case MediaCache:
                return new File(mediaCacheFile, cacheFileName).exists();
            case OtherCache:
                return new File(otherCacheFile, cacheFileName).exists();
            default:
                return false;
        }
    }

    /**
     * 检查缓存是否失效
     *
     * @param cacheType     cache type, CacheType.XXX
     * @param cacheFileName cache file name
     * @return true：缓存已经失效, false：缓存未失效
     */
    public boolean checkCacheFailTimeUseExactTime(CacheType cacheType, String cacheFileName) {
        if (!checkCacheExists(cacheType, cacheFileName)) {
            return false;
        }
        long lastModified = 0;
        switch (cacheType) {
            case DataCache:
                lastModified = FileUtils.getFileLastModifiedTime(new File(dataCacheFile, cacheFileName));
                break;
            case MediaCache:
                lastModified = FileUtils.getFileLastModifiedTime(new File(mediaCacheFile, cacheFileName));
                break;
            case OtherCache:
                lastModified = FileUtils.getFileLastModifiedTime(new File(otherCacheFile, cacheFileName));
                break;
        }

        if (lastModified != 0) {
            return (System.currentTimeMillis() - lastModified) < FAIL_TIME;
        } else {
            return false;
        }
    }

    /**
     * 以一天为单位检查缓存是否失效
     *
     * @param cacheType     cache type, CacheType.XXX
     * @param cacheFileName cache file name
     * @return true：缓存失效, false：缓存未失效
     */
    public boolean checkCacheFailTimeNotUseExactTime(CacheType cacheType, String cacheFileName) {

        long lastModified = 0;
        switch (cacheType) {
            case DataCache:
                lastModified = FileUtils.getFileLastModifiedTime(new File(dataCacheFile, cacheFileName));
                break;
            case MediaCache:
                lastModified = FileUtils.getFileLastModifiedTime(new File(mediaCacheFile, cacheFileName));
                break;
            case OtherCache:
                lastModified = FileUtils.getFileLastModifiedTime(new File(otherCacheFile, cacheFileName));
                break;
            default:
                return false;
        }

        Calendar nowCa = Calendar.getInstance();
        int nowYear = nowCa.get(Calendar.YEAR);
        int nowMonth = nowCa.get(Calendar.MONTH) + 1;
        int nowDay = nowCa.get(Calendar.DATE);

        int nowResult = nowYear + nowMonth + nowDay;

        Calendar lastModifiedCa = Calendar.getInstance();
        lastModifiedCa.setTime(new Date(lastModified));
        int lastModifiedYear = lastModifiedCa.get(Calendar.YEAR);
        int lastModifiedMonth = lastModifiedCa.get(Calendar.MONTH) + 1;
        int lastModifiedDay = lastModifiedCa.get(Calendar.DATE);

        int lastModifiedResult = lastModifiedYear + lastModifiedMonth + lastModifiedDay;

        if (nowResult > lastModifiedResult) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * clear all cache
     */
    public void clearAllCache() {
        clearCache(rootCacheFile);
    }


    /**
     * clear data cache
     */
    public void clearDataCache() {
        clearCache(dataCacheFile);
    }

    /**
     * clear media cache
     */
    public void clearMediaCache() {
        clearCache(mediaCacheFile);
    }

    /**
     * clear other cache
     */
    public void clearOtherCache() {
        clearCache(otherCacheFile);
    }


    /**
     * clear cache
     */
    private void clearCache(File file) {
        FileUtils.deleteDir(file);
        checkCacheFolderExists();
    }


    /**
     * get all cache size
     *
     * @return all cache size
     */
    public String getAllCacheSize() {
        return getCacheSize(rootCacheFile);
    }

    /**
     * get data cache size
     *
     * @return data cache size
     */
    public String getDataCacheSize() {
        return getCacheSize(dataCacheFile);
    }

    /**
     * get media cache size
     *
     * @return media cache size
     */
    public String getMediaCacheSize() {
        return getCacheSize(mediaCacheFile);
    }

    /**
     * get other cache size
     *
     * @return other cache size
     */
    public String getOtherCacheSize() {
        return getCacheSize(otherCacheFile);
    }

    /**
     * get cache size
     *
     * @return After the converted format cache size
     */
    private String getCacheSize(File file) {

        try {
            String cacheSize = FileUtils.formetFileSize(FileUtils.getDirectorySize(file));
            cacheSize = cacheSize.equals("-1.00B") ? "0MB" : cacheSize;
            return cacheSize;
        } catch (Exception e) {
            e.printStackTrace();
            return "0MB";
        }
    }


    /**
     * save string
     *
     * @param cacheType cache type, CacheType.XXX
     * @param cacheName cache name
     * @param content   cache content
     * @throws IOException save failure
     */
    public void saveStringToFile(CacheType cacheType, String cacheName, String content) throws IOException {
        FileUtils.write(new File(underCacheTypeGetCacheFile(cacheType), cacheName), content);
    }


    /**
     * read string from file
     *
     * @param cacheType cache type, CacheType.XXX
     * @param cacheName cache name
     * @return string
     * @throws IOException read failure
     */
    public String readStringFromFile(CacheType cacheType, String cacheName) throws IOException {
        return FileUtils.read(new File(underCacheTypeGetCacheFile(cacheType), cacheName));
    }

    /**
     * save anything(inputStream) to file
     *
     * @param cacheType   cache type, CacheType.XXX
     * @param cacheName   cache name
     * @param inputStream inputStream object
     * @throws IOException save failure
     */
    public void saveAnythingToFile(CacheType cacheType, String cacheName, InputStream inputStream) throws IOException {
        FileUtils.saveInputStreamToFile(inputStream, new File(underCacheTypeGetCacheFile(cacheType), cacheName).toString());
    }

    /**
     * read anything from file
     *
     * @param cacheType cache type, CacheType.XXX
     * @param cacheName cache name
     * @return inputStream
     * @throws IOException read failure
     */
    public InputStream readAnythingFromFile(CacheType cacheType, String cacheName) throws IOException {
        return FileUtils.openInputStream(new File(underCacheTypeGetCacheFile(cacheType), cacheName));
    }

    /**
     * save Serializable object to file
     *
     * @param cacheType cache type, CacheType.XXX
     * @param cacheName cache name
     * @param object    Serializable object
     * @throws IOException save failure
     */
    public void saveObjectToFile(CacheType cacheType, String cacheName, Serializable object) throws IOException {
        FileUtils.writeObjectToFile(object, cacheName, underCacheTypeGetCacheFile(cacheType).toString());
    }


    /**
     * read object from file
     *
     * @param cacheType cache type, CacheType.XXX
     * @param cacheName cache name
     * @param <T>       T
     * @return T
     * @throws IOException            read failure
     * @throws ClassNotFoundException read failure
     */
    public <T> T readObjectFromFile(CacheType cacheType, String cacheName) throws IOException, ClassNotFoundException {
        return FileUtils.readObjectToFile(cacheName, underCacheTypeGetCacheFile(cacheType).toString());
    }

    /**
     * save bitmap to file
     *
     * @param cacheType cache type, CacheType.XXX
     * @param cacheName cache name
     * @param bitmap    bitmap object
     * @throws IOException save failure
     */
    public void saveBitmapToFile(CacheType cacheType, String cacheName, Bitmap bitmap) throws IOException {
        ImageUtils.bitmapToFile(bitmap, new File(underCacheTypeGetCacheFile(cacheType), cacheName));
    }

    /**
     * read bitmap from file
     *
     * @param cacheType cache type, CacheType.XXX
     * @param cacheName cache name
     * @return bitmap object
     * @throws IOException read failure
     */
    public Bitmap readBitmapFromFile(CacheType cacheType, String cacheName) throws IOException {
        return ImageUtils.fileToBitmap(new File(underCacheTypeGetCacheFile(cacheType), cacheName).toString());
    }

    /**
     * save drawable to file
     *
     * @param cacheType cache type, CacheType.XXX
     * @param cacheName cache name
     * @param drawable  drawable object
     * @throws IOException save failure
     */
    public void saveDrawableToFile(CacheType cacheType, String cacheName, Drawable drawable) throws IOException {
        ImageUtils.drawableToFile(drawable, new File(underCacheTypeGetCacheFile(cacheType), cacheName));
    }


    /**
     * read drawable from file
     *
     * @param cacheType cache type, CacheType.XXX
     * @param cacheName cache name
     * @return drawable object
     * @throws IOException read failure
     */
    public Drawable readDrawableFromFile(CacheType cacheType, String cacheName) throws IOException {
        return ImageUtils.fileToDrawable(new File(underCacheTypeGetCacheFile(cacheType), cacheName).toString());
    }

    /**
     * under cache type get cache file object
     *
     * @param cacheType cache type, CacheType.XXX
     * @return cache file object
     */
    public File underCacheTypeGetCacheFile(CacheType cacheType) {
        switch (cacheType) {
            case DataCache:
                return getDataCacheFile();
            case MediaCache:
                return getMediaCacheFile();
            case OtherCache:
                return getOtherCacheFile();
            default:
                throw new IllegalArgumentException("no this cache type");
        }
    }

}