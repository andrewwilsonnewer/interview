package com.junocube.sudoku;

/**
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

    public static boolean solve(final byte[] grid)
            throws GridException {
        return new Sudoku(grid).solveSudoku();
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

        for (int i = 0; i < grid.length; i++) {
            int value = grid[i];

            if (value < 0 || value > 9) {
                throw new GridException("Invalid value in Grid");
            }

            if (value == 0) {
                emptyCellOffsets[emptyLength++] = (byte) i;
            }
            else if (!isSafe(i, value)) {
                throw new GridException("Incorrect value in Grid");
            }
        }
        emptyCellLength = emptyLength;
    }

    private boolean solveSudoku() {

        int myOffset = 0;

        while (myOffset >= 0) {

            if (myOffset == emptyCellLength) {
                // we solved it!
                return true;
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
                grid[cellOffset] = (byte) (value + 1);
            }
        }

        // not a solution
        return false;
    }

    // Check whether it will be legal
    // to assign num to the
    // given row, col
    boolean isSafe(int offset, int num) {

        int row = offset / GRID_SIZE;
        int col = offset % GRID_SIZE;

        // Check if we find the same num
        // in the similar row , we
        // return false
        for (int x = 0; x <= 8; x++)
            if (grid[(row * GRID_SIZE) + x] == num)
                return false;

        // Check if we find the same num
        // in the similar column ,
        // we return false
        for (int x = 0; x <= 8; x++)
            if (grid[(GRID_SIZE *x) + col] == num)
                return false;

        // Check if we find the same num
        // in the particular 3*3
        // matrix, we return false
        int startRow = row - row % 3, startCol
                = col - col % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[GRID_SIZE *(i + startRow) + (j + startCol)] == num)
                    return false;

        return true;
    }
}
