package com.junocube.sudoku;

/**
 * A fast, memory efficient implementation of a Sudoku solver.  See the README.md for more details.
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
    private final byte[] emptyCellOffsets = new byte[81];

    /**
     * How many empty cells there are.
     */
    private final int emptyCellLength;

    /**
     * Solve a 9x9 grid
     * @param grid the grid
     * @throws GridException if a problem occurs
     */
    public static void solve(final byte[] grid) throws GridException {
        new Sudoku(grid).solveSudoku();
    }

    private Sudoku(final byte[] grid) throws GridException {

        if (grid == null) {
            throw new GridException("Null grid");
        }

        if (grid.length != 81) {
            throw new GridException("Incorrect size grid");
        }

        this.grid = grid;

        int emptyLength = 0;

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

        int myOffset = 0;

        while (myOffset >= 0) {

            if (myOffset == emptyCellLength) {
                // we solved it!
                return;
            }
            int cellOffset = emptyCellOffsets[myOffset];

            byte value = grid[cellOffset];

            if (value == 9) {
                // we have tried all the values and need to backtrack
                grid[cellOffset] = 0;
                myOffset--;
            }
            else if (isSafe(cellOffset, value + 1)) {
                // progress to the next cell
                grid[cellOffset] = (byte) (value + 1);
                myOffset++;
            }
            else {
                // try the next value
                grid[cellOffset] = (byte) (value + 1);
            }
        }

        // not a solution
        throw new GridException("No valid solution");
    }


    /**
     * Check whether it is safe to add a given number to the offset of the grid.
     *
     * @param offset the offset into the grid
     * @param number the number we are thinking of adding
     * @return whether or not it is safe to add the given value
     */
    boolean isSafe(final int offset, final int number) {

        int row = offset / GRID_SIZE;
        int col = offset % GRID_SIZE;
        int startRow = row - row % 3, startCol = col - col % 3;

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
            int offset1 = GRID_SIZE *(i / 3 + startRow) + (i % 3 + startCol);
            if (grid[offset1] == number) {
                return false;
            }
        }

        // otherwise we are ok
        return true;
    }
}
