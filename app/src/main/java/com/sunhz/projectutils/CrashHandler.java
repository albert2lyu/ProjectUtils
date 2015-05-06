package com.sunhz.projectutils;


import android.content.Context;
import android.os.Looper;

import com.sunhz.projectutils.logutils.LogUtils;
import com.sunhz.projectutils.toastutils.ToastUtils;

/**
 * 用于程序异常时处理,保存错误日志等
 * Created by Spencer on 15/2/3.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context mContext;
    private static CrashHandler crashHandler;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler(Context mContext) {
        this.mContext = mContext;
    }

    public static CrashHandler getInstance(Context mContext) {
        if (crashHandler == null) {
            crashHandler = new CrashHandler(mContext);
        }
        return crashHandler;
    }

    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {

            // 如果不等待,程序出错的toat没办法弹出来
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        LogUtils.writeExceptionLog(mContext, ex);
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtils.showToast(mContext, R.string.application_crash_alter_text);
                Looper.loop();
                // 关闭所有activity
                ActivityManager.closeAllActivity(mContext);
            }

        }.start();
        return true;
    }

}