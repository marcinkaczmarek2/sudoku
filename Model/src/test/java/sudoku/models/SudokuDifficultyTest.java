package sudoku.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SudokuDifficultyTest {

    @Test
    public void testGetCellsToRemove() {
        assertEquals(30, SudokuDifficulty.EASY.getCellsToRemove());
        assertEquals(40, SudokuDifficulty.MEDIUM.getCellsToRemove());
        assertEquals(50, SudokuDifficulty.HARD.getCellsToRemove());
    }

    @Test
    public void testApplyEasy() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBoard clone = board.clone();

        SudokuDifficulty.EASY.apply(board);

        int removed = countZeros(board);
        assertEquals(30, removed);
        assertNotEquals(clone.toString(), board.toString());
    }

    @Test
    public void testApplyMedium() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuDifficulty.MEDIUM.apply(board);
        assertEquals(40, countZeros(board));
    }

    @Test
    public void testApplyHard() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuDifficulty.HARD.apply(board);
        assertEquals(50, countZeros(board));
    }

    private int countZeros(SudokuBoard board) {
        int count = 0;
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
                if (board.get(i, j) == 0) {
                    count++;
                }
            }
        }
        return count;
    }
}
