package sudoku.models;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LockedFieldsSudokuBoardDecoratorTest {

    private final SudokuSolver dummySolver = board -> {};

    @Test
    void testIsLockedByIndex() {
        SudokuBoard board = new SudokuBoard(dummySolver);
        Set<Integer> lockedIndexes = Set.of(5, 10, 20);
        LockedFieldsSudokuBoardDecorator decorator = new LockedFieldsSudokuBoardDecorator(board, lockedIndexes);

        assertTrue(decorator.isLockedByIndex(10));
        assertFalse(decorator.isLockedByIndex(3));
    }
}
