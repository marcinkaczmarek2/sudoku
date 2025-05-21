package sudoku.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.DaoAccessException;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.DaoReadException;
import sudoku.exceptions.DaoWriteException;
import sudoku.models.LocalizationService;
import sudoku.models.LockedFieldsSudokuBoardDecorator;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileLockedSudokuBoardDao implements Dao<LockedFieldsSudokuBoardDecorator> {
    private static final Logger logger = LoggerFactory.getLogger(FileLockedSudokuBoardDao.class.getName());
    private final Path filePath;

    public FileLockedSudokuBoardDao(String directory) throws DaoException {
        try {
            this.filePath = Paths.get(directory);
        } catch (InvalidPathException e) {
            logger.error("Error reading SudokuBoard from file: {}", directory);
            throw new DaoAccessException(LocalizationService.getInstance().get("error.invalid_directory"), e);
        }
        FileSudokuBoardDao.ensureDirectoryExists(filePath);
        logger.debug("FileSudokuBoardDao initialized with path: {}", filePath);
    }

    @Override
    public void write(String name, LockedFieldsSudokuBoardDecorator object) throws DaoException {
        Path fullFilePath = filePath.resolve(name);

        logger.debug("Trying to write board to file: {}", fullFilePath);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullFilePath.toFile()))) {
            oos.writeObject(object);
        } catch (IOException e) {
            logger.error("Error writing SudokuBoard to file: {}", fullFilePath);
            throw new DaoWriteException(LocalizationService.getInstance().get("error.writing_boards"), e);
        }
        logger.info("Successfully wrote SudokuBoard to file: {}", fullFilePath);
    }

    @Override
    public LockedFieldsSudokuBoardDecorator read(String name) throws DaoException {
        Path fullFilePath = filePath.resolve(name);
        logger.debug("Trying to read board from file: {}", fullFilePath);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullFilePath.toFile()))) {
            logger.info("Successfully read SudokuBoard from file: {}", fullFilePath);
            return (LockedFieldsSudokuBoardDecorator) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error reading SudokuBoard from file: {}", fullFilePath);
            throw new DaoReadException(LocalizationService.getInstance().get("error.reading_boards"), e);
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
        logger.info("Closing FileLockedSudokuBoardDao.");
    }
}
