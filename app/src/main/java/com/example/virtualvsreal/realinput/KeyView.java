package com.example.virtualvsreal.realinput;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import com.example.virtual.view.constants.KeyState;
import com.example.virtual.view.drawable.AbsDrawable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author dongjianye on 2/1/21
 */
public class KeyView extends View {

    private ArrayList<Pair<Rect, AbsDrawable>> mForegroundDrawables;

    public KeyView(Context context) {
        super(context);
    }

    public KeyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawForegrounds(canvas, null);
    }

    public void addForegroundDrawable(Pair<Rect, AbsDrawable> pair) {
        if (pair != null) {
            if (this.mForegroundDrawables == null) {
                this.mForegroundDrawables = new ArrayList<>(3);
            }
            this.mForegroundDrawables.add(pair);
        }
    }

    public void drawForegrounds(Canvas canvas, List<AbsDrawable> list) {
        if (this.mForegroundDrawables != null) {
            for (int size = this.mForegroundDrawables.size() - 1; size >= 0; size--) {
                Pair<Rect, AbsDrawable> pair = this.mForegroundDrawables.get(size);
                if (list == null || !list.contains(pair.second)) {
                    drawContent(canvas, pair.first, pair.second, isPressed() ? KeyState.PRESSED : KeyState.NORMAL);
                }
            }
        }
    }


    public void drawContent(Canvas canvas, Rect rect, Drawable drawable, int state) {
        if (drawable != null && rect != null && !rect.isEmpty()) {
            if (drawable.isStateful()) {
                drawable.setState(KeyState.getStateSet(state));
            }
            drawable.setBounds(rect);
            drawable.draw(canvas);
        }
    }
}