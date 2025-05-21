package sudoku.exceptions;

public class GUIException extends RuntimeException {
    public GUIException(String message, Throwable cause) {
        super(message, cause);
    }

    public GUIException(String message) {
        super(message);
    }

    public GUIException(Throwable cause) {
        super(cause);
    }
}
