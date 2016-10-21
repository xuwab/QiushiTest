package com.xuwab.qiushitest.qiushi;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xuwab on 2016/10/19.
 */

public class MainQiushiPagerAdater extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> fragments;
    private String[] titles={"专享","视频","纯文","纯图","精华","最新"};
    public MainQiushiPagerAdater(FragmentManager fm, Context context, List<Fragment> fragments) {
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
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
