package com.uaoanlao.player;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.uaoanlao.player.List.UaoanRecyclerView;
import com.uaoanlao.player.List.UaoanRecyclerViewAdapter;
import com.uaoanlao.player.component.VodControlView;
import com.uaoanlao.player.tool.BlurShader;
import com.uaoanlao.player.tool.GlobalTimer.CustomCountdownView;
import com.uaoanlao.player.tool.VolumeControl;

import java.util.ArrayList;
import java.util.HashMap;

import xyz.doikki.videoplayer.player.BaseVideoView;
import xyz.doikki.videoplayer.player.VideoView;

public class DkPlayerView extends VideoView {
    public static UaoanSharedSqlite sqlite=new UaoanSharedSqlite();
    private AlertDialog.Builder dialog;
    private AlertDialog tc;
    private VolumeControl volumeControl; //音量
    public static boolean isTime=false;
    private String getVideoType="默认显示";
    public DkPlayerView(Context context) {
        super(context);
        init(null, 0);
    }

    public DkPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

    }

    public DkPlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        sqlite.init(getActivity(),"video"); //初始化表名
        //判断播放倍速
        if (sqlite.getFloat("speed")==0.0){
            sqlite.putFloat("speed",1.0f);
            sqlite.putFloat("longspeed",3.0f);
            setSpeed(sqlite.getFloat("speed"));
        }else {
            setSpeed(1.0f);
        }

        volumeControl = new VolumeControl(getActivity()); //音量

        //倍速
        new InterFace().setSpeedOnClickListener(new InterFace.SpeedClickListener() {
            @Override
            public void onClick(View v) {
                BlurShader.showBlur(getActivity(),"5","5");
                View vw=inflate(getActivity(),R.layout.speed_dialog,null);
                final AlertDialog tc=new AlertDialog.Builder(getActivity())
                        .setView(vw)
                        .create();
                // 监听对话框关闭事件
                tc.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // 在这里处理对话框关闭后的逻辑
                        //System.out.println("对话框已关闭");
                        BlurShader.removeBlur(getActivity());
                    }
                });
                tc.show();
                tc.getWindow().setGravity(Gravity.RIGHT);
                tc.getWindow().setBackgroundDrawable(new ColorDrawable());
                LinearLayout lin=vw.findViewById(R.id.layout);
                lin.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tc.dismiss();
                    }
                });
                final UaoanRecyclerView uaoanRecyclerView=new UaoanRecyclerView();
                RecyclerView recyclerView=vw.findViewById(R.id.recycler);
                final ArrayList<HashMap<String,Object>> arrayList=new ArrayList<>();
                HashMap<String,Object> hashMap=new HashMap<>();
                ArrayList<String> strings=new ArrayList<>();
                strings.add("0.75x");
                strings.add("1.0x");
                strings.add("1.25x");
                strings.add("1.5x");
                strings.add("1.75x");
                strings.add("2.0x");
                strings.add("2.5x");
                strings.add("3.0x");
                for (int po=0;po<strings.size();po++){
                    hashMap=new HashMap<>();
                    hashMap.put("name",strings.get(po));
                    arrayList.add(hashMap);
                }
                uaoanRecyclerView.setAdapter(recyclerView, R.layout.speed_dialog_item, arrayList, new UaoanRecyclerView.OnRecyclerViewAdapter() {
                    @Override
                    public void bindView(UaoanRecyclerViewAdapter.ViewHolder holder, final ArrayList<HashMap<String, Object>> data, final int position) {
                        TextView speed_name=holder.itemView.findViewById(R.id.title);

                        if (data.get(position).get("name").toString().replace("x","").equals(""+sqlite.getFloat("speed"))){
                            speed_name.setTextColor(Color.WHITE);
                            speed_name.setTextSize(30);
                        }else {
                            speed_name.setTextColor(Color.parseColor("#66ffffff"));
                            speed_name.setTextSize(20);
                        }

                        speed_name.setText(arrayList.get(position).get("name").toString());
                        speed_name.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                float num1 = Float.parseFloat(data.get(position).get("name").toString().replace("x",""));
                                setSpeed(num1);
                                sqlite.putFloat("speed",num1);
                                tc.dismiss();
                                uaoanRecyclerView.notifyDataSetChanged(recyclerView);
                            }
                        });
                    }
                }).setLinearLayoutManager(recyclerView,getActivity());


            }
        });




        //设置
        new InterFace().setSetUpOnClickListener(new InterFace.SetUpOnClickListener() {
            @Override
            public void onClick(View v) {
                //BlurShader.showBlur(getActivity(),"5","5");
                //弹窗初始化
                dialog=new AlertDialog.Builder(getActivity());
                tc=dialog.create();
                View vw=inflate(getActivity(),R.layout.setup_dialog,null);
                tc.setView(vw);
                // 监听对话框关闭事件
                tc.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // 在这里处理对话框关闭后的逻辑
                        //System.out.println("对话框已关闭");
                        //BlurShader.removeBlur(getActivity());
                    }
                });
                tc.show();
                tc.getWindow().setGravity(Gravity.RIGHT);
                tc.getWindow().setBackgroundDrawable(new ColorDrawable());

                LinearLayout lin=vw.findViewById(R.id.layout);
                lin.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tc.dismiss();
                    }
                });
                LinearLayout linear1=vw.findViewById(R.id.line1); //画面比例
                LinearLayout linear2=vw.findViewById(R.id.line2); //长按倍速
                LinearLayout linear3=vw.findViewById(R.id.line3); //定时关闭
                LinearLayout linear4=vw.findViewById(R.id.line4); //跳过片头
                LinearLayout linear5=vw.findViewById(R.id.line5); //跳过片尾
                LinearLayout linear6=vw.findViewById(R.id.line6); //小窗播放
                LinearLayout linear7=vw.findViewById(R.id.line7); //底部进度条
                ImageView bottomjd=vw.findViewById(R.id.kgImage); //底部进度条按钮
                if (VodControlView.getBottomProgress){
                    bottomjd.setImageResource(R.mipmap.kg2);
                }else {
                    bottomjd.setImageResource(R.mipmap.kg1);
                }
                //音量
                TextView volumeText=vw.findViewById(R.id.volumeText);
                SeekBar volumeSeekBar=vw.findViewById(R.id.volumeSeek_bar);
                // 设置 SeekBar 的最大值为 100
                volumeSeekBar.setMax(100);

                // 获取当前媒体音量百分比
                int currentVolumePercentage = volumeControl.getCurrentVolumePercentage(AudioManager.STREAM_MUSIC);
                // 设置 SeekBar 的当前进度为当前音量百分比
                volumeSeekBar.setProgress(currentVolumePercentage);
                volumeText.setText(currentVolumePercentage+"%");
                // 为 SeekBar 添加监听器
                volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            // 用户拖动 SeekBar 时，根据百分比设置音量
                            volumeControl.setVolumeByPercentage(AudioManager.STREAM_MUSIC, progress);
                            volumeText.setText(progress+"%");
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // 开始拖动 SeekBar 时的操作
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // 停止拖动 SeekBar 时的操作
                    }
                });

                //画面比例
                linear1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tc.dismiss();
                        setScreenScale();
                    }
                });

                //跳过片头
                linear4.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tc.dismiss();
                        setSkipTopPlayer();
                    }
                });

                //跳过片尾
                linear5.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tc.dismiss();
                        setSkipBottimPlayer();
                    }
                });

                //定时关闭
                linear3.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tc.dismiss();
                        setGlobalTimer();
                    }
                });

                //长按倍速
                linear2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tc.dismiss();
                        setlongSpeed();
                    }
                });

                //小窗播放
                linear6.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tc.dismiss();
                        getActivity().enterPictureInPictureMode();
                        resume();
                    }
                });

                //底部进度条
                linear7.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (VodControlView.getBottomProgress){
                            VodControlView.setBottomProgress(false);
                            bottomjd.setImageResource(R.mipmap.kg1);
                        }else {
                            VodControlView.setBottomProgress(true);
                            bottomjd.setImageResource(R.mipmap.kg2);
                        }
                    }
                });

            }
        });




        //播放状态监听
        addOnStateChangeListener(new BaseVideoView.OnStateChangeListener(){

            @Override
            public void onPlayerStateChanged(int playerState) {
                if (playerState==11){
                    //全屏显示倍速按钮
                    VodControlView.playSpeed.setVisibility(View.VISIBLE);
                    //设置竖屏全屏隐藏
                    VodControlView.shup.setVisibility(GONE);
                }
                if (playerState==10){
                    //竖屏隐藏倍速按钮
                    VodControlView.playSpeed.setVisibility(View.GONE);
                    //判断竖屏全屏是否正在隐藏
                    if (VodControlView.position==1){
                        VodControlView.shup.setVisibility(VISIBLE);
                    }else {
                        VodControlView.shup.setVisibility(GONE);
                    }
                }
            }

            @Override
            public void onPlayStateChanged(int playState) {
                //播放完毕
                if (playState==STATE_PLAYBACK_COMPLETED){
                    if (complete!=null){
                        complete.complete();
                    }
                }
                //正在准备播放
                if (playState==STATE_PREPARING){
                    //获取判断当前播放进度
                    if (keepvideoplaying==1) {
                        if (sqlite.getInt(mUrl) != 0 || sqlite.getInt(mUrl) > 10000) {
                            skipPositionWhenPlay(sqlite.getInt(mUrl)); //设置播放进度
                        } else if (sqlite.getInt(mUrl)==0) {
                            //设置跳过片头
                            if (sqlite.getInt("skiptop")!=0) {
                                skipPositionWhenPlay(sqlite.getInt("skiptop"));
                            }
                        }
                    }else {
                        //设置跳过片头
                        if (sqlite.getInt("skiptop")!=0) {
                            skipPositionWhenPlay(sqlite.getInt("skiptop"));
                        }
                    }

                }
                //正在缓冲
                if (playState==STATE_BUFFERING){

                }
            }
        });


        //实时监听播放
        VodControlView.setOnProgressListener(new VodControlView.OnProgressListener() {
            @Override
            public void onProgress() {
                //写入当前播放进度
                if (keepvideoplaying==1) {
                    sqlite.putInt(mUrl, (int) getCurrentPosition());
                }

                //跳过片尾
                int skipbottom=sqlite.getInt("skipbottom");
                if (skipbottom!=0) {
                    if (getCurrentPosition() == skipbottom || getCurrentPosition() > skipbottom) {
                        seekTo(getDuration());
                    }
                }

                if (onProgressListener != null) {
                    onProgressListener.onProgress();
                }

            }
        });

    }

    //获取当前倍速
    public static float getSpeeds(){
        return sqlite.getFloat("speed");
    }
    //设置倍速
    public static void setSpeeds(float lo){
        sqlite.setFloat("speed",lo);
    }
    //获取长按倍速
    public static float getLongSpeeds(){
        return sqlite.getFloat("longspeed");
    }
    //设置长按倍速
    public static void setLongSpeeds(float lo){
        sqlite.setFloat("longspeed",lo);
    }


    //继续之前播放位置
    private int keepvideoplaying=0;
    public void setKeepVideoPlaying(){
        keepvideoplaying=1;
    }

    //暂停播放
    public void onVideoPause(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (getActivity().isInPictureInPictureMode()) {
                //当前处于画中画模式,这里不做暂停操作
            } else {
                //当前不处于画中画模式
                pause();
            }
        }
    }

    //继续播放
    public void onVideoResume(){
        resume();
    }

    //销毁播放器
    public void onVideoReleaseAllVideos(){
        release();
    }


    //监听播放接口
    public interface OnProgressListener{
        void onProgress();
    }

    private static OnProgressListener onProgressListener;

    public static void setOnProgressListener(OnProgressListener progressListener){
        onProgressListener=progressListener;
    }

    //播放完成
    public interface OnPlayComplete{
        void complete();
    }
    private OnPlayComplete complete;
    public void setOnPlayComplete(OnPlayComplete onPlayComplete){
        complete=onPlayComplete;
    }



    //画面比例
    private void setScreenScale(){
        BlurShader.showBlur(getActivity(),"5","5");
                View vw=inflate(getActivity(),R.layout.speed_dialog,null);
                final AlertDialog tc=new AlertDialog.Builder(getActivity())
                        .setView(vw)
                        .create();
                // 监听对话框关闭事件
                tc.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // 在这里处理对话框关闭后的逻辑
                        //System.out.println("对话框已关闭");
                        BlurShader.removeBlur(getActivity());
                    }
                });

                tc.show();
                tc.getWindow().setGravity(Gravity.RIGHT);
                tc.getWindow().setBackgroundDrawable(new ColorDrawable());
                LinearLayout lin=vw.findViewById(R.id.layout);
                lin.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tc.dismiss();
                    }
                });
                final UaoanRecyclerView uaoanRecyclerView=new UaoanRecyclerView();
                RecyclerView recyclerView=vw.findViewById(R.id.recycler);
                final ArrayList<HashMap<String,Object>> arrayList=new ArrayList<>();
                HashMap<String,Object> hashMap=new HashMap<>();
                ArrayList<String> strings=new ArrayList<>();
                strings.add("默认显示");
                strings.add("16:9");
                strings.add("4:3");
                strings.add("全屏裁剪");
                strings.add("全屏拉伸");
                for (int po=0;po<strings.size();po++){
                    hashMap=new HashMap<>();
                    hashMap.put("name",strings.get(po));
                    arrayList.add(hashMap);
                }
                uaoanRecyclerView.setAdapter(recyclerView, R.layout.speed_dialog_item, arrayList, new UaoanRecyclerView.OnRecyclerViewAdapter() {
                    @Override
                    public void bindView(UaoanRecyclerViewAdapter.ViewHolder holder, final ArrayList<HashMap<String, Object>> data, final int position) {
                        TextView speed_name=holder.itemView.findViewById(R.id.title);
                        final String nr=arrayList.get(position).get("name").toString();
                        if (nr.equals(getVideoType)){
                            speed_name.setTextColor(Color.WHITE);
                            speed_name.setTextSize(30);
                        }else {
                            speed_name.setTextColor(Color.parseColor("#66ffffff"));
                            speed_name.setTextSize(20);
                        }

                        speed_name.setText(nr);
                        speed_name.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getVideoType=nr;
                                if (position==0){
                                    //默认的比例，按照视频宽高等比缩放并填充视频框，视频比例和视频框比例不同时有黑边
                                    setScreenScaleType(SCREEN_SCALE_DEFAULT);
                                }
                                if (position==1){
                                    //16:9，画面可能变形
                                    setScreenScaleType(SCREEN_SCALE_16_9);
                                }
                                if (position==2){
                                    //4:3，画面可能变形
                                    setScreenScaleType(SCREEN_SCALE_4_3);
                                }
                                if (position==3){
                                    //和ImageView的CenterCrop类似，可能出现裁切画面，显示不全（全屏裁剪）
                                    setScreenScaleType(SCREEN_SCALE_CENTER_CROP);
                                }
                                if (position==4){
                                    //填充视频框，画面可能变形（全屏拉伸）
                                    setScreenScaleType(SCREEN_SCALE_MATCH_PARENT);
                                }
                                tc.dismiss();
                                uaoanRecyclerView.notifyDataSetChanged(recyclerView);
                            }
                        });
                    }
                }).setLinearLayoutManager(recyclerView,getActivity());

            }

            //长按倍速
            private void setlongSpeed(){
                BlurShader.showBlur(getActivity(),"5","5");
                View vw=inflate(getActivity(),R.layout.speed_dialog,null);
                final AlertDialog tc=new AlertDialog.Builder(getActivity())
                        .setView(vw)
                        .create();
                // 监听对话框关闭事件
                tc.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // 在这里处理对话框关闭后的逻辑
                        //System.out.println("对话框已关闭");
                        BlurShader.removeBlur(getActivity());
                    }
                });
                tc.show();
                tc.getWindow().setGravity(Gravity.RIGHT);
                tc.getWindow().setBackgroundDrawable(new ColorDrawable());
                LinearLayout lin=vw.findViewById(R.id.layout);
                lin.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tc.dismiss();
                    }
                });
                final UaoanRecyclerView uaoanRecyclerView=new UaoanRecyclerView();
                RecyclerView recyclerView=vw.findViewById(R.id.recycler);
                final ArrayList<HashMap<String,Object>> arrayList=new ArrayList<>();
                HashMap<String,Object> hashMap=new HashMap<>();
                ArrayList<String> strings=new ArrayList<>();
                strings.add("2.0x");
                strings.add("3.0x");
                strings.add("3.5x");
                strings.add("4.0x");
                strings.add("4.5x");
                strings.add("5.0x");
                strings.add("5.5x");
                strings.add("6.0x");
                for (int po=0;po<strings.size();po++){
                    hashMap=new HashMap<>();
                    hashMap.put("name",strings.get(po));
                    arrayList.add(hashMap);
                }
                uaoanRecyclerView.setAdapter(recyclerView, R.layout.speed_dialog_item, arrayList, new UaoanRecyclerView.OnRecyclerViewAdapter() {
                    @Override
                    public void bindView(UaoanRecyclerViewAdapter.ViewHolder holder, final ArrayList<HashMap<String, Object>> data, final int position) {
                        TextView speed_name=holder.itemView.findViewById(R.id.title);

                        if (data.get(position).get("name").toString().replace("x","").equals(""+sqlite.getFloat("longspeed"))){
                            speed_name.setTextColor(Color.WHITE);
                            speed_name.setTextSize(30);
                        }else {
                            speed_name.setTextColor(Color.parseColor("#66ffffff"));
                            speed_name.setTextSize(20);
                        }

                        speed_name.setText(arrayList.get(position).get("name").toString());
                        speed_name.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                float num1 = Float.parseFloat(data.get(position).get("name").toString().replace("x",""));
                                setLongSpeeds(num1);
                                tc.dismiss();
                                uaoanRecyclerView.notifyDataSetChanged(recyclerView);
                            }
                        });
                    }
                }).setLinearLayoutManager(recyclerView,getActivity());



            }


    //播放进度转换成 小时/分钟/秒
    public static String convertMillisecondsToTime(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    //跳过片头
    private void setSkipTopPlayer(){
        BlurShader.showBlur(getActivity(),"5","5");
        View vw=inflate(getActivity(),R.layout.skip_dialog,null);
        final AlertDialog tc=new AlertDialog.Builder(getActivity())
                .setView(vw)
                .create();
        // 监听对话框关闭事件
        tc.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // 在这里处理对话框关闭后的逻辑
                //System.out.println("对话框已关闭");
                BlurShader.removeBlur(getActivity());
            }
        });
        tc.show();
        tc.getWindow().setGravity(Gravity.RIGHT);
        tc.getWindow().setBackgroundDrawable(new ColorDrawable());
        LinearLayout lin=vw.findViewById(R.id.layout);
        lin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tc.dismiss();
            }
        });
        final TextView title=vw.findViewById(R.id.title); //标题
        final TextView name=vw.findViewById(R.id.name); //进度标题
        final SeekBar seekBar=vw.findViewById(R.id.seekBar); //拖动条
        title.setText("跳过片头");
        seekBar.setMax((int) getDuration()); //设置拖动条最大值
        if (sqlite.getInt("skiptop")==0){
            name.setText("00：00");
        }else {
            name.setText(convertMillisecondsToTime(sqlite.getInt("skiptop")));
            seekBar.setProgress(sqlite.getInt("skiptop"));
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // 用户拖动 SeekBar 时，根据百分比设置音量
                    name.setText(convertMillisecondsToTime(progress));
                    sqlite.putInt("skiptop",progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 开始拖动 SeekBar 时的操作
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 停止拖动 SeekBar 时的操作
            }
        });

    }

    //跳过片尾
    private void setSkipBottimPlayer(){
        BlurShader.showBlur(getActivity(),"5","5");
        View vw=inflate(getActivity(),R.layout.skip_dialog,null);
        final AlertDialog tc=new AlertDialog.Builder(getActivity())
                .setView(vw)
                .create();
        // 监听对话框关闭事件
        tc.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // 在这里处理对话框关闭后的逻辑
                //System.out.println("对话框已关闭");
                BlurShader.removeBlur(getActivity());
            }
        });
        tc.show();
        tc.getWindow().setGravity(Gravity.RIGHT);
        tc.getWindow().setBackgroundDrawable(new ColorDrawable());
        LinearLayout lin=vw.findViewById(R.id.layout);
        lin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tc.dismiss();
            }
        });
        final TextView title=vw.findViewById(R.id.title); //标题
        final TextView name=vw.findViewById(R.id.name); //进度标题
        final SeekBar seekBar=vw.findViewById(R.id.seekBar); //拖动条
        title.setText("跳过片尾");
        seekBar.setMax((int) getDuration()); //设置拖动条最大值
        if (sqlite.getInt("skipbottom")==0){
            name.setText("00：00");
        }else {
            name.setText(convertMillisecondsToTime(sqlite.getInt("skipbottom")));
            seekBar.setProgress(sqlite.getInt("skipbottom"));
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // 用户拖动 SeekBar 时，根据百分比设置音量
                    name.setText(convertMillisecondsToTime(progress));
                    sqlite.putInt("skipbottom",progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 开始拖动 SeekBar 时的操作
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 停止拖动 SeekBar 时的操作
            }
        });

    }

    //定时关闭
    private void setGlobalTimer(){
        BlurShader.showBlur(getActivity(),"5","5");
        View vw=inflate(getActivity(),R.layout.timer_dialog,null);
        final AlertDialog tc=new AlertDialog.Builder(getActivity())
                .setView(vw)
                .create();
        // 监听对话框关闭事件
        tc.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // 在这里处理对话框关闭后的逻辑
                //System.out.println("对话框已关闭");
                BlurShader.removeBlur(getActivity());
            }
        });
        tc.show();
        tc.getWindow().setGravity(Gravity.RIGHT);
        tc.getWindow().setBackgroundDrawable(new ColorDrawable());
        LinearLayout lin=vw.findViewById(R.id.layout);
        lin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tc.dismiss();
            }
        });
        final TextView title=vw.findViewById(R.id.title); //标题
        final CustomCountdownView times=vw.findViewById(R.id.times);
        final TextView cancel=vw.findViewById(R.id.cancel); //取消
        final LinearLayout linearLayout1=vw.findViewById(R.id.line1);
        final LinearLayout linearLayout2=vw.findViewById(R.id.line2);
        final RecyclerView recyclerView=vw.findViewById(R.id.recycler);

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                isTime=false;
                CustomCountdownView.stopGlobalCountdown(getActivity());
                linearLayout1.setVisibility(GONE);
                linearLayout2.setVisibility(VISIBLE);
            }
        });

        if (isTime) {
            // 倒计时正在运行
            linearLayout1.setVisibility(VISIBLE);
            linearLayout2.setVisibility(GONE);
        } else {
            // 倒计时未启动或已停止
            linearLayout1.setVisibility(GONE);
            linearLayout2.setVisibility(VISIBLE);

            final UaoanRecyclerView uaoanRecyclerView=new UaoanRecyclerView();
            final ArrayList<HashMap<String,Object>> arrayList=new ArrayList<>();
            HashMap<String,Object> hashMap=new HashMap<>();
            ArrayList<String> strings=new ArrayList<>();
            strings.add("30分钟");
            strings.add("60分钟");
            strings.add("90分钟");
            strings.add("120分钟");
            for (int po=0;po<strings.size();po++){
                hashMap=new HashMap<>();
                hashMap.put("name",strings.get(po));
                arrayList.add(hashMap);
            }
            uaoanRecyclerView.setAdapter(recyclerView, R.layout.speed_dialog_item, arrayList, new UaoanRecyclerView.OnRecyclerViewAdapter() {
                @Override
                public void bindView(UaoanRecyclerViewAdapter.ViewHolder holder, final ArrayList<HashMap<String, Object>> data, final int position) {
                    TextView name1=holder.itemView.findViewById(R.id.title);

                    name1.setText(arrayList.get(position).get("name").toString());
                    name1.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (position==0){
                                //30分钟
                                CustomCountdownView.startGlobalCountdown(getActivity(), 1800000, 1000);
                            }
                            if (position==1){
                                //60分钟
                                CustomCountdownView.startGlobalCountdown(getActivity(), 3600000, 1000);
                            }
                            if (position==2){
                                //90分钟
                                CustomCountdownView.startGlobalCountdown(getActivity(), 5400000, 1000);
                            }
                            if (position==3){
                                //120分钟
                                CustomCountdownView.startGlobalCountdown(getActivity(), 7200000, 1000);
                            }
                            isTime=true;
                            linearLayout1.setVisibility(VISIBLE);
                            linearLayout2.setVisibility(GONE);
                            uaoanRecyclerView.notifyDataSetChanged(recyclerView);
                        }
                    });
                }
            }).setLinearLayoutManager(recyclerView,getActivity());


        }


        /*
        final CustomSeekBar seekBar=vw.findViewById(R.id.seekBar); //拖动条
        title.setText("定时关闭");
        seekBar.setMax(7200000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // 用户拖动 SeekBar 时，根据百分比设置音量
                    progres=progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 开始拖动 SeekBar 时的操作
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 停止拖动 SeekBar 时的操作
                if (progres==0){
                    CustomCountdownView.stopGlobalCountdown(getActivity());
                }else {
                    boolean isRunning = times.getIsCountdownStarted();
                    if (isRunning) {
                        // 倒计时正在运行
                        CustomCountdownView.stopGlobalCountdown(getActivity()); //停止
                        CustomCountdownView.startGlobalCountdown(getActivity(), progres, 1000);
                    } else {
                        // 倒计时未启动或已停止
                        CustomCountdownView.startGlobalCountdown(getActivity(), progres, 1000);
                    }
                }
            }
        });*/
    }

    private int progres=0;

}
