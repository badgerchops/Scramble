package org.housey;

import javafx.scene.media.AudioClip;

/**
 * This enum encapsulates all the sound effects of a game. 
 * To play invoke Sound.SOUND_NAME.play()
 * Pre-load the sound files to avoid delays whilst they load
 * Setting cycleCount to INDEFINITE should make it loop (not working)
 */
public enum Sound {
	MUSIC("/resources/music.wav", 999), // background music
	SHOOT("/resources/photon.wav", 1), 	// missile fired
	BOMB("/resources/bomb.wav", 1);		// bomb dropped

	// Each sound effect has its own clip, loaded with its own sound file.
	private AudioClip clip;
		
	// Constructor to construct each element of the enum with its own sound file.
	Sound(String soundFileName, int cycleC) {
		try {	
			clip = new AudioClip(this.getClass().getResource(soundFileName).toExternalForm());
			clip.setCycleCount(cycleC);
		} catch (NullPointerException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();			
		} 
	}

	// Play sound effect
	public void play() {
			clip.play();
	}

	// Static method to pre-load all the sound files.
	static void init() {
		values(); // calls the constructor for all the elements
	}
}