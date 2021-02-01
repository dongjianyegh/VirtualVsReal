package com.example.virtual.view;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

public class AttachInfo {
    private GridRootView mAttachedView;
    public final Rect mDirty = new Rect();
    private Grid mRootGrid;

    public AttachInfo(GridRootView gridRootView) {
        this.mAttachedView = gridRootView;
    }

    public Grid getRootGrid() {
        return this.mRootGrid;
    }

    public View getAttachedView() {
        return this.mAttachedView;
    }

    /* access modifiers changed from: package-private */
    public void setRootGrid(Grid grid) {
        this.mRootGrid = grid;
    }

    public void onBoundsChange(Grid grid, int i, int i2, int i3, int i4) {
        ViewGroup.LayoutParams layoutParams;
        if (grid == this.mAttachedView.mContentGrid) {
            if ((grid.mWidth != i3 - i || grid.mHeight != i4 - i2) && (layoutParams = this.mAttachedView.getLayoutParams()) != null) {
                if (layoutParams.width == -2 || layoutParams.height == -2) {
                    this.mAttachedView.requestLayout();
                    this.mAttachedView.invalidate();
                }
            }
        }
    }

    public void onGlobalGridAdded(GridGroup gridGroup, Grid grid) {
        this.mAttachedView.onGlobalGridAdded(gridGroup, grid);
    }

    public void onGlobalGridRemoved(GridGroup gridGroup, Grid grid) {
        this.mAttachedView.onGlobalGridRemoved(gridGroup, grid);
    }

    public void onGlobalGridVisibilityChanged(Grid grid, int i) {
        this.mAttachedView.onGlobalGridVisibilityChanged(grid, i);
    }

    public void onGlobalGridAccessibilityModeChanged(Grid grid, boolean z) {
        this.mAttachedView.onGlobalGridAccessibilityModeChanged(grid, z);
    }

    public void onGlobalGridGroupScrollChanged(GridGroup gridGroup, int i, int i2, int i3, int i4) {
        this.mAttachedView.onGlobalGridGroupScrollChanged(gridGroup, i, i2, i3, i4);
    }

    public void invalidateAccessibility(int i) {
        this.mAttachedView.invalidateAccessibility(i);
    }
}
