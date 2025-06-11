package sudoku.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DaoReadExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Read error";
        DaoReadException exception = new DaoReadException(message);

        assertEquals(message, exception.getMessage());
    }
}
