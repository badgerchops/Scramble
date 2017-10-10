package org.housey;

import java.awt.Image;

/**
 * Bomb sprite
 */
public class Bomb extends Sprite implements Common {

	/**
	 * Creates a new Bomb object at the coordinates passed in.
	 * 
	 * @param x the int initial x coordinate of the bomb
	 * @param y the int initial y coordinate of the bomb
	 */
	public Bomb(int x, int y) {
		super(x, y);
		initBomb();
	}

	/**
	 * Initialise a bomb dropped by the players craft  
	 */
	private void initBomb() {
		loadImage("src/resources/bomb.png");
		getImageDimensions();
	}

	/**
	 * Move the bomb within the game board. The bomb is made invisible
	 * when it reaches the ground and is subsequently deleted.
	 */	
	public void move() {
		x += BOMB_HORIZONTAL_SPEED;
		y += BOMB_VERTICAL_SPEED;
		if (y > BOARD_HEIGHT) {
			vis = false;
		}
	}
	
	@Override
	public Image getImage() {
		// determine frame to display counter (0 to 5)
		// bomb should run through frames then stick with last frame
		if (currentFrame != BOMB_TOTAL_FRAMES-1) {
			currentFrame = ++currentFrame % BOMB_TOTAL_FRAMES;
		}		
		loadSubImage("src/resources/bomb-sheet.png", currentFrame, 21);	// 21 pixel wide bomb
		getImageDimensions();  
		
		// update frame count so we can track when to remove when rendering complete
		setCurrentFrame(currentFrame);
				
		return image;
	}
}
