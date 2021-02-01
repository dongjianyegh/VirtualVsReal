package com.example.virtualvsreal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.virtual.view.constants.KeyState;
import com.example.virtual.view.drawable.AbsDrawable;
import com.example.virtual.view.drawable.MultiColorDrawable;
import com.example.virtual.view.drawable.MultiColorTextDrawable;
import com.example.virtual.view.drawable.TextDrawable;
import com.example.virtual.view.drawable.TextDrawingProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author dongjianye on 1/29/21
 */
public class InputData {

    public static InputData sInstance = new InputData();

    public final List<InputInfo> mInfos = new ArrayList<>();

    private final Random mRandom = new Random();

    public final Rect mKeyboardBounds = new Rect();

    private static int screenWidth;
    private static int screenHeight;

    public TextDrawingProxy mTextDrawingProxy = new SimpleTextDraw();

    private InputData() {
        for (int i = 0; i < 26; ++i) {
            MultiColorDrawable background = new MultiColorDrawable();
            background.addColor(KeyState.NORMAL, getRandomColor());
            background.addColor(KeyState.PRESSED_SET, getRandomColor());
            background.addColor(KeyState.FOCUSED_SET, getRandomColor());
            background.addColor(KeyState.SELECTED_SET, getRandomColor());
            background.addColor(KeyState.DISABLE_SET, getRandomColor());

            int[][] states = new int[][] {
                    new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, // enabled
                    new int[] { android.R.attr.state_enabled, android.R.attr.state_focused },
                    new int[] { android.R.attr.state_enabled },
                    new int[] { android.R.attr.state_focused },
                    new int[] { android.R.attr.state_window_focused },
                    new int[] {},
            };

            int[] colors = new int[] {
                    background.getColor(KeyState.NORMAL_SET),
                    background.getColor(KeyState.PRESSED_SET),
                    background.getColor(KeyState.FOCUSED_SET),
                    background.getColor(KeyState.SELECTED_SET),
                    background.getColor(KeyState.DISABLE_SET),
                    background.getColor(KeyState.DISABLE_SET),
            };

            ColorStateList stateList = new ColorStateList(states, colors);

            String text = String.valueOf((char) ('A' + i));

            MultiColorTextDrawable foreground = new MultiColorTextDrawable();
            foreground.setText(text);
            foreground.setTextSize(40f);
            foreground.addColor(KeyState.NORMAL, getRandomColor());
            foreground.addColor(KeyState.PRESSED_SET, getRandomColor());
            foreground.addColor(KeyState.FOCUSED_SET, getRandomColor());
            foreground.addColor(KeyState.SELECTED_SET, getRandomColor());
            foreground.addColor(KeyState.DISABLE_SET, getRandomColor());
            foreground.setTextDrawingProxy(mTextDrawingProxy);

            mInfos.add(new InputInfo(background, foreground, stateList));
        }
    }

    public void initBounds(Context context) {
        final int screenWidth = getScreenWidth(context);
        final int screenHeight = getScreenHeight(context);

        final int keyboardWidth = screenWidth;
        final int keyboardHeight = screenHeight / 3;

        final int gapH = keyboardWidth / 28;
        final int gapV = keyboardHeight / 40;

        final int keyWidth = keyboardWidth / 7;
        final int keyHeight = keyboardHeight / 7;

        mKeyboardBounds.left = mKeyboardBounds.top = 0;
        mKeyboardBounds.right = keyboardWidth;
        mKeyboardBounds.bottom = keyboardHeight;

        for (int i = 0; i < mInfos.size(); ++i) {
            InputInfo info = mInfos.get(i);

            final int row = i / 5;
            final int column = i % 5;

            final int keyLeft = gapH * (column + 1) + keyWidth * column;
            final int keyTop = gapV * (row + 1) + keyHeight * row;

            info.setBounds(keyLeft, keyTop, keyLeft + keyWidth, keyTop + keyHeight);
        }
    }

    private int getRandomColor() {
        int r = mRandom.nextInt(256);
        int g = mRandom.nextInt(256);
        int b = mRandom.nextInt(256);

        return Color.argb(180, r, g, b);
    }

    public static void setScreenSize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    public static void initScreenResolutionInfo(Context context) {
        if(context == null)
            return;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(wm == null)
            return;

        int width = 0, height = 0;
        final DisplayMetrics metrics = new DisplayMetrics();
        Display display = wm.getDefaultDisplay();
        Method mGetRawH = null, mGetRawW = null;

        try {
            // For JellyBean 4.2 (API 17) and onward
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealMetrics(metrics);

                width = metrics.widthPixels;
                height = metrics.heightPixels;
            } else {
                mGetRawH = Display.class.getMethod("getRawHeight");
                mGetRawW = Display.class.getMethod("getRawWidth");

                try {
                    width = (Integer) mGetRawW.invoke(display);
                    height = (Integer) mGetRawH.invoke(display);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                }
            }
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        } catch (Exception e){
        }

        if (width == 0 || height == 0) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                    Point screenSize = new Point();
                    display.getSize(screenSize);
                    width = screenSize.x;
                    height = screenSize.y;
                } else {
                    width = display.getWidth();
                    height = display.getHeight();
                }
            } catch (Exception e) {

            }

        }

        setScreenSize(width, height);
    }

    public static int getScreenHeight(Context context) {
        if(screenHeight <= 0)
            initScreenResolutionInfo(context);

        return screenHeight;
    }

    public static int getScreenWidth(Context context) {
        if(screenWidth <= 0)
            initScreenResolutionInfo(context);

        return screenWidth;
    }
}