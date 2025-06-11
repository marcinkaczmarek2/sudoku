package sudoku.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DaoWriteExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Write error";
        DaoWriteException exception = new DaoWriteException(message);

        assertEquals(message, exception.getMessage());
    }
}
