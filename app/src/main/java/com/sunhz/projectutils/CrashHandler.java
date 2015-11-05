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
package com.sunhz.projectutils;


import android.content.Context;
import android.os.Looper;

import com.sunhz.projectutils.logutils.LogUtils;
import com.sunhz.projectutils.toastutils.ToastUtils;

/**
 * Program for exception handling, save error logs
 * Created by Spencer (www.spencer-dev.com) on 15/2/3.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler crashHandler;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler(Context mContext) {
        this.mContext = mContext;
    }

    public static CrashHandler getInstance(Context mContext) {
        if (crashHandler == null) {
            crashHandler = new CrashHandler(mContext.getApplicationContext());
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

            // 如果不等待,程序出错的toast没办法弹出来
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

            }
            ActivityManager.closeAllActivity();
        }
    }

    /**
     * Custom error handling, error information is sent to collect bug reports and other operations are completed here.
     * Developers can customize according to their own circumstances exception handling logic
     *
     * @param ex
     * @return true : Handles the exception information , false : Do not handle the exception information
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
                // close all activity
                ActivityManager.closeAllActivity();
            }

        }.start();
        return true;
    }

}