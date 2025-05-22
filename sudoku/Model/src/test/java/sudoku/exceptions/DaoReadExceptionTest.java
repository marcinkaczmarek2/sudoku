package sudoku.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.DaoReadException;

public class DaoReadExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Read error";
        DaoReadException exception = new DaoReadException(message);

        assertEquals(message, exception.getMessage());
    }
}
