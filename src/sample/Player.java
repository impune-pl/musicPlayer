package sample;

import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Player implements Runnable
{
    private MediaPlayer mediaPlayer;

    private Media nowPlaying;

    private ProgressBar progressBar;

    public Player(ProgressBar progressBar)
    {
        this.progressBar = progressBar;

        progressBar.progressProperty().bind();
    }

    @Override
    public void run()
    {

    }

    public synchronized void changeTranck(File file)
    {
        this.nowPlaying = new Media(file.toURI().toString());
        this.mediaPlayer = new MediaPlayer(this.nowPlaying);
    }

    public synchronized void play()
    {
        this.mediaPlayer.play();
    }

    public synchronized void stop()
    {
        this.mediaPlayer.stop();
    }

    public synchronized void pause()
    {
        if (this.mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED))
            this.mediaPlayer.pause();
        else
            this.mediaPlayer.play();
    }

    private void setMediaPlayer(MediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        this.mediaPlayer.currentTimeProperty().addListener(event -> {
        });
    }
}
