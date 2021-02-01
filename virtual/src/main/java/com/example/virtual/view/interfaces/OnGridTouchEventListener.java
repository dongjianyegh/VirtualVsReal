package com.example.virtual.view.interfaces;

import android.view.MotionEvent;

import com.example.virtual.view.Grid;

public interface OnGridTouchEventListener {
    public boolean onTouchEvent(Grid grid, MotionEvent motionEvent);
}
