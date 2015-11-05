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
import com.sunhz.projectutils.Constant;
import com.sunhz.projectutils.fixmemoryleakutils.FixInputMethodManagerLeak;

/**
 * Base activity
 * Created by Spencer (www.spencer-dev.com) on 15/2/3.
 */
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

        this.policy = new DefaultRetryPolicy(Constant.TimeInApplication.NET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        ActivityManager.addActivity(this);
    }

    /**
     * 添加请求到 volley 请求队列
     *
     * @param request volley request
     */
    @Override
    public void volleyAdd(Request request) {
        request.setRetryPolicy(policy);
        request.setTag(this);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FixInputMethodManagerLeak.fixInputMethodManagerLeak(this);
        volleyQueue.cancelAll(mContext);
        ActivityManager.removeActivity(this);
    }
}
