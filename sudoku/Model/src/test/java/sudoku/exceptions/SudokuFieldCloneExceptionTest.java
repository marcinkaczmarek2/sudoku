package sudoku.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuFieldCloneExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Clone error on field";
        SudokuFieldCloneException exception = new SudokuFieldCloneException(message);

        assertEquals(message, exception.getMessage());
    }
}
