package org.example;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoard {
    public static final int BOARD_SIZE = 9;

//    public final SudokuField[][] board;

public final List<List<SudokuField>> board;
    private final SudokuSolver solver;

//
//    public SudokuBoard(SudokuSolver solver) {
//        board = new SudokuField[BOARD_SIZE][BOARD_SIZE];
//        for (int i = 0; i < BOARD_SIZE; i++) {
//            for (int j = 0; j < BOARD_SIZE; j++) {
//                board[i][j] = new SudokuField();
//            }
//        }
//        this.solver = solver;
//    }

    public SudokuBoard(SudokuSolver solver) {

        board = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            List<SudokuField> row = new ArrayList<>();
            for (int j = 0; j < BOARD_SIZE; j++) {

                row.add(new SudokuField());
            }
            board.add(row);
        }
        this.solver = solver;
    }

    public void solveGame() {
        solver.solve(this);
    }

    public int get(int x, int y) {
        return board.get(x).get(y).getFieldValue();
    }

    public void set(int x, int y, int value) {
        board.get(x).get(y).setFieldValue(value);
    }

    public boolean checkBoard() {
        boolean boardCorrectness = true;
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify()) {
                boardCorrectness = false;
            }
            if (!getColumn(i).verify()) {
                boardCorrectness = false;
            }
            if (!getBox(i).verify()) {
                boardCorrectness = false;
            }
        }
        return boardCorrectness;
    }

    public SudokuRow getRow(int y) {
        SudokuRow sudokuRow = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            sudokuRow.fields[i].setFieldValue(board.get(y).get(i).getFieldValue());
        }
        return sudokuRow;
    }

    public SudokuColumn getColumn(int x) {
        SudokuColumn sudokuColumn = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            sudokuColumn.fields[i].setFieldValue(board.get(i).get(x).getFieldValue());
        }
        return sudokuColumn;
    }

    public SudokuBox getBox(int x) {
        SudokuBox sudokuBox = new SudokuBox();
        int startRow = (x / 3) * 3;
        int startCol = (x % 3) * 3;
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sudokuBox.fields[index++].setFieldValue(board.get(startRow + i).get(startCol + j).getFieldValue());
            }
        }
        return sudokuBox;
    }
}

