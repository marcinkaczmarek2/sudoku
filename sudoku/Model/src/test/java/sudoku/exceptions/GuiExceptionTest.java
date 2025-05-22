package sudoku.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.GuiException;

public class GuiExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Some GUI error";
        GuiException ex = new GuiException(message);
        assertEquals(message, ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testConstructorWithCause() {
        Throwable cause = new RuntimeException("Cause exception");
        GuiException ex = new GuiException(cause);
        assertEquals(cause, ex.getCause());
        // Message might be cause.toString() by default
        assertTrue(ex.getMessage().contains("Cause exception"));
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Error occurred";
        Throwable cause = new RuntimeException("Cause exception");
        GuiException ex = new GuiException(message, cause);
        assertEquals(message, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
