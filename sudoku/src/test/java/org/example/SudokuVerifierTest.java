package org.example;

import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuVerifierTest {

    @Test
    public void testPropertyChange_skipsWhenLiveVerificationFalse() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuVerifier verifier = new SudokuVerifier(board, 0, 0, false);

        PropertyChangeEvent evt = new PropertyChangeEvent(this, "fieldValue", 1, 2);
        verifier.propertyChange(evt);
    }

    @Test
    public void testPropertyChange_runsWhenLiveVerificationTrue() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuVerifier verifier = new SudokuVerifier(board, 0, 0, true);

        PropertyChangeEvent evt = new PropertyChangeEvent(this, "fieldValue", 1, 2);

        verifier.propertyChange(evt);

        assertTrue(board.getRow(0).verify());
        assertTrue(board.getColumn(0).verify());
        assertTrue(board.getBox(0).verify());
    }
}
