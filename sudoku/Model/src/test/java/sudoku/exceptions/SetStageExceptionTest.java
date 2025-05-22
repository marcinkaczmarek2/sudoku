package sudoku.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.SetStageException;

public class SetStageExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Stage setting error";
        SetStageException ex = new SetStageException(message);
        assertEquals(message, ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Stage setting error";
        Throwable cause = new RuntimeException("Underlying cause");
        SetStageException ex = new SetStageException(message, cause);
        assertEquals(message, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testIsGuiException() {
        SetStageException ex = new SetStageException("Error");
        assertTrue(ex instanceof sudoku.exceptions.GuiException);
    }
}
