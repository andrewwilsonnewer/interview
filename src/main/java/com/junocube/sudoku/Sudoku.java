package com.junocube.sudoku;

/**
 * A fast, memory efficient backtracking implementation of a Sudoku solver.  See the README.md for more details.
 *
 * @author andrew.wilson
 * @since Apr-2022
 */
public class Sudoku {

    /**
     * The grid of data to be solved.
     */
    private final byte[] grid;

    /**
     * A padded array of the empty cell offsets.
     */
    private final int[] emptyCellOffsets;

    /**
     * How many empty cells there are.
     */
    private final int emptyCellLength;

    /**
     * The size of the grid.
     */
    private final int rowLength;

    /**
     * The size of the box.
     */
    private final int boxSize;

    /**
     * Solve a 9x9 sudoku grid.
     *
     * @param grid the grid which may contain 0 (fill) or valid values
     * @param rowLength the length of a row
     * @param boxSize the size of a box
     *
     * @throws GridException if a problem occurs
     */
    public static void solve(final byte[] grid, final int rowLength, final int boxSize) throws GridException {
        new Sudoku(grid, rowLength, boxSize).solveSudoku();
    }

    private Sudoku(final byte[] grid, final int rowLength, final int boxSize) throws GridException {

        if (grid == null) {
            throw new GridException("Null grid");
        }

        if (grid.length != rowLength * rowLength) {
            throw new GridException("Incorrect size grid");
        }

        this.boxSize = boxSize;
        this.rowLength = rowLength;

        this.emptyCellOffsets = new int[rowLength * rowLength];

        this.grid = grid;

        int emptyLength = 0;

        // loop through the grid validating the initial inputs
        for (int i = 0; i < grid.length; i++) {
            byte value = grid[i];

            if (value < 0 || value > rowLength) {
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

            if (value == rowLength) {
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
    private boolean isSafe(final int offset, final int number) {

        int row = offset / rowLength;
        int col = offset % rowLength;
        int startBoxRow = row - row % boxSize, startBoxCol = col - col % boxSize;

        for (int i = 0; i < rowLength; i++) {
            // check same row
            if (grid[(row * rowLength) + i] == number) {
                return false;
            }

            // check same column
            if (grid[(rowLength * i) + col] == number) {
                return false;
            }

            // check same box
            if (grid[rowLength * (i / boxSize + startBoxRow) + (i % boxSize + startBoxCol)] == number) {
                return false;
            }
        }

        // otherwise we are ok
        return true;
    }
}
