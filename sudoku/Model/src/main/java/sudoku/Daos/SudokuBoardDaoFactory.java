package sudoku.Daos;

import sudoku.Models.SudokuBoard;

public class SudokuBoardDaoFactory {

    public static Dao<SudokuBoard> getFileDao(String directory) {
        return new FileSudokuBoardDao(directory);
    }

    private SudokuBoardDaoFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

}