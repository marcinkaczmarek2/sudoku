package sudoku.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CloneExceptionTest {

    // Broken SudokuBoard to trigger CloneNotSupportedException in catch
    static class BrokenSudokuBoard extends SudokuBoard {
        public BrokenSudokuBoard(SudokuSolver solver) {
            super(solver);
        }

        @Override
        protected SudokuBoard doClone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("Simulated");
        }
    }

    @Test
    void testSudokuBoardCloneCatch() {
        SudokuBoard board = new BrokenSudokuBoard(b -> {});
        AssertionError ex = assertThrows(AssertionError.class, board::clone);
        assertEquals("Cloning failed", ex.getMessage());
    }

    // Broken SudokuUnit to trigger CloneNotSupportedException in catch
    static class BrokenSudokuUnit extends SudokuUnit {
        @Override
        protected SudokuUnit doClone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("Simulated");
        }
    }

    @Test
    void testSudokuUnitCloneCatch() {
        SudokuUnit unit = new BrokenSudokuUnit();
        assertThrows(AssertionError.class, unit::clone);
    }

    // Broken SudokuField to trigger CloneNotSupportedException in catch
    static class BrokenSudokuField extends SudokuField {
        @Override
        protected SudokuField doClone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("Simulated");
        }
    }

    @Test
    void testSudokuFieldCloneCatch() {
        SudokuField field = new BrokenSudokuField();
        RuntimeException ex = assertThrows(RuntimeException.class, field::clone);
        assertEquals("Simulated", ex.getCause().getMessage());
    }
}
