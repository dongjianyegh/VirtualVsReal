package com.example.virtual.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.example.virtual.view.constants.GridConfiguration;
import com.example.virtual.view.drawable.AbsDrawable;
import com.example.virtual.view.interfaces.AttachInterface;

import java.util.LinkedList;
import java.util.List;

public class GridGroup extends Grid implements GridParent {
    private static final int ARRAY_CAPACITY_INCREMENT = 5;
    private static final int ARRAY_INITIAL_CAPACITY = 5;
    protected List<Grid> mAnimatingChildren;
    protected Rect mChildMargin = ZERO_BOUND_RECT;
    public Rect mChildPadding = ZERO_BOUND_RECT;
    private Grid[] mChildren = new Grid[5];
    private int mChildrenCount;
    protected long[] mDataTypes;
    private Grid mHoverTarget;
    private boolean mInLayout;
    public AbsDrawable mKeyBackground;
    public AbsDrawable mKeyForeground;
    protected boolean mLayoutDirty;
    private boolean mLayoutForceDirty;
    private Rect mOriginChildMargin = ZERO_BOUND_RECT;
    private Rect mOriginChildPadding = ZERO_BOUND_RECT;
    private Rect mOriginPadding = ZERO_BOUND_RECT;
    protected Rect mPadding = ZERO_BOUND_RECT;
    protected int mScrollX;
    protected int mScrollY;
    private Grid mTouchTarget;

    public GridGroup(Context context) {
        super(context);
    }

    public void setDataTypes(long[] jArr) {
        this.mDataTypes = jArr;
    }

    public long[] getDataTypes() {
        return this.mDataTypes;
    }

    public void notifyInputDataChanged(long j, Object obj) {
    }

    public boolean setPadding(int i, int i2, int i3, int i4) {
        Rect rect = this.mPadding;
        if (rect == ZERO_BOUND_RECT && !(i == 0 && i2 == 0 && i3 == 0 && i4 == 0)) {
            rect = new Rect();
        }
        int i5 = rect.left;
        int i6 = rect.top;
        int i7 = rect.right;
        int i8 = rect.bottom;
        rect.set(i, i2, i3, i4);
        this.mPadding = rect;
        if (this.mOriginPadding == ZERO_BOUND_RECT) {
            this.mOriginPadding = new Rect();
            this.mOriginPadding.set(rect);
        }
        if (i5 == i && i6 == i2 && i7 == i3 && i8 == i4) {
            return false;
        }
        requestLayout();
        return true;
    }

