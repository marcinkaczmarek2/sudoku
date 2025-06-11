package sudoku.exceptions;

public class DaoReadException extends DaoException {
    public DaoReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoReadException(String message) {
        super(message);
    }
}

