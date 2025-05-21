package sudoku.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.DaoReadException;
import sudoku.exceptions.DaoWriteException;
import sudoku.models.LocalizationService;
import sudoku.models.SudokuBoard;

import java.io.*;
import java.nio.file.Path;

public class FileSudokuBoardDao extends AbstractFileDao<SudokuBoard> {
    private static final Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class);

    public FileSudokuBoardDao(String directory) throws DaoException {
        super(directory);
        logger.debug("FileSudokuBoardDao initialized with path: {}", filePath);
    }

    @Override
    public SudokuBoard read(String fileName) throws DaoException {
        Path fullFilePath = filePath.resolve(fileName);
        logger.debug("Trying to read board from file: {}", fullFilePath);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullFilePath.toFile()))) {
            SudokuBoard board = (SudokuBoard) ois.readObject();
            logger.info("Successfully read SudokuBoard from file: {}", fullFilePath);
            return board;
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error reading SudokuBoard from file: {}", fullFilePath);
            throw new DaoReadException(LocalizationService.getInstance().get("error.reading_boards"), e);
        }
    }

    @Override
    public void write(String name, SudokuBoard object) throws DaoException {
        Path fullFilePath = filePath.resolve(name);
        logger.debug("Trying to write board to file: {}", fullFilePath);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullFilePath.toFile()))) {
            oos.writeObject(object);
            logger.info("Successfully wrote SudokuBoard to file: {}", fullFilePath);
        } catch (IOException e) {
            logger.error("Error writing SudokuBoard to file: {}", fullFilePath);
            throw new DaoWriteException(LocalizationService.getInstance().get("error.writing_boards"), e);
        }
    }
}
