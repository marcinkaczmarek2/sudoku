package sudoku.exceptions;

public class CloneException extends RuntimeException {
    public CloneException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloneException(String message) {
        super(message);
    }

    public CloneException(Throwable cause) {
        super(cause);
    }
}
