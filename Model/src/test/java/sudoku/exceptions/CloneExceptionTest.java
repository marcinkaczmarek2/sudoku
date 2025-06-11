package sudoku.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CloneExceptionTest {

    @Test
    void testConstructorWithCause() {
        Throwable cause = new IllegalArgumentException("Invalid argument");

        CloneException exception = new CloneException(cause);

        assertEquals(cause, exception.getCause());
    }
}
