package sudoku.daos;

import org.junit.jupiter.api.*;
import sudoku.exceptions.*;
import sudoku.models.*;

import java.sql.*;
import java.util.*;

import static org.easymock.EasyMock.*;

public class JdbcSudokuBoardDaoTest {

    private Connection mockConnection;
    private PreparedStatement selectPs, insertPs, deletePs, batchPs;
    private ResultSet mockResultSet;
    private JdbcSudokuBoardDao dao;

    @BeforeEach
    public void setUp() throws Exception {
        mockConnection = createMock(Connection.class);
        selectPs = createMock(PreparedStatement.class);
        insertPs = createMock(PreparedStatement.class);
        deletePs = createMock(PreparedStatement.class);
        batchPs = createMock(PreparedStatement.class);
        mockResultSet = createMock(ResultSet.class);

        dao = new JdbcSudokuBoardDao(mockConnection);
    }

    @Test
    public void testWriteNewBoardSuccessfully() throws Exception {
        String boardName = "newBoard";

        // 1) Board does not exist: SELECT returns empty
        expect(mockConnection.prepareStatement("SELECT id FROM SudokuBoardDB WHERE name = ?")).andReturn(selectPs);
        selectPs.setString(1, boardName);
        expectLastCall().once();
        expect(selectPs.executeQuery()).andReturn(mockResultSet);
        expect(mockResultSet.next()).andReturn(false);
        mockResultSet.close();
        expectLastCall().once();
        selectPs.close();
        expectLastCall().once();

        // 2) Insert new board
        expect(mockConnection.prepareStatement("INSERT INTO SudokuBoardDB(name) VALUES (?) RETURNING id")).andReturn(insertPs);
        insertPs.setString(1, boardName);
        expectLastCall().once();
        expect(insertPs.executeQuery()).andReturn(mockResultSet);
        expect(mockResultSet.next()).andReturn(true);
        expect(mockResultSet.getInt(1)).andReturn(42);
        mockResultSet.close();
        expectLastCall().once();
        insertPs.close();
        expectLastCall().once();

        // 3) Delete existing fields for board_id=42
        expect(mockConnection.prepareStatement("DELETE FROM SudokuFieldDB WHERE board_id = ?")).andReturn(deletePs);
        deletePs.setInt(1, 42);
        expectLastCall().once();
        expect(deletePs.executeUpdate()).andReturn(1);
        deletePs.close();
        expectLastCall().once();

        // 4) Insert fields batch
        expect(mockConnection.prepareStatement("INSERT INTO SudokuFieldDB(board_id, index, value, editable) VALUES (?, ?, ?, ?)"))
                .andReturn(batchPs);

        // Prepare batch calls for all 81 fields (simplify with times(81))
        batchPs.setInt(eq(1), eq(42));
        expectLastCall().times(81);
        batchPs.setInt(eq(2), anyInt());
        expectLastCall().times(81);
        batchPs.setInt(eq(3), anyInt());
        expectLastCall().times(81);
        batchPs.setBoolean(eq(4), anyBoolean());
        expectLastCall().times(81);

        batchPs.addBatch();
        expectLastCall().times(81);

        expect(batchPs.executeBatch()).andReturn(new int[81]);
        batchPs.close();
        expectLastCall().once();

        // Commit after all
        mockConnection.commit();
        expectLastCall().once();

        replay(mockConnection, selectPs, insertPs, deletePs, batchPs, mockResultSet);

        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        LockedFieldsSudokuBoardDecorator lockedBoard = new LockedFieldsSudokuBoardDecorator(board, new HashSet<>());

        dao.write(boardName, lockedBoard);

        verify(mockConnection, selectPs, insertPs, deletePs, batchPs, mockResultSet);
    }

    @Test
    public void testWriteExistingBoardSuccessfully() throws Exception {
        String boardName = "existingBoard";

        // 1) Board exists: SELECT returns id
        expect(mockConnection.prepareStatement("SELECT id FROM SudokuBoardDB WHERE name = ?")).andReturn(selectPs);
        selectPs.setString(1, boardName);
        expectLastCall().once();
        expect(selectPs.executeQuery()).andReturn(mockResultSet);
        expect(mockResultSet.next()).andReturn(true);
        expect(mockResultSet.getInt("id")).andReturn(99);
        mockResultSet.close();
        expectLastCall().once();
        selectPs.close();
        expectLastCall().once();

        // No insert board

        // 2) Delete fields for board_id=99
        expect(mockConnection.prepareStatement("DELETE FROM SudokuFieldDB WHERE board_id = ?")).andReturn(deletePs);
        deletePs.setInt(1, 99);
        expectLastCall().once();
        expect(deletePs.executeUpdate()).andReturn(1);
        deletePs.close();
        expectLastCall().once();

        // 3) Insert fields batch (same as before)
        expect(mockConnection.prepareStatement("INSERT INTO SudokuFieldDB(board_id, index, value, editable) VALUES (?, ?, ?, ?)"))
                .andReturn(batchPs);

        batchPs.setInt(eq(1), eq(99));
        expectLastCall().times(81);
        batchPs.setInt(eq(2), anyInt());
        expectLastCall().times(81);
        batchPs.setInt(eq(3), anyInt());
        expectLastCall().times(81);
        batchPs.setBoolean(eq(4), anyBoolean());
        expectLastCall().times(81);

        batchPs.addBatch();
        expectLastCall().times(81);

        expect(batchPs.executeBatch()).andReturn(new int[81]);
        batchPs.close();
        expectLastCall().once();

        mockConnection.commit();
        expectLastCall().once();

        replay(mockConnection, selectPs, deletePs, batchPs, mockResultSet);

        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        LockedFieldsSudokuBoardDecorator lockedBoard = new LockedFieldsSudokuBoardDecorator(board, new HashSet<>());

        dao.write(boardName, lockedBoard);

        verify(mockConnection, selectPs, deletePs, batchPs, mockResultSet);
    }

