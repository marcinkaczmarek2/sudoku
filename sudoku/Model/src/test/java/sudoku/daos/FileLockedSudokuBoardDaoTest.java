package sudoku.daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.DaoReadException;
import sudoku.exceptions.DaoWriteException;
import sudoku.models.*;

import java.io.*;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FileLockedSudokuBoardDaoTest {

    private FileLockedSudokuBoardDao dao;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setup() throws DaoException {
        LocalizationService.initialize(Locale.ENGLISH);
        dao = new FileLockedSudokuBoardDao(tempDir.toString());
    }

    @Test
    void testWriteAndReadSuccess() throws Exception {
        String fileName = "testBoard.ser";

        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        board.set(0, 0, 5);
        LockedFieldsSudokuBoardDecorator originalBoard = new LockedFieldsSudokuBoardDecorator(board, Set.of(0));

        dao.write(fileName, originalBoard);
        LockedFieldsSudokuBoardDecorator readBoard = dao.read(fileName);

        assertNotNull(readBoard);
        assertEquals(originalBoard.getBoard(), readBoard.getBoard());
        assertEquals(originalBoard.getLockedFieldIndexes(), readBoard.getLockedFieldIndexes());
        assertTrue(readBoard.isLocked(0, 0));
    }


    @Test
    void testReadThrowsIoException() {
        String fileName = "non_existent.ser";
        assertThrows(DaoReadException.class, () -> dao.read(fileName));
    }

    @Test
    void testReadThrowsClassNotFoundException() throws Exception {
        String fileName = "invalid_object.ser";
        Path filePath = tempDir.resolve(fileName);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            oos.writeObject("Not a SudokuBoard");
        }

        assertThrows(DaoReadException.class, () -> dao.read(fileName));
    }

    @Test
    void testWriteThrowsIoException() throws Exception {
        String fileName = "invalid.ser";
        Path filePath = tempDir.resolve(fileName);

        File file = filePath.toFile();
        file.createNewFile();
        file.setWritable(false);

        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        LockedFieldsSudokuBoardDecorator lockedBoard = new LockedFieldsSudokuBoardDecorator(board, Set.of(0));

        assertThrows(DaoWriteException.class, () -> dao.write(fileName, lockedBoard));

        file.setWritable(true);
    }
}
