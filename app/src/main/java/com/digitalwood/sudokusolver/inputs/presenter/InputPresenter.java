package com.digitalwood.sudokusolver.inputs.presenter;

import com.digitalwood.sudokusolver.R;
import com.digitalwood.sudokusolver.common.handlers.OnActivityCreatedListener;
import com.digitalwood.sudokusolver.inputs.handlers.OnPuzzleSolvedListener;
import com.digitalwood.sudokusolver.inputs.handlers.OnSolveButtonClickedListener;
import com.digitalwood.sudokusolver.inputs.model.IInputModel;
import com.digitalwood.sudokusolver.inputs.view.IInputView;

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
        mView.whenActivityCreated(new OnActivityCreatedListener() {
            @Override
            public void onActivityCreated() {
                final int[] wikiPuzzle = {
                        5,3,0, 0,7,0, 0,0,0,
                        6,0,0, 1,9,5, 0,0,0,
                        0,9,8, 0,0,0, 0,6,0,

                        8,0,0, 0,6,0, 0,0,3,
                        4,0,0, 8,0,3, 0,0,1,
                        7,0,0, 0,2,0, 0,0,6,

                        0,6,0, 0,0,0, 2,8,0,
                        0,0,0, 4,1,9, 0,0,5,
                        0,0,0, 0,8,0, 0,7,9
                };
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                mView.setInputArray(wikiPuzzle);
//                    }
//                }).start();
            }
        });

        mView.whenSolveButtonClicked(new OnSolveButtonClickedListener() {
            @Override
            public void onClick() {
                int[] inputs = mView.getInputArray();
                mModel.solveSudoku(inputs);
            }
        });
    }

    private void addModelListeners() {
        mModel.whenPuzzleIsSolved(new OnPuzzleSolvedListener() {
            @Override
            public void onSuccess(int[] grid) {
                mView.setSolution(grid);
                //mView.showSolution();
                mView.goToHintScreen();
            }

            @Override
            public void onFailure() {
                mView.showMessage(R.string.message_solver_failed);
            }
        });
    }
}
