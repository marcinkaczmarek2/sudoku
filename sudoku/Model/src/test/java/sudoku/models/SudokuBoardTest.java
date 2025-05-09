package sudoku.models;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SudokuBoardTest {
    public static final int BOARD_SIZE = 9;

    @Test
    public void areBoardsDifferent() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board1 = new SudokuBoard(solver);
        board1.solveGame();

        SudokuBoard board2 = new SudokuBoard(solver);
        board2.solveGame();

        int[][] sudokuBoard1 = new int[BOARD_SIZE][BOARD_SIZE];
        int[][] sudokuBoard2 = new int[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {

                sudokuBoard1[row][col] = board1.get(row, col);
                sudokuBoard2[row][col] = board2.get(row, col);
            }
        }

        boolean condition = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {

                if (sudokuBoard1[i][j] != sudokuBoard2[i][j]) {

                    condition = true;
                    break;
                }
            }
        }
        assertTrue(condition);
    }

    @Test
    public void checkDuplicatesInRow() {

        boolean isRowCorrect = true;

        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();

        HashSet<Integer> testedRow = new HashSet<>();
        HashSet<Integer> correctRow = new HashSet<>();

        for (int i = 1; i < 10; i++) {
            correctRow.add(i);
        }

        int[][] sudokuBoard = new int[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {

                sudokuBoard[row][col] = board.get(row, col);
            }
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {

                testedRow.add(sudokuBoard[row][col]);
            }
            if (!testedRow.equals(correctRow)) {

                isRowCorrect = false;
                break;
            }
            testedRow.clear();
        }
        assertTrue(isRowCorrect);
    }

    @Test
    public void checkDuplicatesInColumn() {

        boolean isColumnCorrect = true;
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();
        HashSet<Integer> testedColumn = new HashSet<>();
        HashSet<Integer> correctColumn = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            correctColumn.add(i);
        }
        int[][] sudokuBoard = new int[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                sudokuBoard[row][col] = board.get(row, col);
            }
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                testedColumn.add(sudokuBoard[row][col]);
            }
            if (!testedColumn.equals(correctColumn)) {
                isColumnCorrect = false;
                break;
            }
            testedColumn.clear();
        }
        assertTrue(isColumnCorrect);
    }


    @Test
    public void checkDuplicatesInBox() {

        boolean isBoxCorrect = true;

        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();

        HashSet<Integer> testedBox = new HashSet<>();
        HashSet<Integer> correctBox = new HashSet<>();

        int[][] sudokuBoard = new int[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                sudokuBoard[row][col] = board.get(row, col);
            }
        }

        for (int i = 1; i < 10; i++) {

            correctBox.add(i);
        }
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {

                testedBox.clear();
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {

                        testedBox.add(sudokuBoard[boxRow * 3 + row][boxCol * 3 + col]);
                    }
                }
                if (!testedBox.equals(correctBox)) {

                    isBoxCorrect = false;
                }
            }
        }
        assertTrue(isBoxCorrect);
    }

    @Test
    public void checkBoardWhenCorrect() {

        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();


        assertTrue(board.checkBoard());
    }

    @Test
    public void checkBoardWhenIncorrect() {

        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();
        board.set(0, 0, 0);

        assertFalse(board.checkBoard());
    }

    @Test
    public void toStringFormat() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();

        String output = board.toString();

        String[] lines = output.split("\n");
        assertEquals(11, lines.length);

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.isEmpty()) {
                continue;
            }

            String[] values = line.split(" ");

            if (i % 4 == 3) {
                assertEquals("-----------------------", line);
            } else {
                assertEquals(13, values.length);
            }
        }
    }

    @Test
    public void toStringEmptyBoard() {
        String expectedOutput =
                """
                        . . .  | . . .  | . . .\s
                        . . .  | . . .  | . . .\s
                        . . .  | . . .  | . . .\s
                        -----------------------
                        . . .  | . . .  | . . .\s
                        . . .  | . . .  | . . .\s
                        . . .  | . . .  | . . .\s
                        -----------------------
                        . . .  | . . .  | . . .\s
                        . . .  | . . .  | . . .\s
                        . . .  | . . .  | . . .\s
                        """;

        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i, j, 0);
            }
        }
        assertEquals(expectedOutput, board.toString());
    }

    @Test
    public void equalsCorrectnessTrue() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard boardExpected = new SudokuBoard(solver);
        SudokuBoard boardActual = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardExpected.set(i, j, 0);
                boardActual.set(i, j, 0);
            }
        }
        assertEquals(boardExpected, boardActual);
    }

    @Test
    public void equalsCorrectnessFalse() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard boardExpected = new SudokuBoard(solver);
        SudokuBoard boardActual = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardExpected.set(i, j, 0);
                boardActual.set(i, j, 0);
            }
        }
        boardActual.set(0, 0, 1);
        assertNotEquals(boardExpected, boardActual);
    }

    @Test
    public void hashCodeCorrectnessTrue() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard boardExpected = new SudokuBoard(solver);
        SudokuBoard boardActual = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardExpected.set(i, j, 0);
                boardActual.set(i, j, 0);
            }
        }
        assertEquals(boardExpected.hashCode(), boardActual.hashCode());
        assertEquals(boardExpected, boardActual);
    }

    @Test
    public void hashCodeCorrectnessFalse() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard boardExpected = new SudokuBoard(solver);
        SudokuBoard boardActual = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardExpected.set(i, j, 0);
                boardActual.set(i, j, 0);
            }
        }
        boardActual.set(0, 0, 1);
        assertNotEquals(boardExpected.hashCode(), boardActual.hashCode());
        assertNotEquals(boardExpected, boardActual);
    }

    @Test
    public void testShallowCopy() {
        SudokuSolver solver = new BacktrackingSudokuSolver(); // Use your actual solver implementation
        SudokuBoard original = new SudokuBoard(solver);
        SudokuBoard clone = original.clone();

        assertNotSame(original, clone, "Cloned board should not be the same instance as the original");
    }

    @Test
    public void testDeepCopyFieldInstances() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard original = new SudokuBoard(solver);
        original.set(0, 0, 5);

        SudokuBoard clone = original.clone();

        // Verify fields are not the same object (i.e., deep copy)
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
                assertNotSame(
                        original.board.get(i * 9 + j),
                        clone.board.get(i * 9 + j),
                        "Each field should be a new instance in the clone"
                );
            }
        }
    }

    @Test
    public void testValueEqualityAfterClone() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard original = new SudokuBoard(solver);
        original.set(1, 1, 9);
        original.set(2, 2, 7);

        SudokuBoard clone = original.clone();

        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
                assertEquals(
                        original.get(i, j),
                        clone.get(i, j),
                        String.format("Value at (%d, %d) should match after cloning", i, j)
                );
            }
        }
    }
}
