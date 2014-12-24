package com.digitalwood.sudokusolver.input.view;

import com.digitalwood.sudokusolver.common.handlers.OnActivityCreatedListener;
import com.digitalwood.sudokusolver.input.handlers.OnSolveButtonClickedListener;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public interface IInputView {
    void whenActivityCreated(OnActivityCreatedListener listener);

    void whenSolveButtonClicked(OnSolveButtonClickedListener listener);

    void setInputArray(int[] inputs);

    int[] getInputArray();

    void setSolution(int[] grid);

//    void showSolution();

    void showMessage(int resId);

    void goToHintScreen();
}
