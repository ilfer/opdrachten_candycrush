package be.kuleuven.opdracht6;

import be.kuleuven.opdracht6.model.CandyCrushModel;
import be.kuleuven.opdracht6.view.CandyCrushView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CandyCrushController {

    @FXML
    private Label Label;

    @FXML
    private Label errorLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnReset;

    @FXML
    private AnchorPane paneel;

    @FXML
    private AnchorPane speelbord;

    @FXML
    private TextField textInput;

    @FXML
    private Pane loginPane;

    @FXML
    private Pane gamePane;

    private CandyCrushModel model;
    private CandyCrushView view;

    @FXML
    void initialize() {
        assert Label != null : "fx:id=\"Label\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert btnStart != null : "fx:id=\"btnStart\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert paneel != null : "fx:id=\"paneel\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert speelbord != null : "fx:id=\"speelbord\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'candycrush-view.fxml'.";

        // Hide the game pane initially
        gamePane.setVisible(false);

        // Set event handler for start button
        btnStart.setOnAction(this::startGame);

        // Set event handler for reset button
        btnReset.setOnAction(event -> resetGame());
    }

    @FXML
    void startGame(ActionEvent event) {
        // Get the player name from the text input
        String playerName = textInput.getText();

        // Check if a name is entered
        if (!playerName.isEmpty()) {
            // Show the game pane
            gamePane.setVisible(true);
            // Hide the login pane
            loginPane.setVisible(false);

            // Initialize the model with the player name and board size
            BoardSize boardSize = new BoardSize(4, 4); // Aangenomen dat het speelbord 4x4 is
            model = new CandyCrushModel(playerName, boardSize);
            view = new CandyCrushView(model, boardSize);
            speelbord.getChildren().add(view);
        } else {
            // Display an error message if no name is entered
            errorLabel.setText("Voer een naam in.");
        }
    }

    @FXML
    void resetGame() {
        model.resetGame();
        update(); // Update the view after resetting the game
    }

    private void update() {
        view.update();
        updateScoreLabel(); // Nieuwe methode om score label bij te werken;
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + model.getScore()); // Bijwerken van de score in het label
    }
}