package com.example.virtualvsreal.virtualinput;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Pair;

import com.example.virtual.view.Grid;
import com.example.virtual.view.constants.KeyState;
import com.example.virtual.view.drawable.AbsDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongjianye on 1/29/21
 */
public class KeyGrid extends Grid {

    private ArrayList<Pair<Rect, AbsDrawable>> mForegroundDrawables;
    private int mProperties;

    private int mCurState = KeyState.NORMAL;

    public KeyGrid(Context context) {
        super(context);
    }

    public boolean isEnabled() {
        return (mProperties & 2) == 2;
    }

    public void setEnabled(boolean z) {
        if (z != isEnabled()) {
            setProperty(z ? 2 : 0, 2);
            invalidateInternal();
        }
    }

    private void setProperty(int i2, int i3) {
        this.mProperties = (this.mProperties & (i3 ^ -1)) | (i2 & i3);
    }

    @Override
    public void draw(Canvas canvas) {
        drawBackground(canvas);
        drawForegrounds(canvas, null);
    }

    @Override
    public void drawBackground(Canvas canvas) {
        if (this.mBackground != null) {
            drawContent(canvas, getBounds(mTmpInvalRect), this.mBackground, mCurState);
        }
    }

    public void drawForegrounds(Canvas canvas, List<AbsDrawable> list) {
        if (this.mForegroundDrawables != null) {
            for (int size = this.mForegroundDrawables.size() - 1; size >= 0; size--) {
                Pair<Rect, AbsDrawable> pair = this.mForegroundDrawables.get(size);
                if (list == null || !list.contains(pair.second)) {
                    drawContent(canvas, pair.first, pair.second, mCurState);
                }
            }
        }
    }

    public void addForegroundDrawable(Pair<Rect, AbsDrawable> pair) {
        if (pair != null) {
            if (this.mForegroundDrawables == null) {
                this.mForegroundDrawables = new ArrayList<>(3);
            }
            this.mForegroundDrawables.add(pair);
        }
    }

    public boolean isPressed() {
        return (this.mProperties & 32) == 32;
    }

    public void setPressed(boolean z) {
        if (z != isPressed()) {
            setProperty(z ? 32 : 0, 32);
            invalidateInternal();
        }
    }

    public boolean isSelected() {
        return (this.mProperties & 16) == 16;
    }

    public void setSelected(boolean z) {
        if (z != isSelected()) {
            setProperty(z ? 16 : 0, 16);
            invalidateInternal();
        }
    }

    public boolean isFocused() {
        return (this.mProperties & 64) == 64;
    }

    public void setFocused(boolean z) {
        if (z != isFocused()) {
            setProperty(z ? 64 : 0, 64);
            invalidateInternal();
        }
    }

    private void invalidateInternal() {
        int drawableState;
        if (!isEnabled()) {
            drawableState = KeyState.DISABLE;
        } else if (isPressed()) {
            drawableState = KeyState.PRESSED;
        } else if (isSelected()) {
            drawableState = KeyState.SELECTED;
        } else {
            drawableState = isFocused() ? KeyState.FOCUSED : KeyState.NORMAL;
        }
        if (!isGridContentAnimating()) {
            if (this.mCurState != drawableState) {
                this.mCurState = drawableState;
                invalidateDrawable(getBounds(mTmpInvalRect), this.mBackground);
                int size = this.mForegroundDrawables == null ? 0 : this.mForegroundDrawables.size();
                for (int i3 = 0; i3 < size; i3++) {
                    Pair<Rect, AbsDrawable> pair = this.mForegroundDrawables.get(i3);
                    invalidateDrawable((Rect) pair.first, (Drawable) pair.second);
                }
            }
        }
    }

    private void invalidateDrawable(Rect rect, Drawable drawable) {
        if (drawable != null && drawable.isStateful()) {
            invalidate(rect);
        }
    }
}