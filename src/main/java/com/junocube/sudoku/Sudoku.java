package com.junocube.sudoku;

/**
 * A fast, memory efficient backtracking implementation of a Sudoku solver.  See the README.md for more details.
 *
 * @author andrew.wilson
 * @since Apr-2022
 */
public class Sudoku {

    /**
     * The size of the grid.
     */
    private static final int GRID_SIZE = 9;

    /**
     * The grid of data to be solved.
     */
    private final byte[] grid;

    /**
     * A padded array of the empty cell offsets.
     */
    private final byte[] emptyCellOffsets = new byte[GRID_SIZE*GRID_SIZE];

    /**
     * How many empty cells there are.
     */
    private final int emptyCellLength;

    /**
     * Solve a 9x9 sudoku grid.
     *
     * @param grid the grid which may contain 0 (fill) or values 1-9
     * @throws GridException if a problem occurs
     */
    public static void solve(final byte[] grid) throws GridException {
        new Sudoku(grid).solveSudoku();
    }

    private Sudoku(final byte[] grid) throws GridException {

        if (grid == null) {
            throw new GridException("Null grid");
        }

        if (grid.length != GRID_SIZE*GRID_SIZE) {
            throw new GridException("Incorrect size grid");
        }

        this.grid = grid;

        int emptyLength = 0;

        // loop through the grid validating the initial inputs
        for (byte i = 0; i < grid.length; i++) {
            byte value = grid[i];

            if (value < 0 || value > 9) {
                throw new GridException("Invalid value in Grid");
            }

            grid[i] = 0;

            if (value == 0) {
                emptyCellOffsets[emptyLength++] = i;
            }
            else if (!isSafe(i, value)) {
                throw new GridException("Incorrect value in Grid");
            }
            grid[i] = value;
        }
        emptyCellLength = emptyLength;
    }

    /**
     * Internal method for solving the grid.
     */
    private void solveSudoku() throws GridException {

        int currentOffset = 0;

        // until we've solved all the blanks
        while (currentOffset != emptyCellLength) {

            if (currentOffset < 0) {
                // not a solution
                throw new GridException("No valid solution");
            }

            int cellOffset = emptyCellOffsets[currentOffset];

            byte value = grid[cellOffset];

            if (value == 9) {
                // we have tried all the values and need to backtrack
                grid[cellOffset] = 0;
                currentOffset--;
            }
            else if (isSafe(cellOffset, value + 1)) {
                // progress to the next cell
                grid[cellOffset] = (byte) (value + 1);
                currentOffset++;
            }
            else {
                // try the next value
                grid[cellOffset] = (byte) (value + 1);
            }
        }
    }

    /**
     * Check whether it is safe to add a given number to the offset of the grid.
     *
     * @param offset the offset into the grid
     * @param number the number we are thinking of adding
     * @return whether it is safe to add the given value
     */
    boolean isSafe(final int offset, final int number) {

        int row = offset / GRID_SIZE;
        int col = offset % GRID_SIZE;
        int startBoxRow = row - row % 3, startBoxCol = col - col % 3;

        for (int i = 0; i < GRID_SIZE; i++) {
            // check same row
            if (grid[(row * GRID_SIZE) + i] == number) {
                return false;
            }

            // check same column
            if (grid[(GRID_SIZE * i) + col] == number) {
                return false;
            }

            // check same box
            if (grid[GRID_SIZE *(i / 3 + startBoxRow) + (i % 3 + startBoxCol)] == number) {
                return false;
            }
        }

        // otherwise we are ok
        return true;
    }
}
