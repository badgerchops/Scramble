package org.housey;

import java.util.Random;

/**
 * This class represents an Alien space craft within the game.<br>
 * The aliens oscillate around a fixed point on the game board in a figure of eight path.<br> 
 * The class extends the Sprite object.
 */
public class Alien extends Sprite implements Common {
	
	// Instance Variables / Fields
	
	/** Counter for current cycle in world */
	int cycles = 0;

	/** original x location of alien */
	int baseX; 
	
	/** original y location of alien */
	int baseY; 

	/** A "fudge" to vary time for alien to oscillate in complete figure of eight path */
	int oscillationConstant = 200;
		
	/** x movement relative to original x location of alien for this cycle*/
	double dX; 
	
	/** y movement relative to original y location of alien for this cycle*/
	double dY; 
	
	Random rand = new Random();
			
	/** the time in milliseconds when the alien was initiated */
	double initTime;
	
	/** the time in milliseconds since the alien was initiated */
	double timeElapsed;
	
	/** random SHM phase offset for alien */
	double randomTimeOffet;
	
	/**
	 * Creates a new Alien object at the coordinates passed in.
	 * 
	 * @param x the int initial x coordinate of the alien
	 * @param y the int initial y coordinate of the alien
	 */
	public Alien(int x, int y) {
		super(x, y);
		// Store original x,y location of sprite as alien oscillates around this point
		baseX = x;
		baseY = y;	
		initAlien();
	}

	/**
	 * Initialise the alien.  
	 */
	private void initAlien() {
		loadImage("src/resources/alien.png");
		getImageDimensions();
		initTime = System.currentTimeMillis();
		randomTimeOffet = 2*Math.PI/(rand.nextInt(9)+1);		
	}
	
	/**
	 * Move the alien within the game board.<br>
	 * Aliens move in a figure of eight movement about their initial location<br>
	 * Variation on simple harmonic motion were dx and dy are the displacements from equilibrium position (0,0)<br>
	 * A complete cycle will see sin(x) move from -1 to + 1 (equivalent to 2 Pi radians)<br> 
	 */	 
	public void move() {		
		
		timeElapsed = (System.currentTimeMillis() - initTime);
		
		// reset tracking counters when alien moves off screen left
		if (x < 0) {
			x = GAME_WORLD_WIDTH;  
			cycles = 0;
		}
		
		dX = ( (Math.cos(Math.toRadians(90)) * Math.cos(timeElapsed/oscillationConstant+randomTimeOffet) ) - (Math.sin(Math.toRadians(90)) * Math.sin(2 * timeElapsed/oscillationConstant+randomTimeOffet)/2 )) * 0.5 * TILE_WIDTH;
		dY = ( (Math.sin(Math.toRadians(90)) * Math.cos(timeElapsed/oscillationConstant+randomTimeOffet) ) + (Math.cos(Math.toRadians(90)) * Math.sin(2 * timeElapsed/oscillationConstant+randomTimeOffet)/2 )) * 1.5 * TILE_HEIGHT;
				
		cycles++;	
		
		// calculate x,y coordinates
		x = (baseX + (int) dX) - (cycles * SCROLL_SPEED);
		y = baseY + (int) dY; 			

	}
}
