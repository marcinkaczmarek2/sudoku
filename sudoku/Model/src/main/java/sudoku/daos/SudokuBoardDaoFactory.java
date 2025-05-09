package sudoku.daos;

import sudoku.exceptions.DaoException;
import sudoku.models.SudokuBoard;

public class SudokuBoardDaoFactory {

    public static Dao<SudokuBoard> getFileDao(String directory) throws DaoException {
        return new FileSudokuBoardDao(directory);
    }

    private SudokuBoardDaoFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

}