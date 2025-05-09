package sudoku.models;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class BacktrackingSudokuSolver implements SudokuSolver {

    private final SecureRandom secureRandom;

    public BacktrackingSudokuSolver() {
        secureRandom = new SecureRandom();
    }

    private static final ArrayList<Integer> oneToNineNumbers = createList();

    private static ArrayList<Integer> createList() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    private ArrayList<Integer> getRandomNumber() {
        ArrayList<Integer> shuffledList = new ArrayList<>(oneToNineNumbers);
        Collections.shuffle(shuffledList, secureRandom);
        return shuffledList;
    }

    private boolean isBoxValid(SudokuBoard sudokuBoard, int row, int column, int numberToCheck) {

        int startOfBoxRow = row / 3 * 3;
        int startOfBoxColumn = column / 3 * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (startOfBoxRow + i != row && startOfBoxColumn + j != column
                        && sudokuBoard.board.get((startOfBoxRow + i) * 9 + startOfBoxColumn + j).getFieldValue()
                        == numberToCheck) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isRowValid(SudokuBoard sudokuBoard, int row, int numberToCheck, int colToIgnore) {

        for (int column = 0; column < 9; column++) {

            if (column != colToIgnore && sudokuBoard.board.get(row * 9 + column).getFieldValue() == numberToCheck) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnValid(SudokuBoard sudokuBoard, int column, int numberToCheck, int rowToIgnore) {
        for (int row = 0; row < 9; row++) {

            if (row != rowToIgnore && sudokuBoard.board.get(row * 9 + column).getFieldValue() == numberToCheck) {
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

    private boolean solveRecursively(SudokuBoard sudokuBoard, int row, int col) {

        if (row == 9) {
            return true;
        }

        if (col == 9) {
            return solveRecursively(sudokuBoard, row + 1, 0);
        }

        ArrayList<Integer> oneToNineNumbers = getRandomNumber();

        for (int number : oneToNineNumbers) {

            if (isNumberValid(sudokuBoard, row, col, number)) {

                sudokuBoard.board.get(row * 9 + col).setFieldValue(number);

                if (solveRecursively(sudokuBoard, row, col + 1)) {

                    return true;
                }
                sudokuBoard.board.get(row * 9 + col).setFieldValue(0);
            }
        }
        return false;

    }
}
