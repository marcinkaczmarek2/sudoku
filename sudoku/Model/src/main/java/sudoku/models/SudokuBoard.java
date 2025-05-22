package sudoku.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.SudokuBoardCloneException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuBoard implements Serializable, Cloneable {
    public static final int BOARD_SIZE = 9;
    private boolean forceCloneException = false;
    protected List<SudokuField> board;
    private final transient SudokuSolver solver;
    private static final Logger logger = LoggerFactory.getLogger(SudokuBoard.class.getName());

    public SudokuBoard(SudokuSolver solver) {

        SudokuField[] fields = new SudokuField[BOARD_SIZE * BOARD_SIZE];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new SudokuField();
        }
        board = new ArrayList<>(Arrays.asList(fields));

        this.solver = solver;
    }

    public void setForceCloneException(boolean forceCloneException) {
        this.forceCloneException = forceCloneException;
    }

    public void solveGame() {
        logger.debug("Starting to solve the Sudoku board.");
        solver.solve(this);
        logger.info("Successfully solved the Sudoku board.");

    }

    public int get(int x, int y) {
        return board.get(x * 9 + y).getFieldValue();
    }

    public void set(int x, int y, int value) {
        board.get(x * 9 + y).setFieldValue(value);
    }

    public boolean checkBoard() {
        boolean boardCorrectness = true;
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify()) {
                boardCorrectness = false;
            }
            if (!getColumn(i).verify()) {
                boardCorrectness = false;
            }
            if (!getBox(i).verify()) {
                boardCorrectness = false;
            }
        }
        return boardCorrectness;
    }

    public SudokuRow getRow(int y) {
        SudokuRow sudokuRow = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            sudokuRow.fields[i].setFieldValue(board.get(y * 9 + i).getFieldValue());
        }
        return sudokuRow;
    }

    public SudokuColumn getColumn(int x) {
        SudokuColumn sudokuColumn = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            sudokuColumn.fields[i].setFieldValue(board.get(i * 9 + x).getFieldValue());
        }
        return sudokuColumn;
    }

    public SudokuBox getBox(int x) {
        SudokuBox sudokuBox = new SudokuBox();
        int startRow = (x / 3) * 3;
        int startCol = (x % 3) * 3;
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sudokuBox.fields[index++].setFieldValue(board.get((startRow + i) * 9 + startCol + j).getFieldValue());
            }
        }
        return sudokuBox;
    }

    public void removeRandomCells(int count) {
        Random rand = new Random();
        int removed = 0;

        while (removed < count) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            if (this.get(row, col) != 0) {
                this.set(row, col, 0);
                removed++;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (i % 3 == 0 && i != 0) {
                stringBuilder.append("-".repeat(23));
                stringBuilder.append("\n");
            }

            for (int j = 0; j < BOARD_SIZE; j++) {
                if (j % 3 == 0 && j != 0) {
                    stringBuilder.append(" | ");
                }

                int value = get(j, i);
                stringBuilder.append(value == 0 ? ". " : value + " ");
            }

            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    protected SudokuBoard doClone() throws SudokuBoardCloneException {
        try {
            if (forceCloneException) {               // <-- wymuszamy wyjątek jeśli flaga true
                throw new CloneNotSupportedException("Forced exception for testing");
            }
            return (SudokuBoard) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new SudokuBoardCloneException(LocalizationService.getInstance().get("error.board_clone"), e);
        }
    }

    @Override
    public SudokuBoard clone() {
        SudokuBoard cloned = doClone();

        List<SudokuField> clonedFields = new ArrayList<>();
        for (SudokuField field : this.board) {
            clonedFields.add(field.clone());
        }

        cloned.board = clonedFields;
        return cloned;
    }

    public SudokuField getField(int row, int col) {
        return board.get(row * 9 + col);
    }
}

