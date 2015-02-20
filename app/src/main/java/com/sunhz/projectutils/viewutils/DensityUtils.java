package com.sunhz.projectutils.viewutils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class DensityUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context mContext, float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context mContext, float pxValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param mContext
     * @param pxValue  （DisplayMetrics类中属性scaledDensity）
     */
    public static int px2sp(Context mContext, float pxValue) {
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param mContext
     * @param spValue  （DisplayMetrics类中属性scaledDensity）
     */
    public static int sp2px(Context mContext, float spValue) {
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽度和高度，单位为px
     *
     * @param mContext
     * @return point.x : 宽度,point.y : 高度
     */
    public static Point getScreenMetrics(Context mContext) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);

    }

    /**
     * 获取屏幕长宽比
     *
     * @param mContext
     * @return
     */
    public static float getScreenRate(Context mContext) {
        Point P = getScreenMetrics(mContext);
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

}
