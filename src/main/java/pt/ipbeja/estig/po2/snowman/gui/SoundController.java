package pt.ipbeja.estig.po2.snowman.gui;

import javax.sound.sampled.*;
import java.io.IOException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

public class SoundController {

    private static Clip currentClip;

    /**
     * Plays a music clip from the specified resource path.
     * Stops any currently playing music before starting the new one.
     * The clip will loop continuously until stopped.
     *
     * @param resourcePath the path to the audio resource inside the JAR or classpath
     */
    public static void playMusic(String resourcePath) {
        stopMusic(); // parar música anterior, se estiver a tocar
        try {
            AudioInputStream audioStream = getAudioInputStream(
                    SoundController.class.getResource(resourcePath)
            );
            currentClip = AudioSystem.getClip();
            currentClip.open(audioStream);
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
            currentClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the currently playing music clip, if any.
     * Releases resources associated with the clip.
     */
    public static void stopMusic() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }

}
