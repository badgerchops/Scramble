package org.housey;

import java.awt.Image;

/**
 * Rocket sprite
 */
public class Rocket extends Sprite implements Common {
	
	// Internal Fields
   	
	/** Has the rocket been launched or not */
	protected boolean launched;

	/**
	 * Creates a new Rocket object at the coordinates passed in.
	 * 
	 * @param x the int initial x coordinate of the alien
	 * @param y the int initial y coordinate of the alien
	 */
	public Rocket(int x, int y) {
		super(x, y);
		initRocket();
	}

	/**
	 * Initialize the rocket  
	 */
	private void initRocket() {
		loadImage("src/resources/rocket.png");
		getImageDimensions();
		launched = false;	// rockets should only be launched when the players craft is near
	}
	
	/**
	 * Move the rocket within the game board.
	 * Rockets move straight up after being launched
	 */
	public void move() {
		x -= SCROLL_SPEED;	// scroll left so they appear stationary relative to the world
		if (launched) {
			y -= ROCKET_SPEED;	// rockets vertical speed.
		}	
	}
	
	@Override
	public Image getImage() {
		// determine frame to display counter (0 to 3)
		// static until rocket launch 
		if (launched) {
			currentFrame = ++currentFrame % ROCKET_TOTAL_FRAMES;
		} else {
			currentFrame = 0;
		}
		loadSubImage("src/resources/rocket-sheet.png", currentFrame);
		getImageDimensions();
		
		// update frame count so we can track when to remove when rendering complete
		setCurrentFrame(currentFrame);
				
		return image;
	}
		
	public boolean isLaunched() {
		return launched;
	}

	public void setLaunched(Boolean launch) {
		launched = launch;
	}
	
}
