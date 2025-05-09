package sudoku.exceptions;

public class DaoWriteException extends DaoException {
    public DaoWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoWriteException(String message) {
        super(message);
    }
}

