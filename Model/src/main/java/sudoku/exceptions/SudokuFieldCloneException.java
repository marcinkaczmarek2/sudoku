package sudoku.exceptions;

public class SudokuFieldCloneException extends CloneException {
    public SudokuFieldCloneException(String message) {
        super(message);
    }

    public SudokuFieldCloneException(String message, Throwable cause) {
        super(message, cause);
    }
}
