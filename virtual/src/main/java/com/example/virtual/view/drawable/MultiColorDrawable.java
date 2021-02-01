package com.example.virtual.view.drawable;

import android.graphics.ColorFilter;
import android.util.SparseIntArray;

import com.example.virtual.view.constants.KeyState;

public class MultiColorDrawable extends SingleColorDrawable {
    private SparseIntArray mColors = new SparseIntArray(KeyState.count());
    private int mCurColor;
    private int[] mCurState;

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
        setColor(this.mCurColor);
        return true;
    }

    public boolean isStateful() {
        return true;
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.SingleColorDrawable
    public void setAlpha(int i) {
        super.setAlpha(i);
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.SingleColorDrawable
    public void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.SingleColorDrawable
    public int getOpacity() {
        return super.getOpacity();
    }

    @Override
    // com.iflytek.inputmethod.common.view.widget.drawable.AbsDrawable, com.iflytek.inputmethod.common.view.widget.drawable.SingleColorDrawable
    public void setColorFilter(SparseIntArray sparseIntArray) {
        super.setColorFilter(sparseIntArray);
        for (int i = 0; i < this.mColors.size(); i++) {
            int keyAt = this.mColors.keyAt(i);
            int i2 = sparseIntArray.get(keyAt, 4178531);
            if (i2 != 4178531) {
                addColor(keyAt, i2);
            }
        }
        setColor(getColor(getState()));
    }

    public SparseIntArray getAllColors() {
        return this.mColors;
    }
}
