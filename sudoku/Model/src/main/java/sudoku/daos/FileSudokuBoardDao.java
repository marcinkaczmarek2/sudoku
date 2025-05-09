package sudoku.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.DaoAccessException;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.DaoReadException;
import sudoku.exceptions.DaoWriteException;
import sudoku.models.SudokuBoard;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private static final Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class.getName());
    private final Path filePath;

    public FileSudokuBoardDao(String directory) throws DaoException {
        try {
            this.filePath = Paths.get(directory);
        } catch (InvalidPathException e) {
            logger.error("Error reading SudokuBoard from file: {}", directory);
            throw new DaoAccessException("Invalid directory name.", e);
        }
        ensureDirectoryExists(filePath);
        logger.debug("FileSudokuBoardDao initialized with path: {}", filePath);
    }

    protected void ensureDirectoryExists(Path filePath) throws DaoException {
        if (!Files.isDirectory(filePath) && !Files.isRegularFile(filePath)) {
            logger.warn("Directory did not exist. Created new directory: {}", filePath);
            try {
                Files.createDirectories(filePath);
            } catch (IOException e) {
                logger.error("Error while creating this directory: {}", filePath);
                throw new DaoAccessException("Error while creating directory", e);
            }
        }
    }

    @Override
    public SudokuBoard read(String fileName) throws DaoException {
        Path fullFilePath = filePath.resolve(fileName);
        SudokuBoard board;
        logger.debug("Trying to read board from file: {}", fullFilePath);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullFilePath.toFile()))) {
            board = (SudokuBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error reading SudokuBoard from file: {}", fullFilePath);
            throw new DaoReadException("Error reading SudokuBoard from file", e);
        }
        logger.info("Successfully read SudokuBoard from file: {}", fullFilePath);
        return board;
    }

    @Override
    public void write(String name, SudokuBoard object) throws DaoException {
        Path fullFilePath = filePath.resolve(name);

        logger.debug("Trying to write board to file: {}", fullFilePath);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullFilePath.toFile()))) {
            oos.writeObject(object);
        } catch (IOException e) {
            logger.error("Error writing SudokuBoard to file: {}", fullFilePath);
            throw new DaoWriteException("Error writing SudokuBoard to file", e);
        }
        logger.info("Successfully wrote SudokuBoard to file: {}", fullFilePath);
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
            throw new DaoAccessException("Error while reading files", e);
        }
        logger.debug("Listing files in directory: {}", filePath);
        return fileNames;
    }


    @Override
    public void close() {
        logger.info("Closing FileSudokuBoardDao.");
    }
}