package com.digitalwood.sudokusolver.input.model;

import android.util.Log;

import com.digitalwood.sudokusolver.common.Constants;
import com.digitalwood.sudokusolver.input.handlers.OnPuzzleSolvedListener;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public class InputModel implements IInputModel {
    private OnPuzzleSolvedListener mOnPuzzleSolvedListener;

    @Override
    public void whenPuzzleIsSolved(OnPuzzleSolvedListener listener) {
        mOnPuzzleSolvedListener = listener;
    }

    @Override
    public int[][] solveSudoku(final int[][] grid) {
        int[][] solvedPuzzle = copy2dSquareArray(grid, Constants.TOTAL_WIDTH);

        boolean wasSolved = solveSudoku(solvedPuzzle, 0, 0);

        if (mOnPuzzleSolvedListener != null) {
            if (wasSolved) {
                mOnPuzzleSolvedListener.onSuccess(solvedPuzzle);
            } else {
                mOnPuzzleSolvedListener.onFailure();
            }
        }

        return solvedPuzzle;
    }



    private boolean solveSudoku(int[][] grid, int i, int j) {
        // Check if done
        if (j == Constants.TOTAL_WIDTH) {
            j = 0;
            if (++i == Constants.TOTAL_WIDTH) {
                return true; // Solved!
            }
        }

        // Don't touch filled squares
        if (grid[i][j] != 0) {
            return solveSudoku(grid, i, j + 1);
        }

        // Find a value that fits at i,j
        for (int value = 1; value <= Constants.TOTAL_WIDTH; value++) {
            if (isValid(grid, i, j, value)) {
                grid[i][j] = value; // Try this value
                if (solveSudoku(grid, i, j + 1)) {
                    return true;
                }
            }
        }

        // There was no valid value for i,j, so backtrack
        grid[i][j] = 0;
        return false;
    }

    public boolean isValid(int[][] grid, int i, int j, int value) {
        // Check row
        for (int x = 0; x < Constants.TOTAL_WIDTH; x++) {
            if (grid[i][x] == value) {
                return false;
            }
        }

        // Check column
        for (int y = 0; y < Constants.TOTAL_WIDTH; y++) {
            if (grid[y][j] == value) {
                return false;
            }
        }

        // Check block
        int startI = i - i % Constants.BLOCK_WIDTH;
        int startJ = j - j % Constants.BLOCK_WIDTH;
        for (int y = startI; y < startI + Constants.BLOCK_WIDTH; y++) {
            for (int x = startJ; x < startJ + Constants.BLOCK_WIDTH; x++) {
                if (grid[y][x] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    public int[][] copy2dSquareArray(int[][] array, int n) {
        int[][] newArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newArray[i][j] = array[i][j];
            }
        }

        return newArray;
    }
}
