package org.example;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

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

                sudokuBoard1[row][col] = board1.getCellNumber(row, col);
                sudokuBoard2[row][col] = board2.getCellNumber(row, col);
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

                sudokuBoard[row][col] = board.getCellNumber(row, col);
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
                sudokuBoard[row][col] = board.getCellNumber(row, col);
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
                sudokuBoard[row][col] = board.getCellNumber(row, col);
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
}
