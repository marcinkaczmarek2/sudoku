package sudoku.daos;

import sudoku.exceptions.DaoException;
import sudoku.models.LockedFieldsSudokuBoardDecorator;

public class DaoFactory {

    public static Dao<LockedFieldsSudokuBoardDecorator> getLockedFileDao(String directory) throws DaoException {
        return new FileLockedSudokuBoardDao(directory);
    }

    public static Dao<LockedFieldsSudokuBoardDecorator> getJbcdFileDao() throws DaoException {
        return new JdbcSudokuBoardDao();
    }

    private DaoFactory() {
        throw new UnsupportedOperationException("Utility class");
    }
}
