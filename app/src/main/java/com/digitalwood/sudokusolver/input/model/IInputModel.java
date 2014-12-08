package com.digitalwood.sudokusolver.input.model;

import com.digitalwood.sudokusolver.input.handlers.OnPuzzleSolvedListener;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public interface IInputModel {
    void whenPuzzleIsSolved(OnPuzzleSolvedListener listener);

    int[] solveSudoku(final int[] grid);
}
