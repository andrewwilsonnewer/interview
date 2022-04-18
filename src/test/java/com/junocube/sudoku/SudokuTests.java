package com.junocube.sudoku;

import org.junit.jupiter.api.Disabled;
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

        Sudoku.solve(problem, 9, 3);

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

        Sudoku.solve(hardest, 9, 3);

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
        Sudoku.solve(empty, 9, 3);
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
        assertThrows(GridException.class, () -> Sudoku.solve(grid, 9, 3));
    }

    /**
     * Expect a {@link GridException} if null is passed.
     */
    @Test
    void shouldExceptionForNullGrid() {
        assertThrows(GridException.class, () -> Sudoku.solve(null, 9, 3));
    }

    /**
     * Expect a {link GridException} if the array is not size 81.
     */
    @Test
    void shouldExceptionForWrongSizeGrid() {
        assertThrows(GridException.class, () -> Sudoku.solve(new byte[3], 9, 3));
    }

    /**
     * Expect a {@link GridException} if the grid contains anything but 0 -> 9.
     */
    @Test
    void shouldFailWithInvalidInput() {
        final byte[] grid = new byte[81];
        grid[10] = 10;
        assertThrows(GridException.class, () -> Sudoku.solve(grid, 9, 3));
    }

    /**
     * I found an unsolvable problem on quora
     * <a href="https://www.quora.com/Has-anyone-ever-seen-an-unsolvable-Sudoku">https://www.quora.com/Has-anyone-ever-seen-an-unsolvable-Sudoku</a>
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
        assertThrows(GridException.class, () -> Sudoku.solve(grid, 9, 3));
    }

    /**
     * This is a 16 x 16 problem.  Unfortunately it takes too long to solve with my solution.  Time to understand dancing links.
     * <a href="https://www.spoj.com/problems/SUDOKU/">https://www.spoj.com/problems/SUDOKU/</a>
     * This proves why we need the dancing links solution.
     */
    @Test
    @Disabled("takes too long!")
    void shouldHandle16Grid() throws GridException {
        final byte[] grid16problem = {
                0, 0, 1, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 15, 0, 9,
                0, 10, 0, 0, 1, 0, 2, 0, 16, 0, 3, 7, 6, 0, 8, 0,
                0, 0, 4, 0, 0, 6, 0, 9, 0, 5, 0, 0, 0, 0, 16, 0,
                0, 7, 0, 5, 12, 0, 8, 0, 0, 0, 0, 13, 0, 10, 0, 0,
                0, 0, 0, 0, 5, 0, 0, 0, 0, 3, 0, 0, 7, 0, 0, 0,
                0, 9, 0, 0, 11, 0, 7, 1, 0, 2, 0, 0, 0, 5, 0, 10,
                4, 0, 7, 16, 0, 0, 10, 0, 6, 0, 0, 0, 0, 1, 0, 0,
                0, 5, 0, 0, 0, 3, 0, 2, 0, 0, 4, 16, 0, 0, 15, 0,
                5, 0, 0, 6, 0, 13, 0, 0, 4, 0, 0, 12, 0, 11, 0, 1,
                0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 9, 0, 12, 0,
                8, 0, 16, 0, 3, 0, 0, 6, 0, 1, 0, 0, 2, 0, 0, 0,
                0, 0, 0, 7, 0, 15, 4, 0, 0, 0, 10, 0, 0, 0, 0, 8,
                11, 0, 0, 0, 10, 0, 0, 0, 0, 8, 0, 1, 0, 16, 0, 12,
                0, 0, 2, 0, 0, 16, 0, 0, 5, 0, 0, 11, 0, 0, 1, 0,
                0, 8, 0, 0, 2, 0, 0, 11, 0, 0, 6, 9, 0, 3, 0, 0,
                0, 0, 6, 0, 0, 0, 3, 0, 0, 4, 0, 0, 8, 0, 14, 0 };

        final byte[] solution = {
                6, 16, 1, 8, 13, 10, 5, 3, 14, 12, 2, 4, 11, 15, 7, 9,
                15, 10, 13, 9, 1, 14, 2, 4, 16, 11, 3, 7, 6, 12, 8, 5, 
                12, 14, 4, 11, 7, 6, 15, 9, 10, 5, 1, 8, 13, 2, 16, 3,
                2, 7, 3, 5, 12, 11, 8, 16, 15, 6, 9, 13, 1, 10, 4, 14, 
                13, 6, 8, 2, 5, 12, 16, 15, 1, 3, 11, 10, 7, 14, 9, 4, 
                3, 9, 12, 14, 11, 4, 7, 1, 8, 2, 13, 15, 16, 5, 6, 10, 
                4, 15, 7, 16, 9, 8, 10, 13, 6, 14, 12, 5, 3, 1, 11, 2, 
                10, 5, 11, 1, 6, 3, 14, 2, 7, 9, 4, 16, 12, 8, 15, 13, 
                5, 2, 15, 6, 16, 13, 9, 10, 4, 7, 8, 12, 14, 11, 3, 1, 
                14, 3, 10, 4, 8, 2, 1, 5, 11, 13, 15, 6, 9, 7, 12, 16, 
                8, 13, 16, 12, 3, 7, 11, 6, 9, 1, 5, 14, 2, 4, 10, 15, 
                1, 11, 9, 7, 14, 15, 4, 12, 2, 16, 10, 3, 5, 6, 13, 8, 
                11, 4, 5, 13, 10, 9, 6, 14, 3, 8, 7, 1, 15, 16, 2, 12, 
                7, 12, 2, 3, 4, 16, 13, 8, 5, 15, 14, 11, 10, 9, 1, 6, 
                16, 8, 14, 15, 2, 1, 12, 11, 13, 10, 6, 9, 4, 3, 5, 7, 
                9, 1, 6, 10, 15, 5, 3, 7, 12, 4, 16, 2, 8, 13, 14, 11 };

        Sudoku.solve(grid16problem, 16, 4);

        assertArrayEquals(solution, grid16problem);
    }
}
