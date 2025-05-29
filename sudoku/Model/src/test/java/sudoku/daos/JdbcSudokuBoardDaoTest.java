package sudoku.daos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.DaoReadException;
import sudoku.models.BacktrackingSudokuSolver;
import sudoku.models.LocalizationService;
import sudoku.models.LockedFieldsSudokuBoardDecorator;
import sudoku.models.SudokuBoard;

import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuBoardDaoTest {

    private Connection connection;
    private JdbcSudokuBoardDao dao;

    @BeforeAll
    static void setupLocalization() {
        LocalizationService.initialize(Locale.ENGLISH);
    }

    @BeforeEach
    void setup() throws SQLException, DaoException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        Statement stmt = connection.createStatement();

        // Drop tables if they already exist
        stmt.execute("DROP TABLE IF EXISTS SudokuFieldDB");
        stmt.execute("DROP TABLE IF EXISTS SudokuBoardDB");

        // Create necessary tables
        stmt.execute("""
            CREATE TABLE SudokuBoardDB (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) UNIQUE
            )
        """);

        stmt.execute("""
            CREATE TABLE SudokuFieldDB (
                board_id INT,
                "index" INT,
                "value" INT,
                editable BOOLEAN
            )
        """);

        connection.setAutoCommit(false);
        dao = new JdbcSudokuBoardDao(connection);
    }

    @AfterEach
    void cleanup() throws Exception {
        if (dao != null) {
            dao.close();
        }
    }

    @Test
    void testWriteNewBoard() throws Exception {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        Set<Integer> lockedIndexes = new HashSet<>();
        lockedIndexes.add(0);
        lockedIndexes.add(1);
        board.set(0, 0, 5);
        board.set(0, 1, 3);
        LockedFieldsSudokuBoardDecorator locked = new LockedFieldsSudokuBoardDecorator(board, lockedIndexes);

        dao.write("testBoard", locked);
        connection.commit(); // optional, write method may do it

        LockedFieldsSudokuBoardDecorator readBoard = dao.read("testBoard");

        assertEquals(5, readBoard.getBoard().get(0, 0));
        assertEquals(3, readBoard.getBoard().get(0, 1));
        assertTrue(readBoard.isLockedByIndex(0));
        assertTrue(readBoard.isLockedByIndex(1));
    }

    @Test
    void testWriteExistingBoard() throws Exception {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        Set<Integer> lockedIndexes = new HashSet<>(List.of(0));
        board.set(0, 0, 9);
        LockedFieldsSudokuBoardDecorator locked = new LockedFieldsSudokuBoardDecorator(board, lockedIndexes);

        dao.write("testBoard2", locked);

        // Overwrite with different board
        board.set(0, 0, 7);
        dao.write("testBoard2", new LockedFieldsSudokuBoardDecorator(board, lockedIndexes));

        LockedFieldsSudokuBoardDecorator read = dao.read("testBoard2");

        assertEquals(7, read.getBoard().get(0, 0));
        assertTrue(read.isLockedByIndex(0));
    }

    @Test
    void testReadBoardNotFound() {
        String name = "nonexistent";
        DaoReadException ex = assertThrows(DaoReadException.class, () -> dao.read(name));
        assertTrue(ex.getMessage().contains("board_not_found") || ex.getMessage().contains(name));
    }

    @Test
    void testNames() throws Exception {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        LockedFieldsSudokuBoardDecorator locked = new LockedFieldsSudokuBoardDecorator(board, Set.of());

        dao.write("boardA", locked);
        dao.write("boardB", locked);

        List<String> names = dao.names();

        assertTrue(names.contains("boardA"));
        assertTrue(names.contains("boardB"));
    }

    @Test
    void testClose() throws Exception {
        dao.close();
        assertTrue(connection.isClosed());
    }
}
