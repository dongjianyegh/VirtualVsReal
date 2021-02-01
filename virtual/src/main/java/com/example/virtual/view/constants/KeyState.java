package com.example.virtual.view.constants;

public class KeyState {
    private static final int COUNT = 5;
    public static final int DISABLE = 4;
    public static final int[] DISABLE_SET = {4};
    public static final int FOCUSED = 2;
    public static final int[] FOCUSED_SET = {2};
    public static final int NORMAL = 0;
    public static final int[] NORMAL_SET = {0};
    public static final int PRESSED = 1;
    public static final int[] PRESSED_SET = {1};
    public static final int SELECTED = 3;
    public static final int[] SELECTED_SET = {3};

    public static final int[] getStateSet(int i) {
        switch (i) {
            case 0:
                return NORMAL_SET;
            case 1:
                return PRESSED_SET;
            case 2:
                return FOCUSED_SET;
            case 3:
                return SELECTED_SET;
            case 4:
                return DISABLE_SET;
            default:
                throw new IllegalArgumentException("the state " + i + " is not exist.");
        }
    }

    public static final int count() {
        return 5;
    }
}
