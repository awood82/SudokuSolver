package com.digitalwood.sudokusolver.hint.presenter;

import com.digitalwood.sudokusolver.R;
import com.digitalwood.sudokusolver.common.handlers.OnActivityCreatedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnEditButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnHideButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnRevealButtonClickedListener;
import com.digitalwood.sudokusolver.hint.model.IHintModel;
import com.digitalwood.sudokusolver.hint.view.IHintView;

/**
 * Created by Andrew on 11/30/2014.
 * Copyright 2014
 */
public class HintPresenter {
    private final IHintView mView;
    private final IHintModel mModel;

    public HintPresenter(IHintView view, IHintModel model) {
        mView = view;
        mModel = model;

        addViewListeners();
        addModelListeners();
    }

    private void addViewListeners() {
        mView.whenActivityCreated(new OnActivityCreatedListener() {
            @Override
            public void onActivityCreated() {
                mView.hideSolution();
            }
        });

        mView.whenEditButtonClicked(new OnEditButtonClickedListener() {
            @Override
            public void onClick() {
                mView.goToInputScreen();
            }
        });

        mView.whenRevealButtonClicked(new OnRevealButtonClickedListener() {
            @Override
            public void onClick() {
                mView.revealSolution();
            }
        });

        mView.whenHideButtonClicked(new OnHideButtonClickedListener() {
            @Override
            public void onClick() {
                mView.hideSolution();
            }
        });
    }

    private void addModelListeners() {
    }
}
