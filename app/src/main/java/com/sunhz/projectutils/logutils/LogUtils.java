package com.sunhz.projectutils.logutils;

import android.content.Context;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.sunhz.projectutils.cacheutils.CacheUtils;
import com.sunhz.projectutils.fileutils.FileUtils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class LogUtils {

    private static final String LOG_FOLDER_NAME = "log";

    private LogUtils() {
    }


    /**
     * 保存错误信息到文件中
     */
    public static void writeExceptionLog(Context mContext, Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);

        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        String result = info.toString();
        printWriter.close();
        try {
            long timestamp = System.currentTimeMillis();
            String fileName = "ex-" + timestamp;
            String fileFolderPath = CacheUtils.getInstance(mContext.getApplicationContext()).getOtherCachePath() + LOG_FOLDER_NAME;
            if (!new File(fileFolderPath).exists()) {
                new File(fileFolderPath).mkdirs();
            }
            String filePath = fileFolderPath + File.separator + fileName;
            FileUtils.write(new File(filePath), result);
        } catch (Exception e) {
            Log.e("", "an error occured while writing report file...", e);
        }
    }

    public static void e(String msg, Object... args) {
        Logger.e(msg, args);
    }

    public static void e(Throwable throwable, String msg, Object... args) {
        Logger.e(throwable, msg, args);

    }

    public static void d(String msg, Object... args) {
        Logger.d(msg, args);
    }

    public static void w(String msg, Object... args) {
        Logger.w(msg, args);
    }

    public static void i(String msg, Object... args) {
        Logger.i(msg, args);
    }


    public static void v(String msg, Object... args) {
        Logger.v(msg, args);
    }

    public static void wtf(String msg, Object... args) {
        Logger.wtf(msg, args);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }


}
