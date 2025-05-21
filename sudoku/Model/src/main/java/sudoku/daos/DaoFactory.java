package sudoku.daos;

import sudoku.exceptions.DaoException;
import sudoku.models.LockedFieldsSudokuBoardDecorator;
import sudoku.models.SudokuBoard;

public class DaoFactory {
    public static Dao<SudokuBoard> getFileDao(String directory) throws DaoException {
        return new FileSudokuBoardDao(directory);
    }

    public static Dao<LockedFieldsSudokuBoardDecorator> getLockedFileDao(String directory) throws DaoException {
        return new FileLockedSudokuBoardDao(directory);
    }

    private DaoFactory() {
        throw new UnsupportedOperationException("Utility class");
    }
}
