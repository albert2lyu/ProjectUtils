/**
 * Copyright (c) 2015, Spencer 给立乐 (www.spencer-dev.com).
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
package com.sunhz.projectutils.viewutils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * 像素相关操作累
 * Created by Spencer (www.spencer-dev.com) on 15/2/3.
 */
public class DensityUtils {

    private DensityUtils() {

    }

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
