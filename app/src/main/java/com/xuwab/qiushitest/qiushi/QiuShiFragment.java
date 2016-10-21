package com.xuwab.qiushitest.qiushi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xuwab.qiushitest.R;
import com.xuwab.qiushitest.qiushi.zhuangxiang.ZhuangXiangFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuwab on 2016/10/19.
 */

public class QiuShiFragment extends Fragment {
    private View view;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager vp;
    private MainQiushiPagerAdater adater;
    private List<Fragment> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.qiushi_fragment,container,false);
        initData();
        initView();
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return view;
    }
    private void initData() {
        list=new ArrayList<>();
        for(int i=0;i<6;i++){
            list.add(new ZhuangXiangFragment());
        }
        adater=new MainQiushiPagerAdater(getChildFragmentManager(),getActivity(),list);
    }

    private void initView() {
        toolbar=(Toolbar)view.findViewById(R.id.tool);
        toolbar.inflateMenu(R.menu.menu_qiushi);
        tabLayout=(TabLayout)view.findViewById(R.id.tab_qiushi);
        vp=(ViewPager)view.findViewById(R.id.vp_qiushi);
        vp.setAdapter(adater);
        tabLayout.setupWithViewPager(vp);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_qiushi,menu);
    }

}

