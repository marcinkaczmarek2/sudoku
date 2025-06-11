package sudoku.exceptions;

public class GuiException extends RuntimeException {
    public GuiException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuiException(String message) {
        super(message);
    }

    public GuiException(Throwable cause) {
        super(cause);
    }
}
