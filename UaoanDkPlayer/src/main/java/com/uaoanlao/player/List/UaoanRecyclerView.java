package com.uaoanlao.player.List;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;



public class UaoanRecyclerView {
    //线性显示
    public UaoanRecyclerView setLinearLayoutManager(RecyclerView byRecyclerView, Context ei){
        byRecyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(ei));
        return this;
    }
    //网格显示
    public UaoanRecyclerView setGridLayoutManager(RecyclerView byRecyclerView, int po, Context ei){
        byRecyclerView.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(ei,po, androidx.recyclerview.widget.GridLayoutManager.VERTICAL, false));
        return this;
    }
    //瀑布流显示
    public UaoanRecyclerView setStaggeredGridLayoutManager(RecyclerView byRecyclerView, int po){
        byRecyclerView.setLayoutManager(new androidx.recyclerview.widget.StaggeredGridLayoutManager(po,androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL));
        return this;
    }
    //横向显示
    public UaoanRecyclerView setHorizontalLinearLayoutManager(RecyclerView byRecyclerView, Context ei){
        byRecyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(ei, RecyclerView.HORIZONTAL,false));
        return this;
    }
    //定位跳转
    public UaoanRecyclerView setScrollToPosition(RecyclerView byRecyclerView, int po){
        byRecyclerView.scrollToPosition(po);
        return this;
    }

    //倒序显示
    public UaoanRecyclerView setCollections(ArrayList dx){
        Collections.reverse(dx);
        return this;
    }
    //刷新
    public UaoanRecyclerView notifyDataSetChanged(RecyclerView byRecyclerView){
        byRecyclerView.getAdapter().notifyDataSetChanged();
        return this;
    }
    //删除
    public UaoanRecyclerView notifyItemRemoved(RecyclerView byRecyclerView, int po){
        byRecyclerView.getAdapter().notifyItemRemoved(po);
        RecyclerView.ItemAnimator animator = new androidx.recyclerview.widget.DefaultItemAnimator();
        byRecyclerView.setItemAnimator(animator);
        return this;
    }

    //保存列表缓存
    public UaoanRecyclerView setListDownCache(RecyclerView byrecyclerview1, int po){
        byrecyclerview1.setItemViewCacheSize(po);
        byrecyclerview1.setDrawingCacheEnabled(true);
        byrecyclerview1.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        return this;
    }
    //加载更多
    public UaoanRecyclerView addAll(ArrayList<HashMap<String,Object>> arrayList, ArrayList<HashMap<String,Object>> arrayList2){
        arrayList.addAll(arrayList2);
        return this;
    }
    public int HORIZONTAL= LinearLayout.HORIZONTAL;  //横向
    public int VERTICAL= LinearLayout.VERTICAL;  //竖向

    public static OnRecyclerViewAdapter onByRecyclerViewAdapters;
    //设置适配器
    public UaoanRecyclerView setAdapter(RecyclerView byRecyclerView, int lay, ArrayList<HashMap<String, Object>> arrayList, OnRecyclerViewAdapter onByRecyclerViewAdapter){
        onByRecyclerViewAdapters=onByRecyclerViewAdapter;
        byRecyclerView.setAdapter(new UaoanRecyclerViewAdapter(byRecyclerView.getContext(), lay,arrayList));
        return this;
    }

    public interface OnRecyclerViewAdapter{
        void bindView(UaoanRecyclerViewAdapter.ViewHolder holder, ArrayList<HashMap<String, Object>> data, final int position);
    }

}
