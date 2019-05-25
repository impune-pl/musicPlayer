package SimpleMusicPlayer;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Player implements Runnable
{
    private MediaPlayer mediaPlayer;

    private Media nowPlaying;

    private Slider progressSlider;

    private Label statusTextLabel;

    private Double volume;

    public Player(Slider progressSlider, Label statusTextLabel, Double volume)
    {
        this.progressSlider = progressSlider;
        this.statusTextLabel = statusTextLabel;
        this.volume = volume / 100;
    }

    @Override
    public void run()
    {
    }

    public synchronized void changeTrack(File file)
    {
        if (file.canRead() && file.isFile() && file.getName()
                                                   .endsWith("mp3"))
        {
            if (this.mediaPlayer != null)
            {
                this.mediaPlayer.stop();
                this.mediaPlayer.dispose();
            }

            this.nowPlaying = new Media(file.toURI()
                                            .toString());
            setMediaPlayer(new MediaPlayer(this.nowPlaying));
            this.mediaPlayer.setVolume(volume);
            setStatusTextLabel(file.getName());
            this.mediaPlayer.setStartTime(Duration.ZERO);
        }
    }

    public synchronized String togglePausePlay()
    {
        if (this.mediaPlayer.getStatus()
                            .equals(MediaPlayer.Status.PLAYING))
        {
            this.mediaPlayer.pause();
            return "Play";
        }
        else
        {
            this.mediaPlayer.play();
            return "Pause";
        }

    }

    public synchronized void stop()
    {
        if (this.mediaPlayer.getStatus()
                            .equals(MediaPlayer.Status.PAUSED) ||
                this.mediaPlayer.getStatus()
                                .equals(MediaPlayer.Status.PLAYING))
        {
            this.mediaPlayer.stop();
        }
    }

    public void setVolume(Double volume)
    {
        if (this.mediaPlayer != null)
        {
            this.mediaPlayer.setVolume(volume);
        }
        this.volume = volume;
    }

    public void seek(Double percentage)
    {
        mediaPlayer.pause();
        mediaPlayer.seek(mediaPlayer.getTotalDuration()
                                    .multiply(percentage / 100));
        mediaPlayer.play();
    }

    public void kill()
    {
        this.mediaPlayer.stop();
        this.mediaPlayer.dispose();
    }

    private void setStatusTextLabel(String filename)
    {
        this.statusTextLabel.setText(filename);
    }

    private void setMediaPlayer(final MediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        mediaPlayer.currentTimeProperty()
                   .addListener((event) -> Platform.runLater(
                           () -> progressSlider.setValue((mediaPlayer.getCurrentTime()
                                                                     .toMillis() / mediaPlayer.getTotalDuration()
                                                                                              .toMillis()) * 100)
                   ));
    }
}
