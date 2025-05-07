package pt.ipbeja.estig.po2.snowman.gui.app.ui;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {

    private Clip clip;

    public void playMusic(String resourcePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    getClass().getResource(resourcePath)
            );
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
