package sudoku.models;

import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuVerifierTest {
    @Test
    public void checkSudokuVerifierChange() {
        SudokuField field = new SudokuField();
        AtomicBoolean verified = new AtomicBoolean(false);

        PropertyChangeListener listener = evt -> verified.set(true);

        field.addFieldValueListener(listener);
        field.setFieldValue(2);

        assertTrue(verified.get());

        verified.set(false);

        field.removeFieldValueListener(listener);
        field.setFieldValue(6);

        assertFalse(verified.get());
    }

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
