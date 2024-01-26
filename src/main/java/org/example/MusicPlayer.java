package org.example;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * this class gives the chance to run the app with independent music
 */
public class MusicPlayer implements Runnable{
    @Override
    public void run() {
        File file = new File("countdown.wav");
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
