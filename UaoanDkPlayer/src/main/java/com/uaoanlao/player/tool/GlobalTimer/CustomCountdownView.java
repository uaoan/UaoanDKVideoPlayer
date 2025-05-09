package com.uaoanlao.player.tool.GlobalTimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.uaoanlao.player.DkPlayerView;

public class CustomCountdownView extends androidx.appcompat.widget.AppCompatTextView {
    private static final String ACTION_START_COUNTDOWN = "com.example.START_COUNTDOWN";
    private static final String ACTION_STOP_COUNTDOWN = "com.example.STOP_COUNTDOWN";
    private static final String EXTRA_MILLIS_IN_FUTURE = "millisInFuture";
    private static final String EXTRA_COUNT_DOWN_INTERVAL = "countDownInterval";
    private static final String ACTION_COUNTDOWN_UPDATE = "com.example.COUNTDOWN_UPDATE";
    private static final String EXTRA_MILLIS_UNTIL_FINISHED = "millisUntilFinished";

    private BroadcastReceiver internalReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_START_COUNTDOWN.equals(action)) {
                long millisInFuture = intent.getLongExtra(EXTRA_MILLIS_IN_FUTURE, 0);
                long countDownInterval = intent.getLongExtra(EXTRA_COUNT_DOWN_INTERVAL, 1000);
                startCountdown(millisInFuture, countDownInterval);
            } else if (ACTION_STOP_COUNTDOWN.equals(action)) {
                stopCountdown();
            } else if (ACTION_COUNTDOWN_UPDATE.equals(action) && !isBroadcastLocked) {
                long millisUntilFinished = intent.getLongExtra(EXTRA_MILLIS_UNTIL_FINISHED, 0);
                updateCountdownText(millisUntilFinished);
            }
        }
    };

    private static CountDownTimer globalCountDownTimer;
    private boolean isCountdownStarted = false;
    private boolean isBroadcastLocked = false;

    public CustomCountdownView(Context context) {
        super(context);
        init();
    }

    public CustomCountdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_START_COUNTDOWN);
        filter.addAction(ACTION_STOP_COUNTDOWN);
        filter.addAction(ACTION_COUNTDOWN_UPDATE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(internalReceiver, filter);
    }

    private void startCountdown(long millisInFuture, long countDownInterval) {
        if (isCountdownStarted) {
            stopCountdown();
            // 清空广播队列
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(internalReceiver);
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(internalReceiver, new IntentFilter() {{
                addAction(ACTION_START_COUNTDOWN);
                addAction(ACTION_STOP_COUNTDOWN);
                addAction(ACTION_COUNTDOWN_UPDATE);
            }});
        }
        globalCountDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendUpdateBroadcast(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                DkPlayerView.isTime=false;
                System.exit(0);
                sendUpdateBroadcast(0);
                isCountdownStarted = false;
            }
        }.start();
        isCountdownStarted = true;
    }

    private void stopCountdown() {
        isBroadcastLocked = true;
        if (globalCountDownTimer != null) {
            globalCountDownTimer.cancel();
            sendUpdateBroadcast(0);
            isCountdownStarted = false;
        }
        isBroadcastLocked = false;
    }

    private void sendUpdateBroadcast(long millisUntilFinished) {
        Intent intent = new Intent(ACTION_COUNTDOWN_UPDATE);
        intent.putExtra(EXTRA_MILLIS_UNTIL_FINISHED, millisUntilFinished);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(internalReceiver);
    }

    private void updateCountdownText(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000);
        setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
    }

    public static void startGlobalCountdown(Context context, long millisInFuture, long countDownInterval) {
        Intent intent = new Intent(ACTION_START_COUNTDOWN);
        intent.putExtra(EXTRA_MILLIS_IN_FUTURE, millisInFuture);
        intent.putExtra(EXTRA_COUNT_DOWN_INTERVAL, countDownInterval);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void stopGlobalCountdown(Context context) {
        globalCountDownTimer.cancel();
        Intent intent = new Intent(ACTION_STOP_COUNTDOWN);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public boolean getIsCountdownStarted() {
        return isCountdownStarted;
    }
}