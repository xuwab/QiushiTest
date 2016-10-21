package com.xuwab.qiushitest.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuwab.qiushitest.R;

import java.util.List;

/**
 * Created by xuwab on 2016/10/19.
 */

public class MainViewPagerAdater extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> fragments;
    private String[] titles={"糗事","糗友圈","发现","小纸条","我"};
    private int[] drawable={R.drawable.tab_image_qiushi_background_selector,
                            R.drawable.tab_image_qiuyou_background_selector,
                            R.drawable.tab_image_find_background_selector,
                            R.drawable.tab_image_smallpager_background_selector,
                            R.drawable.tab_image_me_background_selector};

    public MainViewPagerAdater(FragmentManager fm, Context context, List<Fragment> fragments) {
        super(fm);
        this.context=context;
        this.fragments=fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    public View getTabItemView(int position){
        View view= LayoutInflater.from(context).inflate(R.layout.tab_item_view,null);
        TextView tv = (TextView) view.findViewById(R.id.tv_tab_item);
        tv.setText(titles[position]);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_tab_item);
        iv.setImageResource(drawable[position]);
        return view;
    }
}
