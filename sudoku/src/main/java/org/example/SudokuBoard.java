package org.example;

public class SudokuBoard {
    public static final int BOARD_SIZE = 9;
    public final int[][] board;
    private final SudokuSolver solver;


    public SudokuBoard(SudokuSolver solver) {
        this.board = new int[BOARD_SIZE][BOARD_SIZE];
        this.solver = solver;
    }

    public int getCellNumber(int row, int col) {
        int number = board[row][col];
        return number;
    }

    public void setCellNumber(int row, int col, int number) {

        board[row][col] = number;
    }

        public void solveGame() {

            solver.solve(this);
    }
}
