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
package com.sunhz.projectutils.imageutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理相关类
 * Created by Spencer (www.spencer-dev.com) on 15/2/20.
 */
public class ImageUtils {

    private ImageUtils() {

    }

    /**
     * 质量压缩
     *
     * @param image bitmap
     * @return 压缩后的bitmap
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 图片按比例大小压缩
     *
     * @param image （根据Bitmap图片压缩）
     * @return 压缩后的bitmap
     */
    public static Bitmap compressScale(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be; // 设置缩放比例
        // newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩

        //return bitmap;
    }

    /**
     * 图片按比例大小压缩
     *
     * @param srcPath （根据路径获取图片并压缩）
     * @return 压缩后的bitmap
     */
    public static Bitmap getimage(String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }


    /**
     * 将bitmap,按比例放大缩小
     *
     * @param bitmap  bitmap
     * @param density 如果density为0,则返回原图大小
     * @return 缩放后的bitmap
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float density) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap 为null");
        }
        if (density == 0) {
            density = 1.0f;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(density, density);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 将bitmap,按 width * height 缩放
     *
     * @param bitmap 将被缩放的图片
     * @param width  新bitmap的宽度
     * @param height 新bitmap的高度
     * @return 缩放后的bitmap
     */
    public static Bitmap scaledBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    /**
     * 将bitmap,按比例放大缩小
     *
     * @param drawable drawable
     * @param density  如果 density 为0,则返回原图大小
     * @return 缩放后的bitmap
     */
    public static Drawable scaledBitmap(Drawable drawable, float density) {
        return bitmapToDrawable(scaleBitmap(drawableToBitmap(drawable), density));
    }

    /**
     * 将drawable,按width * height 缩放
     *
     * @param drawable 将被缩放的图片
     * @param width    新drawable的宽
     * @param height   新drawable的高
     * @return 缩放后的bitmap
     */
    public static Drawable scaledDrawable(Drawable drawable, int width, int height) {
        return bitmapToDrawable(scaledBitmap(drawableToBitmap(drawable), width, height));
    }


    /**
     * byte数组 转换成 Drawable
     *
     * @param byteArray 字节数组
     * @return drawable
     */
    public static Drawable byteArrayToDrawable(byte[] byteArray) {
        ByteArrayInputStream ins = new ByteArrayInputStream(byteArray);
        return Drawable.createFromStream(ins, null);
    }

    /**
     * drawable 转换成 byte数组
     *
     * @param drawable drawable
     * @return 字节数组
     */
    public static byte[] drawableToByteArray(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }


    /**
     * bitmap 转换为 byte数组
     *
     * @param bitmap bitmap
     * @return 字节数组
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byte数组 转换为 bitmap
     *
     * @param byteArray 字节数组
     * @return bitmap
     */
    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


    /**
     * Bitmap 转换为 Drawable
     *
     * @param bitmap bitmap
     * @return Drawable
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        bd.setTargetDensity(bitmap.getDensity());
        return new BitmapDrawable(bitmap);
    }

    /**
     * Drawable 转换为 Bitmap
     *
     * @param drawable drawable
     * @return Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * bitmap 转换成 InputStream
     *
     * @param bitmap bitmap
     * @return InputStream
     */
    public static InputStream bitmapToInputStream(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * drawable 转换成 InputStream
     *
     * @param drawable drawable
     * @return 流
     */
    public static InputStream drawableToInputStream(Drawable drawable) {
        return bitmapToInputStream(drawableToBitmap(drawable));
    }

    /**
     * bitmap to file
     *
     * @param bitmap bitmap
     * @param file   文件存储路径
     * @throws IOException 保存失败
     */
    public static void bitmapToFile(Bitmap bitmap, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
        } finally {
            if (bufferedOutputStream != null)
                bufferedOutputStream.close();
            if (fileOutputStream != null)
                fileOutputStream.close();
        }
    }

    /**
     * drawable to file
     *
     * @param drawable drawable
     * @param file     文件存储路径
     * @throws IOException 保存失败
     */
    public static void drawableToFile(Drawable drawable, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        Bitmap bitmap = drawableToBitmap(drawable);
        bitmapToFile(bitmap, file);
    }


    /**
     * 将图片文件 转换成 bitmap
     *
     * @param filePath 文件路径
     * @return bitmap bitmap
     * @throws IOException 转换失败
     */
    public static Bitmap filtToBitmap(String filePath) throws IOException {
        InputStream is = null;
        try {
            is = new FileInputStream(new File(filePath));
            return BitmapFactory.decodeStream(is);
        } finally {
            is.close();
        }
    }

    /**
     * 将图片文件 转换成 drawable
     *
     * @param filePath 文件路径
     * @return drawable drawable
     * @throws IOException 转换失败
     */
    public static Drawable filtToDrawable(String filePath) throws IOException {
        InputStream is = null;
        try {
            is = new FileInputStream(new File(filePath));
            return BitmapDrawable.createFromStream(is, null);
        } finally {
            if (is != null)
                is.close();
        }
    }


    /**
     * drawable 转换成 圆角
     *
     * @param drawable drawable
     * @param pixels   圆角的弧度
     * @return 圆角Drawable
     */
    public static Drawable drawableToRoundCorner(Drawable drawable, int pixels) {
        return bitmapToDrawable(bitmapToRoundCorner(drawableToBitmap(drawable), pixels));

    }


    /**
     * 使圆角功能支持BitmapDrawable
     *
     * @param bitmapDrawable bitmapDrawable
     * @param pixels         圆角的弧度
     * @return 圆角bitmapDrawable
     */
    public static BitmapDrawable bitmapDrawableToRoundCorner(BitmapDrawable bitmapDrawable, int pixels) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmapDrawable = new BitmapDrawable(bitmapToRoundCorner(bitmap, pixels));
        return bitmapDrawable;
    }

    /**
     * 把bitmap变成圆角
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角bitmap
     */
    public static Bitmap bitmapToRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 获取资源图片
     *
     * @param mContext Context
     * @param resId    资源ID
     * @return bitmap
     */
    public static Bitmap getResourceBitmap(Context mContext, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = mContext.getApplicationContext().getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 获取资源图片
     *
     * @param mContext Context
     * @param resId    资源ID
     * @return drawable
     */
    public static Drawable getResourceDrawable(Context mContext, int resId) {
        return mContext.getApplicationContext().getResources().getDrawable(resId);
    }


    /**
     * 计算缩放比的inSampleSize
     *
     * @param options   options
     * @param reqWidth  将要缩放的宽
     * @param reqHeight 将要缩放的高
     * @return inSampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }


}
