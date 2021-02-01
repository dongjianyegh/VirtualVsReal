package com.example.virtual.view.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.SparseIntArray;

import com.example.virtual.view.constants.KeyState;

public class MultiColorTextDrawable extends TextDrawable {
    private SparseIntArray mColors;
    private int mCurColor;
    private int[] mCurState;
    private TextDrawable mDrawable;

    public MultiColorTextDrawable() {
        this(null);
    }

    public MultiColorTextDrawable(TextDrawable textDrawable) {
        this.mColors = new SparseIntArray(KeyState.count());
        this.mDrawable = textDrawable;
    }

    public TextDrawable getTextDrawable() {
        return this.mDrawable == null ? this : this.mDrawable;
    }

    public void addColor(int[] iArr, int i) {
        if (iArr != null && iArr.length > 0) {
            this.mColors.put(iArr[0], i);
            onStateChange(getState());
        }
    }

    public void addColor(int i, int i2) {
        this.mColors.put(i, i2);
        onStateChange(getState());
    }

    public int getColor(int[] iArr) {
        if (iArr != null && iArr.length > 0) {
            return this.mColors.get(iArr[0]);
        }
        throw new IllegalArgumentException("state array : " + iArr + " is invalid.");
    }

    public boolean setState(int[] iArr) {
        return onStateChange(iArr);
    }

    public int[] getState() {
        return this.mCurState == null ? KeyState.NORMAL_SET : this.mCurState;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        if (iArr == null || iArr.length <= 0) {
            return false;
        }
        if (this.mCurState != null && this.mCurState[0] == iArr[0] && this.mCurColor == this.mColors.get(this.mCurState[0])) {
            return false;
        }
        this.mCurState = iArr;
        this.mCurColor = this.mColors.get(this.mCurState[0]);
        setTextColor(this.mCurColor);
        return true;
    }

    public boolean isStateful() {
        return true;
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public void setIgnoreSpace(boolean z) {
        if (this.mDrawable == null) {
            super.setIgnoreSpace(z);
        } else {
            this.mDrawable.setIgnoreSpace(z);
        }
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public boolean isIgnoreSpace() {
        if (this.mDrawable == null) {
            return super.isIgnoreSpace();
        }
        return this.mDrawable.isIgnoreSpace();
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public void draw(Canvas canvas) {
        if (this.mDrawable == null) {
            super.draw(canvas);
        } else {
            this.mDrawable.draw(canvas);
        }
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    @Deprecated
    public void setTextColor(int i) {
        if (this.mDrawable == null) {
            super.setTextColor(i);
        } else {
            this.mDrawable.setTextColor(i);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public void onBoundsChange(Rect rect) {
        if (this.mDrawable == null) {
            super.onBoundsChange(rect);
        } else {
            this.mDrawable.setBounds(rect);
        }
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public void setText(String str) {
        if (this.mDrawable == null) {
            super.setText(str);
        } else {
            this.mDrawable.setText(str);
        }
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public String getText() {
        if (this.mDrawable == null) {
            return super.getText();
        }
        return this.mDrawable.getText();
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public void setTextSize(float f) {
        if (this.mDrawable == null) {
            super.setTextSize(f);
        } else {
            this.mDrawable.setTextSize(f);
        }
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public float getScaleTextSize() {
        if (this.mDrawable == null) {
            return super.getScaleTextSize();
        }
        return this.mDrawable.getScaleTextSize();
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public float getOriTextSize() {
        if (this.mDrawable == null) {
            return super.getOriTextSize();
        }
        return this.mDrawable.getOriTextSize();
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public void setAlign(int i) {
        if (this.mDrawable == null) {
            super.setAlign(i);
        } else {
            this.mDrawable.setAlign(i);
        }
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public int getAlign() {
        if (this.mDrawable == null) {
            return super.getAlign();
        }
        return this.mDrawable.getAlign();
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public Paint getPaint() {
        if (this.mDrawable == null) {
            return super.getPaint();
        }
        return this.mDrawable.getPaint();
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public int getIntrinsicWidth() {
        if (this.mDrawable == null) {
            return super.getIntrinsicWidth();
        }
        return this.mDrawable.getIntrinsicWidth();
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public int getIntrinsicHeight() {
        if (this.mDrawable == null) {
            return super.getIntrinsicHeight();
        }
        return this.mDrawable.getIntrinsicHeight();
    }

    @Override
    // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable, com.iflytek.inputmethod.common.view.widget.drawable.AbsDrawable
    public void scale(float f) {
        if (this.mDrawable == null) {
            super.scale(f);
        } else {
            this.mDrawable.scale(f);
        }
    }

    @Override
    // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable, com.iflytek.inputmethod.common.view.widget.drawable.AbsDrawable
    public void setColorFilter(SparseIntArray sparseIntArray) {
        if (this.mDrawable == null) {
            super.setColorFilter(sparseIntArray);
        } else {
            this.mDrawable.setColorFilter(sparseIntArray);
        }
        for (int i = 0; i < this.mColors.size(); i++) {
            int keyAt = this.mColors.keyAt(i);
            int i2 = sparseIntArray.get(keyAt, 4178531);
            if (i2 != 4178531) {
                addColor(keyAt, i2);
            }
        }
        setTextColor(getColor(getState()));
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public void setTextDrawingProxy(TextDrawingProxy textDrawingProxy) {
        if (this.mDrawable != null) {
            this.mDrawable.setTextDrawingProxy(textDrawingProxy);
        } else {
            super.setTextDrawingProxy(textDrawingProxy);
        }
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.TextDrawable
    public TextDrawingProxy getTextDrawingProxy() {
        if (this.mDrawable != null) {
            return this.mDrawable.getTextDrawingProxy();
        }
        return super.getTextDrawingProxy();
    }
}
