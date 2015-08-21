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
package com.sunhz.projectutils.toastutils;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * toastUtils
 * Created by Spencer (www.spencer-dev.com) on 15/2/21.
 */

public class ToastUtils {

    private ToastUtils() {

    }

    /**
     * show toast
     *
     * @param mContext Context
     * @param text     text
     */
    public static void showToast(Context mContext, String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(mContext.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * show toast
     *
     * @param mContext Context
     * @param resId    text resource id
     */
    public static void showToast(Context mContext, int resId) {
        Toast.makeText(mContext.getApplicationContext(), mContext.getApplicationContext().getResources().getText(resId),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * show toast on sub-thread
     *
     * @param mActivity Activity
     * @param resId     text resource id
     */
    public static void showToastInThread(final Activity mActivity, final int resId) {
        mActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                showToast(mActivity, resId);
            }
        });
    }

    /**
     * show toast on sub-thread
     *
     * @param mActivity Activity
     * @param text      text
     */
    public static void showToastInThread(final Activity mActivity, final String text) {
        mActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                showToast(mActivity, text);
            }
        });
    }

}
