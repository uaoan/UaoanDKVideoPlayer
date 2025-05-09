package com.uaoanlao.player.tool;

import android.content.Context;
import android.media.AudioManager;

public class VolumeControl {
    private AudioManager audioManager;

    public VolumeControl(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }


    // 获取当前音量百分比
    public int getCurrentVolumePercentage(int streamType) {
        try {
            int currentVolume = audioManager.getStreamVolume(streamType);
            int maxVolume = audioManager.getStreamMaxVolume(streamType);
            return (int) ((float) currentVolume / maxVolume * 100);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 获取最大音量
    public int getMaxVolume(int streamType) {
        try {
            return audioManager.getStreamMaxVolume(streamType);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 根据百分比设置音量
    public void setVolumeByPercentage(int streamType, int percentage) {
        int maxVolume = audioManager.getStreamMaxVolume(streamType);
        int volume = (int) ((float) percentage / 100 * maxVolume);
        audioManager.setStreamVolume(streamType, volume, 0);
    }
}