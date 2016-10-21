package com.xuwab.qiushitest.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.xuwab.qiushitest.util.SdCardHelper;

import java.io.File;

/**
 * Created by xuwab on 2016/10/20.
 */
public class FileCache {
    private static final String TAG = "xuwab debug";
    private static FileCache fileCache;
    private SdCardHelper helper;
    private File cache_dir;
    private FileCache(Context context){
        helper=new SdCardHelper(context);//helper负责对sdcard缓存目录下文件读写操作
        //判断sdcard是否可用
        if(helper.isExternalStorageMounted()){
            //sd卡的缓存目录
            cache_dir=context.getExternalCacheDir();
            Log.e(TAG,"sd卡的缓存目录:"+cache_dir.getAbsolutePath());
        }else{
            //内部的缓存目录
            cache_dir=context.getCacheDir();
        }
    }
    public static FileCache getFileCache(Context context){
        if(fileCache==null){
            fileCache=new FileCache(context);
        }
        return fileCache;
    }

    public Bitmap getBitmapFromFile(String imageUrl){
        File file=new File(cache_dir,getImageName(imageUrl));
        if(file.exists()){
            byte[] data=helper.readFromSdCard(file.getPath());
            if(data!=null){
                return   BitmapFactory.decodeByteArray(data,0,data.length);
            }
        }
        return null;
    }

    private String getImageName(String imageUrl) {
        int lastIndex=imageUrl.lastIndexOf("/");
        return imageUrl.substring(lastIndex+1);
    }


    public void putBitmapToFile(String imgUrl, byte[] bytes) {
        helper.saveByteToSdCardCacheDir(getImageName(imgUrl),bytes);

    }
}
