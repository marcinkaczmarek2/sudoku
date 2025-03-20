package org.example;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuBoardTest {
    @Test
    public void areBoardsDifferent() {

        SudokuBoard board1 = new SudokuBoard();
        board1.fillBoard();

        SudokuBoard board2 = new SudokuBoard();
        board2.fillBoard();

        int[][] sudokuBoard1 = board1.getBoard();
        int[][] sudokuBoard2 = board2.getBoard();

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
        board.fillBoard();
        HashSet<Integer> testedRow = new HashSet<Integer>();
        HashSet<Integer> correctRow = new HashSet<Integer>();
        for (int i = 1; i < 10; i++) {
            correctRow.add(i);
        }
        int[][] sudokuBoard = board.getBoard();
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
        board.fillBoard();
        HashSet<Integer> testedColumn = new HashSet<Integer>();
        HashSet<Integer> correctColumn = new HashSet<Integer>();
        for (int i = 1; i < 10; i++) {
            correctColumn.add(i);
        }
        int[][] sudokuBoard = board.getBoard();
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
        board.fillBoard();
        HashSet<Integer> testedBox = new HashSet<Integer>();
        HashSet<Integer> correctBox = new HashSet<Integer>();
        int[][] sudokuBoard = board.getBoard();
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
