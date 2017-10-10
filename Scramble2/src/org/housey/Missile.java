package org.housey;

/**
 * Missile sprite
 */
public class Missile extends Sprite implements Common {

	/**
	 * Creates a new Missile object at the coordinates passed in.
	 * 
	 * @param x the int initial x coordinate of the missile
	 * @param y the int initial y coordinate of the missile
	 */
	public Missile(int x, int y) {
		super(x, y);
		initMissile();
	}

	/**
	 * Initialise a missile fired by the players craft  
	 */
	private void initMissile() {
		loadImage("src/resources/missile.png");
		getImageDimensions();
	}

	/**
	 * Move the missile within the game board. The missile is made invisible
	 * when it reaches the right border of the game board and is subsequently
	 * deleted.
	 */	
	public void move() {
		x += MISSILE_SPEED;
		if (x > BOARD_WIDTH) {
			vis = false;
		}
	}
}
