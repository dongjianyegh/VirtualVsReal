package com.example.virtual.view.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;

import com.example.virtual.BuildConfig;
import com.example.virtual.view.Grid;

import java.util.WeakHashMap;

public class TextDrawable extends AbsDrawable {
    public static final int ALIGH_CENTER = 0;
    public static final int ALIGH_LEFT = 1;
    public static final int ALIGH_RIGHT = 2;
    protected static final Paint.FontMetrics FONT_METRICS = new Paint.FontMetrics();
    private static final int SHADER_DIRECTION_BL = 4;
    private static final int SHADER_DIRECTION_L = 1;
    private static final int SHADER_DIRECTION_T = 2;
    private static final int SHADER_DIRECTION_TL = 3;
    private static final String TAG = "TextDrawable";
    protected Paint.Align mAlign = Paint.Align.CENTER;
    private int mDirection;
    protected boolean mDirty;
    private int mDrawTextLength;
    protected TextDrawingProxy mDrawingProxy;
    private FontConfig mFontConfig;
    protected Paint.FontMetrics mFontMetrics;
    protected boolean mIgnoreSpace;
    private WeakHashMap<TextDrawable, Boolean> mMergedTextDrawableMap;
    private int mOriAlpha = 255;
    protected int mOriginTextColor = 4178531;
    private float mOriginTextSize;
    protected float mScaleTextSize;
    private Shader mShader;
    private float[] mShaderColorPositions;
    private int[] mShaderColors;
    private boolean mShaderDirty = false;
    private Shader.TileMode mShaderTileMode;
    protected String mText;
    protected int mTextColor = 4178531;
    private int mTextHeight = -1;
    private int mTextWidth = -1;
    private boolean mUseShader = false;
    protected float mX;
    protected float mY;

    private void addMergedTextDrawable(TextDrawable textDrawable) {
        synchronized (this) {
            if (textDrawable != null) {
                if (this.mMergedTextDrawableMap == null) {
                    this.mMergedTextDrawableMap = new WeakHashMap<>();
                }
                this.mMergedTextDrawableMap.put(textDrawable, Boolean.TRUE);
            }
        }
    }

    public void setIgnoreSpace(boolean z) {
        this.mIgnoreSpace = z;
    }

    public boolean isIgnoreSpace() {
        return this.mIgnoreSpace;
    }

    public int getIntrinsicWidth() {
        if (TextUtils.isEmpty(this.mText) || this.mScaleTextSize < 1.0f) {
            return 0;
        }
        if (this.mDrawingProxy != null) {
            if (this.mTextWidth < 0 || this.mDirty) {
                TextDrawingProxy.PaintProxy paint = this.mDrawingProxy.getPaint();
                paint.setTextSize(this.mScaleTextSize);
                this.mTextWidth = ((int) paint.measureText(this.mText, 0, this.mText.length())) + 1;
            }
            return this.mTextWidth;
        } else if (!BuildConfig.DEBUG) {
            return 0;
        } else {
            Log.e(TAG, "drawing proxy is null!");
            return 0;
        }
    }

    public int getIntrinsicHeight() {
        if (TextUtils.isEmpty(this.mText) || this.mScaleTextSize < 1.0f) {
            return 0;
        }
        if (this.mDrawingProxy != null) {
            if (this.mTextHeight < 0 || this.mDirty) {
                TextDrawingProxy.PaintProxy paint = this.mDrawingProxy.getPaint();
                paint.setTextSize(this.mScaleTextSize);
                this.mFontMetrics = paint.getFontMetrics(FONT_METRICS);
                this.mTextHeight = ((int) (this.mFontMetrics.bottom - this.mFontMetrics.top)) + 1;
            }
            return this.mTextHeight;
        } else if (!BuildConfig.DEBUG) {
            return 0;
        } else {
            Log.e(TAG, "drawing proxy is null!");
            return 0;
        }
    }

    public Paint getPaint() {
        return this.mDrawingProxy.getPaint().getDelegatedPaint();
    }

    public void draw(Canvas canvas) {
        if (this.mDrawingProxy == null && BuildConfig.DEBUG) {
            Log.e(TAG, "drawing proxy is null!");
        }
        if (this.mText != null && this.mText.length() != 0) {
            if (this.mDirty) {
                measure();
                this.mDirty = false;
            }
            onDraw(canvas);
        } else if (BuildConfig.DEBUG) {
            Log.w(TAG, "the text to be draw is empty.");
        }
    }

