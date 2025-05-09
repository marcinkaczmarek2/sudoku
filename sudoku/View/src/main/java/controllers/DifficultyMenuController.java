package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sudoku.models.BacktrackingSudokuSolver;
import sudoku.models.SudokuBoard;
import sudoku.models.SudokuDifficulty;
import sudoku.models.SudokuSolver;

import java.io.IOException;

public class DifficultyMenuController {

    @FXML
    private  RadioButton easyRadio;

    @FXML
    private  RadioButton mediumRadio;

    @FXML
    private RadioButton hardRadio;

    @FXML
    private Button startButton;

    @FXML
    private ToggleGroup difficultyGroup;

    @FXML
    public void initialize() {
        startButton.setOnAction(event -> handleStart());
    }

     private void handleStart() {
        SudokuDifficulty difficulty;

        if (easyRadio.isSelected()) {
            difficulty = SudokuDifficulty.EASY;
        } else if (mediumRadio.isSelected()) {
            difficulty = SudokuDifficulty.MEDIUM;
        } else {
            difficulty = SudokuDifficulty.HARD;
        }

        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();
        difficulty.apply(board);

         try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/SudokuGrid.fxml"));
             Parent root = loader.load();

             SudokuGridController controller = loader.getController();
             controller.setBoard(board);

             Stage stage = (Stage) startButton.getScene().getWindow();
             stage.setScene(new Scene(root));
             stage.show();

         } catch (IOException e) {
             e.printStackTrace();
         }
    }
}
