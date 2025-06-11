package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import managers.LangManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.exceptions.SetStageException;
import sudoku.models.*;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class DifficultyMenuController {

    private static final Logger logger = LoggerFactory.getLogger(DifficultyMenuController.class.getName());

    @FXML
    private StackPane difficultyStackPane;

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
        startButton.setOnAction(this::handleStart);
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

    private void handleStart(ActionEvent event) {
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
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/fxml/SudokuGrid.fxml"), LangManager.getBundle());
            Parent root = loader.load();

            SudokuGridController controller = loader.getController();
            controller.setBoard(board);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setTitle(LangManager.resources.getString("window.title.sudoku_game"));

            double size = 500;
            stage.setMinWidth(size);
            stage.setMinHeight(size);
            stage.setWidth(size);
            stage.setHeight(size);

        } catch (IOException e) {
            logger.error("Error while setting stage to SudokuGrid");
            throw new SetStageException(LangManager.resources.getString("error.reading_boards"), e);
        }
    }

    private void reloadUI() {
        try {
            String selectedDifficulty = null;
            if (easyRadio.isSelected()) {
                selectedDifficulty = "easy";
            } else if (mediumRadio.isSelected()) {
                selectedDifficulty = "medium";
            } else if (hardRadio.isSelected()) {
                selectedDifficulty = "hard";
            }

            Stage stage = (Stage) difficultyStackPane.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/fxml/DifficultyMenu.fxml"), LangManager.getBundle());
            Parent root = loader.load();

            stage.getScene().setRoot(root);
            stage.setTitle(LangManager.resources.getString("title.difficulty.menu"));

            DifficultyMenuController controller = loader.getController();
            switch (Objects.requireNonNull(selectedDifficulty)) {
                case "easy" -> controller.setSelectedDifficulty("easy");
                case "medium" -> controller.setSelectedDifficulty("medium");
                case "hard" -> controller.setSelectedDifficulty("hard");
                default -> controller.setSelectedDifficulty("easy");
            }

        } catch (IOException e) {
            logger.error("Error reloading the UI");
            throw new SetStageException(LangManager.resources.getString("error.reading_boards"), e);
        }
    }

    private void setSelectedDifficulty(String difficulty) {
        switch (difficulty) {
            case "easy" -> easyRadio.setSelected(true);
            case "medium" -> mediumRadio.setSelected(true);
            case "hard" -> hardRadio.setSelected(true);
            default -> easyRadio.setSelected(true);
        }
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    @FXML
    private void showAuthors() {
        Locale currentLocale = LangManager.getBundle().getLocale();

        ResourceBundle authors = ResourceBundle.getBundle("authors.Authors", currentLocale);

        String name1 = authors.getString("author1.name");
        String name2 = authors.getString("author2.name");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LangManager.getBundle().getString("menu.author.info"));
        alert.setHeaderText(LangManager.getBundle().getString("menu.author.info"));
        alert.setContentText(name1 + " & " + name2);
        alert.showAndWait();
    }


}
