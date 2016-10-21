package com.xuwab.qiushitest.asyctask;

import android.content.Context;
import android.os.AsyncTask;


import com.xuwab.qiushitest.httputil.HttpUitls;

import java.io.UnsupportedEncodingException;

/**
 * Created by xuwab on 2016/8/10.
 */

public class DownLoadTask extends AsyncTask<String,Void,byte[]> {
    private Context context;
    private CallBackListener callback;

    public DownLoadTask(Context context,CallBackListener callback){
        this.context=context;
        this.callback=callback;

    }

    @Override
    protected byte[] doInBackground(String... params) {

        return HttpUitls.loadDataForHttp(params[0]);
    }

    @Override
    protected void onPostExecute(byte[] result) {
        super.onPostExecute(result);

        if(result!=null&&callback!=null){
            try {
                callback.onResponse(new String(result,"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


    }

    public static interface CallBackListener{
        public void onResponse(String string);
    }

}
