package controllers;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import managers.LangManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.daos.Dao;
import sudoku.daos.DaoFactory;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.GuiException;
import sudoku.models.LockedFieldsSudokuBoardDecorator;
import sudoku.models.SudokuBoard;
import sudoku.models.SudokuField;

import java.io.File;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.UnaryOperator;

public class SudokuGridController {

    private final TextField[][] cells = new TextField[9][9];
    private SudokuBoard board;
    private Dao<LockedFieldsSudokuBoardDecorator> dao;
    private static final Logger logger = LoggerFactory.getLogger(SudokuGridController.class.getName());

    @FXML
    private StackPane container;

    @FXML
    private GridPane sudokuGrid;

    @FXML
    private Menu menuFile;

    @FXML
    private Menu menuLanguage;

    @FXML
    private MenuItem menuSave;

    @FXML
    private MenuItem menuLoad;

    @FXML
    private MenuItem menuEn;

    @FXML
    private MenuItem menuPl;

    @FXML
    public void initialize() {
        mapCells();

        //font size updates of fields
        sudokuGrid.widthProperty().addListener((obs, oldVal, newVal) -> updateFontSize(newVal.doubleValue()));
        sudokuGrid.heightProperty().addListener((obs, oldVal, newVal) -> updateFontSize(newVal.doubleValue()));

        //updating size of main grid with every window size change
        container.widthProperty().addListener((obs, oldVal, newVal) -> {
            double size = Math.min(newVal.doubleValue(), container.getHeight());
            sudokuGrid.setPrefWidth(size);
            sudokuGrid.setPrefHeight(size);
        });
        container.heightProperty().addListener((obs, oldVal, newVal) -> {
            double size = Math.min(container.getWidth(), newVal.doubleValue());
            sudokuGrid.setPrefWidth(size);
            sudokuGrid.setPrefHeight(size);
        });

        try {
            dao = DaoFactory.getLockedFileDao("boards");
        } catch (DaoException e) {
            logger.error("Error while creating getLockedFileDao");
        }
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    @FXML
    private void handleSave() {
        if (board == null) {
            return;
        }

        TextInputDialog dialog = new TextInputDialog(LangManager.resources.getString("dialog.new.puzzle"));
        dialog.setTitle(LangManager.resources.getString("dialog.save.title"));
        dialog.setHeaderText(LangManager.resources.getString("dialog.save.header"));
        dialog.setContentText(LangManager.resources.getString("dialog.save.content"));

        dialog.showAndWait().ifPresent(fileName -> {
            if (!fileName.endsWith(".sudoku")) {
                fileName += ".sudoku";
            }

            Set<Integer> lockedFields = new HashSet<>();
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (!cells[row][col].isEditable()) {
                        lockedFields.add(row * 9 + col);
                    }
                }
            }

            try {
                dao.write(fileName, new LockedFieldsSudokuBoardDecorator(board, lockedFields));
            } catch (DaoException e) {
                logger.error("Error while saving LockedFieldSudokuBoard to {}", fileName);
            }
        });
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    @FXML
    private void handleLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(LangManager.resources.getString("dialog.load.title"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(LangManager.resources
                        .getString("dialog.load.extension_filter"), "*.sudoku")
        );
        File file = fileChooser.showOpenDialog(container.getScene().getWindow());

        if (file != null) {
            try {
                LockedFieldsSudokuBoardDecorator loaded = dao.read(file.getName());
                setBoard(loaded.getBoard());
                for (Integer index : loaded.getLockedFieldIndexes()) {
                    int row = index / 9;
                    int col = index % 9;

                    cells[row][col].setEditable(false);
                    cells[row][col].setStyle(cells[row][col].getStyle() + "; -fx-background-color: whitesmoke;");
                }
            } catch (DaoException e) {
                logger.error("Error while loading LockedFieldSudokuBoard from {}", file);
            }
        }
    }

