package sudoku.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.DaoAccessException;
import sudoku.exceptions.DaoException;
import sudoku.models.LocalizationService;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractFileDao<T> implements Dao<T>, AutoCloseable {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final Path filePath;

    protected AbstractFileDao(String directory) throws DaoException {
        try {
            this.filePath = Paths.get(directory);
        } catch (InvalidPathException e) {
            logger.error("Invalid path: {}", directory);
            throw new DaoAccessException(LocalizationService.getInstance().get("error.invalid_directory"), e);
        }
        ensureDirectoryExists(filePath);
    }

    protected static void ensureDirectoryExists(Path path) throws DaoException {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new DaoAccessException(LocalizationService.getInstance().get("error.create_directory"), e);
            }
        }
    }

    @Override
    public List<String> names() throws DaoException {
        List<String> fileNames = new ArrayList<>();

        logger.debug("Trying to list files in directory: {}", filePath);
        try (Stream<Path> paths = Files.list(filePath)) {
            paths.filter(Files::isRegularFile)
                    .forEach(path -> fileNames.add(path.getFileName().toString()));
        } catch (IOException e) {
            logger.error("Error while reading files from directory: {}", filePath);
            throw new DaoAccessException(LocalizationService.getInstance().get("error.reading_files"), e);
        }
        logger.debug("Listing files in directory: {}", filePath);
        return fileNames;
    }

    @Override
    public void close() {
        logger.info("Closing {}", this.getClass().getSimpleName());
    }
}
