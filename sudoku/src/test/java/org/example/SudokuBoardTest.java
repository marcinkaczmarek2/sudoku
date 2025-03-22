package org.example;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuBoardTest {
    @Test
    public void areBoardsDifferent() {

        SudokuBoard board1 = new SudokuBoard();
        board1.solveGame();

        SudokuBoard board2 = new SudokuBoard();
        board2.solveGame();

        int[][] sudokuBoard1 = new int[9][9];
        int[][] sudokuBoard2 = new int[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                sudokuBoard1[row][col] = board1.getCellNumber(row, col);
                sudokuBoard2[row][col] = board2.getCellNumber(row, col);
            }
        }

        boolean condition = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

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

        SudokuBoard board = new SudokuBoard();
        board.solveGame();

        HashSet<Integer> testedRow = new HashSet<>();
        HashSet<Integer> correctRow = new HashSet<>();

        for (int i = 1; i < 10; i++) {
            correctRow.add(i);
        }

        int[][] sudokuBoard = new int[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                sudokuBoard[row][col] = board.getCellNumber(row, col);
            }
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

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
        SudokuBoard board = new SudokuBoard();
        board.solveGame();
        HashSet<Integer> testedColumn = new HashSet<>();
        HashSet<Integer> correctColumn = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            correctColumn.add(i);
        }
        int[][] sudokuBoard = new int [9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuBoard[row][col] = board.getCellNumber(row, col);
            }
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
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

        SudokuBoard board = new SudokuBoard();
        board.solveGame();

        HashSet<Integer> testedBox = new HashSet<>();
        HashSet<Integer> correctBox = new HashSet<>();

        int[][] sudokuBoard = new int [9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
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
