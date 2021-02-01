package com.example.virtualvsreal;

import android.content.res.ColorStateList;
import android.graphics.Rect;

import com.example.virtual.view.drawable.AbsDrawable;

/**
 * @author dongjianye on 1/29/21
 */
public class InputInfo {

    public final AbsDrawable mBackground;
    public final AbsDrawable mForeground;
    public final ColorStateList mRealBackground;
    public final Rect mBounds = new Rect();

    public InputInfo(AbsDrawable background, AbsDrawable foreground, ColorStateList stateList) {
        mBackground = background;
        mForeground = foreground;
        mRealBackground = stateList;
    }

    public void setBounds(int left, int top, int right, int bottom) {
        mBounds.left = left;
        mBounds.top = top;
        mBounds.right = right;
        mBounds.bottom = bottom;
    }


}