package com.digitalwood.sudokusolver.input.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digitalwood.sudokusolver.R;
import com.digitalwood.sudokusolver.common.Constants;
import com.digitalwood.sudokusolver.common.view.SudokuGridLayout;
import com.digitalwood.sudokusolver.common.handlers.OnActivityCreatedListener;
import com.digitalwood.sudokusolver.hint.view.HintActivity;
import com.digitalwood.sudokusolver.input.handlers.OnCellUpdatedListener;
import com.digitalwood.sudokusolver.input.handlers.OnSolveButtonClickedListener;
import com.digitalwood.sudokusolver.input.model.InputModel;
import com.digitalwood.sudokusolver.input.presenter.InputPresenter;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public class InputFragment extends Fragment implements IInputView {

    private static int WIDTH_DP = 30;
    private static int HEIGHT_DP = 30;
    private static int BORDER_DP = 2;
    private SudokuGridLayout mInputGrid;
    private int[] mSolution;
    private OnActivityCreatedListener mOnActivityCreatedListener;
    private OnSolveButtonClickedListener mOnSolveButtonClickedListener;
    private OnCellUpdatedListener mOnCellUpdatedListener;

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

        InputModel model = new InputModel();
        InputPresenter presenter = new InputPresenter(this, model);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_input, container, false);

        mInputGrid = (SudokuGridLayout) rootView.findViewById(R.id.input_grid);
        mInputGrid.setRowCount(Constants.TOTAL_WIDTH);
        mInputGrid.setBackgroundColor(getResources().getColor(R.color.box_outline));
        for (int i = 0; i < Constants.TOTAL_WIDTH; i++) {
            for (int j = 0; j < Constants.TOTAL_WIDTH; j++) {
                final EditText editText = (EditText) inflater.inflate(R.layout.edittext_box, container, false);
                mInputGrid.addView(editText);
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
                    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {                    }

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

                        if (editable.toString().equals("0")) {
                            editable.clear();
                        }

                        mOnCellUpdatedListener.cellUpdated();
                    }
                });
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
    public void whenSolveButtonClicked(OnSolveButtonClickedListener listener) {
        mOnSolveButtonClickedListener = listener;
    }

    @Override
    public void whenCellUpdated(OnCellUpdatedListener listener) {
        mOnCellUpdatedListener = listener;
    }

    @Override
    public int[] getInputArray() {
        int[] inputs = new int[Constants.TOTAL_WIDTH * Constants.TOTAL_WIDTH];
        for (int i = 0; i < Constants.TOTAL_WIDTH; i++) {
            for (int j = 0; j < Constants.TOTAL_WIDTH; j++) {
                final int index = i * Constants.TOTAL_WIDTH + j;
                final EditText editText = (EditText) mInputGrid.getChildAt(index);
                Editable number = editText.getText();
                inputs[index] = number.length() > 0 ? Integer.parseInt(number.toString()) : 0;
            }
        }

        return inputs;
    }

    public void setInputArray(int[] inputs) {
        for (int i = 0; i < Constants.TOTAL_WIDTH; i++) {
            for (int j = 0; j < Constants.TOTAL_WIDTH; j++) {
                final int index = i * Constants.TOTAL_WIDTH + j;
                final EditText editText = (EditText) mInputGrid.getChildAt(index);
                if (inputs[index] != 0) {
                    editText.setText(String.valueOf(inputs[index]));
                }
            }
        }
    }

    @Override
    public void setSolution(int[] grid) {
        mSolution = grid;
    }

/*    @Override
    public void showSolution() {
        int[] inputs = getInputArray();
        for (int i = 0; i < Constants.TOTAL_WIDTH; i++) {
            for (int j = 0; j < Constants.TOTAL_WIDTH; j++) {
                final int index = i * Constants.TOTAL_WIDTH + j;
                if (inputs[index] == 0) {
                    EditText editText = (EditText) mInputGrid.getChildAt(index);
                    editText.setTextColor(getResources().getColor(R.color.solution));
                    editText.setText(String.valueOf(mSolution[index]));
                }
            }
        }
    }*/

    @Override
    public void showMessage(int resId) {
        Toast.makeText(
                getActivity().getApplicationContext(),
                getResources().getText(resId),
                Toast.LENGTH_SHORT)
                .show();
    }

    public void hideKeyboard() {
        // Automatically hide the keyboard
        InputMethodManager mgr = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(mInputGrid.getWindowToken(), 0);
    }

    @Override
    public void goToHintScreen() {
        Intent intent = new Intent(getActivity(), HintActivity.class);
        intent.putExtra(HintActivity.EXTRA_INPUTS, getInputArray());
        intent.putExtra(HintActivity.EXTRA_SOLUTION, mSolution);
        startActivity(intent);
    }
}
