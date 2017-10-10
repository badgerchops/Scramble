package org.housey;

/**
 * Java "interfaces" only contain Fields and Method signatures.<br>
 * Method signatures:<br>
 * - The name, parameters and exceptions of the method<br>
 * - Cannot include the code to implement the method<br>
 * Supports common methods that multiple classes can implement<br>
 * Classes can implement multiple interfaces as well so you can structure your interfaces<br> 
 * Different to "extends" as a class can only extend one class	  
 */
public interface Common {

	/** Height of the top border in pixels */
	public static final int BORDER_HEIGHT = 32;
	
	/** Width of the Game board in pixels */
	public static final int BOARD_WIDTH = 512;
	
	/** Height of the Game board in pixels - 32 tiles *12 rows + 32 border + 32 footer */
	public static final int BOARD_HEIGHT = 448;
	
	/** Width of the Game world levels in pixels - 85 tiles * 32 pixels */
	public static final int GAME_WORLD_WIDTH = 2720;
	
	/** Speed the game world scrolls in pixels per refresh */
	public static final int SCROLL_SPEED = 1;
	
	/** Speed that aliens travel from right to left in pixels per refresh */
	public static final int ALIEN_SPEED = 3;

	/** Speed that meteors travel from right to left in pixels per refresh */
	public static final int METEOR_SPEED = 4;
	
	/** Speed the crafts missiles travel from left to right in pixels per refresh */
	public static final int MISSILE_SPEED = 5;
	
	/** Speed the rockets travel from the ground upwards in pixels per refresh */
	public static final int ROCKET_SPEED = 3;
	
	/** Horizontal distance between craft and rockets that triggers rockets to launch in pixels */
	public static final int ROCKET_LAUNCH_PROXIMITY = 60;
	
	/** Speed the bomb travels from the players craft forwards in pixels per refresh */
	public static final int BOMB_HORIZONTAL_SPEED  = 3;
	    
	/** Speed the bomb falls from the players craft towards the ground in pixels per refresh */
	public static final int BOMB_VERTICAL_SPEED = 4;
		
	/** Initial player craft x location */
	public static final int ICRAFT_X = 32;
	
	/** Initial player craft y location */
	public static final int ICRAFT_Y = 192;
	
	/**
	 * Desired delay between frames in milliseconds A GAME_DELAY of 20
	 * milliseconds corresponds to 50 frames per second (1000/20)
	 */
	public static final int GAME_DELAY = 20;
	
	/** Width of the Game world Tiles in pixels */
	public static final int TILE_WIDTH = 32;
	
	/** Height of the Game world Tiles in pixels */
	public static final int TILE_HEIGHT = 32;
	
	/** Total number of frames in explosion animation  */
	public static final int EXPLOSION_TOTAL_FRAMES = 14;
	
	/** Total number of frames in meteor animation  */
	public static final int METEOR_TOTAL_FRAMES = 4;
	
	/** Total number of frames in rocket animation  */
	public static final int ROCKET_TOTAL_FRAMES = 3;
	
	/** Total number of frames in bomb animation  */
	public static final int BOMB_TOTAL_FRAMES = 8;
	
}