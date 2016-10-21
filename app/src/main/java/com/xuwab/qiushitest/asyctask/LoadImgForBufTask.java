package com.xuwab.qiushitest.asyctask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.xuwab.qiushitest.cache.FileCache;
import com.xuwab.qiushitest.cache.ImageCache;
import com.xuwab.qiushitest.httputil.HttpUitls;


/**
 * Created by robin on 2016/10/20.
 * 根据url可获得bitmap
 */
public class LoadImgForBufTask  {
    private ImageCache imageCache;
    private FileCache fileCache;
    private Context contex;


    public LoadImgForBufTask(Context context){
        this.contex=context;
        imageCache=ImageCache.getInstance();
        fileCache=FileCache.getFileCache(context);

    }

    public Bitmap loadImage(ImageView imageView,String url){

        //1.从内存缓存中找
        Bitmap bitmap=imageCache.getBitmap(url);
        if(bitmap!=null){
            return bitmap;
        }

        //2.从文件缓存中找
        bitmap=fileCache.getBitmapFromFile(url);
        if(bitmap!=null){
            imageCache.putBitmap(url,bitmap);
            return bitmap;
        }

        //3.从网络加载
        new DownloadImageTask(imageView).execute(url);
        return null;
    }

    class DownloadImageTask extends AsyncTask<String,Void,byte[]>{

        private String imgUrl;
        private ImageView imageView;
        public DownloadImageTask(ImageView imageView){
           this.imageView=imageView;
        }

        @Override
        protected byte[] doInBackground(String... params) {
            imgUrl=params[0];
            return HttpUitls.loadDataForHttp(imgUrl);
        }

        @Override
        protected void onPostExecute(byte[] result) {
            super.onPostExecute(result);
            if(result!=null){
                Bitmap bitmap= BitmapFactory.decodeByteArray(result, 0, result.length);
                if(imgUrl!=null&&bitmap!=null){
                    //1.保存到内存缓存
                    imageCache.putBitmap(imgUrl,bitmap);
                    //2.保存到文件缓存
                    fileCache.putBitmapToFile(imgUrl,result);
                }
                if(imageView.getTag()!=null&&imgUrl.equals(imageView.getTag())){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }


    }





}
