package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.security.SecureRandom;

public class SudokuBoard {
    public int[][] board;
    private final SecureRandom secureRandom;

    public SudokuBoard() {
        secureRandom = new SecureRandom();
        board = new int[9][9];
    }

    public boolean areBoardsDifferent(int[][] board1, int[][] board2) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (board1[i][j] != board2[i][j]) {

                    return true;
                }
            }
        }
        return false;
    }

    public int[][] getBoard() {
        return board;
    }

    public ArrayList<Integer> getRandomNumber() {

        ArrayList<Integer> oneToNineNumbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            oneToNineNumbers.add(i);
        }

        Collections.shuffle(oneToNineNumbers, secureRandom);
        return oneToNineNumbers;
    }

    public void displayBoard() {
        System.out.print("\n_ _ _ _ _ _ _ _ _ _ _ _\n");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
                if (j == 2 || j == 5 || j == 8) System.out.print("| ");
            }

            if (i == 2 || i == 5 || i == 8) System.out.print("\n_ _ _ _ _ _ _ _ _ _ _ _");
            System.out.println();
        }
    }

    //    public static void fillBoard() {
//        for (int row = 0; row < 9; row++) {
//            for (int column = 0; column < 9; column++) {
//                int counter = 0;
//                do {
//                    board[row][column] = getRandomNumber();
//                    counter++;
//                    if (counter > 20) {
//                        for (int k = 0; k < 9; k++) {
//                            board[row][k] = 0;
//                        }
//                        row--;
//                        break;
//                    }
//                } while (!isNumberValid(row, column));
//            }
//        }
//    }
    public void fillBoard() {
        sudokuSolver(0, 0);
    }

    public boolean sudokuSolver(int row, int col) {

        if (row == 9) {
            return true;
        }

        if (col == 9) {
            return sudokuSolver(row + 1, 0);
        }


        ArrayList<Integer> oneToNineNumbers = getRandomNumber();

        for (int number : oneToNineNumbers) {
            int currentRow = row;
            int currentCol = col;

            if (isNumberValid(row, col, number, currentCol, currentRow )) {

                board[row][col] = number;

                if (sudokuSolver(row, col + 1)) {

                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }

//    public static boolean isBoxValid(int row, int column) {
//        if (row < 3) {
//            if (column < 3) { //box1
//                for (int i = 0; i < 3; i++) {
//                    for (int j = 0; j < 3; j++) {
//                        if (i == row && j == column) {
//                            continue;
//                        } else {
//                            if (board[row][column] == board[i][j]) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            } else if (column < 6) { //box2
//                for (int i = 0; i < 3; i++) {
//                    for (int j = 3; j < 6; j++) {
//                        if (i == row && j == column) {
//                            continue;
//                        } else {
//                            if (board[row][column] == board[i][j]) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            } else { //box3
//                for (int i = 0; i < 3; i++) {
//                    for (int j = 6; j < 9; j++) {
//                        if (i == row && j == column) {
//                            continue;
//                        } else {
//                            if (board[row][column] == board[i][j]) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if (row > 2 && row < 6) {
//            if (column < 3) { //box4
//                for (int i = 3; i < 6; i++) {
//                    for (int j = 0; j < 3; j++) {
//                        if (i == row && j == column) {
//                            continue;
//                        } else {
//                            if (board[row][column] == board[i][j]) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            } else if (column < 6) { //box5
//                for (int i = 3; i < 6; i++) {
//                    for (int j = 3; j < 6; j++) {
//                        if (i == row && j == column) {
//                            continue;
//                        } else {
//                            if (board[row][column] == board[i][j]) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            } else { //box6
//                for (int i = 3; i < 6; i++) {
//                    for (int j = 6; j < 9; j++) {
//                        if (i == row && j == column) {
//                            continue;
//                        } else {
//                            if (board[row][column] == board[i][j]) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if (row > 5 && row < 9) {
//            if (column < 3) { //box7
//                for (int i = 6; i < 9; i++) {
//                    for (int j = 0; j < 3; j++) {
//                        if (i == row && j == column) {
//                            continue;
//                        } else {
//                            if (board[row][column] == board[i][j]) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            } else if (column < 6) { //box8
//                for (int i = 6; i < 9; i++) {
//                    for (int j = 3; j < 6; j++) {
//                        if (i == row && j == column) {
//                            continue;
//                        } else {
//                            if (board[row][column] == board[i][j]) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            } else { //box9
//                for (int i = 6; i < 9; i++) {
//                    for (int j = 6; j < 9; j++) {
//                        if (i == row && j == column) {
//                            continue;
//                        } else {
//                            if (board[row][column] == board[i][j]) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return true;
//    }

    public boolean isBoxValid(int row, int column, int numberToCheck) {

        int startOfBoxRow = (row / 3) * 3;
        int startOfBoxColumn = (column / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if ((startOfBoxRow + i != row || startOfBoxColumn + j != column) && board[startOfBoxRow + i][startOfBoxColumn + j] == numberToCheck) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isRowValid(int row, int numberToCheck, int colToIgnore) {
        for (int i = 0; i < 9; i++) {

            if (i != colToIgnore && board[row][i] == numberToCheck) {
                return false;
            }
        }
        return true;
    }

    public boolean isColumnValid(int column, int numberToCheck, int rowToIgnore) {
        for (int i = 0; i < 9; i++) {

            if (i != rowToIgnore && board[i][column] == numberToCheck) {
                return false;
            }
        }
        return true;
    }

    public boolean isNumberValid(int row, int column, int numberToCheck, int colToIgnore, int rowToIgnore) {
        return isRowValid(row, numberToCheck, colToIgnore) &&
                isColumnValid(column, numberToCheck, rowToIgnore) &&
                isBoxValid(row, column, numberToCheck);
    }
}
