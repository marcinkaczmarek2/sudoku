package sudoku.Models;

public enum SudokuDifficulty {
    EASY(30),
    MEDIUM(40),
    HARD(50);

    private final int cellsToRemove;

    SudokuDifficulty(int cellsToRemove) {
        this.cellsToRemove = cellsToRemove;
    }

    public int getCellsToRemove() {
        return cellsToRemove;
    }

    public void apply(SudokuBoard board) {
        board.removeRandomCells(this.cellsToRemove);
    }

}
