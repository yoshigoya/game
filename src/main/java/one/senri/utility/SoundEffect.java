package one.senri.utility;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundEffect implements Runnable {
  private static final Logger logger = LogManager.getLogger(SoundEffect.class);
  private Thread thread;
  private boolean loop;

  public static enum Volume { MUTE, LOW, MEDIUM, HIGH }
  private Volume volume;
  
  public SoundEffect(String url, Volume volume) {
    this(url);
    this.volume = volume;
    loop = false;
  }

  public SoundEffect(String url) {
    try {
      this.clip = AudioSystem.getClip();
      this.inputStream = 
        AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(url));
      this.clip.open(inputStream);
    }
    catch (UnsupportedAudioFileException e) {
      logger.debug(e.getMessage());
    }
    catch (LineUnavailableException e) {
      logger.debug(e.getMessage());
    }
    catch (IOException e) {
      logger.debug(e.getMessage());
    }
    this.inputStream = null;
    this.thread = new Thread(this);
  }

  public void play(boolean loop) {
    if (this.volume != Volume.MUTE) {
      // this.thread.start();
      if (clip.isRunning()) {
        clip.stop();
      }
      clip.setMicrosecondPosition(0);
      if (loop) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
      }
      clip.start();
   
    }
  }

  private AudioInputStream inputStream;
  private Clip clip;

  @Override
  public void run() {
 }

  public void disposeSoundEffect() {
    if (this.clip != null) {
      this.clip.stop();
      this.clip.close();
    }
    this.clip = null;
    this.inputStream = null;
  }
}
