package com.junocube.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Sudoku solver.
 *
 * @author andrewwilson
 * @since Apr-2022
 */
class SudokuTests {

    /**
     * This is a standard Sudoku problem.
     */
    @Test
    void shouldSolveStandardProblem() throws GridException {
        final byte[] problem = {
                3, 0, 6, 5, 0, 8, 4, 0, 0,
                5, 2, 0, 0, 0, 0, 0, 0, 0,
                0, 8, 7, 0, 0, 0, 0, 3, 1,
                0, 0, 3, 0, 1, 0, 0, 8, 0,
                9, 0, 0, 8, 6, 3, 0, 0, 5,
                0, 5, 0, 0, 9, 0, 6, 0, 0,
                1, 3, 0, 0, 0, 0, 2, 5, 0,
                0, 0, 0, 0, 0, 0, 0, 7, 4,
                0, 0, 5, 2, 0, 6, 3, 0, 0 };

        final byte[] solution = {
                3, 1, 6, 5, 7, 8, 4, 9, 2,
                5, 2, 9, 1, 3, 4, 7, 6, 8,
                4, 8, 7, 6, 2, 9, 5, 3, 1,
                2, 6, 3, 4, 1, 5, 9, 8, 7,
                9, 7, 4, 8, 6, 3, 1, 2, 5,
                8, 5, 1, 7, 9, 2, 6, 4, 3,
                1, 3, 8, 9, 4, 7, 2, 5, 6,
                6, 9, 2, 3, 5, 1, 8, 7, 4,
                7, 4, 5, 2, 8, 6, 3, 1, 9 };

        Sudoku.solve(problem);

        assertArrayEquals(solution, problem);
    }

    /**
     * Apparently this is the world's hardest sudoku, so lets try it.
     */
    @Test
    void shouldSolveHardestSudoku() throws GridException {
        final byte[] hardest = {
                8, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 3, 6, 0, 0, 0, 0, 0,
                0, 7, 0, 0, 9, 0, 2, 0, 0,
                0, 5, 0, 0, 0, 7, 0, 0, 0,
                0, 0, 0, 0, 4, 5, 7, 0, 0,
                0, 0, 0, 1, 0, 0, 0, 3, 0,
                0, 0, 1, 0, 0, 0, 0, 6, 8,
                0, 0, 8, 5, 0, 0, 0, 1, 0,
                0, 9, 0, 0, 0, 0, 4, 0, 0 };

        final byte[] solution = {
                8, 1, 2, 7, 5, 3, 6, 4, 9,
                9, 4, 3, 6, 8, 2, 1, 7, 5,
                6, 7, 5, 4, 9, 1, 2, 8, 3,
                1, 5, 4, 2, 3, 7, 8, 9, 6,
                3, 6, 9, 8, 4, 5, 7, 2, 1,
                2, 8, 7, 1, 6, 9, 5, 3, 4,
                5, 2, 1, 9, 7, 4, 3, 6, 8,
                4, 3, 8, 5, 2, 6, 9, 1, 7,
                7, 9, 6, 3, 1, 8, 4, 5, 2 };

        Sudoku.solve(hardest);

        assertArrayEquals(solution, hardest);
    }

    /**
     * Should be able to find a solution for an empty grid.
     */
    @Test
    void shouldSolveEmptyGrid() throws GridException {
        final byte[] solution = {
                1, 2, 3, 4, 5, 6, 7, 8, 9,
                4, 5, 6, 7, 8, 9, 1, 2, 3,
                7, 8, 9, 1, 2, 3, 4, 5, 6,
                2, 1, 4, 3, 6, 5, 8, 9, 7,
                3, 6, 5, 8, 9, 7, 2, 1, 4,
                8, 9, 7, 2, 1, 4, 3, 6, 5,
                5, 3, 1, 6, 4, 2, 9, 7, 8,
                6, 4, 2, 9, 7, 8, 5, 3, 1,
                9, 7, 8, 5, 3, 1, 6, 4, 2 };

        final byte[] empty = new byte[81];
        Sudoku.solve(empty);
        assertArrayEquals(solution, empty);
    }

    /**
     * Expect a {@link GridException} if an invalid Grid is provided.
     */
    @Test
    void shouldFailForInvalidInput() {
        final byte[] grid = new byte[81];
        grid[0] = 1;
        grid[1] = 1;
        assertThrows(GridException.class, () -> Sudoku.solve(grid));
    }

    /**
     * Expect a {@link GridException} if null is passed.
     */
    @Test
    void shouldExceptionForNullGrid() {
        assertThrows(GridException.class, () -> Sudoku.solve(null));
    }

    /**
     * Expect a {link GridException} if the array is not size 81.
     */
    @Test
    void shouldExceptionForWrongSizeGrid() {
        assertThrows(GridException.class, () -> Sudoku.solve(new byte[3]));
    }

    /**
     * Expect a {@link GridException} if the grid contains anything but 0 -> 9.
     */
    @Test
    void shouldFailWithInvalidInput() {
        final byte[] grid = new byte[81];
        grid[10] = 10;
        assertThrows(GridException.class, () -> Sudoku.solve(grid));
    }

    /**
     * I found an unsolvable problem on quora https://www.quora.com/Has-anyone-ever-seen-an-unsolvable-Sudoku
     */
    @Test
    void shouldFailForUnsolvableProblem() {
        final byte[] grid = new byte[81];
        grid[0] = 1;
        grid[1] = 2;
        grid[11] = 3; // this makes it unsolvable
        grid[4] = 4;
        grid[5] = 5;
        grid[6] = 6;
        grid[7] = 7;
        grid[8] = 8;
        grid[9] = 9;
        assertThrows(GridException.class, () -> Sudoku.solve(grid));
    }
}
