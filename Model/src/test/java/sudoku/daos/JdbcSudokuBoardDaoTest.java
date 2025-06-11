package sudoku.daos;

import org.junit.jupiter.api.*;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.DaoReadException;
import sudoku.exceptions.DaoWriteException;
import sudoku.models.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcSudokuBoardDaoTest {

    private final String jdbcUrl = "jdbc:postgresql://localhost/sudokuDB";
    private final String username = "sudokuPlayer";
    private final String password = "123";

    private Connection connection;

    @BeforeAll
    void initLocalization() {
        LocalizationService.initialize(Locale.ENGLISH);
    }

    @BeforeEach
    void openConnection() throws SQLException {
        connection = DriverManager.getConnection(jdbcUrl, username, password);
        connection.setAutoCommit(false);
    }

    @AfterEach
    void rollbackAndClose() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
            connection.close();
        }
    }

    private LockedFieldsSudokuBoardDecorator createLockedBoard() {

        //USE FACTORY
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();

        Set<Integer> lockedIndexes = new HashSet<>();
        for (int i = 0; i < 81; i += 3) {
            lockedIndexes.add(i);
        }

        return new LockedFieldsSudokuBoardDecorator(board, lockedIndexes);
    }

    @Test
    void testWriteAndReadBoard() throws DaoException {
        try (JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao(connection)) {
            LockedFieldsSudokuBoardDecorator original = createLockedBoard();
            String name = "test-board";

            dao.write(name, original);
            LockedFieldsSudokuBoardDecorator loaded = dao.read(name);

            assertNotSame(original, loaded);
            assertNotSame(original.getBoard(), loaded.getBoard());


            assertEquals(original.getBoard(), loaded.getBoard(), "Board values should match");
            for (int i = 0; i < 81; i++) {
                assertEquals(original.isLockedByIndex(i), loaded.isLockedByIndex(i),
                        "Locked state should match for index " + i);
            }
        }
    }

    @Test
    void testReadNonexistentBoardThrows() throws DaoException {
        try (JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao(connection)) {
            assertThrows(DaoReadException.class, () -> dao.read("nonexistent-board"));
        }
    }

    @Test
    void testWriteDuplicateBoardNameDoesNotThrow() throws DaoException {
        try (JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao(connection)) {
            String name = "duplicate-board";
            LockedFieldsSudokuBoardDecorator board = createLockedBoard();

            dao.write(name, board);
            dao.write(name, board);
        }
    }

    @Test
    void testNamesReturnsSavedBoards() throws DaoException {
        try (JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao(connection)) {
            dao.write("first", createLockedBoard());
            dao.write("second", createLockedBoard());

            List<String> names = dao.names();
            assertTrue(names.contains("first"));
            assertTrue(names.contains("second"));
        }
    }

    @Test
    void testRollbackOnWriteFailure() throws SQLException, DaoException {
        try (JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao(connection)) {
            String name = "rollback-board";

            dao.write(name, createLockedBoard());

            connection.createStatement().executeUpdate("DELETE FROM SudokuBoardDB WHERE name = '" + name + "'");

            Assertions.assertDoesNotThrow(() -> dao.write(name, createLockedBoard()));
        }

    }

    @Test
    void testCloseDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao(connection);
            dao.close();
        });
    }

    @Test
    void testDefaultConstructorEstablishesConnection() {
        Assertions.assertDoesNotThrow(() -> {
            JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao();
            dao.close();
        });
    }
}
