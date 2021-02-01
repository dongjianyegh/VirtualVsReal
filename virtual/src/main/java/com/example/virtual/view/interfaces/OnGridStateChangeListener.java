package com.example.virtual.view.interfaces;


import com.example.virtual.view.Grid;

public interface OnGridStateChangeListener {
    public void onAttachedToWindow(Grid grid);

    public void onDetachedFromWindow(Grid grid);

    public void onGridVisibilityChanged(Grid grid, int i);

    public void onWindowVisibilityChanged(Grid grid, int i);
}
