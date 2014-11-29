package com.digitalwood.sudokusolver.input.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.digitalwood.sudokusolver.R;
import com.digitalwood.sudokusolver.input.handlers.OnSolveButtonClickedListener;
import com.digitalwood.sudokusolver.input.presenter.InputPresenter;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public class InputFragment extends Fragment implements IInputView {

    public static int BLOCK_WIDTH = 3;
    public static int TOTAL_WIDTH = BLOCK_WIDTH * BLOCK_WIDTH;
    private static int WIDTH_DP = 30;
    private static int HEIGHT_DP = 30;
    private static int BORDER_DP = 2;
    private GridLayout inputGrid;
    private OnSolveButtonClickedListener mOnSolveButtonClickedListener;

    public static InputFragment newInstance() {
        InputFragment fragment = new InputFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        //InputModel model = new InputModel();
        InputPresenter presenter = new InputPresenter(this, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_input, container, false);

        inputGrid = (GridLayout) rootView.findViewById(R.id.input_grid);
        for (int i = 0; i < TOTAL_WIDTH; i++) {
            for (int j = 0; j < TOTAL_WIDTH; j++) {
                final EditText editText = (EditText) inflater.inflate(R.layout.edittext_box, container, false);
                editText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editText.setSelection(editText.length());
                    }
                });
                editText.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            editText.setSelection(editText.length());
                        }
                        return false;
                    }
                });
                editText.addTextChangedListener(new TextWatcher() {
                    int s;

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                        s = start;
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.length() > 1) {
                            if (s == 0) {
                                editable.delete(1, 2);
                            } else {
                                editable.delete(0, 1);
                            }
                        }
                    }
                });
                inputGrid.addView(editText);
            }
        }

        Button solveButton = (Button) rootView.findViewById(R.id.button_solve);
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnSolveButtonClickedListener.onClick();
            }
        });

        return rootView;
    }

    @Override
    public void whenSolveButtonClicked(OnSolveButtonClickedListener listener) {
        mOnSolveButtonClickedListener = listener;
    }

    @Override
    public int[][] getInputArray() {
        int[][] inputs = new int[TOTAL_WIDTH][TOTAL_WIDTH];
        for (int i = 0; i < TOTAL_WIDTH; i++) {
            for (int j = 0; j < TOTAL_WIDTH; j++) {
                EditText editText = (EditText) inputGrid.getChildAt(i * TOTAL_WIDTH + j);
                Editable number = editText.getText();
                inputs[i][j] = number.length() > 0 ? Integer.parseInt(number.toString()) : 0;
            }
        }

        return inputs;
    }
}
