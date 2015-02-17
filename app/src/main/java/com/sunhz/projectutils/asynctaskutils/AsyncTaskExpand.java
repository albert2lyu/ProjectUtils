package com.sunhz.projectutils.asynctaskutils;

import android.os.AsyncTask;

/**
 * 拓展的AsyncTask,随意控制线程并串行
 * Created by Spencer on 15/2/3.
 */
public abstract class AsyncTaskExpand<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private boolean flag = SERIAL;

    /**
     * 并行
     */
    public static final boolean PARALLEL = true;

    /**
     * 串行
     */
    public static final boolean SERIAL = false;

    /**
     * 控制是否并行
     *
     * @param flag AsyncTaskExpend.PARALLEL or AsyncTaskExpend.SERIAL
     */
    public AsyncTaskExpand(boolean flag) {
        super();
        this.flag = flag;
    }


    /**
     * 在AsyncTask基础进行了封装
     *
     * @param params
     * @return
     */
    public AsyncTask<Params, Progress, Result> executeExpand(Params... params) {
        if (flag) {
            return super.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            return super.execute(params);
        }
    }


}
