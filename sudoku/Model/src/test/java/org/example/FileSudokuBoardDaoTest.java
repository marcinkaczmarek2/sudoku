package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FileSudokuBoardDaoTest {
    private Dao<SudokuBoard> sudokuFile;
    private Path tempDirectory;

    @BeforeEach
    public void setUp() throws IOException {
        tempDirectory = Files.createTempDirectory("sudokuTest");
        sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString());
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.walk(tempDirectory)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void listNamesCorrectCase() throws IOException, DaoException {
        Path testFile = tempDirectory.resolve("testBoard.txt");
        Files.createFile(testFile);

        List<String> expectedList = new ArrayList<>();
        expectedList.add("testBoard.txt");

        assertEquals(expectedList, sudokuFile.names());
    }

    @Test
    public void listNamesEmptyCase() throws DaoException {
        List<String> expectedList = new ArrayList<>();
        assertEquals(expectedList, sudokuFile.names());
    }

    @Test
    public void readWhenCorrectDataTestEmptySudoku() throws IOException, DaoException {
        Path testFile = Files.createTempFile(tempDirectory, "testBoard", ".dat");

        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i, j, 0);
            }
        }

        sudokuFile.write(testFile.getFileName().toString(), board);

        SudokuBoard loadedBoard = sudokuFile.read(testFile.getFileName().toString());
        assertEquals(board.toString(), loadedBoard.toString());

        Files.delete(testFile);
    }

    @Test
    public void readWhenCorrectDataTestFullSudoku() throws IOException, DaoException {
        Path testFile = Files.createTempFile(tempDirectory, "testBoard2", ".dat");

        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        int[][] values = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                board.set(row, column, values[row][column]);
            }
        }

        sudokuFile.write(testFile.getFileName().toString(), board);

        SudokuBoard loadedBoard = sudokuFile.read(testFile.getFileName().toString());
        assertEquals(board.toString(), loadedBoard.toString());

        Files.delete(testFile);
    }

    @Test
    public void writeIfCreatesFile() throws IOException, DaoException {
        Path testFile = Files.createTempFile(tempDirectory, "testBoard", ".dat");

        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i, j, 0);
            }
        }

        sudokuFile.write(testFile.getFileName().toString(), board);

        File file = testFile.toFile();
        assertTrue(file.exists(), "File should be created");

        Files.delete(testFile);
    }

    @Test
    public void writeEmptySudokuCase() throws IOException, DaoException {
        Path testFile = Files.createTempFile(tempDirectory, "testBoard3", ".dat");

        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i, j, 0);
            }
        }

        sudokuFile.write(testFile.getFileName().toString(), board);

        SudokuBoard loadedBoard = sudokuFile.read(testFile.getFileName().toString());
        assertEquals(board.toString(), loadedBoard.toString());

        Files.delete(testFile);
    }

    @Test
    public void closeMethodExplicit() throws Exception {
        Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString());
        dao.close(); // Covers the close() method
    }

    @Test
    public void writeThrowsDaoExceptionWhenFileIsDirectory() throws IOException {
        Path subDir = Files.createTempDirectory(tempDirectory, "subDir");

        Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString());
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        assertThrows(DaoException.class, () ->
                dao.write(subDir.getFileName().toString(), board));
    }

    @Test
    public void constructorThrowsIllegalArgumentExceptionForInvalidPath() {
        assertThrows(IllegalArgumentException.class, () ->
                new FileSudokuBoardDao("invalid\0path"));
    }

    @Test
    public void readThrowsDaoExceptionForCorruptedFile() throws IOException {
        Path badFile = tempDirectory.resolve("corruptedBoard.dat");
        Files.write(badFile, "not a serialized object".getBytes());

        assertThrows(DaoException.class, () -> {
            sudokuFile.read("corruptedBoard.dat");
        });
    }

    @Test
    public void namesThrowsDaoExceptionWhenPathIsFileNotDirectory() throws IOException {
        Path fakeDir = tempDirectory.resolve("notADir");
        Files.createFile(fakeDir); // create a file, not a directory

        Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao(fakeDir.toString());

        assertThrows(DaoException.class, dao::names);
    }

    @Test
    public void factoryConstructorIsPrivate() throws Exception {
        Constructor<SudokuBoardDaoFactory> constructor =
                SudokuBoardDaoFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
    }

    @Test
    public void constructorWithMessageOnly() {
        DaoException exception = new DaoException("Test message");
        assertEquals("Test message", exception.getMessage());
    }

}
