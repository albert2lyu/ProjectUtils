package com.sunhz.projectutils.viewutils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class DensityUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param mContext Context
     * @param dpValue  需要计算的dp值
     * @return 计算完成后的px值
     */
    public static int dip2px(Context mContext, float dpValue) {
        float scale = mContext.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param mContext Context
     * @param pxValue  需要计算的px值
     * @return 计算完成后的dp值
     */
    public static int px2dip(Context mContext, float pxValue) {
        float scale = mContext.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * * 将px值转换为sp值，保证文字大小不变
     *
     * @param mContext Context
     * @param pxValue  需要计算的px值
     * @return 计算完成后的sp值
     */
    public static int px2sp(Context mContext, float pxValue) {
        float fontScale = mContext.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param mContext Context
     * @param spValue  需要计算的sp值
     * @return 计算完成后的px值
     */
    public static int sp2px(Context mContext, float spValue) {
        float fontScale = mContext.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽度和高度，单位为px
     *
     * @param mContext Context
     * @return point.x : 宽度,point.y : 高度
     */
    public static Point getScreenMetrics(Context mContext) {
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);

    }

    /**
     * 获取屏幕长宽比
     *
     * @param mContext Context
     * @return 长宽比
     */
    public static float getScreenRate(Context mContext) {
        Point P = getScreenMetrics(mContext.getApplicationContext());
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

}
