package sudoku.daos;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import sudoku.exceptions.DaoAccessException;
import sudoku.exceptions.DaoException;
import sudoku.models.LocalizationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractFileDaoTest {

    static class ConcreteFileDao extends AbstractFileDao<String> {

        protected ConcreteFileDao(String directory) throws DaoException {
            super(directory);
        }

        @Override
        public String read(String name) throws DaoException {
            return null;
        }

        @Override
        public void write(String name, String object) throws DaoException {
        }
    }

    @Test
    void testInvalidPathThrows() {
        LocalizationService.initialize(Locale.ENGLISH);
        String invalidPath = "///invalid\\path::";

        DaoException e = assertThrows(DaoAccessException.class, () -> {
            new ConcreteFileDao(invalidPath);
        });
        assertNotNull(e.getMessage());
    }


    @Test
    void testEnsureDirectoryExistsCreatesDirectory() throws Exception {
        LocalizationService.initialize(Locale.ENGLISH);
        Path tempDir = Files.createTempDirectory("testDir");
        Path newDir = tempDir.resolve("newDir");

        assertFalse(Files.exists(newDir));

        AbstractFileDao.ensureDirectoryExists(newDir);

        assertTrue(Files.exists(newDir));

        Files.deleteIfExists(newDir);
        Files.deleteIfExists(tempDir);
    }

    @Test
    void testNamesListsFiles() throws Exception {
        LocalizationService.initialize(Locale.ENGLISH);
        Path tempDir = Files.createTempDirectory("testNamesDir");

        // Create dummy files
        Files.createFile(tempDir.resolve("file1.txt"));
        Files.createFile(tempDir.resolve("file2.txt"));

        ConcreteFileDao dao = new ConcreteFileDao(tempDir.toString());
        List<String> names = dao.names();

        assertTrue(names.contains("file1.txt"));
        assertTrue(names.contains("file2.txt"));

        // Cleanup
        Files.deleteIfExists(tempDir.resolve("file1.txt"));
        Files.deleteIfExists(tempDir.resolve("file2.txt"));
        Files.deleteIfExists(tempDir);
    }
    @Test
    void testCloseLogsInfo() throws Exception {
        LocalizationService.initialize(Locale.ENGLISH);
        ConcreteFileDao dao = new ConcreteFileDao(Files.createTempDirectory("closeTest").toString());

        assertDoesNotThrow(() -> dao.close());
    }
}
