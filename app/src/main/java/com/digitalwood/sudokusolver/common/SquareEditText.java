package com.digitalwood.sudokusolver.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public class SquareEditText extends EditText {
    public SquareEditText(Context context) {
        super(context);
    }

    public SquareEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int smallerDim = widthMeasureSpec < heightMeasureSpec ? widthMeasureSpec : heightMeasureSpec;
        super.onMeasure(smallerDim, smallerDim);
    }
}
