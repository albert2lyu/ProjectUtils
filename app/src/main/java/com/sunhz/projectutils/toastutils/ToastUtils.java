package com.sunhz.projectutils.toastutils;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * toastUtils , 在UI线程中显示toast和非UI线程中显示toast 
 * Created by Spencer on 15/2/21.
 */

public class ToastUtils {

    /**
     * 描述：Toast提示文本.
     *
     * @param text 文本
     */
    public static void showToast(Context mContext, String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(mContext.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 描述：Toast提示文本.
     *
     * @param resId 文本的资源ID
     */
    public static void showToast(Context mContext, int resId) {
        Toast.makeText(mContext.getApplicationContext(), mContext.getApplicationContext().getResources().getText(resId),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 描述：在线程中提示文本信息.
     *
     * @param resId 要提示的字符串资源ID
     */
    public static void showToastInThread(final Activity mContext, final int resId) {
        mContext.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                showToast(mContext, resId);
            }
        });
    }

    /**
     * 描述：在线程中提示文本信息.
     *
     * @param text
     */
    public static void showToastInThread(final Activity mContext, final String text) {
        mContext.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                showToast(mContext, text);
            }
        });
    }

}
