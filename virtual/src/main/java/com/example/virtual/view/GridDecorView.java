package com.example.virtual.view;

import android.graphics.Rect;
import android.util.Log;

import com.example.virtual.BuildConfig;

public class GridDecorView implements GridParent {
    private final GridRootView mRootView;

    public GridDecorView(GridRootView gridRootView) {
        this.mRootView = gridRootView;
    }

    @Override // com.iflytek.inputmethod.common.view.widget.GridParent
    public void invalidateChild(Grid grid, Rect rect) {
        if (BuildConfig.DEBUG) {
//            //Log.i("GridRootView", "invalidate : " + rect.toShortString());
        }
        if (!Grid.CLIPPING || grid.isGridLightAnimating()) {
            this.mRootView.invalidate();
            this.mRootView.mAttachInfo.mDirty.set(this.mRootView.getLeft(), this.mRootView.getTop(), this.mRootView.getRight(), this.mRootView.getBottom());
            return;
        }
        this.mRootView.mAttachInfo.mDirty.union(rect.left, rect.top, rect.right, rect.bottom);
        this.mRootView.invalidate(rect);
    }

    @Override // com.iflytek.inputmethod.common.view.widget.GridParent
    public GridParent getParent() {
        return this;
    }

    @Override // com.iflytek.inputmethod.common.view.widget.GridParent
    public int getParentScrollX() {
        return 0;
    }

    @Override // com.iflytek.inputmethod.common.view.widget.GridParent
    public int getParentScrollY() {
        return 0;
    }

    @Override // com.iflytek.inputmethod.common.view.widget.GridParent
    public void animateChildInParent(Grid grid) {
    }

    @Override // com.iflytek.inputmethod.common.view.widget.GridParent
    public void unanimateChildInParent(Grid grid) {
    }

    @Override // com.iflytek.inputmethod.common.view.widget.GridParent
    public void requestRootLayout() {
        this.mRootView.requestLayout();
        this.mRootView.invalidate();
    }
}
