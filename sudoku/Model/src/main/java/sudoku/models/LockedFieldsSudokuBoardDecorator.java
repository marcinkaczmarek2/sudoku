package sudoku.models;

import java.io.Serializable;
import java.util.Set;

public class LockedFieldsSudokuBoardDecorator implements Serializable {
    private final SudokuBoard board;
    private final Set<Integer> lockedFieldIndexes;

    public LockedFieldsSudokuBoardDecorator(SudokuBoard board, Set<Integer> lockedFieldIndexes) {
        this.board = board;
        this.lockedFieldIndexes = lockedFieldIndexes;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public Set<Integer> getLockedFieldIndexes() {
        return lockedFieldIndexes;
    }

    public boolean isLocked(int row, int col) {
        return lockedFieldIndexes.contains(row * 9 + col);
    }
}
