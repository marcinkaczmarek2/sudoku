package org.example;

public class SudokuBoard {
    public final int[][] board;
    private final SudokuSolver solver = new SudokuSolver();


    public SudokuBoard() {
        board = new int[9][9];
    }

    public int getCellNumber(int row, int col) {

        return board[row][col];
    }

    public void setCellNumber(int row, int col, int number) {

        board[row][col] = number;
    }

        public void solveGame() {

            solver.solve(this);
    }
}