    public boolean setPadding(Rect rect) {
        return setPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    public int getPaddingLeft() {
        return this.mPadding.left;
    }

    public int getPaddingTop() {
        return this.mPadding.top;
    }

    public int getPaddingRight() {
        return this.mPadding.right;
    }

    public int getPaddingBottom() {
        return this.mPadding.bottom;
    }

    public Rect getPadding(Rect rect) {
        rect.set(this.mPadding);
        return rect;
    }

    public void requestLayout() {
        this.mLayoutDirty = true;
        invalidate();
    }

    public void requestLayout(boolean z) {
        if (z) {
            this.mLayoutForceDirty = true;
        }
        this.mLayoutDirty = true;
        invalidate();
    }

    public boolean isLayoutDirty() {
        return this.mLayoutDirty;
    }

    public boolean isInLayout() {
        return this.mInLayout;
    }

    public void layout() {
        this.mLayoutDirty = false;
    }

    public boolean setChildMargin(int i, int i2, int i3, int i4) {
        Rect rect = this.mChildMargin;
        if (rect == ZERO_BOUND_RECT && !(i == 0 && i2 == 0 && i3 == 0 && i4 == 0)) {
            rect = new Rect();
        }
        int i5 = rect.left;
        int i6 = rect.top;
        int i7 = rect.right;
        int i8 = rect.bottom;
        rect.set(i, i2, i3, i4);
        this.mChildMargin = rect;
        if (this.mOriginChildMargin == ZERO_BOUND_RECT) {
            this.mOriginChildMargin = new Rect();
            this.mOriginChildMargin.set(rect);
        }
        if (i5 == i && i6 == i2 && i7 == i3 && i8 == i4) {
            return false;
        }
        requestLayout();
        return true;
    }

    public boolean setChildMargin(Rect rect) {
        return setChildMargin(rect.left, rect.top, rect.right, rect.bottom);
    }

    public Rect getChildMargin(Rect rect) {
        rect.set(this.mChildMargin);
        return rect;
    }

    public boolean setChildPadding(int i, int i2, int i3, int i4) {
        Rect rect = this.mChildPadding;
        if (rect == ZERO_BOUND_RECT && !(i == 0 && i2 == 0 && i3 == 0 && i4 == 0)) {
            rect = new Rect();
        }
        int i5 = rect.left;
        int i6 = rect.top;
        int i7 = rect.right;
        int i8 = rect.bottom;
        rect.set(i, i2, i3, i4);
        this.mChildPadding = rect;
        if (this.mOriginChildPadding == ZERO_BOUND_RECT) {
            this.mOriginChildPadding = new Rect();
            this.mOriginChildPadding.set(rect);
        }
        if (i5 == i && i6 == i2 && i7 == i3 && i8 == i4) {
            return false;
        }
        requestLayout();
        return true;
    }

    public boolean setChildPadding(Rect rect) {
        return setChildPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    public Rect getChildPadding(Rect rect) {
        rect.set(this.mChildPadding);
        return rect;
    }

    public void scrollTo(int i, int i2) {
        if (this.mScrollX != i || this.mScrollY != i2) {
            int i3 = this.mScrollX;
            int i4 = this.mScrollY;
            this.mScrollX = i;
            this.mScrollY = i2;
            if (this.mAttachInfo != null) {
                this.mAttachInfo.onGlobalGridGroupScrollChanged(this, this.mScrollX, this.mScrollY, i3, i4);
            }
            onScrollChanged(this.mScrollX, this.mScrollY, i3, i4);
            invalidate();
        }
    }

    public void scrollBy(int i, int i2) {
        scrollTo(this.mScrollX + i, this.mScrollY + i2);
    }

    public void onScrollChanged(int i, int i2, int i3, int i4) {
    }

    public int getScrollX() {
        return this.mScrollX;
    }

    public int getScrollY() {
        return this.mScrollY;
    }

    public void computeScroll() {
    }

    public void setKeyBackground(AbsDrawable absDrawable) {
        this.mKeyBackground = absDrawable;
        requestLayout();
    }

    public AbsDrawable getKeyBackground() {
        return this.mKeyBackground;
    }

    public void setKeyForeground(AbsDrawable multiColorTextDrawable) {
        this.mKeyForeground = multiColorTextDrawable;
        requestLayout();
    }

    public AbsDrawable getKeyForeground() {
        return this.mKeyForeground;
    }

    /* access modifiers changed from: protected */
    public void onGridAdded(Grid grid) {
    }

    /* access modifiers changed from: protected */
    public void onGridRemoved(Grid grid) {
    }

    @Override // Grid
    public final Grid findGridByType(int i) {
        if (getType() == i) {
            return this;
        }
        int i2 = this.mChildrenCount;
        for (int i3 = 0; i3 < i2; i3++) {
            Grid findGridByType = this.mChildren[i3].findGridByType(i);
            if (findGridByType != null) {
                return findGridByType;
            }
        }
        return null;
    }

    @Override // Grid
    public Grid findViewById(int i) {
        if (this.mID == i) {
            return this;
        }
        int i2 = this.mChildrenCount;
        for (int i3 = 0; i3 < i2; i3++) {
            Grid findViewById = this.mChildren[i3].findViewById(i);
            if (findViewById != null) {
                return findViewById;
            }
        }
        return null;
    }

    public void addGrid(Grid grid) {
        addGrid(grid, this.mChildrenCount);
    }

    public void addGrid(Grid grid, int i) {
        addGridInterval(grid);
        addGridInArray(grid, i);
        grid.invalidate();
    }

    /* access modifiers changed from: protected */
    public void addGridInLayout(Grid grid) {
        addGridInLayout(grid, this.mChildrenCount);
    }

    /* access modifiers changed from: protected */
    public void addGridInLayout(Grid grid, int i) {
        addGridInArray(grid, i);
        addGridInterval(grid);
    }

    /* access modifiers changed from: protected */
    public void addGridInLayout(List<Grid> list, int i) {
        addGridInArray(list, i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            addGridInterval(list.get(i2));
        }
    }

    private void addGridInterval(Grid grid) {
        grid.assignParent(this);
        if (this.mAttachInteface != null) {
            grid.dispatchInputInterface(this.mAttachInteface);
        }
        if (this.mAttachInfo != null) {
            grid.dispatchAttachedToWindow(this.mAttachInfo);
        }
        if (this.mAttachInfo != null) {
            this.mAttachInfo.onGlobalGridAdded(this, grid);
        }
        onGridAdded(grid);
    }

    private void addGridInArray(Grid grid, int i) {
        Grid[] gridArr = this.mChildren;
        int i2 = this.mChildrenCount;
        int length = gridArr.length;
        if (i == i2) {
            if (length == i2) {
                this.mChildren = new Grid[(length + 5)];
                System.arraycopy(gridArr, 0, this.mChildren, 0, length);
                gridArr = this.mChildren;
            }
            int i3 = this.mChildrenCount;
            this.mChildrenCount = i3 + 1;
            gridArr[i3] = grid;
        } else if (i < i2) {
            if (length == i2) {
                this.mChildren = new Grid[(length + 5)];
                System.arraycopy(gridArr, 0, this.mChildren, 0, i);
                System.arraycopy(gridArr, i, this.mChildren, i + 1, i2 - i);
                gridArr = this.mChildren;
            } else {
                System.arraycopy(gridArr, i, gridArr, i + 1, i2 - i);
            }
            gridArr[i] = grid;
            this.mChildrenCount++;
        } else {
            throw new IndexOutOfBoundsException("index=" + i + " count=" + i2);
        }
    }

    private void addGridInArray(List<Grid> list, int i) {
        if (i > this.mChildrenCount || i < 0) {
            throw new IndexOutOfBoundsException("index=" + i + " count=" + this.mChildrenCount);
        }
        Object[] array = list.toArray();
        int length = array.length;
        if (length != 0) {
            Grid[] gridArr = this.mChildren;
            int i2 = this.mChildrenCount;
            int i3 = i2 + length;
            if (i3 <= gridArr.length) {
                System.arraycopy(gridArr, i, gridArr, i + length, i2 - i);
            } else {
                Grid[] gridArr2 = new Grid[(((((i3 - gridArr.length) / 5) + 1) * 5) + gridArr.length)];
                System.arraycopy(gridArr, 0, gridArr2, 0, i);
                System.arraycopy(gridArr, i, gridArr2, i + length, i2 - i);
                this.mChildren = gridArr2;
                gridArr = gridArr2;
            }
            System.arraycopy(array, 0, gridArr, i, length);
            this.mChildrenCount = i3;
        }
    }

    public void removeGrid(Grid grid) {
        int indexOfChild = indexOfChild(grid);
        if (indexOfChild >= 0) {
            removeGrids(indexOfChild, 1);
        }
    }

    public void removeGridAt(int i) {
        removeGrids(i, 1);
    }

    public void removeAllGrids() {
        removeGrids(0, this.mChildrenCount);
    }

    public void removeGrids(int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            Grid grid = this.mChildren[i3];
            removeGridInterval(grid);
            grid.invalidate();
        }
        removeGridInArray(i, i2);
    }

    /* access modifiers changed from: protected */
    public void removeGridInLayout(Grid grid) {
        int indexOfChild = indexOfChild(grid);
        if (indexOfChild >= 0) {
            removeGridInLayout(indexOfChild);
        }
    }

    /* access modifiers changed from: protected */
    public void removeGridInLayout(int i) {
        removeGridInLayout(i, 1);
    }

    /* access modifiers changed from: protected */
    public void removeGridInLayout(int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            removeGridInterval(this.mChildren[i3]);
        }
        removeGridInArray(i, i2);
    }

