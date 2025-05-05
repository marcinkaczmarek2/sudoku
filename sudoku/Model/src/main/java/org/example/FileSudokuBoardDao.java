package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private static final Logger logger = Logger.getLogger(FileSudokuBoardDao.class.getName());
    private final Path filePath;

    public FileSudokuBoardDao(String directory) {
        try {
            this.filePath = Paths.get(directory);
        } catch (InvalidPathException e) {
            logger.log(Level.SEVERE, "Invalid directory name: " + directory, e);
            throw new IllegalArgumentException("Invalid directory name.", e);
        }
        ensureDirectoryExists(filePath);
    }

    protected void ensureDirectoryExists(Path filePath) {
        if (!Files.isDirectory(filePath) && !Files.isRegularFile(filePath)) {
            try {
                Files.createDirectories(filePath);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error while creating this directory: " + filePath, e);
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public SudokuBoard read(String fileName) throws DaoException {
        Path fullFilePath = filePath.resolve(fileName);
        SudokuBoard board;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullFilePath.toFile()))) {
            board = (SudokuBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error reading SudokuBoard from file: " + fullFilePath, e);
            throw new DaoException("Error reading SudokuBoard from file: " + fullFilePath, e);
        }

        return board;
    }

    @Override
    public void write(String name, SudokuBoard object) throws DaoException {
        Path fullFilePath = filePath.resolve(name);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullFilePath.toFile()))) {
            oos.writeObject(object);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing SudokuBoard to file: " + fullFilePath, e);
            throw new DaoException("Error writing SudokuBoard to file: " + fullFilePath, e);
        }
    }

    @Override
    public List<String> names() throws DaoException {
        List<String> fileNames = new ArrayList<>();

        try (Stream<Path> paths = Files.list(filePath)) {
            paths.filter(Files::isRegularFile)
                    .forEach(path -> fileNames.add(path.getFileName().toString()));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while reading files from directory: " + filePath, e);
            throw new DaoException("Error while reading files from directory: " + filePath, e);
        }

        return fileNames;
    }


    @Override
    public void close() {
        logger.info("Closing FileSudokuBoardDao.");
    }
}