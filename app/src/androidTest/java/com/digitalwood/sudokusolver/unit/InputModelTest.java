package com.digitalwood.sudokusolver.unit;

import com.digitalwood.sudokusolver.input.model.InputModel;

import junit.framework.TestCase;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public class InputModelTest extends TestCase {

    private static final int TOTAL_WIDTH = 9;

    public void testSolve_NearlyCompleteInput_SolvesCorrectly() {
        InputModel model = new InputModel();
        int[][] nearlySolvedPuzzle = getEasySolvedPuzzle();
        int expectedValue = nearlySolvedPuzzle[0][0];
        nearlySolvedPuzzle[0][0] = 0; // Clear one field

        int[][] solvedPuzzle = model.solveSudoku(nearlySolvedPuzzle);

        assertEquals(expectedValue, solvedPuzzle[0][0]);
    }



    // Helper methods to make the tests easier to read and write
    /**
     * Gets solved puzzle that looks like:
     * 1 4 7  2 5 8  3 6 9
     * 2 5 8  3 6 9  4 7 1
     * 3 6 9  4 7 1  5 8 2
     * 4 ...
     * @return
     */
    private int[][] getEasySolvedPuzzle() {
        int[][] inputs = new int[TOTAL_WIDTH][TOTAL_WIDTH];
        for (int i = 0; i < TOTAL_WIDTH; i++) {
            int value = i + 1;
            for (int j = 0; j < TOTAL_WIDTH; j++) {
                inputs[i][j] = value;
                value += 3;
                if (value > TOTAL_WIDTH) {
                    value -= TOTAL_WIDTH;
                }
            }
        }

        return inputs;
    }
}
