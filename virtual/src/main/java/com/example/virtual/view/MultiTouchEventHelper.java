package com.example.virtual.view;

import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import com.example.virtual.BuildConfig;

public class MultiTouchEventHelper {

    private static final String TAG = "NewMultiTouchEventHelper";

    private int mLastID = -1;
    private OnMultiTouchEventListener mListener;
    private OnMultiTouchEventListener mTouchTarget;

    public interface OnMultiTouchEventListener {
        boolean isLongClicked();

        boolean onExportTouchEvent(MotionEvent motionEvent);
    }

    public MultiTouchEventHelper(OnMultiTouchEventListener onMultiTouchEventListener) {
        if (onMultiTouchEventListener == null) {
            throw new NullPointerException("listener can't be null.");
        }
        this.mListener = onMultiTouchEventListener;
    }

    public boolean onImportTouchEvent(MotionEvent motionEvent) {
        if (BuildConfig.DEBUG) {
            //Log.i(TAG, "onTouchEvent : " + motionEvent.getActionMasked());
        }
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                cancelAndClearTouchTargets(motionEvent);
                this.mTouchTarget = this.mListener;
                this.mLastID = motionEvent.getPointerId(0);
                boolean onExportTouchEvent = onExportTouchEvent(this.mTouchTarget, motionEvent);
                if (onExportTouchEvent) {
                    return onExportTouchEvent;
                }
                this.mTouchTarget = null;
                return onExportTouchEvent;
            case MotionEvent.ACTION_UP:
                OnMultiTouchEventListener onMultiTouchEventListener = this.mTouchTarget;
                this.mTouchTarget = null;
                if (motionEvent.findPointerIndex(this.mLastID) >= 0) {
                    onExportTouchEvent(onMultiTouchEventListener, motionEvent);
                    break;
                }
                break;
            case 2:
                if (motionEvent.getPointerCount() == 1) {
                    onExportTouchEvent(this.mTouchTarget, motionEvent);
                    break;
                }
                break;
            case 3:
                OnMultiTouchEventListener onMultiTouchEventListener2 = this.mTouchTarget;
                this.mTouchTarget = null;
                onExportTouchEvent(onMultiTouchEventListener2, motionEvent);
                break;
            case 5:
                if (this.mTouchTarget != null) {
                    if (!this.mTouchTarget.isLongClicked()) {
                        int findPointerIndex = motionEvent.findPointerIndex(this.mLastID);
                        if (findPointerIndex >= 0) {
                            MotionEvent obtain = obtain(motionEvent, findPointerIndex, 1);
                            onExportTouchEvent(this.mTouchTarget, obtain);
                            obtain.recycle();
                        }
                        this.mLastID = motionEvent.getPointerId(motionEvent.getActionIndex());
                        MotionEvent obtain2 = obtain(motionEvent, motionEvent.getActionIndex(), 0);
                        boolean onExportTouchEvent2 = onExportTouchEvent(this.mTouchTarget, obtain2);
                        obtain2.recycle();
                        if (!onExportTouchEvent2) {
                            this.mLastID = -1;
                            break;
                        }
                    }
                } else {
                    return false;
                }
                break;
            case 6:
                int findPointerIndex2 = motionEvent.findPointerIndex(this.mLastID);
                if (findPointerIndex2 >= 0 && findPointerIndex2 == motionEvent.getActionIndex()) {
                    MotionEvent obtain3 = obtain(motionEvent, findPointerIndex2, 1);
                    onExportTouchEvent(this.mTouchTarget, obtain3);
                    obtain3.recycle();
                    break;
                }
        }
        return true;
    }

    private static MotionEvent obtain(MotionEvent motionEvent, int i, int i2) {
        return MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), i2, motionEvent.getX(i), motionEvent.getY(i), motionEvent.getMetaState());
    }

    private static boolean onExportTouchEvent(OnMultiTouchEventListener onMultiTouchEventListener, MotionEvent motionEvent) {
        if (onMultiTouchEventListener != null) {
            return onMultiTouchEventListener.onExportTouchEvent(motionEvent);
        }
        return false;
    }

    public void cancelAndClearTouchTargets(MotionEvent motionEvent) {
        OnMultiTouchEventListener onMultiTouchEventListener = this.mTouchTarget;
        if (onMultiTouchEventListener != null) {
            this.mTouchTarget = null;
            if (motionEvent != null) {
                int action = motionEvent.getAction();
                motionEvent.setAction(MotionEvent.ACTION_CANCEL);
                onMultiTouchEventListener.onExportTouchEvent(motionEvent);
                motionEvent.setAction(action);
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0f, 0f, 0);
            onMultiTouchEventListener.onExportTouchEvent(obtain);
            obtain.recycle();
        }
    }
}
