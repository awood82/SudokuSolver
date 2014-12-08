package com.digitalwood.sudokusolver.hint.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.digitalwood.sudokusolver.R;
import com.digitalwood.sudokusolver.common.Constants;
import com.digitalwood.sudokusolver.common.handlers.OnActivityCreatedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnEditButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnHideButtonClickedListener;
import com.digitalwood.sudokusolver.hint.handlers.OnRevealButtonClickedListener;
import com.digitalwood.sudokusolver.hint.model.HintModel;
import com.digitalwood.sudokusolver.hint.presenter.HintPresenter;

/**
 * Created by Andrew on 11/30/2014.
 * Copyright 2014
 */
public class HintFragment extends Fragment implements IHintView {

    private OnActivityCreatedListener mOnActivityCreatedListener;
    private OnEditButtonClickedListener mOnEditButtonClickedListener;
    private OnRevealButtonClickedListener mOnRevealButtonClickedListener;
    private OnHideButtonClickedListener mOnHideButtonClickedListener;

    private GridLayout mHintGrid;
    private Button mRevealButton;
    private Button mHideButton;
    private int[] mInputArray;
    private int[] mSolution;

    public static HintFragment newInstance(int[] inputs, int[] solution) {
        HintFragment fragment = new HintFragment();

        Bundle args = new Bundle();
        args.putIntArray(HintActivity.EXTRA_INPUTS, inputs);
        args.putIntArray(HintActivity.EXTRA_SOLUTION, solution);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mInputArray = getArguments().getIntArray(HintActivity.EXTRA_INPUTS);
        mSolution = getArguments().getIntArray(HintActivity.EXTRA_SOLUTION);

        HintModel model = new HintModel();
        HintPresenter presenter = new HintPresenter(this, model);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hint, container, false);

        mHintGrid = (GridLayout) rootView.findViewById(R.id.hint_grid);
        for (int i = 0; i < Constants.TOTAL_WIDTH; i++) {
            for (int j = 0; j < Constants.TOTAL_WIDTH; j++) {
                final TextView hintText = (TextView) inflater.inflate(R.layout.hinttext_box, container, false);
                hintText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: Reveal
                    }
                });
                mHintGrid.addView(hintText);
            }
        }

        Button editButton = (Button) rootView.findViewById(R.id.hint_button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnEditButtonClickedListener.onClick();
            }
        });

        mRevealButton = (Button) rootView.findViewById(R.id.hint_button_reveal);
        mRevealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnRevealButtonClickedListener.onClick();
            }
        });

        mHideButton = (Button) rootView.findViewById(R.id.hint_button_hide);
        mHideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnHideButtonClickedListener.onClick();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mOnActivityCreatedListener != null) {
            mOnActivityCreatedListener.onActivityCreated();
        }
    }

    @Override
    public void whenActivityCreated(OnActivityCreatedListener listener) {
        mOnActivityCreatedListener = listener;
    }

    @Override
    public void whenEditButtonClicked(OnEditButtonClickedListener listener) {
        mOnEditButtonClickedListener = listener;
    }

    @Override
    public void whenRevealButtonClicked(OnRevealButtonClickedListener listener) {
        mOnRevealButtonClickedListener = listener;
    }

    @Override
    public void whenHideButtonClicked(OnHideButtonClickedListener listener) {
        mOnHideButtonClickedListener = listener;
    }

    @Override
    public void goToInputScreen() {
        getActivity().finish();
    }

    @Override
    public void revealSolution() {
        for (int i = 0; i < Constants.TOTAL_WIDTH; i++) {
            for (int j = 0; j < Constants.TOTAL_WIDTH; j++) {
                final int index = i * Constants.TOTAL_WIDTH + j;
                final TextView hintText = (TextView) mHintGrid.getChildAt(index);
                if (mInputArray[index] == 0) {
                    hintText.setTextColor(getResources().getColor(R.color.hint));
                    hintText.setText(String.valueOf(mSolution[index]));
                } else {
                    hintText.setTextColor(getResources().getColor(R.color.input));
                    hintText.setText(String.valueOf(mInputArray[index]));
                }
            }
        }

        mRevealButton.setVisibility(View.INVISIBLE);
        mHideButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSolution() {
        for (int i = 0; i < Constants.TOTAL_WIDTH; i++) {
            for (int j = 0; j < Constants.TOTAL_WIDTH; j++) {
                final int index = i * Constants.TOTAL_WIDTH + j;
                final TextView hintText = (TextView) mHintGrid.getChildAt(index);
                if (mInputArray[index] == 0) {
                    hintText.setText("");
                } else {
                    hintText.setText(String.valueOf(mInputArray[index]));
                }
            }
        }

        mHideButton.setVisibility(View.INVISIBLE);
        mRevealButton.setVisibility(View.VISIBLE);
    }
}
