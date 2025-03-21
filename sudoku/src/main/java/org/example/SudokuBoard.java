package org.example;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class SudokuBoard {
    private final int[][] board;
    private final SecureRandom secureRandom;

    public SudokuBoard() {
        secureRandom = new SecureRandom();
        board = new int[9][9];
    }

    public int getCellNumber(int row, int col) {

        return board[row][col];
    }

    private ArrayList<Integer> getRandomNumber() {

        ArrayList<Integer> oneToNineNumbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            oneToNineNumbers.add(i);
        }

        Collections.shuffle(oneToNineNumbers, secureRandom);
        return oneToNineNumbers;
    }

    public void fillBoard() {
        sudokuSolver(0, 0);
    }

    private boolean sudokuSolver(int row, int col) {

        if (row == 9) {
            return true;
        }

        if (col == 9) {
            return sudokuSolver(row + 1, 0);
        }

        ArrayList<Integer> oneToNineNumbers = getRandomNumber();

        for (int number : oneToNineNumbers) {

            if (isNumberValid(row, col, number)) {

                board[row][col] = number;

                if (sudokuSolver(row, col + 1)) {

                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }


    private boolean isBoxValid(int row, int column, int numberToCheck) {

        int startOfBoxRow = (row / 3) * 3;
        int startOfBoxColumn = (column / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if ((startOfBoxRow + i != row && startOfBoxColumn + j != column)
                        && board[startOfBoxRow + i][startOfBoxColumn + j] == numberToCheck) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isRowValid(int row, int numberToCheck, int colToIgnore) {

        for (int col = 0; col < 9; col++) {

            if (col != colToIgnore && board[row][col] == numberToCheck) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnValid(int column, int numberToCheck, int rowToIgnore) {
        for (int row = 0; row < 9; row++) {

            if (row != rowToIgnore && board[row][column] == numberToCheck) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumberValid(int row, int column, int numberToCheck) {
        return isRowValid(row, numberToCheck, column)
                && isColumnValid(column, numberToCheck, row)
                && isBoxValid(row, column, numberToCheck);
    }
}
