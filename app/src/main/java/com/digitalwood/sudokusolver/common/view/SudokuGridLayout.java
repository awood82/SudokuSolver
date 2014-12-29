package com.digitalwood.sudokusolver.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.digitalwood.sudokusolver.R;

/**
 * Created by Andrew on 12/29/2014.
 * Copyright 2014
 */
public class SudokuGridLayout extends ViewGroup {
    private static int BORDER_SIZE_PX = 4;
    private static final int DEFAULT_BG_COLOR = 0xFF000000;
    private int mNumRows;
    private int mNumRowsPerBox;
    private int mBackgroundColor;
    private int mWidth;
    private int mHeight;

    public SudokuGridLayout(Context context) {
        super(context);
        init();
    }

    public SudokuGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SudokuGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mBackgroundColor = DEFAULT_BG_COLOR;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int curL;
            int curT = 0;
            ViewGroup.LayoutParams params = getChildAt(0).getLayoutParams();
            int w = params.width;
            int h = params.height;
            for (int i = 0; i < mNumRows; i++) {
                if (i % mNumRowsPerBox == 0) {
                    curT += BORDER_SIZE_PX;
                }
                curL = 0;
                for (int j = 0; j < mNumRows; j++) {
                    if (j % mNumRowsPerBox == 0) {
                        curL += BORDER_SIZE_PX;
                    }

                    View box = getChild(i, j);
                    box.layout(curL, curT, curL + w, curT + h);
                    curL += w - 1; // overlap slightly so all borders are 1 px
                }
                curT += h - 1; // overlap slightly so all borders are 1 px
            }

            View upLeft = getChild(0, 0);
            View botRight = getChild(mNumRows - 1, mNumRows - 1);
            mWidth = botRight.getRight() - upLeft.getLeft() + BORDER_SIZE_PX * (mNumRowsPerBox + 1);
            mHeight = botRight.getBottom() - upLeft.getTop() + BORDER_SIZE_PX * (mNumRowsPerBox + 1);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mWidth > 0 && mHeight > 0) {
            setMeasuredDimension(mWidth, mHeight);
        }
    }

    public void setRowCount(int rowCount) {
        mNumRows = rowCount;
        mNumRowsPerBox = (int) Math.sqrt(rowCount);
    }

    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw each editable box
        for (int i = 0; i < mNumRows; i++) {
            for (int j = 0; j < mNumRows; j++) {
                View box = getChild(i, j);
                box.draw(canvas);
            }
        }

        // Paint filled rectangle
        Paint bgPaint = new Paint();
        bgPaint.setColor(mBackgroundColor);
        View upLeft = getChild(0, 0);
        View botRight = getChild(mNumRows - 1, mNumRows - 1);
        canvas.drawRect(
                upLeft.getLeft() - BORDER_SIZE_PX,
                upLeft.getTop() - BORDER_SIZE_PX,
                botRight.getRight() + BORDER_SIZE_PX,
                botRight.getBottom() + BORDER_SIZE_PX,
                bgPaint);
    }

    private View getChild(int i, int j) {
        return getChildAt(getIndex(i, j));
    }

    private int getIndex(int i, int j) {
        return i * mNumRows + j;
    }
}
