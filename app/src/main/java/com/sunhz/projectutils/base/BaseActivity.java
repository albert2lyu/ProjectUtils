package com.sunhz.projectutils.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.sunhz.projectutils.AppController;

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

    /**
     * 添加请求到volley队列中
     * @param request
     */
    public void volleyAdd(Request request) {
        int socketTimeout = AppController.NET_TIMEOUT;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
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
        BaseApplication.getInstance().removeActivity(this);
    }
}
