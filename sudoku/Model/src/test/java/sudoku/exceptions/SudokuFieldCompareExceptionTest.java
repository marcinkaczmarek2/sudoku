package sudoku.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.AssertionsKt.assertNull;

public class SudokuFieldCompareExceptionTest {

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Comparison error";
        Throwable cause = new IllegalArgumentException("Invalid argument");
        SudokuFieldCompareException ex = new SudokuFieldCompareException(message, cause);

        assertEquals(message, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testConstructorWithMessageOnly() {
        String message = "Comparison error";
        SudokuFieldCompareException ex = new SudokuFieldCompareException(message);

        assertEquals(message, ex.getMessage());
        assertNull(ex.getCause());
    }
}
