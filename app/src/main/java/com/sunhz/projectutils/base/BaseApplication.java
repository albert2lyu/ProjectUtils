package com.sunhz.projectutils.base;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.sunhz.projectutils.Constance;
import com.sunhz.projectutils.CrashHandler;

public class BaseApplication extends Application {

    protected Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;


        if (!Constance.IS_DEBUG) { // 正式环境
            CrashHandler.getInstance(mContext).init();
            Logger.init().setLogLevel(LogLevel.NONE);
        } else { // 开发环境
            Logger.init().setLogLevel(LogLevel.FULL);
        }
    }

}
