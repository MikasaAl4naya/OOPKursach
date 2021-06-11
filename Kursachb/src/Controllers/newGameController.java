package Controllers;

import Database.Player;
import Database.listOfPlayers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class newGameController {
    @FXML
    private TextField newPlayerName;

    @FXML
    private Button createNewPlayer;

    @FXML
    private VBox difficultGroup;

    @FXML
    private RadioButton RBEasy;

    @FXML
    private ToggleGroup difGroup;

    @FXML
    private RadioButton RBNormal;

    @FXML
    private RadioButton RBHigh;

    @FXML
    private VBox musicStyle;

    @FXML
    private RadioButton RBRock;

    @FXML
    private ToggleGroup styleGroup;

    @FXML
    private RadioButton RBhiphop;

    @FXML
    private RadioButton RBpop;

    @FXML
    private RadioButton RBOST;

    @FXML
    private RadioButton RBallStyles;
    public static Player player;

   static listOfPlayers list = new listOfPlayers();

    @FXML
    void createNewPlayerAction(ActionEvent event) throws IOException {
        String difficult = "easy";
        String style = "allStyles";
        if (RBEasy.isSelected())
        {
            difficult = "Easy";
        }
        else if(RBNormal.isSelected())
        {
            difficult = "Normal";
        }
        else if(RBHigh.isSelected())
        {
            difficult = "Hard";
        }
        if (RBRock.isSelected())
            style = "Rock";
        else if (RBhiphop.isSelected())
            style = "Hiphop";
        else if (RBpop.isSelected())
            style = "Pop";
        else if (RBOST.isSelected())
            style ="OST";
        else if(RBallStyles.isSelected())
            style="AllStyles";
        if (!newPlayerName.getText().equals("")) {
            player = new Player(0, newPlayerName.getText(), difficult, style);
            list.add(player);

            createNewPlayer.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Design/sample.fxml"));


            loader.load();
            Parent root = loader.getRoot();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Угадай мелодию");
            stage.show();
        }
        else{
            JOptionPane.showMessageDialog(null, "Введите имя игрока");
        }
    }

}
