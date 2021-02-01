package com.example.virtual.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.view.MotionEvent;

import com.example.virtual.view.constants.KeyState;
import com.example.virtual.view.constants.Visibility;
import com.example.virtual.view.drawable.AbsDrawable;
import com.example.virtual.view.drawable.LayoutParams;
import com.example.virtual.view.interfaces.AttachInterface;
import com.example.virtual.view.interfaces.OnGridStateChangeListener;
import com.example.virtual.view.interfaces.OnGridTouchEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Grid {
    public static boolean CLIPPING = true;
    public static boolean DEBUG = false;
    public static final int MODIFY_SACLE_TYPE_RIGHT_BOUND = 1;
    private static final AtomicInteger UNIQUE_ID_GENERATOR = new AtomicInteger(0);
    protected static final Rect ZERO_BOUND_RECT = new Rect();
    protected static Paint mDebugPaint;
    public static Handler mHandler = new Handler();
    static List<Grid> mTmpInvalList = new ArrayList(5);
    public static Rect mTmpInvalRect = new Rect();
    protected boolean mAccessibilityMode;
    private Animator.AnimatorListener mAnimatorListener;
    AttachInfo mAttachInfo;
    protected AttachInterface mAttachInteface;
    protected AbsDrawable mBackground;
    public Context mContext;
    protected long[] mExtraModes;
    private boolean mGridContentAnimating = false;
    private boolean mGridLightAnimating = false;
    private boolean mGridObjAnimating = false;
    protected int mHeight;
    protected int mID;
    protected LayoutParams mLayoutParams;
    protected Point mMeasuredDimens;
    protected long[] mModes;
    private int mModifyScaleType = 0;
    protected OnGridStateChangeListener mOnGridStateChangeListener;
    protected OnGridTouchEventListener mOnGridTouchEventListener;
    protected Rect mOriRect = ZERO_BOUND_RECT;
    protected Rect mOriTouchRect;
    GridParent mParent;
    protected Object mTag;
    protected Rect mTouchRect;
    protected int mType;
    private final int mUniqueID = UNIQUE_ID_GENERATOR.incrementAndGet();
    protected int mVisibility;
    protected int mWidth;
    protected int mX;
    protected int mY;

    public int getModifyScaleType() {
        return this.mModifyScaleType;
    }

    public void setModifyScaleType(int i) {
        this.mModifyScaleType = i;
    }

    public Grid(Context context) {
        this.mContext = context;
        this.mVisibility = 0;
    }

    public int getUniqueID() {
        return this.mUniqueID;
    }

    /* access modifiers changed from: protected */
    public void setLayoutParams(LayoutParams layoutParams) {
        this.mLayoutParams = layoutParams;
    }

    /* access modifiers changed from: protected */
    public LayoutParams getLayoutParams() {
        return this.mLayoutParams;
    }

    public void setOnAttachStateChangeListener(OnGridStateChangeListener onGridStateChangeListener) {
        this.mOnGridStateChangeListener = onGridStateChangeListener;
    }

    public OnGridTouchEventListener getOnGridTouchEventListener() {
        return this.mOnGridTouchEventListener;
    }

    public void setOnGridTouchEventListener(OnGridTouchEventListener onGridTouchEventListener) {
        this.mOnGridTouchEventListener = onGridTouchEventListener;
    }

    public static Paint getDebugPaint(int i) {
        if (mDebugPaint == null) {
            mDebugPaint = new Paint();
            mDebugPaint.setAntiAlias(false);
            mDebugPaint.setStyle(Paint.Style.STROKE);
            mDebugPaint.setStrokeWidth(1.0f);
        }
        mDebugPaint.setColor(i);
        return mDebugPaint;
    }

    public void setModes(long[] jArr) {
        this.mModes = jArr;
    }

    public void setID(int i) {
        this.mID = i;
    }

    public int getID() {
        return this.mID;
    }

    public long[] getModes() {
        return this.mModes;
    }

    public void setExtraModes(long[] jArr) {
        this.mExtraModes = jArr;
    }

    public long[] getExtraModes() {
        return this.mExtraModes;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public void setTag(Object obj) {
        this.mTag = obj;
    }

    public Object getTag() {
        return this.mTag;
    }

    public Grid findGridByType(int i) {
        if (this.mType == i) {
            return this;
        }
        return null;
    }

    public Grid findViewById(int i) {
        if (this.mID == i) {
            return this;
        }
        return null;
    }

    public void initBounds(int left, int top, int right, int bottom) {
        if (right < left || bottom < top) {
            right = Math.max(left, right);
            bottom = Math.max(top, bottom);
        }
        if (this.mOriRect == ZERO_BOUND_RECT) {
            this.mOriRect = new Rect();
        }
        this.mOriRect.left = left;
        this.mOriRect.top = top;
        this.mOriRect.right = right;
        this.mOriRect.bottom = bottom;
    }

    public void initTouchBounds(int i, int i2, int i3, int i4) {
        if (i3 < i || i4 < i2) {
//            if (BuildConfig.DEBUG) {
//                Log.e("Grid", "grid id: " + this.mID + " initTouchBounds error :\t" + i + ", " + i2 + ", " + i3 + ", " + i4);
//            }
            i3 = Math.max(i, i3);
            i4 = Math.max(i2, i4);
        }
        if (this.mOriTouchRect == null) {
            this.mOriTouchRect = new Rect();
        }
        this.mOriTouchRect.left = i;
        this.mOriTouchRect.top = i2;
        this.mOriTouchRect.right = i3;
        this.mOriTouchRect.bottom = i4;
    }

    public void setBounds(int left, int top, int right, int bottom) {
        if (right < left || bottom < top) {
//            if (BuildConfig.DEBUG) {
//                Log.e("Grid", "grid id: " + this.mID + " setBound error :\t" + i + ", " + i2 + ", " + i3 + ", " + i4);
//            }
            right = Math.max(left, right);
            bottom = Math.max(top, bottom);
        }
        int i5 = this.mX;
        int i6 = this.mY;
        int i7 = this.mWidth + this.mX;
        int i8 = this.mY + this.mHeight;
        this.mX = left;
        this.mY = top;
        this.mWidth = right - left;
        this.mHeight = bottom - top;
        if (i5 != left || i6 != top || i7 != right || i8 != bottom) {
            onBoundsChange(i5, i6, i7, i8);
            if (this.mParent != null && !(this.mWidth == i7 - i5 && this.mHeight == i8 - i6)) {
                this.mParent.requestRootLayout();
            }
            if (i5 < left) {
                left = i5;
            }
            if (i6 < top) {
                top = i6;
            }
            if (i7 > right) {
                right = i7;
            }
            if (i8 > bottom) {
                bottom = i8;
            }
            invalidate(left, top, right, bottom);
        }
    }

    public void setTouchBounds(int i, int i2, int i3, int i4) {
        if (i3 < i || i4 < i2) {
//            if (BuildConfig.DEBUG) {
//                Log.e("Grid", "grid id: " + this.mID + " setTouchBounds error :\t" + i + ", " + i2 + ", " + i3 + ", " + i4);
//            }
            i3 = Math.max(i, i3);
            i4 = Math.max(i2, i4);
        }
        if (this.mTouchRect == null) {
            this.mTouchRect = new Rect();
        }
        this.mTouchRect.set(i, i2, i3, i4);
    }

    public void setLocationTo(int i, int i2) {
        setBounds(i, i2, this.mWidth + i, this.mHeight + i2);
        if (this.mTouchRect != null) {
            this.mTouchRect.offsetTo(i, i2);
        }
    }

    public void setLocationBy(int i, int i2) {
        setBounds(this.mX + i, this.mY + i2, this.mX + i + this.mWidth, this.mY + i2 + this.mHeight);
        if (this.mTouchRect != null) {
            this.mTouchRect.offset(i, i2);
        }
    }

    public void dispatchSetLocationBy(int i, int i2) {
        setLocationBy(i, i2);
    }

    public final void setMeasuredDimens(int i, int i2) {
        if (this.mMeasuredDimens == null) {
            this.mMeasuredDimens = new Point();
        }
        this.mMeasuredDimens.x = i;
        this.mMeasuredDimens.y = i2;
    }

    public int getMeasuredWidth() {
        if (this.mMeasuredDimens == null) {
            return 0;
        }
        return this.mMeasuredDimens.x;
    }

    public int getMeasuredHeight() {
        if (this.mMeasuredDimens == null) {
            return 0;
        }
        return this.mMeasuredDimens.y;
    }

    public void onBoundsChange(int i, int i2, int i3, int i4) {
    }

    public int getLeft() {
        return this.mX;
    }

    public int getTop() {
        return this.mY;
    }

    public int getAbsX() {
        if (getParent() == null) {
            return this.mX;
        }
        return this.mX + getParent().getParentScrollX();
    }

    public int getAbsY() {
        if (getParent() == null) {
            return this.mY;
        }
        return this.mY + getParent().getParentScrollY();
    }

    public int getRight() {
        return this.mX + this.mWidth;
    }

    public int getBottom() {
        return this.mY + this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getTouchLeft() {
        return this.mTouchRect != null ? this.mTouchRect.left : this.mX;
    }

    public int getTouchTop() {
        return this.mTouchRect != null ? this.mTouchRect.top : this.mY;
    }

    public int getTouchRight() {
        return this.mTouchRect != null ? this.mTouchRect.right : this.mX + this.mWidth;
    }

    public int getTouchBottom() {
        return this.mTouchRect != null ? this.mTouchRect.bottom : this.mY + this.mHeight;
    }

    public int getTouchWidth() {
        return this.mTouchRect != null ? this.mTouchRect.width() : this.mWidth;
    }

    public int getTouchHeight() {
        return this.mTouchRect != null ? this.mTouchRect.height() : this.mHeight;
    }

    public Rect getBounds(Rect rect) {
        rect.set(this.mX, this.mY, this.mX + this.mWidth, this.mY + this.mHeight);
        return rect;
    }

    public void setBounds(Grid grid) {
        setBounds(grid.mX, grid.mY, grid.mX + grid.mWidth, grid.mY + grid.mHeight);
    }

    public void initBounds(Grid grid) {
        initBounds(grid.mOriRect.left, grid.mOriRect.top, grid.mOriRect.right, grid.mOriRect.bottom);
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public void setVisibility(int i) {
        if (this.mVisibility != i) {
            this.mVisibility = i;
            invalidate(this.mX, this.mY, this.mWidth + this.mX, this.mHeight + this.mY, true);
            if (this.mAttachInfo != null) {
                this.mAttachInfo.onGlobalGridVisibilityChanged(this, i);
            }
            if (this.mOnGridStateChangeListener != null) {
                this.mOnGridStateChangeListener.onGridVisibilityChanged(this, i);
            }
        }
    }

    public boolean isVisible() {
        return this.mVisibility == 0;
    }

    public boolean isShown() {
        Grid curGrid = this;
        while (curGrid.isVisible()) {
            GridParent gridParent = this.mParent;
            if (gridParent == null) {
                return false;
            }
            if (!(gridParent instanceof Grid)) {
                return true;
            }
            Grid grid = (Grid) gridParent;
            if (grid == null) {
                return false;
            }
            curGrid = grid;
        }
        return false;
    }

    public int getWindowVisibility() {
        if (this.mAttachInfo == null) {
            return 8;
        }
        return Visibility.getVisibility(this.mAttachInfo.getAttachedView().getWindowVisibility());
    }

    public void setBackground(AbsDrawable absDrawable) {
        if (this.mBackground != absDrawable) {
            this.mBackground = absDrawable;
            invalidate();
        }
    }

    public AbsDrawable getBackground() {
        return this.mBackground;
    }

    public void draw(Canvas canvas) {
        drawBackground(canvas);
    }

    /* access modifiers changed from: protected */
    public void drawBackground(Canvas canvas) {
        if (this.mBackground != null) {
            this.mBackground.setBounds(this.mX, this.mY, this.mX + this.mWidth, this.mY + this.mHeight);
            this.mBackground.setAlpha(this.mBackground.getDefaultAlpha());
            if (this.mBackground.isStateful()) {
                this.mBackground.setState(KeyState.NORMAL_SET);
            }
            this.mBackground.draw(canvas);
        }
    }

    public boolean touchEvent(MotionEvent motionEvent) {
        if (this.mOnGridTouchEventListener != null) {
            return this.mOnGridTouchEventListener.onTouchEvent(this, motionEvent);
        }
        return false;
    }

    public boolean hoverEvent(MotionEvent motionEvent) {
        return true;
    }

    public void onAttachedToWindow() {
    }

    public void onDetachedFromWindow() {
    }

    public void onWindowVisibilityChanged(int i) {
    }

    public void invalidate() {
        invalidate(this.mX, this.mY, this.mWidth + this.mX, this.mHeight + this.mY, false);
    }

    public void invalidate(Rect rect) {
        invalidate(rect.left, rect.top, rect.right, rect.bottom, false);
    }

    public void invalidate(int i, int i2, int i3, int i4) {
        invalidate(i, i2, i3, i4, false);
    }

    public void invalidate(int i, int i2, int i3, int i4, boolean z) {
        if (this.mParent != null && this.mAttachInfo != null) {
            if (z || this.mVisibility == 0) {
                mTmpInvalRect.set(i, i2, i3, i4);
                this.mParent.invalidateChild(this, mTmpInvalRect);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchAttachedToWindow(AttachInfo attachInfo) {
        this.mAttachInfo = attachInfo;
        onAttachedToWindow();
        if (this.mOnGridStateChangeListener != null) {
            this.mOnGridStateChangeListener.onAttachedToWindow(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchInputInterface(AttachInterface attachInterface) {
        this.mAttachInteface = attachInterface;
    }

    /* access modifiers changed from: package-private */
    public void dispatchDetachedFromWindow() {
        onDetachedFromWindow();
        if (this.mOnGridStateChangeListener != null) {
            this.mOnGridStateChangeListener.onDetachedFromWindow(this);
        }
        this.mAttachInfo = null;
    }

    /* access modifiers changed from: package-private */
    public void dispatchWindowVisibilityChanged(int i) {
        onWindowVisibilityChanged(i);
        if (this.mOnGridStateChangeListener != null) {
            this.mOnGridStateChangeListener.onWindowVisibilityChanged(this, i);
        }
    }

    public final GridParent getParent() {
        return this.mParent;
    }

    public boolean isAttached() {
        return this.mAttachInfo != null;
    }

    /* access modifiers changed from: protected */
    public final AttachInfo getAttachInfo() {
        return this.mAttachInfo;
    }

    public final AttachInterface getAttachInterface() {
        return this.mAttachInteface;
    }

    public IBinder getWindowToken() {
        if (this.mAttachInfo == null) {
            return null;
        }
        return this.mAttachInfo.getAttachedView().getWindowToken();
    }

    /* access modifiers changed from: protected */
    public void assignParent(GridParent gridParent) {
        if (this.mParent == null) {
            this.mParent = gridParent;
        } else if (gridParent == null) {
            this.mParent = null;
        } else {
            throw new RuntimeException("grid " + this + " being added, but" + " it already has a parent");
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public final boolean pointInView(float f, float f2) {
        return this.mTouchRect != null ? f >= ((float) this.mTouchRect.left) && f <= ((float) this.mTouchRect.right) && f2 >= ((float) this.mTouchRect.top) && f2 <= ((float) this.mTouchRect.bottom) : f >= ((float) this.mX) && f <= ((float) (this.mX + this.mWidth)) && f2 >= ((float) this.mY) && f2 <= ((float) (this.mY + this.mHeight));
    }

    public void drawContent(Canvas canvas, Rect rect, Drawable drawable, int state) {
        if (drawable != null && rect != null && !rect.isEmpty()) {
            if (drawable.isStateful()) {
                drawable.setState(KeyState.getStateSet(state));
            }
            drawable.setBounds(rect);
            drawable.draw(canvas);
            if (DEBUG) {
                mTmpInvalRect.set(rect);
                Rect rect2 = mTmpInvalRect;
                rect2.right--;
                Rect rect3 = mTmpInvalRect;
                rect3.bottom--;
                canvas.drawRect(mTmpInvalRect, getDebugPaint(-65536));
            }
        }
    }

    public final float pointInViewDistance(float f, float f2) {
        int i;
        int i2;
        int i3;
        int i4;
        if (this.mTouchRect != null) {
            i = this.mTouchRect.left;
            i2 = this.mTouchRect.top;
            i3 = this.mTouchRect.right;
            i4 = this.mTouchRect.bottom;
        } else {
            i = this.mX;
            i2 = this.mY;
            i3 = this.mWidth + this.mX;
            i4 = this.mY + this.mHeight;
        }
        if (((float) i) <= f && f <= ((float) i3)) {
            return Math.min(Math.abs(((float) i2) - f2), Math.abs(((float) i4) - f2));
        }
        if (((float) i2) <= f2 && f2 <= ((float) i4)) {
            return Math.min(Math.abs(((float) i) - f), Math.abs(((float) i3) - f));
        }
        float min = Math.min(Math.abs(((float) i) - f), Math.abs(((float) i3) - f));
        float min2 = Math.min(Math.abs(((float) i2) - f2), Math.abs(((float) i4) - f2));
        return (float) Math.sqrt(Math.pow((double) min2, 2.0d) + Math.pow((double) min, 2.0d));
    }

    public void scaleDrawable(float f, float f2) {
    }

    private int modifyRoundWidth(float f) {
        return Math.round(((float) this.mOriRect.width()) * f);
    }

    public void updateLoc(float f, float f2) {
        int modifyRoundWidth;
        int i = 1;

        int i2 = (int) (((float) this.mOriRect.left) * f);
        int i3 = (int) (((float) this.mOriRect.top) * f2);
        switch (this.mModifyScaleType) {
            case 1:
                modifyRoundWidth = modifyRoundWidth(f);
                break;
            default:
                modifyRoundWidth = (int) (((float) this.mOriRect.width()) * f);
                break;
        }
        int height = (int) (((float) this.mOriRect.height()) * f2);
        if (height < 1 && this.mOriRect.height() > 0) {
            height = 1;
        }
        setBounds(i2, i3, modifyRoundWidth + i2, height + i3);
        if (this.mOriTouchRect != null) {
            int i4 = (int) (((float) this.mOriTouchRect.left) * f);
            int i5 = (int) (((float) this.mOriTouchRect.top) * f2);
            int width = (int) (((float) this.mOriTouchRect.width()) * f);
            int height2 = (int) (((float) this.mOriTouchRect.height()) * f2);
            if (height2 >= 1 || this.mOriTouchRect.height() <= 0) {
                i = height2;
            }
            setTouchBounds(i4, i5, i4 + width, i + i5);
        }
    }

    public void setBackgroundAlpha(int i) {
        if (this.mBackground != null) {
            this.mBackground.setDefaultAlpha(i);
            invalidate();
        }
    }

    public boolean isLongClicked() {
        return false;
    }

    public boolean hasSlipAction() {
        return false;
    }

    public boolean hasLongPressSlipAction() {
        return false;
    }

    public void dispatchSetBackgroundAlpha(int i) {
        setBackgroundAlpha(i);
    }

    public void dispatchOutputLocation() {
        onOutputLocation();
    }

    public void onOutputLocation() {
    }

    public void setGridContentAnimating(boolean z) {
        boolean z2 = false;
        if (z != this.mGridContentAnimating) {
            boolean z3 = this.mGridContentAnimating || this.mGridObjAnimating;
            this.mGridContentAnimating = z;
            if (this.mGridContentAnimating || this.mGridObjAnimating) {
                z2 = true;
            }
            if (z3 != z2) {
                handleGridAnimatingStateChange(z2);
            }
            onAnimatingStateChange();
        }
    }

    public void setGridObjAnimating(boolean z) {
        boolean z2 = false;
        if (z != this.mGridObjAnimating) {
            boolean z3 = this.mGridContentAnimating || this.mGridObjAnimating;
            this.mGridObjAnimating = z;
            if (this.mGridContentAnimating || this.mGridObjAnimating) {
                z2 = true;
            }
            if (z3 != z2) {
                handleGridAnimatingStateChange(z2);
            }
            onAnimatingStateChange();
        }
    }

    public void setGridLightAnimating(boolean z) {
        if (z != this.mGridLightAnimating) {
            this.mGridLightAnimating = z;
        }
    }

    public boolean isGridLightAnimating() {
        return this.mGridLightAnimating;
    }

    private void handleGridAnimatingStateChange(boolean z) {
        GridParent parent = getParent();
        if (parent == null) {
            return;
        }
        if (z) {
            parent.animateChildInParent(this);
            if (this.mAnimatorListener != null) {
                this.mAnimatorListener.onAnimationStart(null);
                return;
            }
            return;
        }
        parent.unanimateChildInParent(this);
        if (this.mAnimatorListener != null) {
            this.mAnimatorListener.onAnimationEnd(null);
        }
    }

    public void setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.mAnimatorListener = animatorListener;
    }

    /* access modifiers changed from: protected */
    public void onAnimatingStateChange() {
    }

    /* access modifiers changed from: protected */
    public boolean isGridContentAnimating() {
        return this.mGridContentAnimating;
    }

    public boolean isAnimating() {
        return this.mGridContentAnimating || this.mGridObjAnimating;
    }

    public void setCapital(boolean z) {
    }

    public void setAccessibilityMode(boolean z) {
        if (this.mAccessibilityMode != z) {
            this.mAccessibilityMode = z;
            if (this.mAttachInfo != null) {
                this.mAttachInfo.onGlobalGridAccessibilityModeChanged(this, z);
            }
        }
    }

    public boolean isAccessibilityMode() {
        return this.mAccessibilityMode;
    }

    public void invalidateAccessibility() {
        invalidateAccessibility(this.mUniqueID);
    }

    public void invalidateAccessibilityRoot() {
        invalidateAccessibility(Integer.MIN_VALUE);
    }

    public void invalidateAccessibility(int i) {
        if (this.mAttachInfo != null) {
            this.mAttachInfo.invalidateAccessibility(i);
        }
    }

    public static boolean isGridDescendantOf(Grid grid, Grid grid2) {
        if (grid == grid2) {
            return true;
        }
        GridParent parent = grid.getParent();
        return (parent instanceof GridGroup) && isGridDescendantOf((Grid) parent, grid2);
    }

    public void getVisibleRect(Rect rect) {
        rect.set(getLeft(), getTop(), getRight(), getBottom());
        for (GridParent gridParent = this.mParent; gridParent instanceof GridGroup; gridParent = gridParent.getParent()) {
            GridGroup gridGroup = (GridGroup) gridParent;
            rect.offset(-gridGroup.getScrollX(), -gridGroup.getScrollY());
            rect.intersect(gridGroup.getLeft(), gridGroup.getTop(), gridGroup.getRight(), gridGroup.getBottom());
        }
    }

    public void getVisibleTouchRect(Rect rect) {
        rect.set(getTouchLeft(), getTouchTop(), getTouchRight(), getTouchBottom());
        for (GridParent gridParent = this.mParent; gridParent instanceof GridGroup; gridParent = gridParent.getParent()) {
            GridGroup gridGroup = (GridGroup) gridParent;
            rect.offset(-gridGroup.getScrollX(), -gridGroup.getScrollY());
            rect.intersect(gridGroup.getLeft(), gridGroup.getTop(), gridGroup.getRight(), gridGroup.getBottom());
        }
    }
}
