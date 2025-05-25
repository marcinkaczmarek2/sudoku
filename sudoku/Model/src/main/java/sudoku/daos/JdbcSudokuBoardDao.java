package sudoku.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.DaoReadException;
import sudoku.exceptions.DaoWriteException;
import sudoku.models.BacktrackingSudokuSolver;
import sudoku.models.LocalizationService;
import sudoku.models.LockedFieldsSudokuBoardDecorator;
import sudoku.models.SudokuBoard;

import java.sql.*;
import java.util.*;

public class JdbcSudokuBoardDao implements Dao<LockedFieldsSudokuBoardDecorator> {
    private final Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    private final String userName = "sudokuPlayer";
    private final String userPassword = "123";

    public JdbcSudokuBoardDao() throws DaoException {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/sudokuDB", userName, userPassword);
            connection.setAutoCommit(false);
            logger.info("Successfully connected to the database.");
        } catch (SQLException e) {
            logger.error("Database connection failed", e);
            throw new DaoException(LocalizationService.getInstance().get("error.db_connection"), e);
        }
    }

    @Override
    public LockedFieldsSudokuBoardDecorator read(String name) throws DaoReadException {
        logger.debug("Attempting to read Sudoku board '{}' from database.", name);
        try {
            Integer boardId = null;

            String selectBoardIdSql = "SELECT id FROM SudokuBoardDB WHERE name = ?";
            try (PreparedStatement ps = connection.prepareStatement(selectBoardIdSql)) {
                ps.setString(1, name);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        boardId = rs.getInt("id");
                    } else {
                        logger.warn("Board '{}' not found in database.", name);
                        throw new DaoReadException(
                                LocalizationService.getInstance().get("error.board_not_found") + ": " + name);
                    }
                }
            }

            String selectFieldsSql = "SELECT index, value, editable FROM SudokuFieldDB WHERE board_id = ?";
            Map<Integer, Integer> fieldValues = new HashMap<>();
            Set<Integer> lockedIndexes = new HashSet<>();

            try (PreparedStatement ps = connection.prepareStatement(selectFieldsSql)) {
                ps.setInt(1, boardId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int index = rs.getInt("index");
                        int value = rs.getInt("value");
                        boolean editable = rs.getBoolean("editable");

                        fieldValues.put(index, value);
                        if (!editable) {
                            lockedIndexes.add(index);
                        }
                    }
                }
            }


            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            for (Map.Entry<Integer, Integer> entry : fieldValues.entrySet()) {
                board.setByIndex(entry.getKey(), entry.getValue());
            }

            LockedFieldsSudokuBoardDecorator lockedBoard = new LockedFieldsSudokuBoardDecorator(board, lockedIndexes);
            connection.commit();
            return lockedBoard;

        } catch (SQLException e) {
            logger.error("Error while reading board '{}' from database.", name);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Rollback failed after error while reading board.");
                throw new DaoReadException(
                        LocalizationService.getInstance().get("error.db_rollback"), ex);
            }
            throw new DaoReadException(
                    LocalizationService.getInstance().get("error.db_read"), e);
        }
    }

    @Override
    public void write(String name, LockedFieldsSudokuBoardDecorator object) throws DaoWriteException {
        logger.debug("Attempting to write Sudoku board '{}' to database.", name);
        try {
            Integer boardId = null;
            String selectBoardIdSql = "SELECT id FROM SudokuBoardDB WHERE name = ?";
            try (PreparedStatement ps = connection.prepareStatement(selectBoardIdSql)) {
                ps.setString(1, name);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        boardId = rs.getInt("id");
                        logger.debug("Board '{}' already exists with ID: {}", name, boardId);
                    }
                }
            }

            if (boardId == null) {
                String insertBoardSql = "INSERT INTO SudokuBoardDB(name) VALUES (?) RETURNING id";
                try (PreparedStatement ps = connection.prepareStatement(insertBoardSql)) {
                    ps.setString(1, name);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            boardId = rs.getInt(1);
                            logger.debug("Inserted new board '{}' with ID: {}", name, boardId);
                        } else {
                            logger.error("Failed to insert board '{}'", name);
                            throw new DaoWriteException(
                                    LocalizationService.getInstance().get("error.db_write_insert_failed"));
                        }
                    }
                }
            }

            String deleteFieldsSql = "DELETE FROM SudokuFieldDB WHERE board_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteFieldsSql)) {
                ps.setInt(1, boardId);
                ps.executeUpdate();
                logger.debug("Deleted existing fields for board ID: {}", boardId);
            }

            String insertFieldSql = "INSERT INTO SudokuFieldDB(board_id, index, value, editable) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertFieldSql)) {
                SudokuBoard board = object.getBoard();

                for (int index = 0; index < 81; index++) {
                    int value = board.getByIndex(index);
                    boolean editable = !object.isLockedByIndex(index);

                    ps.setInt(1, boardId);
                    ps.setInt(2, index);
                    ps.setInt(3, value);
                    ps.setBoolean(4, editable);
                    ps.addBatch();
                }
                ps.executeBatch();
                logger.info("Successfully wrote fields for board '{}'", name);
            }

            connection.commit();
            logger.info("Transaction committed for board '{}'", name);

        } catch (SQLException e) {
            logger.error("Error while writing board '{}' to database.", name);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("Rollback failed after error while writing board '{}'.", name);
                throw new DaoWriteException(
                        LocalizationService.getInstance().get("error.db_rollback"), ex);
            }
            throw new DaoWriteException(
                    LocalizationService.getInstance().get("error.db_write"), e);
        }
    }

    @Override
    public List<String> names() throws DaoException {
        logger.debug("Attempting to read all board names from database.");
        List<String> names = new ArrayList<>();
        String sql = "SELECT name FROM SudokuBoardDB";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
            logger.info("Successfully read {} board names from database.", names.size());
        } catch (SQLException e) {
            logger.error("Error while reading board names from database.");
            throw new DaoReadException(
                    LocalizationService.getInstance().get("error.db_read_names"), e);
        }
        return names;
    }

    @Override
    public void close() throws DaoException {
        logger.debug("Attempting to close database connection.");
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            logger.error("Error while closing the database connection.");
            throw new DaoException(
                    LocalizationService.getInstance().get("error.db_close"), e);
        }
    }
}
