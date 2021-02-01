package com.example.virtualvsreal.realinput;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import com.example.virtual.view.drawable.AbsDrawable;

import java.util.ArrayList;

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
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
    }
}