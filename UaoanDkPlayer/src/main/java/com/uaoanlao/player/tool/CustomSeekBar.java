package com.uaoanlao.player.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class CustomSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
    private Paint textPaint;

    public CustomSeekBar(Context context) {
        super(context);
        init();
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textPaint = new Paint();
        textPaint.setColor(android.graphics.Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int progress = getProgress();
        int seconds = progress / 1000;
        String formattedTime = String.format("%02d:%02d", seconds / 60, seconds % 60);
        float thumbX = getThumbOffset() + (float) progress / getMax() * (getWidth() - 2 * getThumbOffset());
        float thumbY = getHeight() / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
        canvas.drawText(formattedTime, thumbX, thumbY, textPaint);
    }
}