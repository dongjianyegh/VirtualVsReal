package com.example.virtualvsreal.virtualinput;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;

import com.example.virtual.view.Grid;
import com.example.virtual.view.GridRootView;
import com.example.virtual.view.drawable.MultiColorTextDrawable;
import com.example.virtual.view.drawable.SingleColorDrawable;
import com.example.virtual.view.interfaces.OnGridTouchEventListener;
import com.example.virtualvsreal.InputData;
import com.example.virtualvsreal.InputInfo;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author dongjianye on 1/29/21
 */
public class VirtualInputView extends GridRootView {

    private KeyboardGridGroup mKeyboardView;
    private OnGridTouchEventListener mOnGridTouchEventListener;

    public VirtualInputView(Context context) {
        super(context);
        init(context);
    }

    public VirtualInputView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public VirtualInputView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public VirtualInputView(Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context);
    }

    private void init(Context context) {

        mOnGridTouchEventListener = new OnGridTouchEventListener() {
            @Override
            public boolean onTouchEvent(Grid grid, MotionEvent motionEvent) {
                if (!(grid instanceof KeyGrid)) {
                    return false;
                }

                KeyGrid keyGrid = (KeyGrid) grid;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        keyGrid.setPressed(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        keyGrid.setPressed(false);
                        break;
                    default:
                        break;

                }
                return true;
            }
        };

        setSupportMultiTouch(true);
        mKeyboardView = new KeyboardGridGroup(context);

        mKeyboardView.setBounds(InputData.sInstance.mKeyboardBounds.left,
                InputData.sInstance.mKeyboardBounds.top,
                InputData.sInstance.mKeyboardBounds.right,
                InputData.sInstance.mKeyboardBounds.bottom);

        SingleColorDrawable background = new SingleColorDrawable(0x3f0000ff);
        mKeyboardView.setBackground(background);


        final List<InputInfo> inputInfos = InputData.sInstance.mInfos;
        for (InputInfo info : inputInfos) {
            final KeyGrid keyGrid = new KeyGrid(context);
            keyGrid.setEnabled(true);
            keyGrid.setBackground(info.mBackground);
            keyGrid.addForegroundDrawable(new Pair<>(info.mBounds, info.mForeground));
            keyGrid.setBounds(info.mBounds.left, info.mBounds.top, info.mBounds.right, info.mBounds.bottom);
            keyGrid.setOnGridTouchEventListener(mOnGridTouchEventListener);
            mKeyboardView.addGrid(keyGrid);
        }
        setContentGrid(mKeyboardView);
    }
}