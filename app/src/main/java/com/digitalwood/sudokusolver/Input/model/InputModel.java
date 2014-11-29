package com.digitalwood.sudokusolver.input.model;

import com.digitalwood.sudokusolver.common.Constants;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public class InputModel implements IInputModel {
    @Override
    public int[][] solveSudoku(final int[][] grid) {
        int[][] solvedPuzzle = copy2dSquareArray(grid, Constants.TOTAL_WIDTH);

        return solvedPuzzle;
    }


    private int[][] copy2dSquareArray(int[][] array, int n) {
        int[][] newArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newArray[i][j] = array[i][j];
            }
        }

        return newArray;
    }
}
