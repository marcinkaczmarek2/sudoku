package sudoku.exceptions;

public class SetStageException extends GuiException {
    public SetStageException(String message, Throwable cause) {
        super(message, cause);
    }

    public SetStageException(String message) {
        super(message);
    }
}

