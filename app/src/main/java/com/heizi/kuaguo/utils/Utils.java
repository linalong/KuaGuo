package com.heizi.kuaguo.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.heizi.kuaguo.Constants;
import com.heizi.kuaguo.UserModel;
import com.heizi.kuaguo.block.login.ActLogin;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by leo on 17/6/27.
 */

public class Utils {

    public static boolean checkLogin(Context mContext, UserModel userModel) {
        if (userModel != null)
            return true;
        else {
            Intent intent = new Intent();
            intent.setClass(mContext, ActLogin.class);
            mContext.startActivity(intent);
            return false;
        }
    }


    /**
     * 下载一个图片为直角的图片
     *
     * @param url
     * @param imageView
     */
    public static void downloadIcon2View(String url, ImageView imageView,
                                         int emptyResId, int onFailResId) {
        ImageLoader.getInstance().displayImage(
                url,
                imageView,
                getImageLoaderOptions(false, emptyResId,
                        onFailResId));
    }

    /**
     * 下载一个图片为圆角形
     *
     * @param url
     * @param imageView
     */
    public static void downloadIcon2ViewRound(String url, ImageView imageView,
                                              int emptyResId, int onFailResId) {
        ImageLoader.getInstance().displayImage(
                url,
                imageView,
                getImageLoaderOptions(true, emptyResId,
                        onFailResId));
    }

    /**
     * 下载一个图片格式为纯圆形的
     *
     * @param url
     * @param imageView
     */
    public static void downloadIcon2ViewCircle(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView,
                getImageLoaderOptionsOfCircle());
    }

    public static DisplayImageOptions getImageLoaderOptions(boolean isRound, int emptyResId, int onFailResId) {
        int circleValue = 0;
        if (isRound) {
            circleValue = 20;
        }
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(circleValue)).
                        showImageForEmptyUri(emptyResId).
                        showImageOnFail(onFailResId).build();
        return options;
    }

    public static DisplayImageOptions getImageLoaderOptionsOfCircle() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true)
                .displayer(new CircleBitmapDisplayer()).build();
        return options;
    }

    /**
     * uri 转换成真是路径
     *
     * @param contentUri
     * @return
     */
    public static String getRealPathFromURI(Intent data, Uri contentUri, Context context) {
        String res = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                ;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (TextUtils.isEmpty(res)) {
                Object object = data.getExtras().get("data");
                if (null != object && object instanceof Bitmap) {
                    Bitmap bitmap = (Bitmap) object;
                    String folder = Constants.FOLDER_IMAGE;
                    String fileName = System.currentTimeMillis() + ".jpg";
                    saveBitmap(folder, fileName, bitmap);
                    res = folder + fileName;
                }
            }
        }
        return res;
    }

    public static void saveBitmap(String folder, String picName, Bitmap bitmap) {
        File f = new File(folder, picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 动态设置gridView的高度
     *
     * @param j 列数
     */
    public static void setgridViewHeightBasedOnChildren(GridView gridView, int j) {
        // 获取GridView的adapter
        ListAdapter gridAdapter = gridView.getAdapter();
        if (gridAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = j;
        //int col = gridView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < gridAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = gridAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        // 获取listview的布局参数
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        gridView.setLayoutParams(params);
    }

}
