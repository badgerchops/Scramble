package org.housey;

import java.awt.Image;

/**
 * Explosion sprite
 */
public class Explosion extends Sprite implements Common {
	
	// Internal Fields
   	
	/** Value of score displayed after the explosion */
	protected int score;
	
	/** Number of frames the score is displayed for after the explosion */
	protected int explosionDuration = 50;	// 50 frames equates to 1 second at desired fps 
	

	/**
	 * Creates a new Explosion object at the coordinates passed in.
	 * 
	 * @param x the int initial x coordinate of the Explosion
	 * @param y the int initial y coordinate of the Explosion
	 */
	public Explosion(int x, int y) {
		super(x, y);
		initExplosion("src/resources/explosion.png");
		score = 0;	// assign default. means no score will be displayed on screen
	}

	/**
	 * Creates a new Explosion object with a defined score at the coordinates
	 * passed in.
	 * 
	 * @param x
	 *            the int initial x coordinate of the Explosion
	 * @param y
	 *            the int initial y coordinate of the Explosion
	 * @param randomScore
	 *            the int representing the score
	 */
	public Explosion(int x, int y, int randomScore) {
		super(x, y);
		initExplosion("src/resources/explosion-with-score.png");
		score = randomScore;
	}

	
	
	/**
	 * Initialise the explosion
	 *    
	 * @param image
	 *            the String representing the image location  
	 */
	private void initExplosion(String image) {
		loadSubImage(image, 0);
		getImageDimensions();
	}

	/**
	 * Move the explosion within the game board at the same speed as the board
	 * scrolls. The explosion is made invisible after its frames are all
	 * displayed or its duration expires and is then subsequently deleted.
	 */
	public void move() {
		x -= SCROLL_SPEED;
	//	if (currentFrame >= EXPLOSION_TOTAL_FRAMES-1) {
	//		vis = false;
	//	}
	
		// make invisible after set duration
		if (currentFrame >= explosionDuration) {
			vis = false;
		}
		
	}
	
	@Override
	/**
	 * Return correct image to display explosion animation
	 * Display a score for set duration after the standard animation if relevant 
	 */
	public Image getImage() {
		
		// display common animation
		if (currentFrame <= EXPLOSION_TOTAL_FRAMES-1){
			loadSubImage("src/resources/explosion-with-score.png", currentFrame);			
		} else {
			// display score if relevant
			if (score > 0) {
				switch(score) {
		        case 100:
		        	loadSubImage("src/resources/explosion-with-score.png", 14);	// hard coded to 100 image
		        	break;
		        case 200:
		        	loadSubImage("src/resources/explosion-with-score.png", 15);	// hard coded to 200 image
		        	break;
		        case 300:
		        	loadSubImage("src/resources/explosion-with-score.png", 16);	// hard coded to 300 image
		        	break;
		        default:
		        	loadSubImage("src/resources/explosion-with-score.png", 13);	// hard coded to empty image
		        	break;
				};
			}
		}			
		getImageDimensions();
		currentFrame++;
						
		return image;
	}
	
	/*
	@Override
	public Image getImage() {
		// determine frame to display counter (0 to 13)
		currentFrame = ++currentFrame % EXPLOSION_TOTAL_FRAMES;
		loadSubImage("src/resources/explosion-with-score.png", currentFrame);
		getImageDimensions();
		
		// update frame count so we can track when to remove when rendering complete
		setCurrentFrame(currentFrame);
				
		return image;
	}
	*/
	
}
