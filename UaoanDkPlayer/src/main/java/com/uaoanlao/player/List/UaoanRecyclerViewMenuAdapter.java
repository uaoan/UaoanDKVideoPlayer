package com.uaoanlao.player.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class UaoanRecyclerViewMenuAdapter extends RecyclerView.Adapter<UaoanRecyclerViewMenuAdapter.ViewHolder> {
    private ArrayList<String> data;
    private ArrayList<Integer> arrayima;
    private Context context;
    private int layout;

    public UaoanRecyclerViewMenuAdapter(Context context,int lay,ArrayList<String> arr,ArrayList<Integer> arrayImage) {
        data = arr;
        arrayima=arrayImage;
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
        UaoanRecyclerViewMenu.onByRecyclerViewAdapters.bindView(holder,data,arrayima,position);
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