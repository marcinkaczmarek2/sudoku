package sudoku.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.DaoWriteException;

public class DaoWriteExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Write error";
        DaoWriteException exception = new DaoWriteException(message);

        assertEquals(message, exception.getMessage());
    }
}
