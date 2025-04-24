package org.example;

public class SudokuBoardDaoFactory {

    public static Dao<SudokuBoard> createFileSudokuBoardDao(String directory) {
        return new FileSudokuBoardDao(directory);
    }
}