package com.digitalwood.sudokusolver.unit.input;

import com.digitalwood.sudokusolver.common.Constants;
import com.digitalwood.sudokusolver.input.handlers.OnPuzzleSolvedListener;
import com.digitalwood.sudokusolver.input.model.InputModel;

import junit.framework.TestCase;

import static org.mockito.Mockito.*;

/**
 * Created by Andrew on 11/28/2014.
 * Copyright 2014
 */
public class InputModelTest extends TestCase {

    public static final int MAX_SOLVE_TIME = 3000;

    public void testSolve_CompleteExcept00_SolvesCorrectly() {
        InputModel model = new InputModel();
        int[] nearlySolvedPuzzle = getEasySolvedPuzzle();
        int expectedValue = nearlySolvedPuzzle[0];
        nearlySolvedPuzzle[0] = 0; // Clear one field

        int[] solvedPuzzle = model.solveSudoku(nearlySolvedPuzzle);

        assertEquals(expectedValue, solvedPuzzle[0]);
    }

    public void testSolve_CompleteExcept44_SolvesCorrectly() {
        InputModel model = new InputModel();
        int[] nearlySolvedPuzzle = getEasySolvedPuzzle();
        final int cellIndex = 4 * Constants.TOTAL_WIDTH + 4;
        int expectedValue = nearlySolvedPuzzle[cellIndex];
        nearlySolvedPuzzle[cellIndex] = 0; // Clear one field

        int[] solvedPuzzle = model.solveSudoku(nearlySolvedPuzzle);

        assertEquals(expectedValue, solvedPuzzle[cellIndex]);
    }

    public void testSolve_WikipediaPuzzle_SolvesCorrectly() {
        InputModel model = new InputModel();
        int[] wikiPuzzle = {
                5,3,0, 0,7,0, 0,0,0,
                6,0,0, 1,9,5, 0,0,0,
                0,9,8, 0,0,0, 0,6,0,

                8,0,0, 0,6,0, 0,0,3,
                4,0,0, 8,0,3, 0,0,1,
                7,0,0, 0,2,0, 0,0,6,

                0,6,0, 0,0,0, 2,8,0,
                0,0,0, 4,1,9, 0,0,5,
                0,0,0, 0,8,0, 0,7,9
        };
        final int[] solvedWikiPuzzle = {
                5,3,4, 6,7,8, 9,1,2,
                6,7,2, 1,9,5, 3,4,8,
                1,9,8, 3,4,2, 5,6,7,

                8,5,9, 7,6,1, 4,2,3,
                4,2,6, 8,5,3, 7,9,1,
                7,1,3, 9,2,4, 8,5,6,

                9,6,1, 5,3,7, 2,8,4,
                2,8,7, 4,1,9, 6,3,5,
                3,4,5, 2,8,6, 1,7,9
        };

        int[] result = model.solveSudoku(wikiPuzzle);

        for (int i = 0; i < Constants.TOTAL_WIDTH; i++) {
            for (int j = 0; j < Constants.TOTAL_WIDTH; j++) {
                int index = i * Constants.TOTAL_WIDTH + j;
                assertEquals(solvedWikiPuzzle[index], result[index]);
            }
        }
    }

    public void testSolve_SameValuesInFirstRow_FailsQuickly() {
        final InputModel model = new InputModel();
        final int[] invalidPuzzle = new int[Constants.TOTAL_WIDTH * Constants.TOTAL_WIDTH];
        for (int i = 0; i < Constants.BLOCK_WIDTH; i++) {
            invalidPuzzle[i] = 1;
        }
        OnPuzzleSolvedListener mockListener = mock(OnPuzzleSolvedListener.class);
        model.whenPuzzleIsSolved(mockListener);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(MAX_SOLVE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fail("Test timed out"); // Give it only a few seconds
            }
        });

        t.start();
        int[] solvedPuzzle = model.solveSudoku(invalidPuzzle);

        verify(mockListener).onFailure();
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
    private int[] getEasySolvedPuzzle() {
        int[] inputs = new int[Constants.TOTAL_WIDTH * Constants.TOTAL_WIDTH];
        for (int i = 0; i < Constants.TOTAL_WIDTH; i++) {
            int value = i + 1;
            for (int j = 0; j < Constants.TOTAL_WIDTH; j++) {
                inputs[i * Constants.TOTAL_WIDTH + j] = value;
                value += 3;
                if ((j + 1) % Constants.BLOCK_WIDTH == 0) {
                    value++;
                }
                if (value > Constants.TOTAL_WIDTH) {
                    value -= Constants.TOTAL_WIDTH;
                }
            }
        }

        return inputs;
    }
}
