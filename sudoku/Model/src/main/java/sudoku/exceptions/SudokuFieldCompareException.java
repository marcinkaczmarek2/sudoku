package sudoku.exceptions;

public class SudokuFieldCompareException extends RuntimeException {
    public SudokuFieldCompareException(String message) {
        super(message);
    }

    public SudokuFieldCompareException(String message, Throwable cause) {
        super(message, cause);
    }
}