package sudoku.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.AssertionsKt.assertNull;

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
