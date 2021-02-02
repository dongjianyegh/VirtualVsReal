package com.example.virtual.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.example.virtual.BuildConfig;
import com.example.virtual.view.constants.Visibility;
import com.example.virtual.view.interfaces.AttachInterface;

import androidx.annotation.Nullable;

public class GridRootView extends View implements MultiTouchEventHelper.OnMultiTouchEventListener {
    protected static final String TAG = "GridRootView";
    AttachInfo mAttachInfo;
    private Rect mClipRect;
    Grid mContentGrid;
    private GridGroup mDecorGrid;
    private boolean mHasCustomAccessibilityFocus;
    private MultiTouchEventHelper mHelper;
    private AttachInterface mInputInterface;
    private String mName = TAG;
    private GridParent mParent = new GridDecorView(this);
    private boolean mProcessHoverAction = false;

    public GridRootView(Context context) {
        super(context);
        initView();
    }

    public GridRootView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public GridRootView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    @TargetApi(21)
    public GridRootView(Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initView();
    }

    /* access modifiers changed from: protected */
    public void initView() {
        this.mDecorGrid = new GridGroup(getContext());
        this.mDecorGrid.assignParent(this.mParent);
        this.mAttachInfo = new AttachInfo(this);
        this.mAttachInfo.setRootGrid(this.mDecorGrid);
        this.mClipRect = new Rect();
    }

    public void init(AttachInterface attachInterface) {
        this.mInputInterface = attachInterface;
        this.mDecorGrid.dispatchInputInterface(this.mInputInterface);
    }

    public AttachInterface getInputInterface() {
        return this.mInputInterface;
    }

    public void setName(String str) {
        this.mName = str;
    }

    /* access modifiers changed from: protected */
    public Grid getContentGrid() {
        return this.mContentGrid;
    }

    public void setSupportMultiTouch(boolean z) {
        if (!z) {
            this.mHelper = null;
        } else if (this.mHelper == null) {
            this.mHelper = new MultiTouchEventHelper(this);
        }
    }

    public void setContentGrid(Grid grid) {
        int i = 0;
        if (this.mContentGrid != grid) {
            this.mContentGrid = grid;
            this.mDecorGrid.removeAllGrids();
            this.mDecorGrid.addGrid(grid);
            int width = this.mContentGrid == null ? 0 : this.mContentGrid.getWidth();
            if (this.mContentGrid != null) {
                i = this.mContentGrid.getHeight();
            }
            if (getWidth() != width || getHeight() != i) {
                requestLayout();
                invalidate();
            }
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        this.mDecorGrid.setBounds(0, 0, w, h);
    }

    @Override
    public void onMeasure(int i, int i2) {
        int i3 = 0;
        int defaultSize = getDefaultSize(this.mContentGrid != null ? this.mContentGrid.getWidth() : 0, i);
        if (this.mContentGrid != null) {
            i3 = this.mContentGrid.getHeight();
        }
        setMeasuredDimension(defaultSize, getDefaultSize(i3, i2));
    }

    @Override
    public void onDraw(Canvas canvas) {
        this.mAttachInfo.mDirty.setEmpty();
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "start drawing...");
        }
        canvas.getClipBounds(this.mClipRect);

