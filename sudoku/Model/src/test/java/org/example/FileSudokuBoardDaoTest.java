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
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest {
    private Path tempDirectory;

    @BeforeEach
    public void setUp() throws IOException {
        tempDirectory = Files.createTempDirectory("sudokuTest");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.walk(tempDirectory)
                .map(Path::toFile)
                .forEach(File::delete);
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

        try (Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            sudokuFile.write(testFile.getFileName().toString(), board);
        }

        try (Dao<SudokuBoard> sudokuFile = SudokuBoardDaoFactory.getFileDao(tempDirectory.toString())) {
            SudokuBoard loadedBoard = sudokuFile.read(testFile.getFileName().toString());
            assertEquals(board, loadedBoard);
        }

        Files.delete(testFile);
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
        assertThrows(IllegalArgumentException.class, () ->
                new FileSudokuBoardDao("invalid\0path"));
    }

    @Test
    public void readThrowsDaoExceptionForCorruptedFile() throws IOException {
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
    public void directoryGetsCreatedIfNotExists() throws Exception {
        Path newDir = tempDirectory.resolve("newlyCreated");
        assertFalse(Files.exists(newDir));

        Dao<SudokuBoard> dao = new FileSudokuBoardDao(newDir.toString());
        assertTrue(Files.exists(newDir));
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenUnsupportedDirCreation() {
        String invalidPath = "nul://invalid";

        assertThrows(IllegalArgumentException.class, () -> {
            new FileSudokuBoardDao(invalidPath);
        });
    }

    @Test
    public void testUnsupportedOperationException() {
        Path readOnlyDirectory = Paths.get("read-only-dir");

        // Tworzymy katalog jako "tylko do odczytu"
        try {
            Files.createDirectory(readOnlyDirectory);
            Files.setPosixFilePermissions(readOnlyDirectory,
                    PosixFilePermissions.fromString("r--r--r--")); // ustawiamy tylko do odczytu
        } catch (IOException e) {
            fail("Error during setting up test directory", e);
        }

        try {
            // Próba utworzenia katalogu w katalogu, który jest tylko do odczytu
            someDirectoryCreationMethod1(readOnlyDirectory);
            fail("Expected UnsupportedOperationException to be thrown");
        } catch (UnsupportedOperationException e) {
            // Sprawdzamy, czy odpowiedni wyjątek jest rzucony
            assertTrue(e.getMessage().contains("Invalid directory name."));
        } finally {
            try {
                Files.deleteIfExists(readOnlyDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testIOException() {
        Path nonExistentDirectory = Paths.get("C:/nonExistentDirectory/");

        try {
            someDirectoryCreationMethod2(nonExistentDirectory);
            fail("Expected IOException to be thrown");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("Error, while creating this directory"));
        }
    }

    private void someDirectoryCreationMethod1(Path directory) {
        try {
            if (directory == null) {
                throw new UnsupportedOperationException("Invalid directory name.");
            }
            Files.createDirectory(directory);
        } catch (UnsupportedOperationException e) {
            throw new IllegalArgumentException("Invalid directory name.", e);
        } catch (IOException e) {
            throw new RuntimeException("Error, while creating this directory: " + directory, e);
        }
    }
    private void someDirectoryCreationMethod2(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new RuntimeException("Error, while creating this directory: " + directory, e);
        }
    }
}


