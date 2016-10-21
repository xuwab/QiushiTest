package com.xuwab.qiushitest.qiushi.zhuangxiang;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xuwab.qiushitest.R;
import com.xuwab.qiushitest.asyctask.DownLoadTask;
import com.xuwab.qiushitest.bean.SpecialInfo;
import com.xuwab.qiushitest.constans.PageConst;
import com.xuwab.qiushitest.util.JSONUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */


public class ZhuangXiangFragment extends Fragment implements AbsListView.OnScrollListener,SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{
    private static final String TAG ="robin debug";
//    private ListView lv;
    private RecyclerView rv;
    private ZhuangxiangAdater adapter;
    private List<SpecialInfo> specialInfoList;
    private int pageNum =1;
    private boolean isEnd=false;
    private LinearLayout ll;
    private View view;
    private SwipeRefreshLayout swipe;
    private boolean isRefresh=false;
    private FloatingActionButton fb;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.qiushi_zhuangxiang_fragment,container,false);
        initData();
        initView();
        while (specialInfoList==null){
            initSpecialINfoList();
        }
        return view;
    }

    private void initData() {
        specialInfoList=new ArrayList<>();
//        adapter=new SpecialBaseAdapter(getActivity(),specialInfoList);
        adapter=new ZhuangxiangAdater(getActivity(),specialInfoList);
        initSpecialINfoList();

    }

    private void initSpecialINfoList() {
        new DownLoadTask(getActivity(), new DownLoadTask.CallBackListener() {
            @Override
            public void onResponse(String string) {
                specialInfoList= JSONUtils.jsonStringToList(string);
                adapter.setList(specialInfoList);
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }).execute(PageConst.PAGE+pageNum);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        Log.e(TAG,"size:"+specialInfoList.size());
        ll=(LinearLayout)view.findViewById(R.id.linearLayout) ;
//        lv=(MyListview) view.findViewById(R.id.listView1) ;
//        lv.setAdapter(adapter);
//        lv.setOnScrollListener(this);
        rv=(RecyclerView) view.findViewById(R.id.listView1);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    ll.setVisibility(View.VISIBLE);
                    new DownLoadTask(getActivity(), new DownLoadTask.CallBackListener() {
                        @Override
                        public void onResponse(String string) {
                            Log.e(TAG,">>>ll");
                            Log.e(TAG,">>>ll2")  ;
                            List list=JSONUtils.jsonStringToList(string);
                            specialInfoList.addAll(list);
                            adapter.setList(specialInfoList);
                            adapter.notifyDataSetChanged();
                            ll.setVisibility(View.GONE);
                        }
                    }).execute(PageConst.PAGE+(++pageNum));
                }
            }
        });

        swipe=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipe.setOnRefreshListener(this);
        fb=(FloatingActionButton)view.findViewById(R.id.fab2);
        fb.setOnClickListener(this);
    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()-10)
            return true;
        return false;
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState){
            case SCROLL_STATE_IDLE:
                Log.e(TAG,">>>isEnd");
                if(isEnd){
                    ll.setVisibility(View.VISIBLE);
                    new DownLoadTask(getActivity(), new DownLoadTask.CallBackListener() {
                        @Override
                        public void onResponse(String string) {
                            Log.e(TAG,">>>ll");
                            Log.e(TAG,">>>ll2")  ;
                            List list=JSONUtils.jsonStringToList(string);
                            specialInfoList.addAll(list);
                            adapter.setList(specialInfoList);
                            adapter.notifyDataSetChanged();
                            ll.setVisibility(View.GONE);
                        }
                    }).execute(PageConst.PAGE+(++pageNum));
                }
                if(isRefresh){
                    Toast.makeText(getActivity(),"正在加载，请稍后",Toast.LENGTH_SHORT).show();
                    isRefresh=false;
                }
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        isEnd=((firstVisibleItem+visibleItemCount)>=totalItemCount-10);
        if((firstVisibleItem+visibleItemCount)==totalItemCount){
            isRefresh=true;
        }
    }


    @Override
    public void onRefresh() {
        initSpecialINfoList();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab2:
                rv.smoothScrollToPosition(0);
                break;
        }
    }
}