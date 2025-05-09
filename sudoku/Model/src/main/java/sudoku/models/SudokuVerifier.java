package sudoku.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SudokuVerifier implements PropertyChangeListener {

    private final SudokuBoard board;
    private final int rowIndex;
    private final int colIndex;
    private final boolean liveVerification;
    private static final Logger logger = LoggerFactory.getLogger(SudokuVerifier.class.getName());

    public SudokuVerifier(SudokuBoard board, int rowIndex, int colIndex, boolean liveVerification) {
        this.board = board;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.liveVerification = liveVerification;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (liveVerification) {
            logger.info("Verifying [{}] [{}] due to value change.", rowIndex, colIndex);
            boolean rowValid = board.getRow(rowIndex).verify();
            boolean colValid = board.getColumn(colIndex).verify();
            boolean boxValid = board.getBox(rowIndex / 3 * 3 + colIndex / 3).verify();

            logger.info("Row valid: {}", rowValid);
            logger.info("Column valid: {}", colValid);
            logger.info("Box valid: {}", boxValid);
        }
    }
}

