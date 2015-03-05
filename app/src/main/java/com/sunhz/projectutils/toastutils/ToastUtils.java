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
     * 显示Toast.
     */
    public static final int SHOW_TOAST = 0;

    /**
     * 描述：Toast提示文本.
     *
     * @param text 文本
     */
    public static void showToast(Context context, String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 描述：Toast提示文本.
     *
     * @param resId 文本的资源ID
     */
    public static void showToast(Context context, int resId) {
        Toast.makeText(context, context.getResources().getText(resId),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 描述：在线程中提示文本信息.
     *
     * @param resId 要提示的字符串资源ID
     */
    public static void showToastInThread(final Activity context, final int resId) {
        context.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                showToast(context, resId);
            }
        });
    }

    /**
     * 描述：在线程中提示文本信息.
     * @param context
     * @param text
     */
    public static void showToastInThread(final Activity context,
                                         final String text) {
        context.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                showToast(context, text);
            }
        });
    }

}
