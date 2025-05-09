package sudoku.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class SudokuBoardRepositoryTest {

    @Test
    public void testCreateInstanceReturnsClonedBoard() {
        SudokuBoard original = new SudokuBoard(new BacktrackingSudokuSolver());
        original.solveGame();
        original.set(0, 0, 9); // Modify to ensure state is copied

        SudokuBoardRepository repository = new SudokuBoardRepository(original);
        SudokuBoard cloned = repository.createInstance();

        assertNotSame(original, cloned);

        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
                assertEquals(original.get(i, j), cloned.get(i, j));
            }
        }
    }
}
