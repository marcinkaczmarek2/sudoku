package sudoku.daos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.DaoAccessException;
import sudoku.exceptions.DaoException;
import sudoku.models.BacktrackingSudokuSolver;
import sudoku.models.SudokuBoard;
import sudoku.models.SudokuSolver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileSudokuBoardDaoTest {
    private Path tempDirectory;

    @BeforeEach
    public void setUp() throws IOException {
        tempDirectory = Files.createTempDirectory("sudokuTest");
    }

    @AfterEach
    public void tearDown() {

        try (Stream<Path> paths = Files.walk(tempDirectory)) {
            paths
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(file -> {
                        if (!file.delete()) {
                            System.err.println("Deletion of file failed, path to this file: " + file.getAbsolutePath());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void listNamesCorrectCase() throws Exception {
        Path testFile = tempDirectory.resolve("testBoard.txt");
        Files.createFile(testFile);

        try (Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            List<String> expectedList = new ArrayList<>();
            expectedList.add("testBoard.txt");
            assertEquals(expectedList, sudokuFile.names());
        }
    }

    @Test
    public void listNamesEmptyCase() {
        try (Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            List<String> expectedList = new ArrayList<>();
            assertEquals(expectedList, sudokuFile.names());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readWhenCorrectDataTestEmptySudoku() throws Exception {
        Path testFile = Files.createTempFile(tempDirectory, "testBoard", ".dat");

        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i, j, 0);
            }
        }

        try (Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            sudokuFile.write(testFile.getFileName().toString(), board);
        }

        try (Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            SudokuBoard loadedBoard = sudokuFile.read(testFile.getFileName().toString());
            assertEquals(board, loadedBoard);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Files.delete(testFile);
    }

    @Test
    public void readWhenCorrectDataTestFullSudoku() throws Exception {
        Path testFile = Files.createTempFile(tempDirectory, "testBoard2", ".dat");

        SudokuBoard board = getSudokuBoard();

        try (Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            sudokuFile.write(testFile.getFileName().toString(), board);
        }

        try (Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            SudokuBoard loadedBoard = sudokuFile.read(testFile.getFileName().toString());
            assertEquals(board, loadedBoard);
        }

        Files.delete(testFile);
    }

    private static SudokuBoard getSudokuBoard() {
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
        return board;
    }

    @Test
    public void writeIfCreatesFile() throws IOException {
        Path testFile = Files.createTempFile(tempDirectory, "testBoard", ".dat");

        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i, j, 0);
            }
        }

        try (Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            sudokuFile.write(testFile.getFileName().toString(), board);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        File file = testFile.toFile();
        assertTrue(file.exists(), "File should be created");

        Files.delete(testFile);
    }

    @Test
    public void writeEmptySudokuCase() throws Exception {
        Path testFile = Files.createTempFile(tempDirectory, "testBoard3", ".dat");

        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i, j, 0);
            }
        }

        try (Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            sudokuFile.write(testFile.getFileName().toString(), board);
        }

        try (Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            SudokuBoard loadedBoard = sudokuFile.read(testFile.getFileName().toString());
            assertEquals(board.toString(), loadedBoard.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Files.delete(testFile);
    }

    @Test
    public void closeMethodExplicit() throws Exception {
        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            assertNotNull(dao);
        }
    }

    @Test
    public void writeThrowsDaoExceptionWhenFileIsDirectory() throws Exception {
        Path subDir = Files.createTempDirectory(tempDirectory, "subDir");

        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

            assertThrows(DaoException.class, () ->
                    dao.write(subDir.getFileName().toString(), board));
        }
    }

    @Test
    public void constructorThrowsIllegalArgumentExceptionForInvalidPath() {
        assertThrows(DaoException.class, () -> {
            try (FileSudokuBoardDao ignored = new FileSudokuBoardDao("invalid\0path")) {
                //intentional empty try block
            }
        });
    }

    @Test
    public void readThrowsDaoExceptionForCorruptedFile() throws IOException, DaoException {
        Path badFile = tempDirectory.resolve("corruptedBoard.dat");
        Files.write(badFile, "not a serialized object".getBytes());

        Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString());
        assertThrows(DaoException.class, () -> sudokuFile.read("corruptedBoard.dat"));
    }


    @Test
    public void namesThrowsDaoExceptionWhenPathIsFileNotDirectory() throws Exception {
        Path fakeDir = tempDirectory.resolve("notADir");
        Files.createFile(fakeDir); // create a file, not a directory

        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao(fakeDir.toString())) {
            assertThrows(DaoException.class, dao::names);
        }
    }

    @Test
    public void factoryConstructorIsPrivate() throws Exception {
        Constructor<SudokuBoardDaoFactory> constructor =
                SudokuBoardDaoFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertInstanceOf(UnsupportedOperationException.class, exception.getCause());
    }

    @Test
    public void constructorWithMessageOnly() {
        DaoException exception = new DaoException("Test message");
        assertEquals("Test message", exception.getMessage());
    }

    @Test
    public void directoryAlreadyExists() throws Exception {
        Files.createDirectory(tempDirectory.resolve("existingDir"));
        Dao<SudokuBoard> dao = new FileSudokuBoardDao(tempDirectory.resolve("existingDir").toString());
        assertNotNull(dao);
    }

    @Test
    public void directoryGetsCreatedIfNotExists() {
        Path newDir = tempDirectory.resolve("newlyCreated");
        assertFalse(Files.exists(newDir));

        try (Dao<SudokuBoard> ignored = new FileSudokuBoardDao(newDir.toString())) {
            //intentional empty try block
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(Files.exists(newDir));
    }

    @Test
    public void constructorThrowsRuntimeExceptionForInvalidPath() {
        String invalidPath = "nul://invalid";

        assertThrows(DaoException.class, () -> {
            try (FileSudokuBoardDao ignored = new FileSudokuBoardDao(invalidPath)) {
                //intentional empty try block
            }
        });
    }


    @Test
    void testEnsureDirectoryExists_throwsRuntimeExceptionOnInputOutputException() throws DaoException, IOException {
        Path tempFile = Files.createTempFile("testFile", ".tmp");
        Path invalidDirPath = tempFile.resolve("someFolder");
        FileSudokuBoardDao dao = new FileSudokuBoardDao("dummy") {
            public void callEnsureDirectoryExists(Path path) throws DaoException {
                ensureDirectoryExists(path);
            }
        };

        DaoAccessException ex = assertThrows(DaoAccessException.class, () -> dao.ensureDirectoryExists(invalidDirPath));

        assertInstanceOf(IOException.class, ex.getCause());

    }
}


