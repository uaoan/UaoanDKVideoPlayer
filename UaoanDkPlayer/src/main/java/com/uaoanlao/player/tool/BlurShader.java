package com.uaoanlao.player.tool;


import android.app.Activity;
import android.graphics.Shader;
import android.graphics.RenderEffect;
import android.annotation.SuppressLint;
import android.os.Build;

public class BlurShader {
    public static void showBlur(Activity activity,String x,String y) {
        int r=Integer.parseInt(x);
        int v=Integer.parseInt(y);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            activity.getWindow().getDecorView().setRenderEffect(
                    RenderEffect.createBlurEffect(r, v, Shader.TileMode.CLAMP));
        }
    }

    public static void removeBlur(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            activity.getWindow().getDecorView().setRenderEffect(null);
        }
    }
}

