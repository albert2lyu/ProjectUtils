package com.sunhz.projectutils;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

/**
 * activity 管理器
 * Created by Spencer on 15/2/3.
 */
public class ActivityManager {

    private static ArrayList<Activity> actList = new ArrayList<Activity>();

    /**
     * 关掉所有activity
     *
     * @param mContext
     */
    public static void closeAllActivity(Context mContext) {
        for (int i = 0; i < actList.size(); i++) {
            Activity activity = actList.get(i);
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public static void addActivity(Activity activity) {
        actList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        actList.remove(activity);
    }

    public static ArrayList<Activity> getAllActivity() {
        return actList;
    }

}