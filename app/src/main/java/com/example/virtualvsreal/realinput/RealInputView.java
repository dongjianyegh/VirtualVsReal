package com.example.virtualvsreal.realinput;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.virtual.view.Grid;
import com.example.virtual.view.interfaces.OnGridTouchEventListener;
import com.example.virtualvsreal.InputData;
import com.example.virtualvsreal.InputInfo;
import com.example.virtualvsreal.virtualinput.KeyGrid;
import com.example.virtualvsreal.virtualinput.KeyboardGridGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author dongjianye on 2/1/21
 */
public class RealInputView extends FrameLayout {

    public RealInputView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RealInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RealInputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        OnTouchListener touchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(0x3F00FF00);
                        v.setPressed(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setBackgroundColor(0x3FFF0000);
                        v.setPressed(false);
                        break;
                    default:
                        break;

                }
                return true;
            }
        };


        final List<InputInfo> inputInfos = InputData.sInstance.mInfos;
        for (InputInfo info : inputInfos) {
            final KeyView keyGrid = new KeyView(context);
//            keyGrid.setEnabled(true);
//            keyGrid.setBackgroundTintList(info.mRealBackground);
            keyGrid.setBackgroundColor(0x3FFF0000);
            keyGrid.setOnTouchListener(touchListener);
            Rect newRc = new Rect(info.mBounds);
            newRc.offset(-info.mBounds.left, -info.mBounds.top);
            keyGrid.addForegroundDrawable(new Pair<>(newRc, info.mForeground));
            addView(keyGrid);
        }

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1000);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (Float) animation.getAnimatedValue();
                for (int i = 0; i < getChildCount(); ++i) {
                    getChildAt(i).setAlpha(alpha);
                }
            }
        });
        animator.start();
        setBackgroundColor(0x3f0000ff);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), InputData.sInstance.mKeyboardBounds.bottom);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        for (int i = 0; i < getChildCount(); ++i) {
            final Rect rc = InputData.sInstance.mInfos.get(i).mBounds;
            getChildAt(i).layout(rc.left, rc.top, rc.right, rc.bottom);
        }
    }
}