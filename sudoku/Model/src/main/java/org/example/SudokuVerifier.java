package org.example;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SudokuVerifier implements PropertyChangeListener {

    private final SudokuBoard board;
    private final int rowIndex;
    private final int colIndex;
    private final boolean liveVerification;

    public SudokuVerifier(SudokuBoard board, int rowIndex, int colIndex, boolean liveVerification) {
        this.board = board;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.liveVerification = liveVerification;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (liveVerification) {
            System.out.println("Verifying [" + rowIndex + "] [" + colIndex + "] due to value change.");
            boolean rowValid = board.getRow(rowIndex).verify();
            boolean colValid = board.getColumn(colIndex).verify();
            boolean boxValid = board.getBox(rowIndex / 3 * 3 + colIndex / 3).verify();

            System.out.println("Row valid: " + rowValid);
            System.out.println("Column valid: " + colValid);
            System.out.println("Box valid: " + boxValid);
        }
    }
}

