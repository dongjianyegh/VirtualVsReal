package com.example.virtual.view.constants;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public final class GridConfiguration {
    private static final int ANIMATION_INTERVAL_TIME = 25;
    private static final int DEFAULT_CUSTOM_SYMBOL_LONG_PRESS_TIMEOUT = 800;
    private static final int DEFAULT_FLIPPER_INTRERVAL = 500;
    private static final int DEFAULT_LONG_PRESS_TIMEOUT = 500;
    private static final int DEFAULT_REPEAT_DELETE_START_TIMEOUT = 400;
    private static final int DEFAULT_REPEAT_PROCESS_DELETE_TIMEOUT = 34;
    private static final int DEFAULT_REPEAT_PROCESS_TIMEOUT = 50;
    private static final int DEFAULT_REPEAT_START_TIMEOUT = 800;
    private static final int EDIT_BTN_LONG_PRESS_DURATION = 250;
    private static final int MAXIMUM_FLING_VELOCITY = 4000;
    private static final int MINIMUM_FLING_VELOCITY = 50;
    private static final int SCROLL_ANIMATION_DURATION = 500;
    private static final int SCROLL_BAR_DEFAULT_DELAY = 300;
    private static final int SCROLL_BAR_FADE_DURATION = 250;
    private static final float SLIP_EXTRA_PERCENT = 0.5f;
    private static final float SLIP_TOP_EXTRA_PERCENT = 0.5f;
    private static final int TOUCH_EDGE_LENGTH = 10;
    private static final int TOUCH_SLOP = 16;
    private static GridConfiguration mConfiguration;
    private boolean mIsLayoutSizeAtLeastXLarge = false;
    private final int mMaximumFlingVelocity;
    private final int mMinimumFlingVelocity;
    private final int mPerfectMaximumFlingVelocity;
    private final int mTouchEdgeLength;
    private final int mTouchSlop;

    private GridConfiguration(Context context) {
        float f;
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        float f2 = displayMetrics.density;
        try {
            this.mIsLayoutSizeAtLeastXLarge = ((Boolean) Class.forName("android.content.res.Configuration").getMethod("isLayoutSizeAtLeast", Integer.TYPE).invoke(configuration, 4)).booleanValue();
        } catch (Exception e) {
        }
        if (this.mIsLayoutSizeAtLeastXLarge) {
            f = 1.5f * f2;
        } else {
            f = f2;
        }
        this.mTouchEdgeLength = (int) ((10.0f * f) + 0.5f);
        this.mTouchSlop = (int) ((16.0f * f) + 0.5f);
        this.mMinimumFlingVelocity = (int) ((50.0f * f) + 0.5f);
        this.mMaximumFlingVelocity = (int) ((f * 4000.0f) + 0.5f);
        this.mPerfectMaximumFlingVelocity = this.mMaximumFlingVelocity / 5;
    }

    public static GridConfiguration get(Context context) {
        if (mConfiguration == null) {
            mConfiguration = new GridConfiguration(context);
        }
        return mConfiguration;
    }

    public boolean isLayoutSizeAtLeastXLarge() {
        return this.mIsLayoutSizeAtLeastXLarge;
    }

    public int getTouchEdgeLength() {
        return this.mTouchEdgeLength;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public int getMinimumFlingVelocity() {
        return this.mMinimumFlingVelocity;
    }

    public int getMaximumFlingVelocity() {
        return this.mMaximumFlingVelocity;
    }

    public int getPerfectMaximumFlingVelocity() {
        return this.mPerfectMaximumFlingVelocity;
    }

    public static int getFlipperInterval() {
        return 500;
    }

    public static int getLongPressTimeout() {
        return 500;
    }

    public static int getCustomSymbolLongPressTimeout() {
        return 800;
    }

    public static int getRepeatProcessTimeout() {
        return 50;
    }

    public static int getRepeatProcessDeleteTimeout() {
        return 34;
    }

    public static int getRepeatStartTimeout() {
        return 800;
    }

    public static int getRepeatStartDeleteTimeout() {
        return 400;
    }

    public static int getAnimationIntervalTime() {
        return 25;
    }

    public static int getScrollAnimationTime() {
        return 500;
    }

    public static float getSlipExtraPercent() {
        return 0.5f;
    }

    public static float getSlipTopExtraPercent() {
        return 0.5f;
    }

    public static int getEditBtnLongPressDuration() {
        return 250;
    }

    public static int getScrollDefaultDelay() {
        return 300;
    }

    public static int getScrollBarFadeDuration() {
        return 250;
    }
}
