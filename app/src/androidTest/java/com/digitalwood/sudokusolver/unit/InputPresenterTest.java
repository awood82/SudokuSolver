package com.digitalwood.sudokusolver.unit;

import com.digitalwood.sudokusolver.input.handlers.OnSolveButtonClickedListener;
import com.digitalwood.sudokusolver.input.model.IInputModel;
import com.digitalwood.sudokusolver.input.presenter.InputPresenter;
import com.digitalwood.sudokusolver.input.view.IInputView;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public class InputPresenterTest extends TestCase {

    public void testView_WhenSolveClicked_GetsProblemFromViewAndTriesToSolve() {
        IInputView mockView = mock(IInputView.class);
        IInputModel mockModel = mock(IInputModel.class);
        InputPresenter presenter = new InputPresenter(mockView, mockModel);
        ArgumentCaptor<OnSolveButtonClickedListener> arg = ArgumentCaptor.forClass(OnSolveButtonClickedListener.class);

        verify(mockView).whenSolveButtonClicked(arg.capture());
        arg.getValue().onClick();

        verify(mockView).getInputArray();
        verify(mockModel).solveSudoku(any(int[][].class));
    }
}
