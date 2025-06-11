package sudoku.daos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.DaoException;
import sudoku.models.LockedFieldsSudokuBoardDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;


class DaoFactoryTest {

    @Test
    void testGetLockedFileDao() throws DaoException {
        String tempDir = System.getProperty("java.io.tmpdir");
        Dao<LockedFieldsSudokuBoardDecorator> dao = DaoFactory.getLockedFileDao(tempDir);
        assertNotNull(dao);
        Assertions.assertInstanceOf(FileLockedSudokuBoardDao.class, dao);
    }

    @Test
    void testGetJdbcFileDao() throws DaoException {
        Dao<LockedFieldsSudokuBoardDecorator> dao = DaoFactory.getJbcdFileDao();
        assertNotNull(dao);
        Assertions.assertEquals("class sudoku.daos.JdbcSudokuBoardDao", dao.getClass().toString());
    }

    @Test
    void testPrivateConstructorThrows() throws Exception {
        Constructor<DaoFactory> constructor = DaoFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException thrown = assertThrows(
                InvocationTargetException.class,
                () -> constructor.newInstance()
        );

        Throwable cause = thrown.getCause();
        assertNotNull(cause);
        assertTrue(cause instanceof UnsupportedOperationException);
        Assertions.assertEquals("Utility class", cause.getMessage());
    }
}
