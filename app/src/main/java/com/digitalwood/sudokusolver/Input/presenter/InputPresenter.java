package com.digitalwood.sudokusolver.input.presenter;

import com.digitalwood.sudokusolver.R;
import com.digitalwood.sudokusolver.input.handlers.OnPuzzleSolvedListener;
import com.digitalwood.sudokusolver.input.handlers.OnSolveButtonClickedListener;
import com.digitalwood.sudokusolver.input.model.IInputModel;
import com.digitalwood.sudokusolver.input.view.IInputView;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public class InputPresenter {

    private final IInputView mView;
    private final IInputModel mModel;

    public InputPresenter(IInputView view, IInputModel model) {
        mView = view;
        mModel = model;

        addViewListeners();
        addModelListeners();
    }

    private void addViewListeners() {
        mView.whenSolveButtonClicked(new OnSolveButtonClickedListener() {
            @Override
            public void onClick() {
                int[][] inputs = mView.getInputArray();
                mModel.solveSudoku(inputs);
            }
        });
    }

    private void addModelListeners() {
        mModel.whenPuzzleIsSolved(new OnPuzzleSolvedListener() {
            @Override
            public void onSuccess(int[][] grid) {
                mView.setSolution(grid);
                mView.showSolution();
            }

            @Override
            public void onFailure() {
                mView.showMessage(R.string.message_solver_failed);
            }
        });
    }
}
