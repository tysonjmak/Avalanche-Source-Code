package com.tysonmak.audio;


import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class AudioPlayer {

    private Clip clip;

    public AudioPlayer(String s) {
        try {
            BufferedInputStream source = new BufferedInputStream(getClass().getResourceAsStream(s));
            AudioInputStream ais = AudioSystem.getAudioInputStream(source);

            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);

            clip = AudioSystem.getClip();
            clip.open(dais);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if(clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        if(clip.isRunning()) clip.stop();
    }

    public void resume() {
        if(!clip.isRunning()) clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void close() {
        stop();
        clip.close();
    }

    public void setVolume(float newVolume) {
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(newVolume);
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }
}
