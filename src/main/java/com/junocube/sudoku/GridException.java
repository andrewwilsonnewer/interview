package com.junocube.sudoku;

/**
 * A very simple Grid Exception which could be significantly extended with subclasses, error codes etc.
 *
 * @author andrewwilson
 * @since Apr-2022
 */
public class GridException
        extends Exception {

    public GridException(final String problem) {
        super(problem);
    }
}