    /* access modifiers changed from: protected */
    public void removeAllGridsInLayout() {
        removeGridInLayout(0, this.mChildrenCount);
    }

    private void removeGridInterval(Grid grid) {
        cancelTouchTarget(grid);
        if (grid.mAttachInfo != null) {
            grid.dispatchDetachedFromWindow();
        }
        if (this.mAttachInfo != null) {
            this.mAttachInfo.onGlobalGridRemoved(this, grid);
        }
        grid.assignParent(null);
        onGridRemoved(grid);
    }

    private void removeGridInArray(int i) {
        Grid[] gridArr = this.mChildren;
        int i2 = this.mChildrenCount;
        if (i == i2 - 1) {
            int i3 = this.mChildrenCount - 1;
            this.mChildrenCount = i3;
            gridArr[i3] = null;
        } else if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException();
        } else {
            System.arraycopy(gridArr, i + 1, gridArr, i, (i2 - i) - 1);
            int i4 = this.mChildrenCount - 1;
            this.mChildrenCount = i4;
            gridArr[i4] = null;
        }
    }

    private void removeGridInArray(int i, int i2) {
        Grid[] gridArr = this.mChildren;
        int i3 = this.mChildrenCount;
        int max = Math.max(0, i);
        int min = Math.min(i3, max + i2);
        if (max != min) {
            if (min == i3) {
                for (int i4 = max; i4 < min; i4++) {
                    gridArr[i4] = null;
                }
            } else {
                for (int i5 = max; i5 < min; i5++) {
                }
                System.arraycopy(gridArr, min, gridArr, max, i3 - min);
                for (int i6 = i3 - (min - max); i6 < i3; i6++) {
                    gridArr[i6] = null;
                }
            }
            this.mChildrenCount -= min - max;
        }
    }

    /* access modifiers changed from: protected */
    public boolean dispatchTouchTarget(MotionEvent motionEvent) {
        Grid grid;
        Grid grid2;
        int action = motionEvent.getAction();
        if (action == 0) {
            cancelAndClearTouchTargets(motionEvent);
            motionEvent.offsetLocation((float) this.mScrollX, (float) this.mScrollY);
            int locationViewInPosition = getLocationViewInPosition(motionEvent.getX(), motionEvent.getY());
            if (locationViewInPosition >= 0) {
                grid2 = this.mChildren[locationViewInPosition];
                this.mTouchTarget = grid2;
            } else {
                grid2 = null;
            }
            grid = grid2;
        } else {
            motionEvent.offsetLocation((float) this.mScrollX, (float) this.mScrollY);
            Grid grid3 = this.mTouchTarget;
            if (action == 3 || action == 1) {
                this.mTouchTarget = null;
            }
            grid = grid3;
        }
        boolean z = false;
        if (grid != null) {
            z = grid.touchEvent(motionEvent);
        }
        if (!z && action == 0) {
            this.mTouchTarget = null;
        }
        motionEvent.offsetLocation((float) (-this.mScrollX), (float) (-this.mScrollY));
        return z;
    }

    /* access modifiers changed from: protected */
    public boolean dispatchHoverTarget(MotionEvent motionEvent) {
        Grid grid;
        int action = motionEvent.getAction();
        int actionMasked = motionEvent.getActionMasked();
        motionEvent.offsetLocation((float) this.mScrollX, (float) this.mScrollY);
        if (actionMasked == 9 || actionMasked == 7) {
            int locationViewInPosition = getLocationViewInPosition(motionEvent.getX(), motionEvent.getY());
            if (locationViewInPosition >= 0) {
                grid = this.mChildren[locationViewInPosition];
            } else {
                grid = null;
            }
            if (grid != null) {
                if (this.mHoverTarget == null) {
                    this.mHoverTarget = grid;
                    motionEvent.setAction(9);
                    this.mHoverTarget.hoverEvent(motionEvent);
                    motionEvent.offsetLocation((float) (-this.mScrollX), (float) (-this.mScrollY));
                    motionEvent.setAction(action);
                } else if (this.mHoverTarget == grid) {
                    this.mHoverTarget.hoverEvent(motionEvent);
                    motionEvent.offsetLocation((float) (-this.mScrollX), (float) (-this.mScrollY));
                } else {
                    Grid grid2 = this.mHoverTarget;
                    this.mHoverTarget = grid;
                    cancelHoverTarget(grid2);
                    if (this.mHoverTarget != null) {
                        motionEvent.setAction(9);
                        this.mHoverTarget.hoverEvent(motionEvent);
                        motionEvent.offsetLocation((float) (-this.mScrollX), (float) (-this.mScrollY));
                        motionEvent.setAction(action);
                    }
                }
            }
            return true;
        }
        if (actionMasked == 10 || actionMasked == 3) {
            if (motionEvent.getY() < 0f) {
                motionEvent.setAction(3);
            }
            if (this.mHoverTarget != null) {
                this.mHoverTarget.hoverEvent(motionEvent);
                this.mHoverTarget = null;
                motionEvent.offsetLocation((float) (-this.mScrollX), (float) (-this.mScrollY));
            }
        }
        return true;
    }

    private void cancelHoverTarget(Grid grid) {
        if (grid != null) {
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0, 0, 0);
            grid.hoverEvent(obtain);
            obtain.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public int getLocationViewInPosition(float f, float f2) {
        float f3;
        for (int i = this.mChildrenCount - 1; i >= 0; i--) {
            Grid grid = this.mChildren[i];
            if (grid.getVisibility() == 0 && grid.pointInView(f, f2)) {
                return i;
            }
        }
        int i2 = 0;
        float f4 = Float.MAX_VALUE;
        int i3 = this.mChildrenCount - 1;
        while (i3 >= 0) {
            Grid grid2 = this.mChildren[i3];
            if (grid2.getVisibility() == 0) {
                f3 = grid2.pointInViewDistance(f, f2);
                if (f3 < f4) {
                    i2 = i3;
                    i3--;
                    f4 = f3;
                }
            }
            f3 = f4;
            i3--;
            f4 = f3;
        }
        if (f4 > ((float) GridConfiguration.get(this.mContext).getTouchEdgeLength())) {
            return -1;
        }
        return i2;
    }

    /* access modifiers changed from: protected */
    public void cancelTouchTarget(Grid grid) {
        Grid grid2 = this.mTouchTarget;
        if (grid2 != null && grid2 == grid) {
            this.mTouchTarget = null;
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0f, 0f, 0);
            grid2.touchEvent(obtain);
            obtain.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public void cancelAndClearTouchTargets(MotionEvent motionEvent) {
        Grid grid = this.mTouchTarget;
        if (grid != null) {
            this.mTouchTarget = null;
            if (motionEvent != null) {
                int action = motionEvent.getAction();
                motionEvent.setAction(3);
                motionEvent.offsetLocation((float) this.mScrollX, (float) this.mScrollY);
                grid.touchEvent(motionEvent);
                motionEvent.offsetLocation((float) (-this.mScrollX), (float) (-this.mScrollY));
                motionEvent.setAction(action);
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0f, 0f, 0);
            grid.touchEvent(obtain);
            obtain.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public Grid getTouchTarget() {
        return this.mTouchTarget;
    }

    public int getChildCount() {
        return this.mChildrenCount;
    }

    public Grid getChildAt(int i) {
        if (i < 0 || i >= this.mChildrenCount) {
            return null;
        }
        return this.mChildren[i];
    }

    public int indexOfChild(Grid grid) {
        for (int i = 0; i < this.mChildrenCount; i++) {
            if (this.mChildren[i] == grid) {
                return i;
            }
        }
        return -1;
    }

    public int getInnerWidth() {
        return (this.mWidth - this.mPadding.left) - this.mPadding.right;
    }

    public int getInnerHeight() {
        return (this.mHeight - this.mPadding.top) - this.mPadding.bottom;
    }

    @Override // Grid
    public boolean touchEvent(MotionEvent motionEvent) {
        return dispatchTouchTarget(motionEvent);
    }

    @Override // Grid
    public boolean hoverEvent(MotionEvent motionEvent) {
        return dispatchHoverTarget(motionEvent);
    }

    @Override // Grid
    public void draw(Canvas canvas) {
        int i;
        computeScroll();
        if (this.mLayoutDirty || this.mLayoutForceDirty) {
            this.mLayoutForceDirty = false;
            this.mInLayout = true;
            layout();
            this.mInLayout = false;
            this.mLayoutDirty = false;
        }
        drawBackground(canvas);
        if (this.mPadding == ZERO_BOUND_RECT || !CLIPPING || isGridLightAnimating()) {
            if (this.mScrollX == 0 && this.mScrollY == 0) {
                i = -1;
            } else {
                i = canvas.save();
                canvas.translate((float) (-this.mScrollX), (float) (-this.mScrollY));
            }
            dispatchDraw(canvas);
        } else {
            i = canvas.save();
            mTmpInvalRect.left = this.mX + this.mPadding.left;
            mTmpInvalRect.top = this.mY + this.mPadding.top;
            mTmpInvalRect.right = (this.mX + this.mWidth) - this.mPadding.right;
            mTmpInvalRect.bottom = (this.mY + this.mHeight) - this.mPadding.bottom;
            canvas.clipRect(mTmpInvalRect);
            if (!(this.mScrollX == 0 && this.mScrollY == 0)) {
                canvas.translate((float) (-this.mScrollX), (float) (-this.mScrollY));
            }
            dispatchDraw(canvas);
        }
        if (i != -1) {
            canvas.restoreToCount(i);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        int i = this.mChildrenCount;
        Grid[] gridArr = this.mChildren;
        for (int i2 = 0; i2 < i; i2++) {
            Grid grid = gridArr[i2];
            if (grid.mVisibility == 0 && !grid.isAnimating()) {
                drawChild(canvas, grid);
            }
            if (DEBUG) {
                grid.getBounds(mTmpInvalRect);
                Rect rect = mTmpInvalRect;
                rect.right--;
                Rect rect2 = mTmpInvalRect;
                rect2.bottom--;
                canvas.drawRect(mTmpInvalRect, getDebugPaint(-16776961));
            }
        }
        if (!(this.mAnimatingChildren == null || this.mAnimatingChildren.isEmpty())) {
            for (Grid grid2 : this.mAnimatingChildren) {
                if (grid2.isShown()) {
                    drawChild(canvas, grid2);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawChild(Canvas canvas, Grid grid) {
        canvas.getClipBounds(mTmpInvalRect);
        if (!CLIPPING || grid.isAnimating() || grid.isGridLightAnimating()) {
            grid.draw(canvas);
        } else if (mTmpInvalRect.intersect(grid.mX, grid.mY, grid.mX + grid.mWidth, grid.mY + grid.mHeight)) {
            int save = canvas.save();
            canvas.clipRect(mTmpInvalRect);
            grid.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    @Override // GridParent
    public void invalidateChild(Grid grid, Rect rect) {
        if (!this.mInLayout && this.mAttachInfo != null && this.mParent != null) {
            rect.offset(-this.mScrollX, -this.mScrollY);
            if (grid.isAnimating()) {
                if (!this.mAttachInfo.mDirty.contains(rect)) {
                    this.mParent.invalidateChild(grid, rect);
                }
            } else if (rect.intersect(this.mX + this.mPadding.left, this.mY + this.mPadding.top, (this.mX + this.mWidth) - this.mPadding.right, (this.mY + this.mHeight) - this.mPadding.bottom) && !this.mAttachInfo.mDirty.contains(rect)) {
                this.mParent.invalidateChild(grid, rect);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @Override // Grid
    public final void dispatchAttachedToWindow(AttachInfo attachInfo) {
        super.dispatchAttachedToWindow(attachInfo);
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            this.mChildren[i2].dispatchAttachedToWindow(attachInfo);
        }
    }

    /* access modifiers changed from: package-private */
    @Override // Grid
    public void dispatchInputInterface(AttachInterface attachInterface) {
        if (this.mAttachInteface == null) {
            super.dispatchInputInterface(attachInterface);
            int i = this.mChildrenCount;
            for (int i2 = 0; i2 < i; i2++) {
                this.mChildren[i2].dispatchInputInterface(attachInterface);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @Override // Grid
    public final void dispatchDetachedFromWindow() {
        cancelAndClearTouchTargets(null);
        int i = this.mChildrenCount;
        for (int i2 = 0; i2 < i; i2++) {
            this.mChildren[i2].dispatchDetachedFromWindow();
        }
        super.dispatchDetachedFromWindow();
    }

    /* access modifiers changed from: package-private */
    @Override // Grid
    public void dispatchWindowVisibilityChanged(int i) {
        super.dispatchWindowVisibilityChanged(i);
        int i2 = this.mChildrenCount;
        for (int i3 = 0; i3 < i2; i3++) {
            this.mChildren[i3].dispatchWindowVisibilityChanged(i);
        }
    }

    @Override // Grid
    public void scaleDrawable(float f, float f2) {
        if (this.mKeyForeground != null) {
//            if (this.mKeyBackground instanceof TextDrawable) {
//                this.mKeyForeground.scale(f2);
//            } else {
//                this.mKeyForeground.scale(f);
//            }
            this.mKeyForeground.scale(f);
            requestLayout();
        } else if (this.mChildren != null) {
            for (int i = 0; i < this.mChildrenCount; i++) {
                this.mChildren[i].scaleDrawable(f, f2);
            }
        }
    }

    @Override // Grid
    public void updateLoc(float f, float f2) {
        super.updateLoc(f, f2);
        if (this.mOriginPadding != ZERO_BOUND_RECT) {
            int i = (int) (((float) this.mOriginPadding.left) * f);
            int i2 = (int) (((float) this.mOriginPadding.top) * f2);
            this.mPadding.set(i, i2, ((int) (((float) this.mOriginPadding.width()) * f)) + i, ((int) (((float) this.mOriginPadding.height()) * f2)) + i2);
            requestLayout();
        }
        if (this.mOriginChildMargin != ZERO_BOUND_RECT) {
            int i3 = (int) (((float) this.mOriginChildMargin.left) * f);
            int i4 = (int) (((float) this.mOriginChildMargin.top) * f2);
            this.mChildMargin.set(i3, i4, ((int) (((float) this.mOriginChildMargin.width()) * f)) + i3, ((int) (((float) this.mOriginChildMargin.height()) * f2)) + i4);
            requestLayout();
        }
        if (this.mOriginChildPadding != ZERO_BOUND_RECT) {
            int i5 = (int) (((float) this.mOriginChildPadding.left) * f);
            int i6 = (int) (((float) this.mOriginChildPadding.top) * f2);
            this.mChildPadding.set(i5, i6, ((int) (((float) this.mOriginChildPadding.width()) * f)) + i5, ((int) (((float) this.mOriginChildPadding.height()) * f2)) + i6);
            requestLayout();
        }
        if (this.mKeyForeground == null && this.mChildren != null) {
            for (int i7 = 0; i7 < this.mChildrenCount; i7++) {
                this.mChildren[i7].updateLoc(f, f2);
            }
        }
    }

    @Override // Grid
    public void dispatchSetLocationBy(int i, int i2) {
        super.dispatchSetLocationBy(i, i2);
        int i3 = this.mChildrenCount;
        for (int i4 = 0; i4 < i3; i4++) {
            this.mChildren[i4].dispatchSetLocationBy(i, i2);
        }
    }

    @Override // Grid
    public void dispatchSetBackgroundAlpha(int i) {
        super.dispatchSetBackgroundAlpha(i);
        if (this.mKeyBackground != null) {
            this.mKeyBackground.setAlpha(i);
        }
        for (int i2 = 0; i2 < this.mChildrenCount; i2++) {
            this.mChildren[i2].dispatchSetBackgroundAlpha(i);
        }
    }

    @Override // GridParent
    public int getParentScrollX() {
        if (this.mParent == null) {
            return -this.mScrollX;
        }
        return (-this.mScrollX) + this.mParent.getParentScrollX();
    }

    @Override // GridParent
    public int getParentScrollY() {
        if (this.mParent == null) {
            return -this.mScrollY;
        }
        return (-this.mScrollY) + this.mParent.getParentScrollY();
    }

    @Override // Grid
    public boolean isLongClicked() {
        if (this.mTouchTarget != null) {
            return this.mTouchTarget.isLongClicked();
        }
        return false;
    }

    @Override // Grid
    public boolean hasSlipAction() {
        if (this.mTouchTarget != null) {
            return this.mTouchTarget.hasSlipAction();
        }
        return false;
    }

    @Override // Grid
    public boolean hasLongPressSlipAction() {
        if (this.mTouchTarget != null) {
            return this.mTouchTarget.hasLongPressSlipAction();
        }
        return false;
    }

    @Override // Grid
    public void dispatchOutputLocation() {
        super.dispatchOutputLocation();
        for (int i = 0; i < this.mChildrenCount; i++) {
            this.mChildren[i].dispatchOutputLocation();
        }
    }

    @Override // GridParent
    public void animateChildInParent(Grid grid) {
        GridParent parent = getParent();
        if (parent != null) {
            parent.animateChildInParent(grid);
            return;
        }
        if (this.mAnimatingChildren == null) {
            this.mAnimatingChildren = new LinkedList();
        }
        this.mAnimatingChildren.add(grid);
    }

    @Override // GridParent
    public void unanimateChildInParent(Grid grid) {
        GridParent parent = getParent();
        if (parent != null) {
            parent.unanimateChildInParent(grid);
        } else if (this.mAnimatingChildren != null) {
            this.mAnimatingChildren.remove(grid);
        }
    }

    @Override // Grid
    public void setCapital(boolean z) {
        super.setCapital(z);
        for (int i = 0; i < this.mChildrenCount; i++) {
            this.mChildren[i].setCapital(z);
        }
    }

    @Override // GridParent
    public void requestRootLayout() {
        if (this.mParent != null) {
            this.mParent.requestRootLayout();
        }
    }
}
