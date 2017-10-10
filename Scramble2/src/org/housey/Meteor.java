package org.housey;

import java.awt.Image;

/**
 * This class represents a Meteor within the game.<br>
 * Meteors travel across the game world right to left and reappear on the right
 * side after they have disappeared on the left.<br>
 * Meteors only reappear within the level they were created in.<br>
 * The class extends the Sprite object.
 */
public class Meteor extends Sprite implements Common {

	/**
	 * Creates a new Meteor object at the coordinates passed in.
	 * 
	 * @param x the int initial x coordinate of the meteor
	 * @param y the int initial y coordinate of the meteor
	 */
	public Meteor(int x, int y) {
		super(x, y);
		initMeteor();
	}

	/**
	 * Initialize the meteor  
	 */
	private void initMeteor() {
		loadImage("src/resources/meteor.png");
		getImageDimensions();	
		destroyable = false;	// meteors won't be destroyed in a collision with the players craft
	}

	/**
	 * Move the meteor within the game board. Meteors reappear on the right
	 * after they have disappeared on the left.<br>
	 */
	public void move() {
	
		//Meteor made invisible when it reaches the left border of the game board 
		//if (x < 0) {
		//	vis = false;
		//}
		
		/*
		 * Meteors return to the game world on the right side after they have
		 * disappeared on the left. The meteors array is reset for each level,
		 * so meteors only reappear within the level the were created
		 */
		if (x < 0) {
			x = GAME_WORLD_WIDTH;
		}
		
		x -= METEOR_SPEED;
	}

	@Override
	public Image getImage() {
		// determine frame to display counter (0 to 3)
		currentFrame = ++currentFrame % METEOR_TOTAL_FRAMES;
		loadSubImage("src/resources/meteor-sheet.png", currentFrame);
		getImageDimensions();
		
		// update frame count so we can track when to remove when rendering complete
		setCurrentFrame(currentFrame);
				
		return image;
	}
	
}
