package org.example;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SudokuVerifier implements PropertyChangeListener {

    private final SudokuBoard board;
    private final int row;
    private final int col;
    private final boolean liveVerification;

    public SudokuVerifier(SudokuBoard board, int row, int col, boolean liveVerification) {
        this.board = board;
        this.row = row;
        this.col = col;
        this.liveVerification = liveVerification;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!liveVerification) {
            return;
        }

        System.out.println("Verifying [" + row + "] [" + col + "] due to value change.");
        boolean rowValid = board.getRow(row).verify();
        boolean colValid = board.getColumn(col).verify();
        boolean boxValid = board.getBox(row / 3 * 3 + col / 3).verify();

        System.out.println("Row valid: " + rowValid);
        System.out.println("Column valid: " + colValid);
        System.out.println("Box valid: " + boxValid);
    }
}

