package com.digitalwood.sudokusolver.input.view;

import com.digitalwood.sudokusolver.input.handlers.OnSolveButtonClickedListener;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public interface IInputView {
    void whenSolveButtonClicked(OnSolveButtonClickedListener listener);

    int[][] getInputArray();
}
