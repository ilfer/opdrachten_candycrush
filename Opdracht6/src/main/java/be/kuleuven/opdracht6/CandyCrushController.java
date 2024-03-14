package be.kuleuven.opdracht6;

import java.net.URL;
import java.util.ResourceBundle;

import be.kuleuven.opdracht6.model.CandyCrushModel;
import be.kuleuven.opdracht6.view.CandyCrushView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CandyCrushController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

            // Initialize the model with the player name
            model = new CandyCrushModel(playerName);
            view = new CandyCrushView(model);
            speelbord.getChildren().add(view);
            view.setOnMouseClicked(this::onCandyClicked);
            btnReset.setOnAction(event1 -> resetGame());
        } else {
            // Display an error message if no name is entered
            errorLabel.setText("Voer een naam in.");
        }
    }

    @FXML
    public void onCandyClicked(MouseEvent me) {
        int candyIndex = view.getIndexOfClicked(me);
        model.candyWithIndexSelected(candyIndex);
        update();
    }

    private void resetGame() {
        model.resetGame();
        update(); // Update the view after resetting the game
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + model.getScore()); // Bijwerken van de score in het label
    }

    public void update() {
        view.update();
        updateScoreLabel(); // Nieuwe methode om score label bij te werken;
    }
}