package com.digitalwood.sudokusolver.Input.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public class InputFragment extends Fragment {

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
        //InputPresenter presenter = new InputPresenter(this, model);
    }
}
