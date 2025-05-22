package sudoku.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.CloneException;

public class CloneExceptionTest {

    @Test
    void testConstructorWithCause() {
        Throwable cause = new IllegalArgumentException("Invalid argument");

        CloneException exception = new CloneException(cause);

        assertEquals(cause, exception.getCause());
    }
}
