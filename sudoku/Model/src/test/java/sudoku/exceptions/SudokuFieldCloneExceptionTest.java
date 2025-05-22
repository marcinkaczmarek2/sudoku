package sudoku.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.SudokuFieldCloneException;

public class SudokuFieldCloneExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Clone error on field";
        SudokuFieldCloneException exception = new SudokuFieldCloneException(message);

        assertEquals(message, exception.getMessage());
    }
}
