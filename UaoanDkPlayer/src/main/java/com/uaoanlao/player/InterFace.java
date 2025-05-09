package com.uaoanlao.player;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.view.View;

import com.uaoanlao.player.component.TitleView;

public class InterFace {

    //设置倍速按钮
    public void setSpeedOnClickListener(SpeedClickListener speed){
        playSpeed=speed;
    }

    public static SpeedClickListener playSpeed;
    public interface SpeedClickListener{
        void onClick(View v);
    }

    //设置设置按钮
    public void setSetUpOnClickListener(SetUpOnClickListener setup){
        playsetup=setup;
    }

    public static SetUpOnClickListener playsetup;
    public interface SetUpOnClickListener{
        void onClick(View v);
    }
}
