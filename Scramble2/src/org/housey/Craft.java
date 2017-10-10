package org.housey;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.housey.Sound;

/**
 * Represents the players Craft in the game
 * Extends the Sprite object, so has access to all the methods Sprite does 
 *
 * @author Paul House
 * @see Sprite
 * @version 1.0
 */
public class Craft extends Sprite implements Common {

	// Internal Fields
   	
	/** Speed of players craft x movement in pixels per game cycle */
	private int dx;

	/** Speed of players craft y movement in pixels per game cycle */
	private int dy;
	
	/** List of Missile objects belonging to the craft sprite */
	private ArrayList<Missile> missiles;

	/** List of Bomb objects belonging to the craft sprite */
	private ArrayList<Bomb> bombs;
	
	/** Current animation frame displayed for Craft  */
	private int currentFrame = 0;
	
	/** Total number of frames in craft animation  */
	private static final int CRAFT_TOTAL_FRAMES = 5;
	
	/** Total number of active Bomb objects allowed at the same time */
	private int maxBombs = 2;
	
	/** Craft Max Fuel tank level */
	private double maxFuel = 128;
	
	/** Craft current Fuel tank level */
	private double currentFuel;
	
	/**
	 * Creates a new Craft object at the coordinates passed in.
	 * 
	 * @param x the int initial x coordinate of the craft
	 * @param y the int initial y coordinate of the craft
	 */
	public Craft(int x, int y) {
		super(x, y);
		initCraft();
	}

	/**
	 * Initialize the players craft  
	 */
	private void initCraft() {
		currentFuel = maxFuel;
		missiles = new ArrayList<Missile>();
		bombs = new ArrayList<Bomb>();
		loadImage("src/resources/ship.png");
		getImageDimensions();
	}

	/**
	 * Move the craft within the game board
	 */
	public void move() {
		// craft falls to ground if no fuel left
		if(currentFuel > 0){
			x += dx;
			y += dy;			
		} else {
			y +=1; 
			
		}
		
		// keep craft x and y within game board and borders
        if (x < 1) {
            x = 1;
        }
        if (y < BORDER_HEIGHT+1) {
            y = BORDER_HEIGHT+1;
        }
	}

	/**
	 * Return any missile objects that the craft has created (fired)
	 * 
	 * @return the Missile objects belonging to the craft
	 */
	public ArrayList<Missile> getMissiles() {
		return missiles;
	}

	/**
	 * Return any bomb objects that the craft has created (dropped)
	 * 
	 * @return the Bomb objects belonging to the craft
	 */
	public ArrayList<Bomb> getBombs() {
		return bombs;
	}
	
	/**
	 * Capture Keys being pressed and determine action to take 
	 * @param e the KeyEvent from user input
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// If we press the Space key, we fire.
		if (key == KeyEvent.VK_SPACE) {
			fireMissile();
		}
		// If we press the Control key, we drop a bomb.
		if (key == KeyEvent.VK_CONTROL) {
			dropBomb();
		}
		// Set up left, right, up and down
		if (key == KeyEvent.VK_LEFT) {
			dx = -1;
		}
		if (key == KeyEvent.VK_RIGHT) {
			dx = 1;
		}
		if (key == KeyEvent.VK_UP) {
			dy = -1;
		}
		if (key == KeyEvent.VK_DOWN) {
			dy = 1;
		}
	}

	/**
	 * Create a new Missile object and add to the missiles ArrayList. Missile
	 * object is retained in the list until it collides with an alien or goes
	 * out of the window
	 */
	public void fireMissile() {
		missiles.add(new Missile(x + width, y + height / 2));
		Sound.SHOOT.play();		
	}

	/**
	 * Create a new Bomb object and add to the bombs ArrayList. Bomb object is
	 * retained in the list until it collides with a destroyable sprite or goes
	 * out of the window. THe maximum number of concurrent bomb objects allowed
	 * is configurable.
	 */
	public void dropBomb() {
		if (bombs.size()< maxBombs){
			bombs.add(new Bomb(x + width / 2, y));
			Sound.BOMB.play();
		}						
	}
	
	/**
	 * Capture Keys being released and determine action to take
	 * 
	 * @param e the KeyEvent from user input
	 */
	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}


	@Override
	public Image getImage() {
		// determine frame to display counter (0 to 4)
		// TODO: use key pressed to set correct anim frame rather than cycle through
		currentFrame = ++currentFrame % CRAFT_TOTAL_FRAMES;
		loadSubImage("src/resources/shipsheet.png", currentFrame);
		getImageDimensions();	
		return image;
	}

	public double getCurrentFuel() {
		return currentFuel;
	}

	public void setCurrentFuel(double currentFuel) {
		this.currentFuel = currentFuel;
	}

	public double getMaxFuel() {
		return maxFuel;
	}

	public void setMaxFuel(double maxFuel) {
		this.maxFuel = maxFuel;
	}

	
}