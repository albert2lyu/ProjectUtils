package com.sunhz.projectutils.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    protected Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;



    }

}
