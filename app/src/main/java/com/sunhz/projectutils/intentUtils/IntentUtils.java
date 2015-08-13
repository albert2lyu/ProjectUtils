package com.sunhz.projectutils.intentUtils;

import android.content.Context;
import android.content.Intent;

/**
 * intentUtils 跳转 activity 的 utils
 * Created by Spencer on 15/2/20.
 */
public class IntentUtils {

    /**
     * 快捷跳转 activity
     *
     * @param mContext Context
     * @param clazz    要跳转到的 activity class对象
     */
    public synchronized static void startActivity(Context mContext, Class clazz) {
        Intent intent = new Intent(mContext.getApplicationContext(), clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 快捷跳转 activity 并携带参数
     *
     * @param mContext    Context
     * @param clazz       要跳转到的 activity class对象
     * @param getArgument 传参 call back
     */
    public synchronized static void startActivityCarryArgments(Context mContext, Class clazz, GetArgument getArgument) {
        Intent intent = new Intent(mContext.getApplicationContext(), clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getArgument.getArgument(intent);
        mContext.startActivity(intent);
    }

    /**
     * 快捷跳转 activity,清空 activity 栈
     *
     * @param mContext Context
     * @param clazz    要跳转到的 activity class对象
     */
    public synchronized static void startActivityClearTask(Context mContext, Class clazz) {
        Intent intent = new Intent(mContext.getApplicationContext(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 快捷跳转 activity 并携带参数,清空 activity 栈
     *
     * @param mContext    Context
     * @param clazz       要跳转到的 activity class对象
     * @param getArgument 传参 call back
     */
    public synchronized static void startActivityClearTaskCarryArgments(Context mContext, Class clazz, GetArgument getArgument) {
        Intent intent = new Intent(mContext.getApplicationContext(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getArgument.getArgument(intent);
        mContext.startActivity(intent);
    }

    public interface GetArgument {
        void getArgument(Intent intent);
    }

}
