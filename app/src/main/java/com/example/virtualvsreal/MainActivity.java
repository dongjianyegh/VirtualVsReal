package com.example.virtualvsreal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.example.virtualvsreal.realinput.RealInputView;
import com.example.virtualvsreal.virtualinput.VirtualInputView;

public class MainActivity extends AppCompatActivity {

    private boolean mVirtual = true;
    private VirtualInputView mVirtualInputView;
    private RealInputView mRealInputView;

    private FrameLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBounds();
        setContentView(R.layout.activity_main);
        mRootView = (FrameLayout) findViewById(R.id.root_view);

        if (mVirtual) {
            addVirtualInputView();
        } else {
            addRealInputView();
        }
    }

    private void addVirtualInputView() {
        mVirtualInputView = new VirtualInputView(this);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        mRootView.addView(mVirtualInputView, layoutParams);
    }

    private void addRealInputView() {
        mRealInputView = new RealInputView(this);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        mRootView.addView(mRealInputView, layoutParams);
    }

    private void initBounds() {
        InputData.sInstance.initBounds(this);
    }
}