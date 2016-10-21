package com.xuwab.qiushitest.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by xuwab on 2016/10/20.
 */
public class ImageCache {
    private static ImageCache imageCache;
    public LruCache<String,Bitmap> lruCache;
    private ImageCache(){
        int memorySize= (int) Runtime.getRuntime().maxMemory();
        int maxSize=memorySize/8;
        //创建缓存对象
        lruCache=new LruCache<String,Bitmap>(maxSize){
            @Override //计算出每张图片所占空间大小
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getHeight()*bitmap.getRowBytes();
            }
        };

    }
    public static ImageCache getInstance(){
        if(imageCache==null){
            imageCache=new ImageCache();
        }
        return imageCache;
    }

    public void putBitmap(String url,Bitmap bitmap){
        lruCache.put(url,bitmap);
    }
    public Bitmap getBitmap(String url){
        return  lruCache.get(url);
    }
}
