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
import android.text.TextUtils;

import com.sunhz.projectutils.Constant;
import com.sunhz.projectutils.fileutils.FileUtils;
import com.sunhz.projectutils.fileutils.SDCardUtils;
import com.sunhz.projectutils.packageutils.PackageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * cache manager
 * Directory Structure : (!!!See the complete structure README.md!!!)
 * in sdcard or machine store
 * - application package name
 * -- cache folder
 * --- dataCache folder
 * --- imageCache folder
 * --- otherCache folder
 */
public class CacheUtils {

    // 检查文件失效时间
    public static final Boolean CHECK_FAIL_TIME = true;
    // 不检查文件失效时间
    public static final Boolean NOT_CHECK_FAIL_TIME = false;

    /**
     * 如果缓存有效时间设置为一整天,自动计算开关
     */
    public static final boolean AUTO_C_DAY = true;
    private static final long DAY = 1000 * 60 * 60 * 24; // 一天的毫秒数
    private static final long FAIL_TIME = Constant.TimeInApplication.CACHE_FAIL_TIME;

    // 缓存文件夹名字
    private static final String ROOT_DATA_FOLDER_NAME = "cache"; // 缓存根目录文件夹
    private static final String DATA_CACHE_FOLDER_NAME = "dataCache"; // 数据缓存
    private static final String IMAGE_CACHE_FOLDER_NAME = "imageCache"; // 图片缓存
    private static final String OTHER_CACHE_FOLDER_NAME = "otherCahce"; // 其他类型缓存

    private Context mContext;
    private static CacheUtils cacheUtils;

    // cache路径
    private String rootCachePath;
    private String dataCachePath;
    private String imageCachePath;
    private String otherCachePath;


    private CacheUtils(Context mContext) {
        this.mContext = mContext;
        init();
    }

    public synchronized static CacheUtils getInstance(Context mContext) {
        if (cacheUtils == null) {
            cacheUtils = new CacheUtils(mContext.getApplicationContext());
        }
        return cacheUtils;
    }

    /**
     * If the sd card is present And writable,the cache into the sd card, otherwise, put the machine store.
     */
    private void init() {
        if (SDCardUtils.checkSDCardIsExist() && SDCardUtils.isSdCardWritable()) {
            StringBuffer rootCachePathStringBuffer = new StringBuffer();
            rootCachePathStringBuffer.append(SDCardUtils.getSDCardPath()).
                    append(File.separator).
                    append(PackageUtils.getInstance(mContext).getMyApplicationPackageName()).
                    append(File.separator).
                    append(ROOT_DATA_FOLDER_NAME).
                    append(File.separator);
            rootCachePath = rootCachePathStringBuffer.toString();
        } else {
            rootCachePath = mContext.getCacheDir().toString();
        }

        dataCachePath = rootCachePath + DATA_CACHE_FOLDER_NAME + File.separator;
        imageCachePath = rootCachePath + IMAGE_CACHE_FOLDER_NAME + File.separator;
        otherCachePath = rootCachePath + OTHER_CACHE_FOLDER_NAME + File.separator;


        checkFileFolder();

    }

    /**
     * 检查各个缓存目录是否正常
     */
    private void checkFileFolder() {
        File rootCacheFolder = new File(rootCachePath);
        if (!rootCacheFolder.exists()) {
            rootCacheFolder.mkdirs();
        }
        rootCacheFolder = null;

        File dataCacheFolder = new File(dataCachePath);
        if (!dataCacheFolder.exists()) {
            dataCacheFolder.mkdirs();
        }
        dataCacheFolder = null;

        File imageCacheFolder = new File(imageCachePath);
        if (!imageCacheFolder.exists()) {
            imageCacheFolder.mkdirs();
        }
        imageCacheFolder = null;

        File otherCacheFolder = new File(otherCachePath);
        if (!otherCacheFolder.exists()) {
            otherCacheFolder.mkdirs();
        }
        otherCacheFolder = null;

    }

    /**
     * 获取缓存根目录
     *
     * @return Str
     */
    public String getRootCachePathStr() {
        return rootCachePath;
    }

    /**
     * 获取缓存根目录
     *
     * @return File
     */
    public File getRootCachePathFile() {
        return new File(rootCachePath);
    }

    /**
     * 获取数据缓存根目录
     *
     * @return Str
     */
    public String getDataCachePathStr() {
        return dataCachePath;
    }

    /**
     * 获取数据缓存根目录
     *
     * @return File
     */
    public File getDataCachePathFile() {
        return new File(dataCachePath);
    }

