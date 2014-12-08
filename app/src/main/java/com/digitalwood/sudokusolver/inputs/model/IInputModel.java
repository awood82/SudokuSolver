package com.digitalwood.sudokusolver.inputs.model;

import com.digitalwood.sudokusolver.inputs.handlers.OnPuzzleSolvedListener;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public interface IInputModel {
    void whenPuzzleIsSolved(OnPuzzleSolvedListener listener);

    int[] solveSudoku(final int[] grid);
}