    /* access modifiers changed from: protected */
    public boolean measure() {
        if (this.mScaleTextSize < 1.0f || TextUtils.isEmpty(this.mText) || this.mDrawingProxy == null) {
            return false;
        }
        TextDrawingProxy.PaintProxy paint = this.mDrawingProxy.getPaint();
        paint.setTextSize(this.mScaleTextSize);
        this.mFontMetrics = paint.getFontMetrics(FONT_METRICS);
        this.mTextWidth = ((int) paint.measureText(this.mText, 0, this.mText.length())) + 1;
        this.mTextHeight = ((int) (this.mFontMetrics.bottom - this.mFontMetrics.top)) + 1;
        Rect bounds = getBounds();
        if (!this.mIgnoreSpace || !paint.supportGetTextBounds()) {
            this.mX = this.mDrawingProxy.getX(this, bounds, (float) this.mTextWidth);
        } else {
            paint.getTextBounds(this.mText, 0, this.mText.length(), Grid.mTmpInvalRect);
            this.mX = (float) getPositionIgnoreSpace(bounds, Grid.mTmpInvalRect);
        }
        this.mY = this.mDrawingProxy.getY(this, bounds, this.mFontMetrics);
        this.mDirty = false;
        if (this.mShaderDirty) {
            configShader();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mDrawingProxy != null && this.mDrawTextLength > 0) {
            this.mDrawingProxy.drawText(this, canvas, this.mText, 0, this.mDrawTextLength, this.mX, this.mY, true);
        }
    }

    /* access modifiers changed from: protected */
    public int getPositionX() {
        Rect bounds = getBounds();
        switch (mAlign) {
            case LEFT:
                return bounds.left;
            case RIGHT:
                return bounds.right;
            default:
                return bounds.centerX();
        }
    }

    /* access modifiers changed from: protected */
    public int getPositionIgnoreSpace(Rect rect, Rect rect2) {
        switch (mAlign) {
            case LEFT:
                return rect.left - rect2.left;
            case RIGHT:
                return (rect.right - rect2.width()) - rect2.left;
            default:
                return (rect.left + ((rect.width() - rect2.width()) / 2)) - rect2.left;
        }
    }

    public void setText(String str) {
        if (str != null && str.equals(this.mText)) {
            return;
        }
        if (str != null || this.mText != null) {
            this.mText = str;
            this.mDrawTextLength = str != null ? str.length() : 0;
            this.mDirty = true;
        }
    }

    public void setTextDrawLengthProgress(float f) {
        if (TextUtils.isEmpty(this.mText) || this.mDrawingProxy == null) {
            this.mDrawTextLength = 0;
        }
        int length = (int) (((double) (((float) this.mText.length()) * f)) + 0.5d);
        if (this.mDrawTextLength != length) {
            this.mDrawTextLength = length;
            if (length != 0 && measure()) {
                TextDrawingProxy.PaintProxy paint = this.mDrawingProxy.getPaint();
                this.mX -= (paint.measureText(this.mText, 0, this.mText.length()) - paint.measureText(this.mText, 0, length)) / 2.0f;
            }
        }
    }

    public void setAlign(int i) {
        Paint.Align align;
        switch (i) {
            case 0:
                align = Paint.Align.CENTER;
                break;
            case 1:
                align = Paint.Align.LEFT;
                break;
            case 2:
                align = Paint.Align.RIGHT;
                break;
            default:
                align = Paint.Align.CENTER;
                break;
        }
        if (this.mAlign != align) {
            this.mAlign = align;
            this.mDirty = true;
        }
    }

    public int getAlign() {
        switch (mAlign) {
            case LEFT:
            default:
                return 0;
            case CENTER:
                return 1;
            case RIGHT:
                return 2;
        }
    }

    public Paint.Align getAlignReal() {
        return this.mAlign;
    }

    public String getText() {
        return this.mText;
    }

    public void setTextColor(int i) {
        if (this.mTextColor != i) {
            this.mTextColor = i;
            this.mOriAlpha = Color.alpha(i);
        }
    }

    public void saveColorBeforSetColorFilter() {
        if (this.mOriginTextColor != this.mTextColor) {
            this.mOriginTextColor = this.mTextColor;
        }
    }