    /**
     * 获取图片缓存根目录
     *
     * @return Str
     */
    public String getImageCachePathStr() {
        return imageCachePath;
    }

    /**
     * 获取图片缓存根目录
     *
     * @return File
     */
    public File getImageCachePathFile() {
        return new File(imageCachePath);
    }

    /**
     * 获取其他缓存根目录
     *
     * @return Str
     */
    public String getOtherCachePathStr() {
        return otherCachePath;
    }

    /**
     * 获取其他缓存根目录
     *
     * @return File
     */
    public File getOtherCachePathFile() {
        return new File(otherCachePath);
    }


    /**
     * 检查缓存是否存在,是否过期
     *
     * @param cacheFileName 缓存文件名称
     * @param cacheType     缓存类型 CacheType.XXX
     * @return true:存在,没有过期. false:不存在/过期/存在但过期
     */
    public boolean checkCacheExistsAndFailTime(String cacheFileName, CacheType cacheType) {
        return checkCacheExists(cacheFileName, cacheType) && checkCacheFailTime(cacheFileName, cacheType);
    }

    public boolean checkCacheExistsAndFailTime(String cacheFilePath, String cacheFileName) {
        return checkCacheExists(cacheFilePath, cacheFileName) && checkCacheFailTime(cacheFilePath, cacheFileName);
    }

    /**
     * 检查缓存是否存在
     *
     * @param cacheFileName 缓存文件名
     * @param cacheType     缓存类型 CacheType.XXX
     * @return true 存在, false 不存在
     */
    public boolean checkCacheExists(String cacheFileName, CacheType cacheType) {

        switch (cacheType) {
            case DATA:
                return new File(dataCachePath + cacheFileName).exists();
            case IMAGE:
                return new File(imageCachePath + cacheFileName).exists();
            case OTHER:
                return new File(otherCachePath + cacheFileName).exists();
            default:
                return false;
        }
    }

    public boolean checkCacheExists(String cacheFilePath, String cacheFileName) {
        return new File(cacheFilePath, cacheFileName).exists();
    }


    /**
     * 检查缓存是否过期
     * 如果缓存检查时间设置为24小时.
     * 会自动从缓存当天的0时开始计算,一直到当天24时. 算完整一整天.
     * 例: 2010年03月04日 13时34分35秒 将文件成功缓存到本地.
     * 文件到2010年03月04日 24时0分0秒 文件将会自动过期.
     * 如果需要24小时时间缓存,但不需要自动按照整天来计算缓存有效时间这项功能
     * 可在 application 中将 CacheUtils.AUTO_C_DAY 设置为 false, 此属性默认为true
     *
     * @param cacheFileName 缓存文件名
     * @param cacheType     缓存类型 CacheType.XXX
     * @return true:还没有过期,false:已经过期
     */
    public boolean checkCacheFailTime(String cacheFileName, CacheType cacheType) {
        if (!checkCacheExists(cacheFileName, cacheType)) {
            return false;
        }
        long lastModified = 0;
        switch (cacheType) {
            case DATA:
                lastModified = FileUtils.getFileLastModifiedTime(new File(dataCachePath + cacheFileName));
                break;
            case IMAGE:
                lastModified = FileUtils.getFileLastModifiedTime(new File(imageCachePath + cacheFileName));
                break;
            case OTHER:
                lastModified = FileUtils.getFileLastModifiedTime(new File(otherCachePath + cacheFileName));
                break;
        }

        if (lastModified != 0) {
            if (FAIL_TIME == DAY && AUTO_C_DAY) { // 按天来区分缓存数据
                Calendar nowCa = Calendar.getInstance();
                int nowYear = nowCa.get(Calendar.YEAR);// 获取年份
                int nowMonth = nowCa.get(Calendar.MONTH) + 1;// 获取月份
                int nowDay = nowCa.get(Calendar.DATE);// 获取日

                int nowResult = nowYear + nowMonth + nowDay;

                Calendar lastModifiedCa = Calendar.getInstance();
                lastModifiedCa.setTime(new Date(lastModified));
                int lastModifiedYear = lastModifiedCa.get(Calendar.YEAR);// 获取年份
                int lastModifiedMonth = lastModifiedCa.get(Calendar.MONTH) + 1;// 获取月份
                int lastModifiedDay = lastModifiedCa.get(Calendar.DATE);// 获取日

                int lastModifiedResult = lastModifiedYear - lastModifiedMonth - lastModifiedDay;

                if (nowResult > lastModifiedResult) {
                    return false;
                } else {
                    return true;
                }


            } else {
                return (System.currentTimeMillis() - lastModified) < FAIL_TIME;
            }

        } else {
            return false;
        }
    }

