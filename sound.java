import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.sound.sampled.*;
import java.io.*;

class Sound {
  private Clip clip;
  private AudioInputStream is;
  private long last_play = 0;

  public Sound(String name) {
    try {
      clip = AudioSystem.getClip( );
      is = AudioSystem.getAudioInputStream(new File(name));
      clip.open(is);
    } catch(Exception e) {
    }
  }

  public void play( ) {
    long now = new Date( ).getTime( );
    if((now - last_play) < 250) {
      return;
    }
    try {
      clip.stop( );
      clip.setFramePosition(0);
      clip.start( );
      last_play = now;
    } catch(Exception e) {
    }
  }
}
