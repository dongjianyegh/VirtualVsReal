package com.example.virtualvsreal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.virtual.view.drawable.TextDrawable;
import com.example.virtual.view.drawable.TextDrawingProxy;

public class SimpleTextDraw implements TextDrawingProxy {
    public Paint a;
    public TextDrawingProxy.PaintProxy b;

    SimpleTextDraw() {
        this.a = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy
    public void drawText(TextDrawable textDrawable, Canvas canvas, String str, int i, int i2, float f, float f2, boolean z) {
        if (textDrawable.getScaleTextSize() >= 1.0f) {
            this.a.setAntiAlias(true);
            this.a.setColor(textDrawable.getTextColor());
            this.a.setTextSize(textDrawable.getScaleTextSize());
            if (textDrawable.isUseShader()) {
                this.a.setShader(textDrawable.getShader());
            } else {
                this.a.setShader(null);
            }
            if (textDrawable.isIgnoreSpace()) {
                this.a.setTextAlign(Paint.Align.LEFT);
            } else {
                this.a.setTextAlign(textDrawable.getAlignReal());
            }
            canvas.drawText(str, i, i2, f, f2, this.a);
        }
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy
    public void onBoundsChanged(TextDrawable textDrawable, Rect rect) {
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy
    public TextDrawingProxy.PaintProxy getPaint() {
        if (this.b == null) {
            this.b = new a(this.a);
        }
        return this.b;
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy
    public TextDrawingProxy getAnimationDrawingProxy() {
        return this;
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy
    public float getX(TextDrawable textDrawable, Rect rect, float f) {
        switch (textDrawable.getAlign()) {
            case 1:
                return (float) rect.centerX();
            case 2:
                return (float) rect.left;
            case 3:
                return (float) rect.right;
            default:
                return (float) rect.centerX();
        }
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy
    public float getY(TextDrawable textDrawable, Rect rect, Paint.FontMetrics fontMetrics) {
        return (((float) rect.top) + ((((float) (rect.bottom - rect.top)) - (fontMetrics.bottom - fontMetrics.top)) / 2.0f)) - fontMetrics.top;
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy
    public void release() {
    }

    public static class a implements TextDrawingProxy.PaintProxy {
        public Paint a;

        a(Paint paint) {
            this.a = paint;
        }

        @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy.PaintProxy
        public void setTextSize(float f) {
            this.a.setTextSize(f);
        }

        @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy.PaintProxy
        public float measureText(String str, int i, int i2) {
            return this.a.measureText(str, i, i2);
        }

        @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy.PaintProxy
        public boolean supportMultiLine() {
            return true;
        }

        @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy.PaintProxy
        public int breakText(String str, int i, int i2, boolean z, float f, float[] fArr) {
            return this.a.breakText(str, i, i2, z, f, fArr);
        }

        @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy.PaintProxy
        public Paint.FontMetrics getFontMetrics(Paint.FontMetrics fontMetrics) {
            this.a.getFontMetrics(fontMetrics);
            return fontMetrics;
        }

        @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy.PaintProxy
        public boolean supportGetTextBounds() {
            return true;
        }

        @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy.PaintProxy
        public void getTextBounds(String str, int i, int i2, Rect rect) {
            this.a.getTextBounds(str, i, i2, rect);
        }

        @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawingProxy.PaintProxy
        public Paint getDelegatedPaint() {
            return this.a;
        }
    }
}
