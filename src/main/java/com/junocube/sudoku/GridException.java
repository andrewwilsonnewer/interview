package com.junocube.sudoku;

public class GridException
        extends Exception {

    public GridException(final String problem) {
        super(problem);
    }
}