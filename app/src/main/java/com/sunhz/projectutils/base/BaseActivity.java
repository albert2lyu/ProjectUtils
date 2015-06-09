package com.sunhz.projectutils.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.sunhz.projectutils.ActivityManager;
import com.sunhz.projectutils.Constance;

public class BaseActivity extends FragmentActivity implements Base {

    protected Context mContext;
    protected Context mApplicationContext;
    protected Activity mActivity;

    protected RequestQueue volleyQueue;
    private RetryPolicy policy;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        this.mContext = this;
        this.mApplicationContext = getApplicationContext();
        this.mActivity = this;

        this.volleyQueue = Volley.newRequestQueue(mContext);

        this.policy = new DefaultRetryPolicy(Constance.TimeInApplication.NET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        ActivityManager.addActivity(this);
    }

    /**
     * 添加请求到volley队列中
     *
     * @param request
     */
    @Override
    public void volleyAdd(Request request) {
        request.setRetryPolicy(policy);
        volleyQueue.add(request);
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
        ActivityManager.removeActivity(this);
    }
}
