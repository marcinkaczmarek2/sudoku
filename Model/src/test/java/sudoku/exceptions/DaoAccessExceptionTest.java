package sudoku.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DaoAccessExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Access error occurred";
        DaoAccessException exception = new DaoAccessException(message);

        assertEquals(message, exception.getMessage());
    }
}
