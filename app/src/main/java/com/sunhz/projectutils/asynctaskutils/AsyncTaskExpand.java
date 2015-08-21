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
package com.sunhz.projectutils.asynctaskutils;

import android.os.AsyncTask;

/**
 * Expand AsyncTask , Random control thread Parallel Serial
 * Created by Spencer (www.spencer-dev.com) on 15/2/3.
 */
public abstract class AsyncTaskExpand<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    /**
     * Parallel
     */
    public static final boolean PARALLEL = true;
    /**
     * Serial
     */
    public static final boolean SERIAL = false;
    private boolean flag = SERIAL;

    /**
     * control thread Parallel or Serial
     * @param flag AsyncTaskExpend.PARALLEL or AsyncTaskExpend.SERIAL
     */
    public AsyncTaskExpand(boolean flag) {
        super();
        this.flag = flag;
    }


    public AsyncTask<Params, Progress, Result> executeExpand(Params... params) {
        if (flag) {
            return super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            return super.execute(params);
        }
    }


}