    public void resetOriginTextColor() {
        if (4178531 != this.mOriginTextColor) {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sparseIntArray.put(0, this.mOriginTextColor);
            setColorFilter(sparseIntArray);
            this.mOriginTextColor = 4178531;
        }
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public void setTextSize(float f) {
        if (this.mScaleTextSize - f < -0.01f || this.mScaleTextSize - f > 0.01f) {
            this.mOriginTextSize = f;
            this.mScaleTextSize = this.mOriginTextSize;
            this.mDirty = true;
        }
    }

    public void setOriginTextSize(float f) {
        this.mOriginTextSize = f;
        this.mDirty = true;
    }

    public float getScaleTextSize() {
        return this.mScaleTextSize;
    }

    public float getTextSize() {
        return this.mScaleTextSize;
    }

    public float getOriTextSize() {
        return this.mOriginTextSize;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.mDirty = true;
        if (this.mDrawingProxy != null) {
            this.mDrawingProxy.onBoundsChanged(this, rect);
        }
    }

    public void setAlpha(int i) {
        if (this.mOriAlpha != 0) {
            this.mTextColor = Color.argb(i, Color.red(this.mTextColor), Color.green(this.mTextColor), Color.blue(this.mTextColor));
        }
    }

    public void resetAlpha() {
        this.mTextColor = Color.argb(this.mOriAlpha, Color.red(this.mTextColor), Color.green(this.mTextColor), Color.blue(this.mTextColor));
    }

    @Deprecated
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Deprecated
    public int getOpacity() {
        return 0;
    }

    public void setFontConfig(FontConfig fontConfig) {
        this.mFontConfig = fontConfig;
    }

    public FontConfig getFontConfig() {
        return this.mFontConfig;
    }

    public void setTextDrawingProxy(TextDrawingProxy textDrawingProxy) {
        this.mDrawingProxy = textDrawingProxy;
        synchronized (this) {
            if (this.mMergedTextDrawableMap != null) {
                for (TextDrawable textDrawable : this.mMergedTextDrawableMap.keySet()) {
                    if (!(textDrawable == null || this == textDrawable)) {
                        textDrawable.setTextDrawingProxy(textDrawingProxy);
                    }
                }
            }
        }
        this.mDirty = true;
    }

    public TextDrawingProxy getTextDrawingProxy() {
        return this.mDrawingProxy;
    }

    @Override // AbsDrawable
    public void scale(float f) {
        float f2 = this.mScaleTextSize;
        this.mScaleTextSize = this.mOriginTextSize * f;
        if (((double) Math.abs(this.mScaleTextSize - f2)) >= 0.01d) {
            this.mDirty = true;
            if (this.mShader != null) {
                this.mShaderDirty = true;
            }
        }
    }

    public void setUseShader(boolean z) {
        this.mUseShader = z;
    }

    public boolean isUseShader() {
        return this.mUseShader;
    }

    public void setShaderParams(int i, int[] iArr, float[] fArr, Shader.TileMode tileMode) {
        this.mDirection = i;
        this.mShaderColors = iArr;
        this.mShaderColorPositions = fArr;
        this.mShaderTileMode = tileMode;
        if (measure()) {
            configShader();
        }
    }

    private void configShader() {
        switch (this.mDirection) {
            case 1:
                this.mShader = new LinearGradient(this.mX, this.mY, this.mX + ((float) this.mTextWidth), this.mY, this.mShaderColors, this.mShaderColorPositions, this.mShaderTileMode);
                return;
            case 2:
                this.mShader = new LinearGradient(this.mX, this.mY, this.mX, this.mY + ((float) this.mTextHeight), this.mShaderColors, this.mShaderColorPositions, this.mShaderTileMode);
                return;
            case 3:
                this.mShader = new LinearGradient(this.mX, this.mY, this.mX + ((float) this.mTextWidth), this.mY + ((float) this.mTextHeight), this.mShaderColors, this.mShaderColorPositions, this.mShaderTileMode);
                return;
            case 4:
                this.mShader = new LinearGradient(this.mX, this.mY + ((float) this.mTextHeight), this.mX + ((float) this.mTextWidth), this.mY, this.mShaderColors, this.mShaderColorPositions, this.mShaderTileMode);
                return;
            default:
                this.mShader = new LinearGradient(this.mX, this.mY, this.mX + ((float) this.mTextWidth), this.mY, this.mShaderColors, this.mShaderColorPositions, this.mShaderTileMode);
                return;
        }
    }

    public Shader getShader() {
        return this.mShader;
    }

    @Override // AbsDrawable
    public void setColorFilter(SparseIntArray sparseIntArray) {
        int i = sparseIntArray.get(0, 4178531);
        if (i != 4178531) {
            setTextColor(i);
        }
    }

    public void scaleLastTextSize(float f) {
        float f2 = this.mScaleTextSize;
        this.mScaleTextSize *= f;
        if (((double) Math.abs(this.mScaleTextSize - f2)) >= 0.01d) {
            this.mDirty = true;
        }
    }

    public static final class FontConfig {
        private String mFontPath;
        private boolean mIsIcon;
        private boolean mIsInAsset;

        public FontConfig(String str, boolean z, boolean z2) {
            this.mFontPath = str;
            this.mIsInAsset = z;
            this.mIsIcon = z2;
        }

        public String getFontPath() {
            return this.mFontPath;
        }

        public boolean isInAsset() {
            return this.mIsInAsset;
        }

        public boolean isIcon() {
            return this.mIsIcon;
        }
    }
}
