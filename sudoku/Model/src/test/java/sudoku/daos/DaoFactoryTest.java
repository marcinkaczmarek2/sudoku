package sudoku.daos;

import org.junit.jupiter.api.Test;
import sudoku.daos.Dao;
import sudoku.daos.DaoFactory;
import sudoku.daos.FileLockedSudokuBoardDao;
import sudoku.exceptions.DaoException;
import sudoku.models.LockedFieldsSudokuBoardDecorator;

import static org.junit.jupiter.api.Assertions.*;

class DaoFactoryTest {

    @Test
    void testGetLockedFileDao() throws DaoException {
        String tempDir = System.getProperty("java.io.tmpdir");
        Dao<LockedFieldsSudokuBoardDecorator> dao = DaoFactory.getLockedFileDao(tempDir);
        assertNotNull(dao);
        assertInstanceOf(FileLockedSudokuBoardDao.class, dao);
    }
}
