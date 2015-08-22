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
 * cache manager
 * Directory Structure : (!!!See the complete structure README.md!!!)
 * in sdcard/android/[package name]/files or machine store
 * -- cache folder
 * --- dataCache folder
 * --- imageCache folder
 * --- otherCache folder
 */
public class CacheUtils {

    private Context mContext;

    private CacheUtils(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        initCacheUtils();
    }

    private static CacheUtils cacheUtils;

    public static CacheUtils getInstance(Context mContext) {
        if (cacheUtils == null) {
            cacheUtils = new CacheUtils(mContext);
        }
        return cacheUtils;
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
     * if phone have sd card , create cache folder in sd card/android/[package name]/files/cache
     * if phone not have sd card , create cache folder in data/data/[package name]/cache
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
     * check cache path, if the path not folder or not exists, create it.
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
     * get root cache file
     *
     * @return root cache file
     */
    public File getRootCacheFile() {
        return rootCacheFile;
    }

    /**
     * get data cache file
     *
     * @return data cache file
     */
    public File getDataCacheFile() {
        return dataCacheFile;
    }

    /**
     * get data cache path
     *
     * @return String type data cache path
     */
    public String getDataCachePath() {
        return dataCacheFile.toString();
    }

    /**
     * get media cache file
     *
     * @return media cache file
     */
    public File getMediaCacheFile() {
        return mediaCacheFile;
    }

    /**
     * get media cache path
     *
     * @return String type media cache path
     */
    public String getMediaCachePath() {
        return mediaCacheFile.toString();
    }

    /**
     * get other cache file
     *
     * @return other cache file
     */
    public File getOtherCacheFile() {
        return otherCacheFile;
    }


    /**
     * get other cache path
     *
     * @return String type other cache path
     */
    public String getOtherCachePath() {
        return otherCacheFile.toString();
    }

    /**
     * check cache exists and fail time
     *
     * @param cacheFileName cache file name
     * @param cacheType     cache type, CacheType.XXX
     * @param useExactTime  use exact time , true: use, false: do not use
     * @return true:exists,not fail. false:not exists/fail/exists but fail
     */
    public boolean checkCacheExistsAndFailTime(String cacheFileName, CacheType cacheType, boolean useExactTime) {
        return checkCacheExists(cacheFileName, cacheType) && useExactTime ? checkCacheFailTimeUseExactTime(cacheFileName, cacheType) : checkCacheFailTimeNotUseExactTime(cacheFileName, cacheType);
    }

    /**
     * check cache exists
     *
     * @param cacheFileName cache file name
     * @param cacheType     cache type, CacheType.XXX
     * @return true exists, false not exists
     */
    public boolean checkCacheExists(String cacheFileName, CacheType cacheType) {

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
     * check cache file whether failure
     *
     * @param cacheFileName cache file name
     * @param cacheType     cache type, CacheType.XXX
     * @return true: cache fail, false: cache not fail
     */
    public boolean checkCacheFailTimeUseExactTime(String cacheFileName, CacheType cacheType) {
        if (!checkCacheExists(cacheFileName, cacheType)) {
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
     * For a day as a unit, check cache fail time, over the same day zero expired
     *
     * @param cacheFileName cache file name
     * @param cacheType     cache type, CacheType.XXX
     * @return true: cache fail, false: cache not fail
     */
    public boolean checkCacheFailTimeNotUseExactTime(String cacheFileName, CacheType cacheType) {

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
