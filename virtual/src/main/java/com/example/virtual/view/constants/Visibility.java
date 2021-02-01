package com.example.virtual.view.constants;

public class Visibility {
    public static final int GONE = 8;
    public static final int INVISIBLE = 4;
    public static final int VISIBLE = 0;

    public static int getVisibility(int i) {
        switch (i) {
            case 0:
                return 0;
            case 4:
                return 4;
            case 8:
                return 8;
            default:
                throw new IllegalArgumentException("the visibility " + i + " is not exist.");
        }
    }
}
