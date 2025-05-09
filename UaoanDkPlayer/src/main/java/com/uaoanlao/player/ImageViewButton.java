package com.uaoanlao.player;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageViewButton {
    public ImageView getImageView(Context context,int image){
        ImageView view=new ImageView(context);
        view.setImageResource(image);
        // 获取屏幕密度
        float density = context.getResources().getDisplayMetrics().density; // 将 20dp 转换为像素
        int sizeInPixels = (int) (50 * density);
        int padd=(int) (13 * density);
        view.setPadding(padd,padd,padd,padd);
        // 设置 ImageView 的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(sizeInPixels, sizeInPixels);
        view.setLayoutParams(layoutParams);
       return view;
    }
}
