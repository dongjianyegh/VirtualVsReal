package com.example.virtual.view.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;

import static android.graphics.PixelFormat.OPAQUE;
import static android.graphics.PixelFormat.TRANSLUCENT;
import static android.graphics.PixelFormat.TRANSPARENT;

public class SingleColorDrawable extends AbsDrawable {
    private int mIntrinsicHeight;
    private int mIntrinsicWidth;
    private boolean mMutated;
    private a mState;

    public SingleColorDrawable() {
        this((a) null);
    }

    public SingleColorDrawable(int i) {
        this((a) null);
        setColor(i);
    }

    public SingleColorDrawable(int i, int i2, int i3) {
        this((a) null);
        setColor(i);
        setIntrinsicWidth(i2);
        setIntrinsicHeight(i3);
    }

    public int getIntrinsicWidth() {
        return this.mIntrinsicWidth;
    }

    public int getIntrinsicHeight() {
        return this.mIntrinsicHeight;
    }

    public void setIntrinsicWidth(int i) {
        this.mIntrinsicWidth = i;
    }

    public void setIntrinsicHeight(int i) {
        this.mIntrinsicHeight = i;
    }

    private SingleColorDrawable(a aVar) {
        this.mIntrinsicWidth = -1;
        this.mIntrinsicHeight = -1;
        this.mState = new a(aVar);
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mState.c;
    }

    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = new a(this.mState);
            this.mMutated = true;
        }
        return this;
    }

    public void draw(Canvas canvas) {
        if ((this.mState.b >>> 24) != 0 && !getBounds().isEmpty()) {
            int save = canvas.save();
            canvas.clipRect(getBounds());
            canvas.drawColor(this.mState.b);
            canvas.restoreToCount(save);
        }
    }

    public int getColor() {
        return this.mState.b;
    }

    public void setColor(int i) {
        if (this.mState.a != i || this.mState.b != i) {
            invalidateSelf();
            a aVar = this.mState;
            this.mState.b = i;
            aVar.a = i;
        }
    }

    public int getAlpha() {
        return this.mState.b >>> 24;
    }

    public void setAlpha(int i) {
        int i2 = this.mState.b;
        this.mState.b = (((((i >> 7) + i) * (this.mState.a >>> 24)) >> 8) << 24) | ((this.mState.a << 8) >>> 8);
        if (i2 != this.mState.b) {
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        switch (this.mState.b >>> 24) {
            case 0:
                return TRANSPARENT;
            case 255:
                return OPAQUE;
            default:
                return TRANSLUCENT;
        }
    }

    public ConstantState getConstantState() {
        this.mState.c = getChangingConfigurations();
        return this.mState;
    }

    /* access modifiers changed from: package-private */
    public static final class a extends ConstantState {
        int a;
        int b;
        int c;

        a(a aVar) {
            if (aVar != null) {
                this.a = aVar.a;
                this.b = aVar.b;
                this.c = aVar.c;
            }
        }

        public Drawable newDrawable() {
            return new SingleColorDrawable(this);
        }

        public Drawable newDrawable(Resources resources) {
            return new SingleColorDrawable(this);
        }

        public int getChangingConfigurations() {
            return this.c;
        }
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.AbsDrawable
    public void scale(float f) {
    }

    @Override // com.iflytek.inputmethod.common.view.widget.drawable.AbsDrawable
    public void setColorFilter(SparseIntArray sparseIntArray) {
        int i = sparseIntArray.get(0, 4178531);
        if (i != 4178531) {
            setColor(i);
        }
    }
}
