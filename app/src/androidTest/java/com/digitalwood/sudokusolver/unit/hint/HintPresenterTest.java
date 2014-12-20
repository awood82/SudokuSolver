package com.digitalwood.sudokusolver.unit.hint;

import com.digitalwood.sudokusolver.common.handlers.OnActivityCreatedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnEditButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnHideButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnRevealButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnSquareClickedListener;
import com.digitalwood.sudokusolver.hint.model.IHintModel;
import com.digitalwood.sudokusolver.hint.presenter.HintPresenter;
import com.digitalwood.sudokusolver.hint.view.IHintView;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

/**
 * Created by Andrew on 11/30/2014.
 * Copyright 2014
 */
public class HintPresenterTest extends TestCase {

    private IHintView mockView;
    private IHintModel mockModel;

    public void testView_WhenEditClicked_ReturnsToInputScreen() {
        HintPresenter presenter = getNewPresenter();
        ArgumentCaptor<OnEditButtonClickedListener> arg = ArgumentCaptor.forClass(OnEditButtonClickedListener.class);

        verify(mockView).whenEditButtonClicked(arg.capture());
        arg.getValue().onClick();

        verify(mockView).goToInputScreen();
    }

    public void testView_WhenActivityCreated_HidesSolutionShowsPuzzle() {
        HintPresenter presenter = getNewPresenter();
        ArgumentCaptor<OnActivityCreatedListener> arg = ArgumentCaptor.forClass(OnActivityCreatedListener.class);

        verify(mockView).whenActivityCreated(arg.capture());
        arg.getValue().onActivityCreated();

        verify(mockView).hideSolution();
    }

    public void testView_WhenRevealButtonClicked_RevealsSolution() {
        HintPresenter presenter = getNewPresenter();
        ArgumentCaptor<OnRevealButtonClickedListener> arg = ArgumentCaptor.forClass(OnRevealButtonClickedListener.class);

        verify(mockView).whenRevealButtonClicked(arg.capture());
        arg.getValue().onClick();

        verify(mockView).revealSolution();
    }

    public void testView_WhenHideButtonClicked_HidesSolution() {
        HintPresenter presenter = getNewPresenter();
        ArgumentCaptor<OnHideButtonClickedListener> arg = ArgumentCaptor.forClass(OnHideButtonClickedListener.class);

        verify(mockView).whenHideButtonClicked(arg.capture());
        arg.getValue().onClick();

        verify(mockView).hideSolution();
    }

    public void testView_WhenSquareClicked_RevealsTheSquare() {
        HintPresenter presenter = getNewPresenter();
        ArgumentCaptor<OnSquareClickedListener> arg = ArgumentCaptor.forClass(OnSquareClickedListener.class);

        verify(mockView).whenSquareClicked(arg.capture());
        arg.getValue().onClick(0, 1);

        verify(mockView).revealSquare(0, 1);
    }


    // Helper methods to make tests easier to read and write
    private HintPresenter getNewPresenter() {
        mockView = mock(IHintView.class);
        mockModel = mock(IHintModel.class);
        return new HintPresenter(mockView, mockModel);
    }
}
