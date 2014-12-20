package com.digitalwood.sudokusolver.hint.view;

import com.digitalwood.sudokusolver.common.handlers.OnActivityCreatedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnEditButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnHideButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnRevealButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnSquareClickedListener;

/**
 * Created by Andrew on 11/30/2014.
 * Copyright 2014
 */
public interface IHintView {
    void whenActivityCreated(OnActivityCreatedListener listener);

    void whenEditButtonClicked(OnEditButtonClickedListener listener);

    void whenRevealButtonClicked(OnRevealButtonClickedListener listener);

    void whenHideButtonClicked(OnHideButtonClickedListener listener);

    void whenSquareClicked(OnSquareClickedListener listener);

    void goToInputScreen();

    void revealSolution();

    void revealSquare(int i, int j);

    void hideSolution();
}
