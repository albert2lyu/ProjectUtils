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
package com.sunhz.projectutils.imageutils;

import android.content.Context;
import android.content.res.Resources;
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
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Image Processing tool
 * Created by Spencer (www.spencer-dev.com) on 15/2/20.
 */
public class ImageUtils {

    private ImageUtils() {

    }

    /**
     * Picture quality compression
     *
     * @param bitmap bitmap
     * @return Quality bitmap
     */
    public static Bitmap compressImage(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap result = BitmapFactory.decodeStream(isBm, null, null);
        return result;
    }

    /**
     *
     * Photo compression ratio of the size
     *
     * @param image bitmap
     * @param height image height
     * @param width image width
     *              @param config bitmap config
     * @return Compressed bitmap
     */
    public static Bitmap compressScale(Bitmap image, float height, float width,Bitmap.Config config) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        float hh = height;
        float ww = width;

        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        newOpts.inPreferredConfig = config;

        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

        return compressImage(bitmap);
    }


    /**
     * The bitmap, in proportion to zoom
     *
     * @param bitmap  bitmap
     * @param density If the density is 0, then return the original image size
     * @return Bitmap scaled
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float density) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap can not null");
        }
        if (density == 0) {
            density = 1.0f;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(density, density);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * The bitmap, by scaling width * height
     *
     * @param bitmap bitmap
     * @param width  new bitmap width
     * @param height new bitmap height
     * @return Bitmap scaled
     */
    public static Bitmap scaledBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    /**
     * drawable, in proportion to zoom
     *
     * @param drawable drawable
     * @param density  If the density is 0, then return the original image size
     * @return drawable scaled
     */
    public static Drawable scaledDrawable(Drawable drawable, float density) {
        return bitmapToDrawable(scaleBitmap(drawableToBitmap(drawable), density));
    }

    /**
     * The drawable, by scaling width * height
     *
     * @param drawable drawable
     * @param width    new drawable width
     * @param height   new drawable height
     * @return drawable scaled
     */
    public static Drawable scaledDrawable(Drawable drawable, int width, int height) {
        return bitmapToDrawable(scaledBitmap(drawableToBitmap(drawable), width, height));
    }


    /**
     * byte array converter to drawable
     *
     * @param byteArray byte array
     * @return drawable
     */
    public static Drawable byteArrayToDrawable(byte[] byteArray) {
        ByteArrayInputStream ins = new ByteArrayInputStream(byteArray);
        return Drawable.createFromStream(ins, null);
    }

    /**
     * drawable converter to byte array
     *
     * @param drawable drawable
     * @return byte array
     */
    public static byte[] drawableToByteArray(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }


    /**
     * bitmap converter to byte array
     *
     * @param bitmap bitmap
     * @return byte array
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byte array converter to bitmap
     *
     * @param byteArray byte array
     * @return bitmap
     */
    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


    /**
     * Bitmap converter to Drawable
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
     * Drawable converter to Bitmap
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
     * bitmap converter to  InputStream
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
     * drawable converter to InputStream
     *
     * @param drawable drawable
     * @return inputStream
     */
    public static InputStream drawableToInputStream(Drawable drawable) {
        return bitmapToInputStream(drawableToBitmap(drawable));
    }

    /**
     * bitmap converter to file
     *
     * @param bitmap bitmap
     * @param file   file
     * @throws IOException save failure
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
     * @param file     file
     * @throws IOException save failure
     */
    public static void drawableToFile(Drawable drawable, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        Bitmap bitmap = drawableToBitmap(drawable);
        bitmapToFile(bitmap, file);
    }


    /**
     * image file converter to bitmap
     *
     * @param filePath file path
     * @return bitmap bitmap
     * @throws IOException converter failure
     */
    public static Bitmap fileToBitmap(String filePath) throws IOException {
        InputStream is = null;
        try {
            is = new FileInputStream(new File(filePath));
            return BitmapFactory.decodeStream(is);
        } finally {
            is.close();
        }
    }

    /**
     * image file converter to drawable
     *
     * @param filePath file path
     * @return drawable drawable
     * @throws IOException save failure
     */
    public static Drawable fileToDrawable(String filePath) throws IOException {
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
     * drawable converter to RoundCorner drawable
     *
     * @param drawable drawable
     * @param pixels   Rounded radian
     * @return Rounded Drawable
     */
    public static Drawable drawableToRoundCorner(Drawable drawable, int pixels) {
        return bitmapToDrawable(bitmapToRoundCorner(drawableToBitmap(drawable), pixels));

    }


    /**
     * Make fillet feature supports BitmapDrawable
     *
     * @param bitmapDrawable bitmapDrawable
     * @param pixels         rounded radian
     * @return rounded bitmapDrawable
     */
    public static BitmapDrawable bitmapDrawableToRoundCorner(BitmapDrawable bitmapDrawable, int pixels) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmapDrawable = new BitmapDrawable(bitmapToRoundCorner(bitmap, pixels));
        return bitmapDrawable;
    }

    /**
     * bitmap converter to RoundCorner bitmap
     *
     * @param bitmap bitmap
     * @param pixels rounded radian
     * @return rounded bitmap
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
     * get resource bitmap
     *
     * @param mContext Context
     * @param resId    resource ID
     * @return bitmap
     */
    public static Bitmap getResourceBitmap(Context mContext, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = mContext.getApplicationContext().getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * get resource drawable
     *
     * @param mContext Context
     * @param resId    resource ID
     * @return drawable
     */
    public static Drawable getResourceDrawable(Context mContext, int resId) {
        return mContext.getApplicationContext().getResources().getDrawable(resId);
    }


    /**
     * Calculate zoom rate of inSampleSize
     *
     * @param options   options
     * @param reqWidth  Will be scaled width
     * @param reqHeight Will be scaled height
     * @return inSampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static void recycleImageViewBitMap(ImageView imageView) {
        if (imageView != null) {
            BitmapDrawable bd = (BitmapDrawable) imageView.getDrawable();
            recycleBitmapDrawable(bd);
        }
    }

    public static void recycleBitmapDrawable(BitmapDrawable bitmapDrawable) {
        if (bitmapDrawable != null) {
            Bitmap bitmap = bitmapDrawable.getBitmap();
            recycleBitmap(bitmap);
        }
        bitmapDrawable = null;
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    public static void recycleBackgroundBitMap(ImageView view) {
        if (view != null) {
            BitmapDrawable bd = (BitmapDrawable) view.getBackground();
            recycleBitmapDrawable(bd);
        }
    }

}
