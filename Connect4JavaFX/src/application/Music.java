package application;

import javax.sound.sampled.*;

/**
 * 
 * @author Yassr Shaar - 14328571
 * This class focuses on creating functions that handle music throughout the game
 * 
 */
public class Music {
	
	private Clip audioClip;
    private String musicFile;
    private boolean musicOn;
	
    /**
    * Initiates the audioClip
    *
    * @param file is a String for the location of the music file, along with a boolean to determine
    * @param musicOn requests the current status of the music on or off
    * if the music clip is playing
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
   
   /**
    * Empty constructor
    */
   public Music() {
       
   }


  /**
   * Creates a audioClip that can play as many times as specified by the repeat parameter 
   * @param repeat how many times to play the song
   */
   public void play(int repeat) {
       Clip playClip;
       
       for(int i = 0; i < repeat; i++) {
    	   try {
               AudioInputStream ais = AudioSystem.getAudioInputStream(Music.class.getResource(musicFile));
               playClip = AudioSystem.getClip();
               playClip.open(ais);
               playClip.loop(i);
               playClip.start();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       
   }


   /**
    * Stops the audioClip and sets musicOn to false
    */
   public void stop() {
	 musicOn = false;
     audioClip.stop();
   }

   /**
    * Loops the audioClip and plays it continuously until told to stop
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