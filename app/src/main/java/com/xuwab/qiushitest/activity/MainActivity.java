package com.xuwab.qiushitest.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import com.xuwab.qiushitest.R;
import com.xuwab.qiushitest.cache.ImageCache;
import com.xuwab.qiushitest.qiushi.QiuShiFragment;
import com.xuwab.qiushitest.custom.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> list;

    private TabLayout tab;
    private MyViewPager myVP;
    private MainViewPagerAdater adater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        list=new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add(new QiuShiFragment());
        }
        adater=new MainViewPagerAdater(getSupportFragmentManager(),this,list);
    }
    private void initView() {
        tab=(TabLayout) findViewById(R.id.main_tab);
        myVP=(MyViewPager)findViewById(R.id.my_vp);
        myVP.setAdapter(adater);
        myVP.setOffscreenPageLimit(0);
        tab.setupWithViewPager(myVP);
        if(tab!=null){
            for(int i=0;i<tab.getTabCount();i++){
                TabLayout.Tab tab1=tab.getTabAt(i);
                tab1.setCustomView(adater.getTabItemView(i));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageCache.getInstance().lruCache.evictAll();
    }
}
