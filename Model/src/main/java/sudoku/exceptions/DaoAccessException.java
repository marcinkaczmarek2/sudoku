package sudoku.exceptions;

public class DaoAccessException extends DaoException {
    public DaoAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoAccessException(String message) {
        super(message);
    }
}
