package com.digitalwood.sudokusolver.input.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.digitalwood.sudokusolver.R;


public class InputActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.fragmentContainer) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, InputFragment.newInstance())
                    .commit();
        }
    }
}
