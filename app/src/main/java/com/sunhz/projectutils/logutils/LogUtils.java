package com.sunhz.projectutils.logutils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.sunhz.projectutils.DebugController;
import com.sunhz.projectutils.fileutils.FileUtils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class LogUtils {

    private static Context mContext;

    private LogUtils() {
    }

    /**
     * 初始化log管理器
     */
    public static void init(Context context) {
        mContext = context;
    }

    private static void writeExceptionLog(Throwable ex) {
        if (mContext == null) {
            return;
        }
        writeExceptionLog(mContext, ex);
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
            String fileFolderPath = Environment.getExternalStorageDirectory() + File.separator + mContext.getPackageName() + File.separator + "log";
            if (!new File(fileFolderPath).exists()) {
                new File(fileFolderPath).mkdirs();
            }
            String filePath = fileFolderPath + File.separator + fileName;
            FileUtils.getInstance().write(new File(filePath), result);
        } catch (Exception e) {
            Log.e("", "an error occured while writing report file...", e);
        }
    }

    public static void e(Context mcontext, String errorMsg) {
        if (!DebugController.isDebug) {
            return;
        }
        e(getTag(mcontext), errorMsg);
    }

    public static void e(String tag, String errorMsg) {
        if (!DebugController.isDebug) {
            return;
        }
        Log.e(tag, errorMsg);
    }

    public static void d(Context mcontext, String errorMsg) {
        if (!DebugController.isDebug) {
            return;
        }
        d(getTag(mcontext), errorMsg);
    }

    public static void d(String tag, String errorMsg) {
        if (!DebugController.isDebug) {
            return;
        }
        Log.d(tag, errorMsg);
    }

    public static void w(Context mcontext, String errorMsg) {
        if (!DebugController.isDebug) {
            return;
        }
        w(getTag(mcontext), errorMsg);
    }

    public static void w(String tag, String errorMsg) {
        if (!DebugController.isDebug) {
            return;
        }
        Log.w(tag, errorMsg);
    }

    public static void w(String tag, String errorMsg, Throwable throwable) {

        writeExceptionLog(throwable);
        if (!DebugController.isDebug) {
            return;
        }
        w(tag, errorMsg);
    }

    public static void i(Context mcontext, String errorMsg) {
        if (!DebugController.isDebug) {
            return;
        }
        i(getTag(mcontext), errorMsg);
    }

    public static void i(String tag, String errorMsg) {
        if (!DebugController.isDebug) {
            return;
        }
        Log.i(tag, errorMsg);
    }

    public static void v(Context mcontext, String errorMsg) {
        if (!DebugController.isDebug) {
            return;
        }
        v(getTag(mcontext), errorMsg);
    }

    public static void v(String tag, String errorMsg) {
        if (!DebugController.isDebug) {
            return;
        }
        Log.v(tag, errorMsg);
    }

    private static String getTag(Context mcontext) {

        return mcontext != null ? mcontext.getClass().getSimpleName() : LogUtils.class.getSimpleName();
    }
}
