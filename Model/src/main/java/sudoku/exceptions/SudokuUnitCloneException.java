package sudoku.exceptions;

public class SudokuUnitCloneException extends CloneException {
  public SudokuUnitCloneException(String message) {
    super(message);
  }

  public SudokuUnitCloneException(String message, Throwable cause) {
    super(message, cause);
  }
}