//在AndroidManifest.xml中
<activity
android:name=".PlayerActivity"
android:exported="true"
android:screenOrientation="portrait"
android:supportsPictureInPicture="true"
android:configChanges="screenSize|smallestScreenSize|screenLayout|orientation"
android:resizeableActivity="true" />

videoView=findViewById(R.id.player);
//设置视频地址
videoView.setUrl(url);
//创建控制器
UaoanStandardVideoController controller = new UaoanStandardVideoController(this,videoView);
//设置标题
controller.addDefaultControlComponent("标题", false);
//设置显示底部进度条
controller.setBottomProgress(true);
//设置控制器
videoView.setVideoController(controller);
//开始播放，不调用则不自动播放
videoView.start();

//创建接口调用类
UaoanInterFace face=new UaoanInterFace();
//显示投屏按钮并设置点击事件
face.setScreenTvOnClickListener(new UaoanInterFace.ScreenTvOnClickListener() {
@Override
public void onClick(View v) {
Toast.makeText(MainActivity.this, "点击了投屏", Toast.LENGTH_SHORT).show();
}
});

//显示下一集按钮并设置点击事件
face.setPlayDownOnClickListener(new UaoanInterFace.PlayDownOnClickListener() {
@Override
public void onClick(View v) {
Toast.makeText(MainActivity.this, "点击了下一集", Toast.LENGTH_SHORT).show();
}
});

//显示选集按钮并设置点击事件
face.setPlayListOnClickListener(new UaoanInterFace.PlayListOnClickListener() {
@Override
public void onClick(View v) {
Toast.makeText(MainActivity.this, "点击了选集", Toast.LENGTH_SHORT).show();
}
});

//显示竖屏全屏按钮
face.setFullScreenVertical(true);


//实时监听播放
 videoView.setOnProgressListener(new DkPlayerView.OnProgressListener() {
   @Override
    public void onProgress() {

       }
    });

//继续上次播放位置
videoView.setKeepVideoPlaying()

//播放完成
videoView.setOnPlayComplete(new DkPlayerView.OnPlayComplete() {
@Override
public void complete() {

            }
        });