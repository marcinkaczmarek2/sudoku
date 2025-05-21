package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.util.converter.NumberStringConverter;
import sudoku.models.SudokuBoard;
import sudoku.models.SudokuField;

public class SudokuGridController {

    private final TextField[][] cells = new TextField[9][9];
    private SudokuBoard board;

    @FXML
    private StackPane container;

    @FXML
    private GridPane sudokuGrid;

    @FXML
    public void initialize() {
        mapCells();

        sudokuGrid.widthProperty().addListener((obs, oldVal, newVal) -> updateFontSize(newVal.doubleValue()));
        sudokuGrid.heightProperty().addListener((obs, oldVal, newVal) -> updateFontSize(newVal.doubleValue()));


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
    }

    private void mapCells() {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField textField) {
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

                    Bindings.bindBidirectional(tf.textProperty(), property, new NumberStringConverter() {
                        @Override
                        public Integer fromString(String value) {
                            if (value == null || value.isEmpty()) {
                                return 0;
                            }
                            return (Integer) super.fromString(value);
                        }
                    });

                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Property binding failed for cell [" + row + "," + col + "]", e);
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
                tf.setStyle(borderStyle + " -fx-font-size: " + fontSize + "px;");
            }
        }
    }

}
