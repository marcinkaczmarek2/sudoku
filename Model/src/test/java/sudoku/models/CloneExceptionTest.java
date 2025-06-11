package sudoku.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.CloneException;
import sudoku.exceptions.SudokuBoardCloneException;
import sudoku.exceptions.SudokuFieldCloneException;
import sudoku.exceptions.SudokuUnitCloneException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CloneExceptionTest {


    @BeforeEach
    public void setUp() {
        LocalizationService.initialize(Locale.forLanguageTag("pl"));
    }

    // Broken SudokuField to simulate error during deep copy
    static class BrokenSudokuField extends SudokuField {
        @Override
        protected SudokuField doClone() throws SudokuFieldCloneException {
            throw new SudokuFieldCloneException("Simulated field failure", new CloneException("low-level cause"));
        }
    }

    @Test
    void testSudokuBoardCloneThrowsDueToField() {
        SudokuBoard board = new SudokuBoard(b -> {
        });

        // Replace one field with a broken one to simulate failure during cloning
        List<SudokuField> brokenBoard = new ArrayList<>(board.board);
        brokenBoard.set(0, new BrokenSudokuField());
        board.board = brokenBoard;

        // Expecting SudokuFieldCloneException from SudokuField.clone()
        SudokuFieldCloneException ex = assertThrows(SudokuFieldCloneException.class, board::clone);
        assertEquals("Simulated field failure", ex.getMessage());
        assertTrue(ex.getCause() instanceof CloneException);
        assertEquals("low-level cause", ex.getCause().getMessage());
    }

    // Test that clone fails due to broken clone at base level
    static class BrokenBoardBaseClone extends SudokuBoard {
        public BrokenBoardBaseClone(SudokuSolver solver) {
            super(solver);
        }

        @Override
        protected SudokuBoard doClone() throws SudokuBoardCloneException {
            throw new SudokuBoardCloneException("Simulated base clone failure", new CloneException("test"));
        }
    }

    @Test
    void testSudokuBoardCloneThrowsDueToBaseClone() {
        SudokuBoard board = new BrokenBoardBaseClone(b -> {
        });
        SudokuBoardCloneException ex = assertThrows(SudokuBoardCloneException.class, board::clone);
        assertEquals("Simulated base clone failure", ex.getMessage());
        assertTrue(ex.getCause() instanceof CloneException);
        assertEquals("test", ex.getCause().getMessage());
    }

    // Broken SudokuUnit for completeness (unchanged)
    static class BrokenSudokuUnit extends SudokuUnit {
        @Override
        protected SudokuUnit doClone() throws SudokuUnitCloneException {
            throw new SudokuUnitCloneException("Simulated");
        }
    }

    @Test
    void testSudokuUnitCloneCatch() {
        SudokuUnit unit = new BrokenSudokuUnit();
        assertThrows(SudokuUnitCloneException.class, unit::clone);
    }

    @Test
    void testSudokuFieldCloneCatch() {
        SudokuField field = new BrokenSudokuField();
        SudokuFieldCloneException ex = assertThrows(SudokuFieldCloneException.class, field::clone);
        assertEquals("Simulated field failure", ex.getMessage());
        assertTrue(ex.getCause() instanceof CloneException);
        assertEquals("low-level cause", ex.getCause().getMessage());
    }

    @Test
    public void testDoCloneThrowsSudokuFieldCloneException() {
        SudokuField field = new SudokuField();
        field.setForceCloneException(true);

        assertThrows(SudokuFieldCloneException.class, () -> {
            field.doClone();
        });
    }

}
