package com.digitalwood.sudokusolver.hint.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.digitalwood.sudokusolver.R;

/**
 * Created by Andrew on 11/30/2014.
 * Copyright 2014
 */
public class HintActivity extends ActionBarActivity {

    public static final String EXTRA_INPUTS = "EXTRA_INPUTS";
    public static final String EXTRA_SOLUTION = "EXTRA_SOLUTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        int[] inputs = getIntent().getIntArrayExtra(EXTRA_INPUTS);
        int[] solution = getIntent().getIntArrayExtra(EXTRA_SOLUTION);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, HintFragment.newInstance(inputs, solution))
                    .commit();
        }
    }

}
