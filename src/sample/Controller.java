package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

public class Controller
{
    private Player playerThread;

    private Scene mainScene;

    @FXML
    private Label trackTitleLabel;

    @FXML
    private Slider trackProgressSlider;

    @FXML
    private Button playButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button chooseTrackButton;

    @FXML
    private Slider volumeSlider;

    @FXML
    void initialize()
    {
        this.playerThread = new Player(trackProgressSlider, trackTitleLabel, volumeSlider.getValue());
        playerThread.run();

        this.playButton.setOnMouseClicked(event -> playerThread.play());

        this.pauseButton.setOnMouseClicked(event -> playerThread.pause());

        this.stopButton.setOnMouseClicked(event -> playerThread.stop());

        this.chooseTrackButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose track");
            ArrayList<String> extensionList = new ArrayList<>();
            extensionList.add("mp3");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Tracks", extensionList));
            File file = fileChooser.showOpenDialog(mainScene.getWindow());
            if (file != null)
            {
                playerThread.changeTrack(file);
                this.playButton.setDisable(false);
                this.pauseButton.setDisable(false);
                this.stopButton.setDisable(false);
                this.volumeSlider.setDisable(false);
            }
        });

        this.volumeSlider.valueProperty()
                         .addListener(
                                 (observable, oldvalue, newvalue) -> this.playerThread.setVolume(
                                         (Double) newvalue / 100)
                         );

        this.trackProgressSlider.valueProperty()
                                .addListener(event -> {
                                    if (trackProgressSlider.isPressed())
                                    {
                                        playerThread.seek(trackProgressSlider.getValue());
                                    }
                                });

        this.playButton.setDisable(true);
        this.pauseButton.setDisable(true);
        this.stopButton.setDisable(true);
        this.volumeSlider.setDisable(true);
    }

    @FXML
    public void exitApplication(ActionEvent event)
    {
        playerThread.kill();
        Platform.exit();
    }

    public void setMainScene(Scene mainScene)
    {
        this.mainScene = mainScene;
    }
}
