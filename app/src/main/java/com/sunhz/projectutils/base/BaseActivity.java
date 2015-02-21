package com.sunhz.projectutils.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class BaseActivity extends FragmentActivity {

    protected Context mContext;
    protected RequestQueue volleyQueue;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        this.volleyQueue = Volley.newRequestQueue(mContext);
        BaseApplication.getInstance().addActivity(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        volleyQueue.cancelAll(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().removeActivity(this);
    }
}
