package com.sunhz.projectutils.cacheutils;

import android.content.Context;
import android.text.TextUtils;

import com.sunhz.projectutils.Constance;
import com.sunhz.projectutils.fileutils.FileUtils;
import com.sunhz.projectutils.fileutils.SDCardUtils;
import com.sunhz.projectutils.packageutils.PackageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * 数据缓存管理
 * <p/>
 * 目录结构 : (!!!完整结构查看README.md!!!)
 * <p/>
 * sdcard下
 * - package name (程序包名)
 * -- cache (缓存目录)
 * --- dataCache (缓存数据)
 * --- imageCache (缓存图片)
 * --- otherCache (缓存其他东西)
 */
public class CacheUtils {

    private static final long DAY = 1000 * 60 * 60 * 24; // 一天的毫秒数

    private static final long FAIL_TIME = Constance.TimeInApplication.CACHE_FAIL_TIME;

    /**
     * 如果缓存有效时间设置为一整天,自动计算开关
     */
    public static final boolean AUTO_C_DAY = true;

    // 检查文件失效时间
    public static final Boolean CHECK_FAIL_TIME = true;

    // 不检查文件失效时间
    public static final Boolean NOT_CHECK_FAIL_TIME = false;

    private static CacheUtils cacheUtils;

    private Context mContext;

    // 缓存文件夹名字
    private static final String ROOT_DATA_FOLDER_NAME = "cache"; // 缓存根目录文件夹

    private static final String DATA_CACHE_FOLDER_NAME = "dataCache"; // 数据缓存

    private static final String IMAGE_CACHE_FOLDER_NAME = "imageCache"; // 图片缓存

    private static final String OTHER_CACHE_FOLDER_NAME = "otherCahce"; // 其他类型缓存


    // cache路径
    private String rootCachePath;

    private String dataCachePath;

    private String imageCachePath;

    private String otherCachePath;


    public synchronized static CacheUtils getInstance(Context mContext) {
        if (cacheUtils == null) {
            cacheUtils = new CacheUtils(mContext.getApplicationContext());
        }
        return cacheUtils;
    }


    private CacheUtils(Context mContext) {
        this.mContext = mContext;
        init();
    }

    /**
     * 如果sd卡存在，并且可写
     */
    private void init() {
        if (SDCardUtils.checkSDCard() && SDCardUtils.isSdCardWrittenable()) {
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
     * @return
     */
    public String getRootCachePath() {
        return rootCachePath;
    }

    /**
     * 获取数据缓存根目录
     *
     * @return
     */
    public String getDataCachePath() {
        return dataCachePath;
    }

    /**
     * 获取图片缓存根目录
     *
     * @return
     */
    public String getImageCachePath() {
        return imageCachePath;
    }

    /**
     * 获取其他缓存根目录
     *
     * @return
     */
    public String getOtherCachePath() {
        return otherCachePath;
    }

    /**
     * 检查缓存是否存在,是否过期
     *
     * @param cacheFileName
     * @param cacheType     CacheType.XXX
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
     * @return
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
     * <p/>
     * 如果缓存检查时间设置为24小时.
     * 会自动从缓存当天的0时开始计算,一直到当天24时. 算完整一整天.
     * <p/>
     * 例: 2010年03月04日 13时34分35秒 将文件成功缓存到本地.
     * 文件到2010年03月04日 24时0分0秒 文件将会自动过期.
     * <p/>
     * 如果需要24小时时间缓存,但不需要自动按照整天来计算缓存有效时间这项功能
     * 可在 application 中将 CacheUtils.AUTO_C_DAY 设置为 false, 此属性默认为true
     *
     * @param cacheFileName
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
     */
    public void saveStringCacheToDataCacheFolder(String content, String cacheName) throws IOException {
        FileUtils.write(new File(dataCachePath, cacheName), content);
    }


    /**
     * 保存字符串到其他缓存
     */
    public void saveStringCacheToOtherFolder(String content, String cacheName) throws IOException {
        FileUtils.write(new File(otherCachePath, cacheName), content);
    }

    /**
     * 从Data目录下,获取string缓存
     *
     * @param cacheName    缓存名字
     * @param failTimeFlag 过期时间是否生效,CacheUtils.CHECK_FAIL_TIME(true):生效,CacheUtils.NOT_CHECK_FAIL_TIME(false):不生效
     * @return
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
     * @return
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
     * @return
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
        File file = new File(getRootCachePath());
        FileUtils.deleteDir(file);
    }


}
