package com.xuwab.qiushitest.qiushi.zhuangxiang;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuwab.qiushitest.R;
import com.xuwab.qiushitest.asyctask.LoadImgForBufTask;
import com.xuwab.qiushitest.bean.SpecialInfo;

import java.util.List;

/**
 * Created by naf on 2016/10/21.
 */

public class ZhuangxiangAdater extends RecyclerView.Adapter<ZhuangxiangAdater.MyViewHolder> {
    private Context context;
    private List<SpecialInfo> specialInfoList;
    private LoadImgForBufTask loadImage;

    public ZhuangxiangAdater(Context context, List<SpecialInfo> specialInfoList) {
        this.context = context;
        this.specialInfoList = specialInfoList;
        this.loadImage=new LoadImgForBufTask(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.listview_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SpecialInfo specialInfo = specialInfoList.get(position);
        holder.imageView_content.setTag(specialInfo.getUserHeaderUrl());
        holder.imageView_content.setTag(specialInfo.getContent_image());


        //Show datas
        //1.Show icon

        String headerImagUrl = specialInfoList.get(position).getUserHeaderUrl();
        if ("".equals(headerImagUrl)||headerImagUrl==null) {
            holder.imageView_header.setImageResource(R.drawable.default_anonymous_users_avatar);
        } else {
            Bitmap bitmap= loadImage.loadImage(holder.imageView_header, headerImagUrl);
            if(bitmap!=null){
                holder.imageView_header.setImageBitmap(bitmap);
            }
        }


        //2.Show user
        String userName = specialInfoList.get(position).getUser_name();
        if (userName == null) {
            holder.textView_name.setText("匿名用户");
        } else {
            holder.textView_name.setText(userName);
        }


        //3.Show content
        String content = specialInfoList.get(position).getContent();
        holder.textView_content.setText(content);


        //4.Show image

        String contentUrl = specialInfo.getContent_image();
        if ("".equals(contentUrl)) {
            holder.imageView_content.setVisibility(View.GONE);
        } else {
            Bitmap bitmap=loadImage.loadImage(holder.imageView_content,contentUrl);
            if(bitmap!=null){
                holder.imageView_content.setImageBitmap(bitmap);
            }

        }

    }

    public void setList(List<SpecialInfo> specialInfoList) {
        this.specialInfoList = specialInfoList;
    }


    @Override
    public int getItemCount() {
        return specialInfoList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_header;
        TextView textView_name;
        TextView textView_content;
        ImageView imageView_content;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView_header = (ImageView) itemView.findViewById(R.id.imgUserHeader);
            textView_name = (TextView) itemView.findViewById(R.id.txtUserName);
            textView_content = (TextView) itemView.findViewById(R.id.txtContent);
            imageView_content = (ImageView) itemView.findViewById(R.id.imgContent);
        }

    }
}
