package com.uaoanlao.player;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.view.View;

import com.uaoanlao.player.component.TitleView;
import com.uaoanlao.player.component.VodControlView;

public class UaoanInterFace {

    //显示隐藏竖屏全屏按钮
    public void setFullScreenVertical(boolean visibility){
        if (visibility){
            VodControlView.shup.setVisibility(VISIBLE);
            VodControlView.position=1;
        }else {
            VodControlView.shup.setVisibility(GONE);
            VodControlView.position=0;
        }
    }

    //设置投屏按钮
    public void setScreenTvOnClickListener(ScreenTvOnClickListener screenTV){
        tvScreen=screenTV;
        TitleView.tv.setVisibility(VISIBLE);
    }
    public void setScreenTvOnClickListener(boolean Visibility){
        if (Visibility) {
            TitleView.tv.setVisibility(VISIBLE);
        }else {
            TitleView.tv.setVisibility(GONE);
        }
    }
    public static ScreenTvOnClickListener tvScreen;
    public interface ScreenTvOnClickListener{
        void onClick(View v);
    }

    //设置下一集按钮
    public void setPlayDownOnClickListener(PlayDownOnClickListener down){
        playDown=down;
        VodControlView.playDown.setVisibility(VISIBLE);
    }
    public void setPlayDownOnClickListener(boolean Visibility){
        if (Visibility) {
            VodControlView.playDown.setVisibility(VISIBLE);
        }else {
            VodControlView.playDown.setVisibility(GONE);
        }
    }
    public static PlayDownOnClickListener playDown;
    public interface PlayDownOnClickListener{
        void onClick(View v);
    }

    //设置选集按钮
    public void setPlayListOnClickListener(PlayListOnClickListener list){
        playList=list;
        VodControlView.playList.setVisibility(VISIBLE);
    }
    public void setPlayListOnClickListener(boolean Visibility){
        if (Visibility) {
            VodControlView.playList.setVisibility(VISIBLE);
        }else {
            VodControlView.playList.setVisibility(GONE);
        }
    }
    public static PlayListOnClickListener playList;
    public interface PlayListOnClickListener{
        void onClick(View v);
    }
}

