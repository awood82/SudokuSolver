package com.digitalwood.sudokusolver.unit.input;

import com.digitalwood.sudokusolver.input.handlers.OnPuzzleSolvedListener;
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

    private IInputView mockView;
    private IInputModel mockModel;

    public void testView_WhenSolveClicked_GetsProblemFromViewAndTriesToSolve() {
        InputPresenter presenter = getNewPresenter();
        ArgumentCaptor<OnSolveButtonClickedListener> arg = ArgumentCaptor.forClass(OnSolveButtonClickedListener.class);

        verify(mockView).whenSolveButtonClicked(arg.capture());
        arg.getValue().onClick();

        verify(mockView).getInputArray();
        verify(mockModel).solveSudoku(any(int[].class));
    }

    public void testModel_WhenPuzzleSolvedSuccessfully_GoesToHintScreen() {
        InputPresenter presenter = getNewPresenter();
        ArgumentCaptor<OnPuzzleSolvedListener> arg = ArgumentCaptor.forClass(OnPuzzleSolvedListener.class);
        int[] solution = new int[81];

        verify(mockModel).whenPuzzleIsSolved(arg.capture());
        arg.getValue().onSuccess(solution);

        verify(mockView).setSolution(solution);
        verify(mockView).goToHintScreen();
    }

    public void testModel_WhenPuzzleCannotBeSolved_ShowsErrorMessage() {
        InputPresenter presenter = getNewPresenter();
        ArgumentCaptor<OnPuzzleSolvedListener> arg = ArgumentCaptor.forClass(OnPuzzleSolvedListener.class);

        verify(mockModel).whenPuzzleIsSolved(arg.capture());
        arg.getValue().onFailure();

        verify(mockView).showMessage(anyInt());
    }



    // Helper methods to make tests easier to read and write
    private InputPresenter getNewPresenter() {
        mockView = mock(IInputView.class);
        mockModel = mock(IInputModel.class);
        return new InputPresenter(mockView, mockModel);
    }
}
