package com.digitalwood.sudokusolver.input.model;

import android.util.Log;

import com.digitalwood.sudokusolver.common.Constants;
import com.digitalwood.sudokusolver.input.handlers.OnPuzzleSolvedListener;

import java.util.Arrays;

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
    public int[] solveSudoku(final int[] grid) {
        int[] solvedPuzzle = Arrays.copyOf(grid, Constants.TOTAL_WIDTH * Constants.TOTAL_WIDTH);

        boolean wasSolved = validateSudoku(solvedPuzzle) ? solveSudoku(solvedPuzzle, 0, 0) : false;

        if (mOnPuzzleSolvedListener != null) {
            if (wasSolved) {
                mOnPuzzleSolvedListener.onSuccess(solvedPuzzle);
            } else {
                mOnPuzzleSolvedListener.onFailure();
            }
        }

        return solvedPuzzle;
    }

    private boolean validateSudoku(int[] grid) {
        for (int i = 0; i < Constants.TOTAL_WIDTH; i++) {
            for (int j = 0; j < Constants.TOTAL_WIDTH; j++) {
                int value = grid[i * Constants.TOTAL_WIDTH + j];
                if (value != 0) {
                    if (!isValid(grid, i, j, value)) {
                        return false;
                    }
                }
           }
        }
        return true;
    }

    private boolean solveSudoku(int[] grid, int i, int j) {
        // Check if done
        if (j == Constants.TOTAL_WIDTH) {
            j = 0;
            if (++i == Constants.TOTAL_WIDTH) {
                return true; // Solved!
            }
        }

        final int index = i * Constants.TOTAL_WIDTH + j;
        // Don't touch filled squares
        if (grid[index] != 0) {
            return solveSudoku(grid, i, j + 1);
        }

        // Find a value that fits at i,j
        for (int value = 1; value <= Constants.TOTAL_WIDTH; value++) {
            if (isValid(grid, i, j, value)) {
                grid[index] = value; // Try this value
                if (solveSudoku(grid, i, j + 1)) {
                    return true;
                }
            }
        }

        // There was no valid value for i,j, so backtrack
        grid[index] = 0;
        return false;
    }

    public boolean isValid(int[] grid, int i, int j, int value) {
        // Check row
        for (int x = 0; x < Constants.TOTAL_WIDTH; x++) {
            if (grid[i * Constants.TOTAL_WIDTH + x] == value) {
                return false;
            }
        }

        // Check column
        for (int y = 0; y < Constants.TOTAL_WIDTH; y++) {
            if (grid[y * Constants.TOTAL_WIDTH + j] == value) {
                return false;
            }
        }

        // Check block
        int startI = i - i % Constants.BLOCK_WIDTH;
        int startJ = j - j % Constants.BLOCK_WIDTH;
        for (int y = startI; y < startI + Constants.BLOCK_WIDTH; y++) {
            for (int x = startJ; x < startJ + Constants.BLOCK_WIDTH; x++) {
                if (grid[y * Constants.TOTAL_WIDTH + x] == value) {
                    return false;
                }
            }
        }

        return true;
    }
}
