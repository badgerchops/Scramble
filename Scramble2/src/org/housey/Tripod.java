package org.housey;

/**
 * Tripod sprite
 */
public class Tripod extends Sprite implements Common {

	/**
	 * Creates a new Tripod object at the coordinates passed in.
	 * 
	 * @param x the int initial x coordinate of the alien
	 * @param y the int initial y coordinate of the alien
	 */
	public Tripod(int x, int y) {
		super(x, y);
		initTripod();
	}

	/**
	 * Initialise the tripod  
	 */
	private void initTripod() {
		loadImage("src/resources/tripod.png");
		getImageDimensions();		
	}

	/**
	 * Move the Tripod within the game board. 
	 * Scroll left so they remain stationary relative to the world
	 */
	public void move() {
		x -= SCROLL_SPEED;
	}
	
}