    public boolean checkCacheFailTime(String cacheFilePath, String cacheFileName) {
        if (!checkCacheExists(cacheFilePath, cacheFileName)) {
            return false;
        }

        long lastModified = FileUtils.getFileLastModifiedTime(new File(cacheFilePath, cacheFileName));

        if (lastModified != 0) {
            if (FAIL_TIME == DAY && AUTO_C_DAY) { // 按天来区分缓存数据
                Calendar nowCa = Calendar.getInstance();
                int nowYear = nowCa.get(Calendar.YEAR);// 获取年份
                int nowMonth = nowCa.get(Calendar.MONTH) + 1;// 获取月份
                int nowDay = nowCa.get(Calendar.DATE);// 获取日

                int nowResult = nowYear + nowMonth + nowDay;

                Calendar lastModifiedCa = Calendar.getInstance();
                lastModifiedCa.setTime(new Date(lastModified));
                int lastModifiedYear = lastModifiedCa.get(Calendar.YEAR);// 获取年份
                int lastModifiedMonth = lastModifiedCa.get(Calendar.MONTH) + 1;// 获取月份
                int lastModifiedDay = lastModifiedCa.get(Calendar.DATE);// 获取日

                int lastModifiedResult = lastModifiedYear - lastModifiedMonth - lastModifiedDay;

                if (nowResult > lastModifiedResult) {
                    return false;
                } else {
                    return true;
                }


            } else {
                return (System.currentTimeMillis() - lastModified) < FAIL_TIME;
            }

        } else {
            return false;
        }
    }

    /**
     * 保存字符串到数据缓存
     *
     * @param content   待保存内容
     * @param cacheName 缓存文件名
     * @throws IOException 保存失败
     */
    public void saveStringCacheToDataCacheFolder(String content, String cacheName) throws IOException {
        FileUtils.write(new File(dataCachePath, cacheName), content);
    }


    /**
     * 保存字符串到其他缓存
     *
     * @param content   待保存内容
     * @param cacheName 缓存文件名
     * @throws IOException 保存失败
     */
    public void saveStringCacheToOtherFolder(String content, String cacheName) throws IOException {
        FileUtils.write(new File(otherCachePath, cacheName), content);
    }

    /**
     * 从Data目录下,获取string缓存
     *
     * @param cacheName    缓存名字
     * @param failTimeFlag 过期时间是否生效,CacheUtils.CHECK_FAIL_TIME(true):生效,CacheUtils.NOT_CHECK_FAIL_TIME(false):不生效
     * @return 字符串缓存
     * @throws Exception 异常情况(文件不存在,cache 已经过期,cache 为空 等其他错误)
     */
    public String getStringCacheToDataCacheFolder(String cacheName, boolean failTimeFlag) throws Exception {
        File file = new File(dataCachePath, cacheName);

        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在:" + file.toString());
        }

        if (failTimeFlag && !checkCacheFailTime(cacheName, CacheType.DATA)) {
            throw new Exception("cache 已经过期");
        }

        String cacheContent = FileUtils.read(file);

        if (TextUtils.isEmpty(cacheContent)) {
            throw new Exception("cache 为空");
        }

        return cacheContent;
    }

    /**
     * 从Other目录下,获取string缓存
     *
     * @param cacheName    缓存名字
     * @param failTimeFlag 过期时间是否生效,CacheUtils.CHECK_FAIL_TIME(true):生效,CacheUtils.NOT_CHECK_FAIL_TIME(false):不生效
     * @return 字符串缓存
     * @throws Exception 异常情况(文件不存在,cache 已经过期,cache 为空 等其他错误)
     */
    public String getStringCacheToOtherCacheFolder(String cacheName, boolean failTimeFlag) throws Exception {
        File file = new File(otherCachePath, cacheName);

        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在:" + file.toString());
        }

        if (failTimeFlag && !checkCacheFailTime(cacheName, CacheType.DATA)) {
            throw new Exception("cache 已经过期");
        }

        String cacheContent = FileUtils.read(file);

        if (TextUtils.isEmpty(cacheContent)) {
            throw new Exception("cache 为空");
        }

        return cacheContent;
    }

    /**
     * 获取缓存大小
     *
     * @return 已转换格式后的缓存大小
     */
    public String getCacheSize() {
        File file = new File(rootCachePath);

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
     * 清除缓存
     */
    public void clearAllCache() {
        File file = new File(getRootCachePathStr());
        FileUtils.deleteDir(file);
    }


}
