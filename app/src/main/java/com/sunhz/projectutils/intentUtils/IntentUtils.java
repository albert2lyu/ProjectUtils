package com.sunhz.projectutils.intentUtils;

import android.content.Context;
import android.content.Intent;

/**
 * intentUtils 跳转act的utils
 * Created by Spencer on 15/2/20.
 */
public class IntentUtils {
    public static void startActivity(Context mContext, Class clazz) {
        Intent intent = new Intent(mContext, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
