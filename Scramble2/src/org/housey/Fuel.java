package org.housey;

/**
 * Fuel sprite
 */
public class Fuel extends Sprite implements Common {

	/**
	 * Creates a new Fuel tank object at the coordinates passed in.
	 * 
	 * @param x the int initial x coordinate of the fuel tank
	 * @param y the int initial y coordinate of the tank
	 */
	public Fuel(int x, int y) {
		super(x, y);
		initFuel();
	}

	/**
	 * Initialise the tripod  
	 */
	private void initFuel() {
		loadImage("src/resources/fuel.png");
		getImageDimensions();		
	}

	/**
	 * Move the Fuel tank within the game board. 
	 * Scroll left so they remain stationary relative to the world
	 */
	public void move() {
		x -= SCROLL_SPEED;
	}
	
}
