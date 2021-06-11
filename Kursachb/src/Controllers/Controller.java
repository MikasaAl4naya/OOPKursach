package Controllers;

import Database.MusicList;
import Database.Player;
import Database.dbConnection;
import Database.listOfPlayers;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Slider volumeSLider;
    @FXML
    private AnchorPane Pane;

    @FXML
    private Button nextMedia;

    @FXML
    private Button ResumeMedia;

    @FXML
    private Button pauseMedia;

    @FXML
    private Label fpScore;

    @FXML
    private Label FP;

    @FXML
    private Button Answer2;

    @FXML
    private Button Answer3;

    @FXML
    private Button Answer1;

    @FXML
    private Button Answer4;


    @FXML
    private Button fiftytofifty;

    @FXML
    private Button chanceToMistake;

    private ArrayList<File> songs;

    private Timer timer;

    private TimerTask timerTask;

    private boolean running;
    @FXML
    private Label endGame;
    private Media media;

    private MediaPlayer mediaPlayer;

    @FXML
    private Label songName;
    @FXML
    private Label whenLose;

    @FXML
    private Label whenWin;
    @FXML
    private Button endGame2;

    public static MusicList ml;
    private dbConnection dbc;

    @FXML
    private Button ratingButton;
    @FXML
    private Button btnWhenLose;
    public Player player;
    public static listOfPlayers list = new listOfPlayers();
    private final int duration=20;
    private int g =0;
    private int bonus = 200;
    private boolean helper=false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songName.setVisible(false);
        endGame.setVisible(false);
        Answer2.setVisible(false);
        Answer3.setVisible(false);
        Answer4.setVisible(false);
        Answer1.setVisible(false);
        whenLose.setVisible(false);
        whenWin.setVisible(false);
        btnWhenLose.setVisible(false);
        endGame2.setVisible(false);
        ratingButton.setDisable(true);
        songs = new ArrayList<File>();
        ml = new MusicList();
        File directory = new File("resources/" + newGameController.player.getStyle());

        File[] files = directory.listFiles();

        if(files !=null){
            for(File file : files)
            {
                songs.add(file);
                Random random = new Random();
            }
        }
        ml.clear();
        dbConnection dbConnection = new dbConnection();
        try {
            dbConnection.selectMusic(newGameController.player.getStyle());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        File f = new File(ml.returnPath(0));
        media = new Media(f.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        beginTimer();
        nextMedia();
        volumeSLider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(volumeSLider.getValue()*0.01);
            }
        });
        Answer2.setOnMouseClicked(event -> {

            answerClick(Answer2);

        });
        Answer4.setOnMouseClicked(event -> {

            answerClick(Answer4);

        });

        Answer1.setOnMouseClicked(event -> {

            answerClick(Answer1);

        });
        Answer3.setOnMouseClicked(event -> {
                answerClick(Answer3);
        });
        int duration = 0;
        switch(newGameController.player.getDifficult()) {
            case "Easy": {
                duration = 20;
                bonus = 200;
                break;
            }
            case "Normal": {
                duration = 15;
                bonus = 300;
                break;
            }
            case "Hard":{
                duration = 10;
                bonus =400;
                break;
            }

        }


        FP.setText(list.returnName());
        mediaPlayer.setStopTime(Duration.seconds(duration));

        ratingButton.setOnAction(action->{
            dbc = new dbConnection();
            player = new Player(Integer.parseInt(fpScore.getText()),FP.getText(), newGameController.player.getDifficult(), newGameController.player.getStyle() );

            try {
                dbc.playerInsert(player);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            ratingButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Design/playersRating.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root=loader.getRoot();

            Stage stage = new Stage();
            stage.setTitle("Рейтинг игроков");
            stage.setScene(new Scene(root));
            stage.show();


        });
        fiftytofifty.setOnAction(action->{

            if (Answer1.getText().equals(ml.returnFN(g)))
            {
                Answer2.setVisible(false);
                Answer3.setVisible(false);
            }
            else if(Answer2.getText().equals(ml.returnFN(g)))
            {Answer1.setVisible(false);
                Answer4.setVisible(false);

            }
            else if(Answer3.getText().equals(ml.returnFN(g)))
            {
                Answer2.setVisible(false);
                Answer4.setVisible(false);
            }
            else if(Answer4.getText().equals(ml.returnFN(g)))
            {
                Answer1.setVisible(false);
                Answer3.setVisible(false);
            }
            fiftytofifty.setVisible(false);

        });
    }
    @FXML
    private void Mistake ()
    {
        helper =true;
        chanceToMistake.setVisible(false);
    }

    void answerClick(Button answer)
    {
        Answer2.setVisible(false);
        Answer3.setVisible(false);
        Answer4.setVisible(false);
        Answer1.setVisible(false);
        if (answer.getText().equals(ml.returnFN(g)) ){
            answer.setStyle("-fx-text-fill: white; -fx-background-color: green");
            int score = (Integer.parseInt(fpScore.getText()) + bonus);
            fpScore.setText(String.valueOf(score));
            whenWin.setVisible(true);
            songName.setVisible(true);
            mediaPlayer.stop();

            if (g== songs.size())
            {
                endGame.setText("Вы угадали все песни");
                endGame.setVisible(true);
                btnWhenLose.setVisible(true);
                endGame2.setVisible(true);
            }
        }
        else
        {
            answer.setStyle("-fx-text-fill: black; -fx-background-color: red");
            if(!helper)
            {
                whenLose.setText("Игра окончена");
                whenLose.setVisible(true);
                btnWhenLose.setVisible(true);
                nextMedia.setDisable(true);

            }
            else {
                whenLose.setText("Вы использовали свое право на ошибку");
                whenLose.setStyle("-fx-text-fill: red");
                whenLose.setVisible(true);
                chanceToMistake.setDisable(true);
            }
            ratingButton.setDisable(false);

        }
        fiftytofifty.setDisable(true);
        chanceToMistake.setDisable(true);
        mediaPlayer.stop();
        answer.setVisible(true);
        pauseMedia.setDisable(true);
        ResumeMedia.setDisable(true);
        volumeSLider.setDisable(true);


    }


    void nextMedia()
    {
        if(fiftytofifty.isDisable())
        fiftytofifty.setDisable(false);
        System.out.println(helper);
        if(!helper)

        chanceToMistake.setDisable(false);
        else
    {
        chanceToMistake.setDisable(true);  }
        whenLose.setVisible(false);
        helper =false;
        ratingButton.setDisable(true);
        g++;
        if (g>=songs.size()) {
            endGame.setText("Это последняя песня");
            endGame.setVisible(true);
            pauseMedia.setDisable(true);
            ResumeMedia.setDisable(true);
            volumeSLider.setDisable(true);
            Answer2.setVisible(false);
            Answer3.setVisible(false);
            Answer4.setVisible(false);
            Answer1.setVisible(false);
            nextMedia.setDisable(true);
            chanceToMistake.setVisible(false);
            fiftytofifty.setVisible(false);
            ratingButton.setDisable(false);
            btnWhenLose.setVisible(true);

        }

        mediaPlayer.stop();
        whenWin.setVisible(false);
        beginTimer();

        songName.setText(ml.returnFN(g));
        media = new Media(ml.returnPath(g).toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
            Answer1.setStyle("-fx-text-fill: black; -fx-background-color: gray");
            Answer2.setStyle("-fx-text-fill: black; -fx-background-color: gray");
            Answer3.setStyle("-fx-text-fill: black; -fx-background-color: gray");
            Answer4.setStyle("-fx-text-fill: black; -fx-background-color: gray");


            Random rndButton = new Random();
            int i = rndButton.nextInt(3);
            switch(i)
            {
                case(0):
                {
                    Answer2.setText(ml.returnFN(g));

                    Random random = new Random();
                    Answer3.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    Answer4.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    Answer1.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    while (Answer2.getText().equals(Answer3.getText()) || Answer2.getText().equals(Answer4.getText()) || Answer2.getText().equals(Answer1.getText()))
                    {
                        Answer3.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                        Answer4.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                        Answer1.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    }
                    break;

                }
                case(1):
                {
                    Answer3.setText(ml.returnFN(g));


                    Random random = new Random();
                    Answer2.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    Answer4.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    Answer1.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    while (Answer3.getText().equals(Answer2.getText()) || Answer3.getText().equals(Answer4.getText()) || Answer3.getText().equals(Answer1.getText()))
                    {
                        Answer2.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                        Answer4.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                        Answer1.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    }
                    break;

                }
                case(2):
                {
                    Answer1.setText(ml.returnFN(g));

                    Random random = new Random();
                    Answer2.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    Answer4.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    Answer3.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    while (Answer1.getText().equals(Answer2.getText()) || Answer1.getText().equals(Answer4.getText()) || Answer1.getText().equals(Answer3.getText()))
                    {
                        Answer2.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                        Answer4.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                        Answer3.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    }
                    break;

                }
                case(3):
                {
                    Answer4.setText(ml.returnFN(g));

                    Random random = new Random();
                    Answer2.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    Answer3.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    Answer1.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    while (Answer4.getText().equals(Answer2.getText()) || Answer4.getText().equals(Answer3.getText()) || Answer4.getText().equals(Answer1.getText()))
                    {
                        Answer2.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                        Answer3.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                        Answer1.setText(ml.list.get(random.nextInt(ml.list.size())).getFullName());
                    }
                    break;

                }
            }
            Answer2.setVisible(true);
            Answer3.setVisible(true);
            Answer4.setVisible(true);
        songName.setVisible(false);
            Answer1.setVisible(true);
        }

    @FXML
    void nextSong(ActionEvent event) {
        pauseMedia.setDisable(false);
        ResumeMedia.setDisable(false);
        volumeSLider.setDisable(false);
        nextMedia();

    }

    @FXML
    void pauseSong(ActionEvent event) {
        cancelTimer();
    mediaPlayer.pause();
    }

    @FXML
    void resumeSong(ActionEvent event) {
        beginTimer();
        mediaPlayer.play();
    }
    public void beginTimer() throws NullPointerException {

        try {
            timer = new Timer();

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    running = true;
                    double current = mediaPlayer.getCurrentTime().toSeconds();
                    double end = duration;
                    progressBar.setProgress(current / end);
                    if (current /end ==1) {
                        cancelTimer();
                    }
                }

            };
            timer.scheduleAtFixedRate(timerTask, 100, 100);
        }catch(NullPointerException e)
        {
          timerTask.run();
        }
    }

    public void cancelTimer(){
        running = false;
        timer.cancel();
    }
    @FXML
    public void newGame(ActionEvent event) throws IOException {
        dbc = new dbConnection();
        player = new Player(Integer.parseInt(fpScore.getText()),FP.getText(), newGameController.player.getDifficult(), newGameController.player.getStyle() );
        try {
            dbc.playerInsert(player);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        mediaPlayer.stop();
        btnWhenLose.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("../Design/newGame.fxml"));

        loader.load();
        Parent root=loader.getRoot();

        Stage stage = new Stage();
        stage.setTitle("Рейтинг игроков");
        stage.setScene(new Scene(root));
        stage.show();
    }

}