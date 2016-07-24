package com.hawk.gank.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    /**
     * 保存图片
     *
     * @param bm
     * @param dir
     * @param picName
     */
    public static boolean saveBitmap(Bitmap bm, String dir, String picName) {
        try {
            if (!FileUtil.checkDirectoryExists(dir)) {
                FileUtil.createDirectory(dir);
            }
            File file = FileUtil.createFile(dir, picName);

            if (file == null) {
                Log.e("test", "创建图片文件失败");
                return false;
            }
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e("", "保存图片文件成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取缩略图片
     *
     * @param path
     */
    public static Bitmap revitionImageSize(String path) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        options.inSampleSize = 5;
        float imagew = 150;
        float imageh = 150;
        int yRatio = (int) Math.ceil(options.outHeight
                / imageh);
        int xRatio = (int) Math
                .ceil(options.outWidth / imagew);

        if (yRatio > 1 || xRatio > 1) {
            if (yRatio > xRatio) {
                options.inSampleSize = yRatio;
            } else {
                options.inSampleSize = xRatio;
            }

        }
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);

        return bitmap;
    }

    /**
     * 保存缩略图片
     *
     * @param originBitmap
     */
    public static void saveCompressBitmap(Bitmap originBitmap, String path) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int options = 100;
        // Store the bitmap into output stream(no compress)
        originBitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while (os.toByteArray().length / 1024 > 128) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            originBitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
        }

        try {
            FileOutputStream out = new FileOutputStream(path);
            out.write(os.toByteArray());
            out.flush();
            out.close();
            Log.e("", "保存图片文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 获取缩略图片
     *
     * @param filePath
     */
    public static Bitmap getBitmapByPath(String filePath) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, opts);

            opts.inSampleSize = ImageUtil.computeSampleSize(
                    opts, -1, 1200 * 1200);
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(filePath, opts);
            Log.e("", "获取图片文件成功");
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 获取缩略图片
     *
     * @param datas
     */
    public static Bitmap getBitmapByBytes(byte[] datas) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(datas, 0, datas.length, opts);

            opts.inSampleSize = ImageUtil.computeSampleSize(
                    opts, -1, 1200 * 1200);
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeByteArray(datas, 0 , datas.length, opts);
            Log.e("", "获取图片文件成功");
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 获取图片
     *
     * @param filePath
     * @param opts
     */
    public static Bitmap getOriginBitmapByPath(String filePath,
                                         BitmapFactory.Options opts) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
            Log.e("", "获取图片文件成功");
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
        }
        return bitmap;
    }
    /**
     * 获取图片角度
     *
     * @param path
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    /**
     * 旋转图片角度
     *
     * @param angle
     * @param bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        angle = angle % 360;
        if (angle != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);

            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return resizedBitmap;
        } else {
            return bitmap;
        }
    }

}
