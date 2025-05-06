package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.example.BacktrackingSudokuSolver;
import org.example.SudokuBoard;
import org.example.SudokuDifficulty;
import org.example.SudokuSolver;

public class MainMenuController {

    @FXML
    private RadioButton easyRadio;

    @FXML
    private RadioButton mediumRadio;

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

        System.out.println("Difficulty: " + difficulty + ", cells removed: " + difficulty.getCellsToRemove());
        System.out.println(board);
    }
}
