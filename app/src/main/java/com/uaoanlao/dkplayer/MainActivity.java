package com.uaoanlao.dkplayer;

import android.app.AlertDialog;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Rational;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.uaoanlao.player.DkPlayerView;
import com.uaoanlao.player.UaoanInterFace;
import com.uaoanlao.player.UaoanStandardVideoController;

import java.io.IOException;

import xyz.doikki.videoplayer.exo.ExoMediaPlayerFactory;

public class MainActivity extends AppCompatActivity {
    private DkPlayerView videoView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        final EditText urlEdit = findViewById(R.id.url);
        AppCompatButton an1 = findViewById(R.id.an1);
        Button an2 = findViewById(R.id.an2);
        Button an3 = findViewById(R.id.an3);
        linearLayout = findViewById(R.id.layout1);


        videoView = findViewById(R.id.player);
        String url = "https://video-player-mp4-1253865537.cos.ap-guangzhou.myqcloud.com/Assassin%27s%20Creed%20Shadows.mp4";
        videoView.setUrl(url); //设置视频地址
        UaoanStandardVideoController controller = new UaoanStandardVideoController(this, videoView);
        controller.addDefaultControlComponent("标题", false);
        controller.setBottomProgress(true);
        UaoanInterFace face = new UaoanInterFace();
        face.setFullScreenVertical(true); //显示竖屏全屏按钮
        face.setScreenTvOnClickListener(new UaoanInterFace.ScreenTvOnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了投屏", Toast.LENGTH_SHORT).show();
            }
        });
        face.setPlayDownOnClickListener(new UaoanInterFace.PlayDownOnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了下一集", Toast.LENGTH_SHORT).show();
            }
        });
        face.setPlayListOnClickListener(new UaoanInterFace.PlayListOnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了选集", Toast.LENGTH_SHORT).show();
            }
        });
        videoView.setPlayerFactory(ExoMediaPlayerFactory.create());
        //videoView.setKeepVideoPlaying(); //续播
        videoView.setVideoController(controller); //设置控制器
        videoView.start(); //开始播放，不调用则不自动播放



        an1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urls = urlEdit.getText().toString();
                if (!urls.equals("")) {
                    videoView.release();
                    videoView.setUrl(urls); //设置视频地址
                    videoView.start();
                } else {
                    Toast.makeText(MainActivity.this, "你家播放链接是空白啊？", Toast.LENGTH_SHORT).show();
                }
            }
        });

        an2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.release();
                videoView.setUrl("https://video-player-mp4-1253865537.cos.ap-guangzhou.myqcloud.com/Assassin%27s%20Creed%20Shadows.mp4"); //设置视频地址
                videoView.start();
            }
        });

        an3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.release();
                videoView.setUrl("https://video-player-mp4-1253865537.cos.ap-guangzhou.myqcloud.com/%E7%AC%AC%E4%B8%80%E9%9B%86%EF%BC%9A%E5%9B%9B%E5%B9%B4%E5%90%8E.mp4"); //设置视频地址
                videoView.start();
            }
        });

    }
    public void tz(View view){
        startActivity(new Intent(MainActivity.this, UaoanActivity.class));
    }
    public void tz2(View view){
        startActivity(new Intent(MainActivity.this, UaoanActivity.class));
        finish();
    }

   /* @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        }
        if (isInPictureInPictureMode) {
            // 进入画中画模式，隐藏不必要的控件
            //pipButton.setVisibility(View.GONE);
        } else {
            // 退出画中画模式，显示控件
            //pipButton.setVisibility(View.VISIBLE);
        }
    }*/


    @Override
    protected void onPause() {
        super.onPause();
        //暂停播放
        videoView.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //继续播放
        videoView.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁
        videoView.onVideoReleaseAllVideos();
    }


    @Override
    public void onBackPressed() {
        if (!videoView.onBackPressed()) {
            super.onBackPressed();
        }
    }




}