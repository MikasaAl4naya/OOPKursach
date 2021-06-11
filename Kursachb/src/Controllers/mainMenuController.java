package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class mainMenuController  {

    @FXML
    private Button startToPlay;


    @FXML
    private Button ratingOfPlayers;

    @FXML
    private Button Exit;

    @FXML
    void exitFromTheGame(ActionEvent event) {
        System. exit(0);
    }

    @FXML
    void openList(ActionEvent event) throws IOException {
        ratingOfPlayers.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Design/playersRating.fxml"));
        loader.load();
        Parent root=loader.getRoot();

        Stage stage = new Stage();
        stage.setTitle("Рейтинг игроков");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void startNewGame(ActionEvent event) throws IOException {
        startToPlay.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("../Design/newGame.fxml"));

        loader.load();
        Parent root=loader.getRoot();

        Stage stage = new Stage();
        stage.setTitle("Настройки");
        stage.setScene(new Scene(root));
        stage.show();
    }
}


