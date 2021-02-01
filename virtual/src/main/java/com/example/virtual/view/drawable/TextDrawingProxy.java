package com.example.virtual.view.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public interface TextDrawingProxy {

    public interface PaintProxy {
        int breakText(String str, int i, int i2, boolean z, float f, float[] fArr);

        Paint getDelegatedPaint();

        Paint.FontMetrics getFontMetrics(Paint.FontMetrics fontMetrics);

        void getTextBounds(String str, int i, int i2, Rect rect);

        float measureText(String str, int i, int i2);

        void setTextSize(float f);

        boolean supportGetTextBounds();

        boolean supportMultiLine();
    }

    void drawText(TextDrawable textDrawable, Canvas canvas, String str, int i, int i2, float f, float f2, boolean z);

    TextDrawingProxy getAnimationDrawingProxy();

    PaintProxy getPaint();

    float getX(TextDrawable textDrawable, Rect rect, float f);

    float getY(TextDrawable textDrawable, Rect rect, Paint.FontMetrics fontMetrics);

    void onBoundsChanged(TextDrawable textDrawable, Rect rect);

    void release();
}
