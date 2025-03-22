package org.example;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class SudokuSolver implements BackTrackingSudokuSolver {

    private final SecureRandom secureRandom;

    public SudokuSolver() {
        secureRandom = new SecureRandom();
    }

    private ArrayList<Integer> getRandomNumber() {

        ArrayList<Integer> oneToNineNumbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            oneToNineNumbers.add(i);
        }

        Collections.shuffle(oneToNineNumbers, secureRandom);
        return oneToNineNumbers;
    }

    private boolean isBoxValid(SudokuBoard sudokuBoard, int row, int column, int numberToCheck) {

        int startOfBoxRow = (row / 3) * 3;
        int startOfBoxColumn = (column / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if ((startOfBoxRow + i != row && startOfBoxColumn + j != column)
                        && sudokuBoard.board[startOfBoxRow + i][startOfBoxColumn + j] == numberToCheck) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isRowValid(SudokuBoard sudokuBoard, int row, int numberToCheck, int colToIgnore) {

        for (int col = 0; col < 9; col++) {

            if (col != colToIgnore && sudokuBoard.board[row][col] == numberToCheck) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnValid(SudokuBoard sudokuBoard, int column, int numberToCheck, int rowToIgnore) {
        for (int row = 0; row < 9; row++) {

            if (row != rowToIgnore && sudokuBoard.board[row][column] == numberToCheck) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumberValid(SudokuBoard sudokuBoard, int row, int column, int numberToCheck) {
        return isRowValid(sudokuBoard, row, numberToCheck, column)
                && isColumnValid(sudokuBoard, column, numberToCheck, row)
                && isBoxValid(sudokuBoard, row, column, numberToCheck);
    }


    public void solve(SudokuBoard sudokuBoard) {
        solveRecursively(sudokuBoard, 0, 0);
    }

    public boolean solveRecursively(SudokuBoard sudokuBoard, int row, int col) {

        if (row == 9) {
            return true;
        }

        if (col == 9) {
            return solveRecursively(sudokuBoard, row + 1, 0 );
        }

        ArrayList<Integer> oneToNineNumbers = getRandomNumber();

        for (int number : oneToNineNumbers) {

            if (isNumberValid(sudokuBoard, row, col, number)) {

                sudokuBoard.setCellNumber(row, col, number);

                if (solveRecursively(sudokuBoard, row, col + 1)) {

                    return true;
                }
                sudokuBoard.board[row][col] = 0;
            }
        }
        return false;

    }
}
