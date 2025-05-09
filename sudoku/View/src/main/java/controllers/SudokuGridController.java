package controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sudoku.models.SudokuBoard;

public class SudokuGridController {

    private final TextField[][] cells = new TextField[9][9];
    private SudokuBoard board;


    @FXML
    private GridPane sudokuGrid;

    @FXML
    public void initialize() {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField textField) {
                Integer row = GridPane.getRowIndex(node);
                Integer col = GridPane.getColumnIndex(node);

                if (row == null) {
                    row = 0;
                }
                if (col == null) {
                    col = 0;
                }

                cells[row][col] = textField;
            }
        }
    }

    private void fillBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = this.board.get(row, col);
                cells[row][col].setText(value == 0 ? "" : String.valueOf(value));
            }
        }
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
        fillBoard();
    }
}