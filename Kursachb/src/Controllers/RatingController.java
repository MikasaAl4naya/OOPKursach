
package Controllers;

        import Database.Player;
        import Database.dbConnection;
        import Database.listOfPlayers;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.util.ResourceBundle;

public class RatingController implements Initializable {

    @FXML
    private Button connectBtn;
    @FXML
    private TableView<Player> PlayersTable;

    @FXML
    private TableColumn<Player, String> NameColumn;

    @FXML
    private TableColumn<Player, Integer> ScoreColumn;

    @FXML
    private TableColumn<Player, String> DifColumn;

    @FXML
    private TableColumn<Player, String> styleColumn;
    public static Database.listOfPlayers listOfPlayers = new listOfPlayers();
    @FXML
    void ButtonConnect(ActionEvent event) throws IOException {

        connectBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Design/mainMenu.fxml"));
        loader.load();
        Parent root = loader.getRoot();

        Stage stage = new Stage();
        stage.setTitle("Главное меню");
        stage.setScene(new Scene(root));
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listOfPlayers.clear();
        NameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("Name"));
        ScoreColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("score"));
        DifColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("difficult"));
        styleColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("style"));
        PlayersTable.setItems(listOfPlayers.players);

        dbConnection dbconnection = new dbConnection();
        try {
            dbconnection.selectPlayers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }



}

