package sudoku.models;

public class SudokuBoardRepository {
    private final SudokuBoard prototype;

    public SudokuBoardRepository(SudokuBoard prototype) {
        this.prototype = prototype;
    }

    public SudokuBoard createInstance() {
        return prototype.clone();
    }
}
