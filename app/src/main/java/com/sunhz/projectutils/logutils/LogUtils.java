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
package com.sunhz.projectutils.logutils;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.sunhz.projectutils.cacheutils.CacheUtils;
import com.sunhz.projectutils.fileutils.FileUtils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * LogUtils
 * Created by Spencer (www.spencer-dev.com) on 15/2/27.
 */
public class LogUtils {

    private LogUtils() {

    }

    private static final String LOG_FOLDER_NAME = "log";

    /**
     * save exception info save to file
     *
     * @param mContext Context
     * @param ex       exception
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
            String fileFolderPath = CacheUtils.getInstance(mContext.getApplicationContext()).getOtherCachePathStr() + LOG_FOLDER_NAME;
            if (!new File(fileFolderPath).exists()) {
                new File(fileFolderPath).mkdirs();
            }
            String filePath = fileFolderPath + File.separator + fileName;
            FileUtils.write(new File(filePath), result);
        } catch (Exception e) {
            LogUtils.e(e, "an error occurred while writing report file...");
        }
    }

    public static void e(String msg, Object... args) {
        Logger.e(msg, args);
    }

    public static void e(Throwable throwable, String msg, Object... args) {
        Logger.e(throwable, msg, args);
    }

    public static void e(Throwable throwable) {
        throwable.printStackTrace();
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
