package sudoku.daos;

import org.junit.jupiter.api.Test;
import sudoku.daos.Dao;
import sudoku.daos.DaoFactory;
import sudoku.daos.FileLockedSudokuBoardDao;
import sudoku.exceptions.DaoException;
import sudoku.models.LockedFieldsSudokuBoardDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class DaoFactoryTest {

    @Test
    void testGetLockedFileDao() throws DaoException {
        String tempDir = System.getProperty("java.io.tmpdir");
        Dao<LockedFieldsSudokuBoardDecorator> dao = DaoFactory.getLockedFileDao(tempDir);
        assertNotNull(dao);
        assertInstanceOf(FileLockedSudokuBoardDao.class, dao);
    }
    @Test
    void testGetJdbcFileDao() throws DaoException {
        Dao<LockedFieldsSudokuBoardDecorator> dao = DaoFactory.getJbcdFileDao();
        assertNotNull(dao);
        assertEquals("class sudoku.daos.JdbcSudokuBoardDao", dao.getClass().toString());
    }

    @Test
    void testPrivateConstructorThrows() throws Exception {
        Constructor<DaoFactory> constructor = DaoFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException thrown = assertThrows(
                InvocationTargetException.class,
                () -> constructor.newInstance()
        );

        // unwrap the cause to assert the UnsupportedOperationException
        Throwable cause = thrown.getCause();
        assertNotNull(cause);
        assertTrue(cause instanceof UnsupportedOperationException);
        assertEquals("Utility class", cause.getMessage());
    }
}
