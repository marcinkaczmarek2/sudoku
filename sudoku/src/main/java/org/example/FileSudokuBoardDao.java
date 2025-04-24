package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    private static final Logger logger = Logger.getLogger(FileSudokuBoardDao.class.getName());
    private final Path filePath;

    public FileSudokuBoardDao(String directory) {
        try {
            this.filePath = Paths.get(directory);
        } catch (InvalidPathException e) {
            logger.log(Level.SEVERE, "Invalid directory name: " + directory, e);
            throw new IllegalArgumentException("Invalid directory name.", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An unexpected error occurred while creating path.", e);
            throw new RuntimeException("An unexpected error occurred.", e);
        }
    }

    @Override
    public SudokuBoard read(String fileName) {
        Path fullFilePath = filePath.resolve(fileName);
        SudokuBoard board;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullFilePath.toFile()))) {
            board = (SudokuBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error reading SudokuBoard from file: " + fullFilePath, e);
            throw new RuntimeException("Error reading SudokuBoard from file: " + e.getMessage(), e);
        }

        return board;
    }

    @Override
    public void write(String name, SudokuBoard object) {
        Path fullFilePath = filePath.resolve(name);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullFilePath.toFile()))) {
            oos.writeObject(object);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing SudokuBoard to file: " + fullFilePath, e);
            throw new RuntimeException("Error writing SudokuBoard to file: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> names() {
        List<String> fileNames = new ArrayList<>();

        try {
            Files.list(filePath)
                    .filter(Files::isRegularFile)
                    .forEach(path -> fileNames.add(path.getFileName().toString()));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while reading files from directory: " + filePath, e);
            throw new RuntimeException("Error while reading files from directory: " + e.getMessage(), e);
        }

        return fileNames;
    }

    @Override
    public void close() {
        logger.info("Closing FileSudokuBoardDao.");
    }
}
