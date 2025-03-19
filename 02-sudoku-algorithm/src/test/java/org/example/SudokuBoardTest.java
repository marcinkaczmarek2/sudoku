package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuBoardTest {
    @Test
    public void areBoardsDifferent() {

        SudokuBoard board1 = new SudokuBoard();
        board1.fillBoard();

        SudokuBoard board2 = new SudokuBoard();
        board2.fillBoard();

        assertTrue(board1.areBoardsDifferent(board1.getBoard(), board2.getBoard()));
    }

    @Test
    public void checkDuplicatesInRow() {

        SudokuBoard board = new SudokuBoard();
        board.fillBoard();

        int[][] number = board.getBoard();
        int numberToCheck = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                numberToCheck = number[row][col];
                assertTrue(board.isRowValid(row, numberToCheck, col));

            }
        }

        int testRow = 3;
        number[testRow][1] = number[testRow][0];

        assertFalse(board.isRowValid(testRow, number[testRow][0], 0));
    }

    @Test
    public void checkDuplicatesInColumn() {

        SudokuBoard board = new SudokuBoard();
        board.fillBoard();

        int[][] number = board.getBoard();
        int numberToCheck = 0;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                numberToCheck = number[row][col];
                assertTrue(board.isColumnValid(col, numberToCheck, row));

            }
        }

        int testColumn = 3;
        number[1][testColumn] = number[0][testColumn];

        assertFalse(board.isColumnValid(0, number[0][testColumn], testColumn));
    }


    @Test
    public void checkDuplicatesInBox() {

        SudokuBoard board = new SudokuBoard();
        board.fillBoard();
        int[][] number = board.getBoard();

        int numberToCheck;

        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {

                        numberToCheck = number[row + i][col + j];
                        assertTrue(board.isBoxValid(row + i, col + j, numberToCheck));

                    }
                }
            }
        }
        int testBoxRow = 0;
        int testBoxCol = 3;
        number[1][testBoxCol] = number[0][testBoxCol];
        assertFalse(board.isBoxValid(0, testBoxCol, number[1][testBoxCol]));    }
}
