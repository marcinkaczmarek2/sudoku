package sudoku.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.DaoAccessException;

public class DaoAccessExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Access error occurred";
        DaoAccessException exception = new DaoAccessException(message);

        assertEquals(message, exception.getMessage());
    }
}
