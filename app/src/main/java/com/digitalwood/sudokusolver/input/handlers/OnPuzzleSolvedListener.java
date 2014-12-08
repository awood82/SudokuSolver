package com.digitalwood.sudokusolver.input.handlers;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public interface OnPuzzleSolvedListener {
    void onSuccess(int[] grid);
    void onFailure();
}