        this.mDecorGrid.draw(canvas);

        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "end drawing...");
        }
    }

    public void cancelAndClearTouchTargets(MotionEvent motionEvent) {
        if (this.mHelper != null) {
            this.mHelper.cancelAndClearTouchTargets(motionEvent);
        } else {
            this.mDecorGrid.cancelAndClearTouchTargets(motionEvent);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean z, int i, @Nullable Rect rect) {
        super.onFocusChanged(z, i, rect);
    }

    @Override
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        boolean z = false;
        int action = motionEvent.getAction();
        z = super.dispatchHoverEvent(motionEvent);

        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "dispatchHoverEvent | action: " + motionEvent.getAction() + " x: " + motionEvent.getX() + ", y: " + motionEvent.getY() + ", result: " + z);
        }
        return z;
    }

    private void clearCustomAccessibilityFocus(MotionEvent motionEvent) {
        if (this.mHasCustomAccessibilityFocus) {
            this.mHasCustomAccessibilityFocus = false;
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            obtain.setAction(3);
            super.dispatchHoverEvent(obtain);
            obtain.recycle();
        }
    }

    @Override
    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "onHoverEvent | action: " + motionEvent.getAction() + " x: " + motionEvent.getX() + ", y: " + motionEvent.getY());
        }
        if (motionEvent.getAction() == 10 && (Math.abs(((float) getWidth()) - motionEvent.getX()) < 10.0f || motionEvent.getX() < 10.0f || Math.abs(((float) getHeight()) - motionEvent.getY()) < 10.0f)) {
            motionEvent.setAction(3);
            return this.mDecorGrid.hoverEvent(motionEvent);
        } else if (this.mProcessHoverAction) {
            return this.mDecorGrid.hoverEvent(motionEvent);
        } else {
            return super.onHoverEvent(motionEvent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "start onTouchEvent...");
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mHelper != null) {
            z = this.mHelper.onImportTouchEvent(motionEvent);
        } else {
            z = this.mDecorGrid.touchEvent(motionEvent);
        }
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "end onTouchEvent...");
        }
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "onTouchEvent:" + (System.currentTimeMillis() - currentTimeMillis));
        }
        return z;
    }

    @Override
    // com.iflytek.inputmethod.common.view.widget.MultiTouchEventHelper.OnMultiTouchEventListener
    public boolean onExportTouchEvent(MotionEvent motionEvent) {
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "onExportTouchEvent, action = " + motionEvent.getAction());
        }
        return this.mDecorGrid.touchEvent(motionEvent);
    }

    @Override
    // com.iflytek.inputmethod.common.view.widget.MultiTouchEventHelper.OnMultiTouchEventListener
    public boolean isLongClicked() {
        return this.mDecorGrid.isLongClicked();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "start onTouchEvent...");
        }
        super.onAttachedToWindow();
        this.mDecorGrid.dispatchAttachedToWindow(this.mAttachInfo);
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "end onTouchEvent...");
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "start onDetachedFromWindow...");
        }
        super.onDetachedFromWindow();
        this.mDecorGrid.dispatchDetachedFromWindow();
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "end onDetachedFromWindow...");
        }
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int i) {
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "start onWindowVisibilityChanged...");
        }
        super.onWindowVisibilityChanged(i);
        this.mDecorGrid.dispatchWindowVisibilityChanged(Visibility.getVisibility(i));
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "end onWindowVisibilityChanged...");
        }
    }

    public static int getDefaultSize(int i, int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
            default:
                return i;
            case MeasureSpec.EXACTLY:
                return size;
        }
    }

    public void setProcessHoverAction(boolean z) {
        this.mProcessHoverAction = z;
    }

    /* access modifiers changed from: protected */
    public void onGlobalGridAdded(GridGroup gridGroup, Grid grid) {
//        if (this.mAccessibilityHelper != null) {
//            this.mAccessibilityHelper.onGlobalGridAdded(gridGroup, grid);
//        }
    }

    /* access modifiers changed from: protected */
    public void onGlobalGridRemoved(GridGroup gridGroup, Grid grid) {
//        if (this.mAccessibilityHelper != null) {
//            this.mAccessibilityHelper.onGlobalGridRemoved(gridGroup, grid);
//        }
    }

    /* access modifiers changed from: protected */
    public void onGlobalGridVisibilityChanged(Grid grid, int i) {
//        if (this.mAccessibilityHelper != null) {
//            this.mAccessibilityHelper.onGlobalGridVisibilityChanged(grid, i);
//        }
    }

    /* access modifiers changed from: protected */
    public void onGlobalGridAccessibilityModeChanged(Grid grid, boolean z) {
//        if (this.mAccessibilityHelper != null) {
//            this.mAccessibilityHelper.onGlobalGridAccessibilityModeChanged(grid, z);
//        }
    }

    /* access modifiers changed from: protected */
    public void onGlobalGridGroupScrollChanged(GridGroup gridGroup, int i, int i2, int i3, int i4) {
//        if (this.mAccessibilityHelper != null) {
//            this.mAccessibilityHelper.onGlobalGridGroupScrollChanged(gridGroup, i, i2, i3, i4);
//        }
    }

    public void invalidateAccessibility(int i) {
//        if (this.mAccessibilityHelper == null) {
//            return;
//        }
//        if (i == Integer.MIN_VALUE) {
//            this.mAccessibilityHelper.invalidateRoot();
//        } else {
//            this.mAccessibilityHelper.invalidateVirtualView(i);
//        }
    }
}
