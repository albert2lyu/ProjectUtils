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
package com.sunhz.projectutils.viewutils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * Pixel operations tool
 * Created by Spencer (www.spencer-dev.com) on 15/2/3.
 */
public class DensityUtils {

    private DensityUtils() {

    }

    /**
     * dp converter to px
     *
     * @param mContext Context
     * @param dpValue  before computing dp
     * @return after computing px
     */
    public static int dip2px(Context mContext, float dpValue) {
        float scale = mContext.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px converter to dp
     *
     * @param mContext Context
     * @param pxValue  before computing px
     * @return after computing dp
     */
    public static int px2dip(Context mContext, float pxValue) {
        float scale = mContext.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px converter to sp, To ensure that the same text size
     *
     * @param mContext Context
     * @param pxValue   before computing px
     * @return after computing sp
     */
    public static int px2sp(Context mContext, float pxValue) {
        float fontScale = mContext.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp converter to px, To ensure that the same text size
     *
     * @param mContext Context
     * @param spValue  before computing sp
     * @return after computing px
     */
    public static int sp2px(Context mContext, float spValue) {
        float fontScale = mContext.getApplicationContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * Gets the screen width and height, the unit is px
     *
     * @param mContext Context
     * @return point.x : width ,point.y : height
     */
    public static Point getScreenMetrics(Context mContext) {
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);

    }

    /**
     * Get screen aspect ratio
     *
     * @param mContext Context
     * @return screen aspect ratio
     */
    public static float getScreenRate(Context mContext) {
        Point P = getScreenMetrics(mContext.getApplicationContext());
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

}