    @Test
    public void testWriteSQLExceptionRollsBack() throws Exception {
        String boardName = "failBoard";

        expect(mockConnection.prepareStatement("SELECT id FROM SudokuBoardDB WHERE name = ?")).andReturn(selectPs);
        selectPs.setString(1, boardName);
        expectLastCall().once();
        expect(selectPs.executeQuery()).andThrow(new SQLException("fail"));

        mockConnection.rollback();
        expectLastCall().once();

        replay(mockConnection, selectPs);

        LockedFieldsSudokuBoardDecorator dummyBoard = new LockedFieldsSudokuBoardDecorator(new SudokuBoard(new BacktrackingSudokuSolver()), new HashSet<>());

        Assertions.assertThrows(DaoWriteException.class, () -> dao.write(boardName, dummyBoard));

        verify(mockConnection, selectPs);
    }

    @Test
    public void testReadBoardSuccessfully() throws Exception {
        String boardName = "readBoard";

        // Board id select
        expect(mockConnection.prepareStatement("SELECT id FROM SudokuBoardDB WHERE name = ?")).andReturn(selectPs);
        selectPs.setString(1, boardName);
        expectLastCall().once();
        expect(selectPs.executeQuery()).andReturn(mockResultSet);
        expect(mockResultSet.next()).andReturn(true);
        expect(mockResultSet.getInt("id")).andReturn(7);
        mockResultSet.close();
        expectLastCall().once();
        selectPs.close();
        expectLastCall().once();

        // Fields select
        expect(mockConnection.prepareStatement("SELECT index, value, editable FROM SudokuFieldDB WHERE board_id = ?")).andReturn(deletePs);
        deletePs.setInt(1, 7);
        expectLastCall().once();

        ResultSet fieldsRs = createMock(ResultSet.class);
        expect(deletePs.executeQuery()).andReturn(fieldsRs);

        // Simulate 2 fields
        expect(fieldsRs.next()).andReturn(true);
        expect(fieldsRs.getInt("index")).andReturn(0);
        expect(fieldsRs.getInt("value")).andReturn(5);
        expect(fieldsRs.getBoolean("editable")).andReturn(false);

        expect(fieldsRs.next()).andReturn(true);
        expect(fieldsRs.getInt("index")).andReturn(1);
        expect(fieldsRs.getInt("value")).andReturn(3);
        expect(fieldsRs.getBoolean("editable")).andReturn(true);

        expect(fieldsRs.next()).andReturn(false);

        fieldsRs.close();
        expectLastCall().once();
        deletePs.close();
        expectLastCall().once();

        mockConnection.commit();
        expectLastCall().once();

        replay(mockConnection, selectPs, deletePs, mockResultSet, fieldsRs);

        LockedFieldsSudokuBoardDecorator board = dao.read(boardName);

        verify(mockConnection, selectPs, deletePs, mockResultSet, fieldsRs);

        // Validate locked index 0 is locked, 1 is editable
        Assertions.assertTrue(board.isLockedByIndex(0));
        Assertions.assertFalse(board.isLockedByIndex(1));
    }

    @Test
    public void testReadBoardNotFound() throws Exception {
        String boardName = "missingBoard";

        expect(mockConnection.prepareStatement("SELECT id FROM SudokuBoardDB WHERE name = ?")).andReturn(selectPs);
        selectPs.setString(1, boardName);
        expectLastCall().once();
        expect(selectPs.executeQuery()).andReturn(mockResultSet);
        expect(mockResultSet.next()).andReturn(false);
        mockResultSet.close();
        expectLastCall().once();
        selectPs.close();
        expectLastCall().once();

        replay(mockConnection, selectPs, mockResultSet);

        Assertions.assertThrows(DaoReadException.class, () -> dao.read(boardName));

        verify(mockConnection, selectPs, mockResultSet);
    }

    @Test
    public void testNamesSuccessfully() throws Exception {
        expect(mockConnection.prepareStatement("SELECT name FROM SudokuBoardDB")).andReturn(selectPs);
        expect(selectPs.executeQuery()).andReturn(mockResultSet);

        expect(mockResultSet.next()).andReturn(true);
        expect(mockResultSet.getString("name")).andReturn("board1");
        expect(mockResultSet.next()).andReturn(true);
        expect(mockResultSet.getString("name")).andReturn("board2");
        expect(mockResultSet.next()).andReturn(false);

        mockResultSet.close();
        expectLastCall().once();
        selectPs.close();
        expectLastCall().once();

        replay(mockConnection, selectPs, mockResultSet);

        List<String> names = dao.names();

        verify(mockConnection, selectPs, mockResultSet);

        Assertions.assertEquals(2, names.size());
        Assertions.assertTrue(names.contains("board1"));
        Assertions.assertTrue(names.contains("board2"));
    }

    @Test
    public void testCloseSuccess() throws Exception {
        expect(mockConnection.isClosed()).andReturn(false);
        mockConnection.close();
        expectLastCall().once();

        replay(mockConnection);

        dao.close();

        verify(mockConnection);
    }

    @Test
    public void testCloseThrowsException() throws Exception {
        expect(mockConnection.isClosed()).andReturn(false);
        mockConnection.close();
        expectLastCall().andThrow(new SQLException("fail"));

        replay(mockConnection);

        Assertions.assertThrows(DaoException.class, () -> dao.close());

        verify(mockConnection);
    }
}
