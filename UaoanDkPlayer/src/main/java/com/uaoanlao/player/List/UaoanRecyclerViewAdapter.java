package com.uaoanlao.player.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class UaoanRecyclerViewAdapter extends RecyclerView.Adapter<UaoanRecyclerViewAdapter.ViewHolder> {
    private ArrayList<HashMap<String, Object>> data;
    private Context context;
    private int layout;

    public UaoanRecyclerViewAdapter(Context context,int lay,ArrayList<HashMap<String, Object>> arr) {
        data = arr;
        this.context=context;
        this.layout=lay;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        View vw = holder.itemView;
        UaoanRecyclerView.onByRecyclerViewAdapters.bindView(holder,data,position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View v){
            super(v);
        }


    }

}