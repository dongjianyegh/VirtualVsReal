package com.example.virtual.view;

import android.graphics.Rect;

public interface GridParent {
    void animateChildInParent(Grid grid);

    GridParent getParent();

    int getParentScrollX();

    int getParentScrollY();

    void invalidateChild(Grid grid, Rect rect);

    void requestRootLayout();

    void unanimateChildInParent(Grid grid);
}
