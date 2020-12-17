package application;

import javax.sound.sampled.*;

public class Music {
	
	private Clip audioClip;
    private String musicFile;
    private boolean musicOn;
	
    /*
    * Initiates the audioClip
    *
    * @param file String that contains the url of the sound file.
    */
   public Music(String file, boolean musicOn) {
       try {
           this.musicFile = file;
           this.musicOn = musicOn;
           AudioInputStream ais = AudioSystem.getAudioInputStream(Music.class.getResource(file));
           audioClip = AudioSystem.getClip();
           audioClip.open(ais);
           
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   
   public Music() {
       
   }


   /**
    * Creates a audioClip that only gets played once.
    */
   public void play() {
       Clip onceClip;
       try {
           AudioInputStream ais = AudioSystem.getAudioInputStream(Music.class.getResource(musicFile));
           onceClip = AudioSystem.getClip();
           onceClip.open(ais);
           onceClip.setFramePosition(0);
           onceClip.start();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }


   /**
    * Stops the audioClip
    */
   public void stop() {
	 musicOn = false;
     audioClip.stop();
   }

   /**
    * Loops the audioClip
    */
   public void loop() {
	   try {
           if (audioClip != null) {
               new Thread() {
                   public void run() {
                       synchronized (audioClip) {
                           audioClip.stop();
                           audioClip.setFramePosition(0);
                           audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                       }
                   }
               }.start();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

}