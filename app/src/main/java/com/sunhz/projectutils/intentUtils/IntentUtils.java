package com.sunhz.projectutils.intentUtils;

import android.content.Context;
import android.content.Intent;

/**
 * intentUtils 跳转act的utils
 * Created by Spencer on 15/2/20.
 */
public class IntentUtils {

    /**
     * 快捷跳转activity
     *
     * @param mContext
     * @param clazz
     */
    public synchronized static void startActivity(Context mContext, Class clazz) {
        Intent intent = new Intent(mContext, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 快捷跳转activity并携带参数
     *
     * @param mContext
     * @param clazz
     * @param getArgment
     */
    public synchronized static void startActivityCarryArgments(Context mContext, Class clazz, GetArgment getArgment) {
        Intent intent = new Intent(mContext, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getArgment.getArgment(intent);
        mContext.startActivity(intent);
    }

    /**
     * 快捷跳转activity,清空activity栈
     *
     * @param mContext
     * @param clazz
     */
    public synchronized static void startActivityClearTask(Context mContext, Class clazz) {
        Intent intent = new Intent(mContext, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 快捷跳转activity并携带参数,清空activity栈
     */
    public synchronized static void startActivityClearTaskCarryArgments(Context mContext, Class clazz, GetArgment getArgment) {
        Intent intent = new Intent(mContext, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getArgment.getArgment(intent);
        mContext.startActivity(intent);
    }

    public interface GetArgment {
        void getArgment(Intent intent);
    }

}
