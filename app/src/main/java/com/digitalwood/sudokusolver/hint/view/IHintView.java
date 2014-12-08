package com.digitalwood.sudokusolver.hint.view;

import com.digitalwood.sudokusolver.common.handlers.OnActivityCreatedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnEditButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnHideButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnRevealButtonClickedListener;

/**
 * Created by Andrew on 11/30/2014.
 * Copyright 2014
 */
public interface IHintView {
    void whenActivityCreated(OnActivityCreatedListener listener);

    void whenEditButtonClicked(OnEditButtonClickedListener listener);

    void whenRevealButtonClicked(OnRevealButtonClickedListener listener);

    void whenHideButtonClicked(OnHideButtonClickedListener listener);

    void goToInputScreen();

    void revealSolution();

    void hideSolution();
}
