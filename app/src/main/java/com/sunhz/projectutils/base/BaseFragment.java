package com.sunhz.projectutils.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.sunhz.projectutils.Constance;

public class BaseFragment extends Fragment implements Base {

    protected Context mContext;
    protected Context mApplicationContext;
    protected Activity mActivity;
    protected RequestQueue volleyQueue = Volley.newRequestQueue(mContext);
    private RetryPolicy policy = new DefaultRetryPolicy(Constance.TimeInApplication.NET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mContext = activity.getBaseContext();
        mApplicationContext = mContext.getApplicationContext();
        mActivity = activity;

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
    public void onStop() {
        super.onStop();
        volleyQueue.cancelAll(mContext);
    }

}