    private void mapCells() {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField textField) {
                //dynamically changing the grid and field sizes
                GridPane.setHgrow(node, Priority.ALWAYS);
                GridPane.setVgrow(node, Priority.ALWAYS);
                textField.setMaxWidth(Double.MAX_VALUE);
                textField.setMaxHeight(Double.MAX_VALUE);

                Integer row = GridPane.getRowIndex(node);
                Integer col = GridPane.getColumnIndex(node);

                if (row == null) {
                    row = 0;
                }
                if (col == null) {
                    col = 0;
                }
                String borderStyle = this.getBorderStyle(row, col);

                node.setUserData(borderStyle);
                node.setStyle(borderStyle + " -fx-font-size: 14px;");

                cells[row][col] = textField;

            }
        }
    }


    private void bindCellsToBoard() {
        if (board == null) {
            return;
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                try {
                    SudokuField field = board.getField(row, col);
                    TextField tf = cells[row][col];

                    JavaBeanIntegerProperty property = JavaBeanIntegerPropertyBuilder.create()
                            .bean(field)
                            .name("fieldValue")
                            .build();

                    TextFormatter<Integer> formatter = new TextFormatter<>(converter, 0, filter);
                    tf.setTextFormatter(formatter);

                    tf.textProperty().addListener((obs, oldText, newText) -> {
                        Integer newVal = converter.fromString(newText);
                        if (!newVal.equals(property.get())) {
                            property.set(newVal);
                        }
                    });

                    property.addListener((obs, oldVal, newVal) -> {
                        if (newVal != null && !newVal.equals(formatter.getValue())) {
                            formatter.setValue(newVal.intValue());
                        }
                    });
                } catch (NoSuchMethodException e) {
                    logger.error("Property binding failed");
                    throw new GuiException(LangManager.resources.getString("error.binding_cell"), e);
                }
            }
        }
    }

    private void fillBoard() {
        if (board == null) {
            return;
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = this.board.get(row, col);
                cells[row][col].setText(value == 0 ? "" : String.valueOf(value));
                if (value != 0) {
                    cells[row][col].setEditable(false);
                    cells[row][col].setStyle(cells[row][col].getStyle() + "; -fx-background-color: whitesmoke;");
                }
            }
        }
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
        bindCellsToBoard();
        fillBoard();
    }

    private void updateFontSize(double size) {
        double fontSize = size / 20;

        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField tf) {
                String borderStyle = (String) node.getUserData();
                if (borderStyle == null) {
                    borderStyle = "";
                }

                String existingStyle = tf.getStyle();
                String backgroundColor = "";

                if (existingStyle.contains("-fx-background-color")) {
                    int start = existingStyle.indexOf("-fx-background-color");
                    int end = existingStyle.indexOf(";", start);
                    if (end == -1) {
                        end = existingStyle.length();
                    }
                    backgroundColor = existingStyle.substring(start, end + 1).trim();
                }

                String finalStyle = borderStyle + "; " + backgroundColor + " -fx-font-size: " + fontSize + "px;";
                tf.setStyle(finalStyle);
            }
        }
    }


    StringConverter<Integer> converter = new StringConverter<>() {
        @Override
        public String toString(Integer value) {
            return value == 0 ? "" : value.toString();
        }

        @Override
        public Integer fromString(String text) {
            if (text == null || text.isEmpty()) {
                return 0;
            }
            try {
                int val = Integer.parseInt(text);
                if (val < 1 || val > 9) {
                    return 0;
                }
                return val;
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    };

    UnaryOperator<TextFormatter.Change> filter = change -> {
        String newText = change.getControlNewText();
        if (newText.isEmpty() || newText.matches("[1-9]")) {
            return change;
        }
        return null;
    };


    private String getBorderStyle(int row, int col) {

        boolean thickRight = (col + 1) % 3 == 0 && col != 8;
        boolean thickBottom = (row + 1) % 3 == 0 && row != 8;

        String borderStyle = "-fx-border-color: lightgray lightgray lightgray lightgray; -fx-border-width: 1;";
        if (thickRight && thickBottom) {
            borderStyle = "-fx-border-color: lightgray black black lightgray; -fx-border-width: 1 3 3 1;";
        } else if (thickRight) {
            borderStyle = "-fx-border-color: lightgray black lightgray lightgray; -fx-border-width: 1 3 1 1;";
        } else if (thickBottom) {
            borderStyle = "-fx-border-color: lightgray lightgray black lightgray; -fx-border-width: 1 1 3 1;";
        }
        return borderStyle;
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    @FXML
    private void handleLangPl() {
        LangManager.setLocale(new Locale("pl"));
        reloadUI();
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    @FXML
    private void handleLangEn() {
        LangManager.setLocale(new Locale("en"));
        reloadUI();
    }

    private void reloadUI() {
        Stage stage = (Stage) container.getScene().getWindow();
        stage.setTitle(LangManager.resources.getString("title.difficulty.menu"));
        menuFile.setText(LangManager.resources.getString("menu.file"));
        menuLanguage.setText(LangManager.resources.getString("menu.language"));
        menuSave.setText(LangManager.resources.getString("menu.save"));
        menuLoad.setText(LangManager.resources.getString("menu.load"));
        menuPl.setText(LangManager.resources.getString("menuitem.pl"));
        menuEn.setText(LangManager.resources.getString("menuitem.en"));
    }
}
