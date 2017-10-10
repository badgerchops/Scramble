package org.housey;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * The Board is a panel where the game takes place.<br>
 * Implements our Common interface to access shared fields / methods used across
 * the game.<br>
 * Implements Java's Runnable interface as thread for animation.
 */
public class Board extends JPanel implements Runnable, Common {
	
	// Instance Variables / Fields
	
	/** Default serial version ID */
	private static final long serialVersionUID = 1L;

	/** Game animation thread */
	private Thread animator;
	
	/** Players craft */
	private Craft craft;
	
	/** List of active aliens in game */
	private ArrayList<Alien> aliens = new ArrayList<>();
	/** Temporary list of aliens prior to to adding to active list */
	private ArrayList<Alien> tempAliens = new ArrayList<>();
	/** Alien starting x coordinate in pixels */	
	private int alienX;
	/** Alien starting y coordinate in pixels */
	private int alienY;
	
	/** List of active meteors in game */
	private ArrayList<Meteor> meteors = new ArrayList<>();
	/** Temporary list of meteors prior to to adding to active list */
	private ArrayList<Meteor> tempMeteors = new ArrayList<>();
	/** Meteor starting x coordinate in pixels */	
	private int meteorX;
	/** Meteor starting y coordinate in pixels */
	private int meteorY;
	
	/** List of active rockets in game */
	private ArrayList<Rocket> rockets = new ArrayList<>();	
	/** Temporary list of rockets prior to to adding to active list */
	private ArrayList<Rocket> tempRockets = new ArrayList<>();
	/** Rocket starting x coordinate in pixels */	
	private int rocketX;
	/** Rocket starting y coordinate in pixels */
	private int rocketY;
	
	/** List of active tripods in game */
	private ArrayList<Tripod> tripods = new ArrayList<>();	
	/** Temporary list of tripods prior to to adding to active list */
	private ArrayList<Tripod> tempTripods = new ArrayList<>();
	/** Tripod starting x coordinate in pixels */	
	private int tripodX;
	/** Tripod starting y coordinate in pixels */
	private int tripodY;
	
	/** List of active fuel tanks in game */
	private ArrayList<Fuel> fuelTanks = new ArrayList<>();	
	/** Temporary list of fuel tanks prior to to adding to active list */
	private ArrayList<Fuel> tempFuelTanks = new ArrayList<>();
	/** Fuel tank starting x coordinate in pixels */	
	private int fuelX;
	/** Fuel tank starting y coordinate in pixels */
	private int fuelY;
	
	/** List of active explosions in game */
	private ArrayList<Explosion> explosions;
	
	/** Game active - in game (true) or game over (false) */
	private boolean ingame;
	
	/** Game state - game won (true) or not (false) */
	private boolean won = false;

	/** High score for current game session */
	private int highScore;
	
	/** The initial x y coordinates of the alien ships. */
	private final int[][] levelZeroAlienCordinates = {{1312,64},{1696,64},{2016,64},{2528,64},{608,96},{1632,96},{1984,96},{2048,96},{1248,128},{2144,128},{2656,128},{448,160},{864,160}};
	private final int[][] levelOneAlienCordinates = {{1440,128},{2496,128},{1536,160},{2624,160},{768,192},{928,192},{1152,192},{1312,192},{2048,192},{448,224},{544,224},{640,224},{2176,224},{2400,224},{1824,256}};
	private final int[][] levelTwoAlienCordinates = {};
	private final int[][] levelThreeAlienCordinates = {};
	private final int[][] levelFourAlienCordinates = {};
	private final int[][] levelFiveAlienCordinates = {};
	
	/** The initial x y coordinates of the meteors. */
	private final int[][] levelZeroMeteorCordinates = {{416,32},{800,32},{224,64},{2624,64},{1184,96},{1856,96}};
	private final int[][] levelOneMeteorCordinates = {{2656,160},{1728,192},{2272,224}};
	private final int[][] levelTwoMeteorCordinates = {{2240,32},{416,64},{736,64},{1120,64},{1440,64},{544,96},{2016,96},{2304,96},{2496,96},{224,128},{864,128},{1248,128},{1824,128},{2656,160},{384,192},{640,192},{992,192},{1568,192},{2144,192},{2432,192}};
	private final int[][] levelThreeMeteorCordinates = {};
	private final int[][] levelFourMeteorCordinates = {};
	private final int[][] levelFiveMeteorCordinates = {};
	
	/** The initial x y coordinates of the rockets. */
	private final int[][] levelZeroRocketCordinates = {{1344,192},{1408,192},{448,256},{480,256},{512,256},{1120,256},{1152,256},{2368,256},{1696,288},{1728,288},{1888,288},{1920,288},{128,320},{896,320},{992,320},{2688,320}};
	private final int[][] levelOneRocketCordinates = {{2080,320},{2112,320},{2176,320},{128,352},{1120,352},{1728,352},{1760,352},{1920,352},{1952,352},{2272,352}};
	private final int[][] levelTwoRocketCordinates = {{800,288},{352,320},{416,320},{1536,320},{1568,320},{1728,320},{128,352},{2336,352},{2368,352}};
	private final int[][] levelThreeRocketCordinates = {{352,128},{416,128},{448,128},{1888,128},{1952,128},{2016,128},{160,160},{480,160},{1056,160},{1120,160},{1216,160},{2080,160},{2144,160},{2240,160},{2304,160},{2368,160},{256,192},{544,192},{576,192},{992,192},{1312,192},{1792,192},{608,224},{928,224},{1664,224},{2592,224},{864,256},{1376,256},{1440,256},{1600,256},{1632,256},{800,288}};
	private final int[][] levelFourRocketCordinates = {{160,96},{224,96},{32,224},{96,224}};
	private final int[][] levelFiveRocketCordinates = {{960,192},{1024,192},{160,224},{224,224},{512,224},{544,224},{576,224},{1184,224},{1248,224},{1696,224},{1760,224},{2048,224},{320,256},{352,256},{96,320},{128,320},{256,320},{288,320},{384,320},{480,320},{608,320},{640,320},{704,320},{736,320},{832,320},{864,320},{896,320},{928,320},{1056,320},{1088,320},{1280,320},{1312,320},{1408,320},{1440,320},{1472,320},{1504,320},{1632,320},{1664,320},{1792,320},{1824,320},{1920,320},{2016,320},{2144,320}};

	/** The initial x y coordinates of the tripods. */
	private final int[][] levelZeroTripodCordinates = {{416,256},{928,320}};
	private final int[][] levelOneTripodCordinates = {{608,352},{1888,352},{2304,352}};
	private final int[][] levelTwoTripodCordinates = {{1344,320},{1376,320},{1632,320},{1664,320},{928,352},{2176,352}};
	private final int[][] levelThreeTripodCordinates = {{704,256},{1504,256},{1568,256}};
	private final int[][] levelFourTripodCordinates = {};
	private final int[][] levelFiveTripodCordinates = {{992,64},{2528,64},{192,128},{800,128},{1216,128},{1728,128},{1536,192},{2496,192},{2560,192},{1120,224},{1152,224},{2080,224},{2112,224},{2656,224},{2688,224},{1344,256},{1376,256},{1856,256},{1888,256},{672,320},{2176,320},{2208,320},{2240,320},{2272,320},{2368,320},{2400,320},{2432,320},{2464,320},{2592,320},{2624,320}};

	/** The initial x y coordinates of the fuel tanks. */
	private final int[][] levelZeroFuelCordinates = {{384,256},{2400,256},{1760,288},{1856,288},{768,320},{832,320}};
	private final int[][] levelOneFuelCordinates = {{32,320},{64,320},{1024,320},{2048,320},{2688,320},{576,352},{1152,352},{1792,352}};
	private final int[][] levelTwoFuelCordinates = {{384,320},{512,320},{1248,320},{1504,320},{1696,320},{960,352},{2016,352},{2304,352},{2464,352}};
	private final int[][] levelThreeFuelCordinates = {{32,128},{64,128},{2176,128},{512,160},{1696,192},{2432,192},{2464,192},{2496,192},{2528,192},{2560,192},{640,224},{736,256},{1536,256}};
	private final int[][] levelFourFuelCordinates = {{352,128},{384,128},{640,128},{672,128},{704,128},{736,128},{64,160},{1088,256},{1120,256},{1152,256},{1184,256},{1216,256},{1408,320},{1440,320},{1472,320},{1504,320},{1984,320},{2016,320},{2048,320},{2080,320},{2112,320}};
	private final int[][] levelFiveFuelCordinates = {{416,96},{448,96},{768,128},{1568,224},{1600,224}};

	/** List of Game world tiles */
	private ArrayList<Tile> tiles = new ArrayList<>();
	/** Temporary list of tiles prior to to adding to active list */
	private ArrayList<Tile> tempTiles = new ArrayList<>();
	/** Game world Tiles x coordinate in pixels */	
	private int tileX;
	/** Game world Tiles y coordinate in pixels */
	private int tileY;
	
	/** Game world Tile row tracker */
	private int rowNumber;
	
	/** Game world Tile column tracker */
	private int colNumber;

	/** The level the player has reached in the Game world */
	private int currentLevel;
	
	/** Game world x location tracker. Increases by one per game cycle */
	private int worldX;	
		
	/**
	 * The Game world consists of multiple levels.<br>
	 * Each level consists of 12 rows of tiles vertically by 85 columns
	 * horizontally across.<br>
	 * Only part of the level (16 tiles) is visible in the screen at any point.
	 */	
	private final int[][] levelZeroTiles = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,2,1,3,0,0,0,0,0,0,0,2,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,7,7,7,1,1,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,2,1,1,1,7,7,7,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,2,1,1,1,3,0,0,0,0,0,2,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,7,7,7,1,1,1,1,1,1,1,1,1,1,3,0,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,1,1,1,7,7,3,0,0,2,7,7,7,7,3,0,0,0},
			{0,0,0,0,0,0,2,1,1,1,1,1,7,7,7,7,7,1,1,1,1,1,3,0,0,0,0,0,0,0,0,0,0,2,7,7,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,0,0,0,2,3,0,0,0,2,1,1,1,1,1,1,1,1,1,1,1,1,7,7,1,1,1,1,1,1,3,0,0},
			{0,0,0,0,0,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,0,0,0,0,0,0,6,0,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,7,1,1,7,7,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,3,0},
			{7,7,7,7,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,7,7,7,7,1,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};

	private final int[][] levelOneTiles = {
			{0,0,0,0,0,0,0,0,0,0,0,4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,8,8,1,1,1,1,1,1,1,1,1,1,8,8,8,5,4,8,8,8,8,8,8,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,5,4,1,1,1,1,1,1,1,1,1,1,1,8,8,1,1,5,4,5},
			{0,0,0,0,0,0,0,0,0,0,0,0,4,1,1,1,1,1,1,1,1,1,1,1,1,5,0,0,4,1,1,1,1,8,1,1,1,5,0,0,0,0,0,0,0,0,0,0,0,4,1,1,1,1,1,1,1,1,1,5,4,1,1,1,5,0,0,4,1,1,1,1,1,8,1,1,1,5,0,0,4,5,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,4,1,1,1,5,4,5,4,5,4,8,5,0,0,0,0,4,8,8,5,0,4,8,5,0,0,0,0,0,0,0,0,0,0,0,0,0,4,1,1,1,1,1,1,1,5,0,0,4,8,5,0,0,0,0,4,1,1,1,5,0,4,8,5,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,8,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,8,1,1,1,1,5,0,0,0,0,0,0,0,0,0,0,0,4,8,5,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,8,8,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0},
			{0,0,0,0,0,0,0,0,2,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,7,7,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,3,0,0,0},
			{0,0,0,0,0,0,0,2,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,2,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,7,7,1,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,1,1,3,0,0},
			{0,0,0,0,0,0,2,1,1,1,1,1,3,0,0,0,0,0,0,0,0,0,0,2,1,1,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,2,7,7,1,1,1,1,1,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,2,7,3,2,1,1,1,1,1,3,0},
			{7,7,7,3,0,2,1,1,1,1,1,1,1,3,2,3,2,3,0,0,2,3,2,1,1,1,1,1,1,1,7,7,7,7,3,0,0,2,7,7,7,1,1,1,1,1,1,1,1,1,1,1,1,3,0,0,0,0,0,0,0,0,0,2,7,7,7,1,7,7,3,0,0,2,1,1,1,1,1,1,1,1,1,1,7},
			{1,1,1,1,7,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,7,3,2,7,7,7,7,1,1,1,1,1,1,1,1,7,7,1,1,1,1,1,1,1,1,1,1,1,1}
		};
	
	private final int[][] levelTwoTiles = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,2,7,7,3,0,0,0,0,0,0,0,0,0,0,2,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,2,1,3,0,0,0,0,0,0,0,0,0,2,1,1,1,1,3,0,0,0,0,0,0,0,0,2,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,1,1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0},
			{0,0,0,0,0,0,2,1,1,1,3,0,0,0,6,6,0,0,2,1,1,1,1,1,1,7,7,3,0,0,0,0,2,1,1,1,1,1,3,0,2,3,0,0,6,2,3,0,0,0,6,0,0,0,0,2,1,1,1,1,1,3,0,0,0,2,3,0,0,0,6,0,0,0,0,0,0,0,0,2,1,3,0,0,2},
			{7,7,7,3,0,2,1,1,1,1,1,7,7,7,1,1,7,7,1,1,1,1,1,1,1,1,1,1,3,0,0,2,1,1,1,1,1,1,1,7,1,1,7,7,1,1,1,7,7,7,1,7,7,7,7,1,1,1,1,1,1,1,3,0,2,1,1,3,0,2,1,3,0,0,0,2,3,0,2,1,1,1,3,2,1},
			{1,1,1,1,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,1,1,1,1,7,1,1,1,7,7,7,1,1,7,1,1,1,1,1,1,1}
		};

	private final int[][] levelThreeTiles = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,10,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,10,0,10,0,10,0,10,0,0,10,0,10,0,10,0,0,0,0,0,0,0,0,0,0,0},
			{0,10,10,10,10,0,10,10,0,10,9,10,9,10,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,9,0,10,10,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,9,9,9,9,9,10,9,0,9,0,10,9,0,9,0,9,0,0,0,0,0,0,0,0,10,0,0},
			{0,9,9,9,9,10,9,9,0,9,9,9,9,9,9,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,9,10,9,10,9,9,10,9,10,0,0,0,0,0,0,0,0,0,0,0,0,0,10,10,0,9,9,9,9,9,9,9,9,10,9,10,9,9,10,9,10,9,10,0,0,0,0,0,0,0,9,9,10},
			{0,9,9,9,9,9,9,9,10,9,9,9,9,9,9,9,9,10,10,0,0,0,0,0,0,0,0,0,10,0,9,10,9,9,9,9,9,9,9,9,9,10,0,0,0,0,0,0,0,0,0,0,0,10,9,9,10,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,10,10,10,10,10,10,0,9,9,9},
			{0,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,10,10,0,0,0,0,0,10,0,9,10,9,9,9,9,9,9,9,9,9,9,9,9,10,0,10,0,0,0,0,0,0,0,10,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,10,9,9,9},
			{12,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,10,10,10,10,0,9,10,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,10,9,10,10,10,10,10,10,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
			{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,10,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
			{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
			{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9}
	};
	
	private final int[][] levelFourTiles = {
			{0,0,0,0,0,0,0,0,0,0,0,9,9,11,11,11,11,11,11,9,9,9,9,9,11,11,11,11,11,11,11,11,11,11,11,11,9,9,9,9,9,9,9,9,9,9,9,9,9,9,11,11,11,11,11,11,11,11,11,11,11,11,9,9,9,9,9,9,9,9,11,11,11,11,11,9,9,9,9,9,9,9,9,9,9},
			{0,0,0,0,0,0,0,0,0,0,0,11,11,0,0,0,0,0,0,11,11,11,11,11,0,0,0,0,0,0,0,0,0,0,0,0,11,9,9,9,9,9,9,9,9,9,9,9,9,9,0,0,0,0,0,0,0,0,0,0,0,0,9,9,9,9,9,9,9,9,0,0,0,0,0,11,11,11,11,11,9,9,11,11,11},
			{0,0,0,0,10,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,9,9,9,9,9,9,9,9,9,9,9,9,0,0,0,0,0,0,0,0,0,0,0,0,9,9,9,11,11,11,11,11,0,0,0,0,0,0,0,0,0,0,9,9,0,0,0},
			{0,0,0,0,9,10,9,10,0,0,0,0,0,0,0,10,10,0,0,0,0,0,0,0,0,0,0,10,10,10,10,0,0,0,0,0,0,9,9,9,9,9,9,9,9,9,9,9,9,9,0,0,0,0,10,10,10,10,0,0,0,0,9,9,9,0,0,0,0,0,0,0,13,14,0,0,0,0,0,0,9,9,0,0,0},
			{0,0,0,0,9,9,9,9,10,10,10,10,10,10,10,9,9,10,10,10,10,10,10,10,10,10,10,9,9,9,9,0,0,0,0,0,0,9,9,9,9,9,9,9,9,9,9,9,9,9,0,0,0,0,9,9,9,9,0,0,0,0,9,9,9,0,0,0,0,0,0,0,9,9,10,10,10,0,0,0,9,9,0,0,0},
			{10,0,10,0,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,0,0,0,0,0,0,11,11,11,11,11,11,9,9,9,9,9,9,9,0,0,0,0,9,9,9,9,0,0,0,0,9,9,9,0,0,0,0,10,10,10,9,9,9,9,9,0,0,0,9,9,0,0,0},
			{9,0,9,0,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,0,0,0,0,0,0,0,0,0,0,0,0,9,9,9,9,9,9,9,0,0,0,0,9,9,9,9,0,0,0,0,9,9,9,0,0,0,0,9,9,9,9,9,9,9,9,0,0,0,9,9,0,0,0},
			{9,10,9,10,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,0,0,0,0,0,0,0,0,0,0,0,0,11,11,11,11,11,11,11,0,0,0,0,9,9,9,9,0,0,0,0,11,11,11,0,0,0,0,9,9,9,9,9,9,9,9,0,0,0,9,9,0,0,0},
			{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,10,10,10,10,10,10,10,10,10,10,0,0,0,0,0,0,0,0,0,0,0,0,0,9,9,9,9,0,0,0,0,0,0,0,0,0,0,0,9,9,9,9,9,9,9,9,0,0,0,11,11,0,0,0},
			{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,0,0,0,0,0,0,0,0,0,0,0,0,0,9,9,9,9,0,0,0,0,0,0,0,0,0,0,0,9,9,9,9,9,9,9,9,0,0,0,0,0,0,0,0},
			{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,10,10,10,10,10,10,10,10,10,10,10,10,10,9,9,9,9,10,10,10,10,10,10,10,10,10,10,10,9,9,9,9,9,9,9,9,0,0,0,0,0,0,0,0},
			{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,10,10,10,10,10,10,10,10},
	};
	
	private final int[][] levelFiveTiles = {
			{9,9,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{11,11,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13,14,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,9,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0},
			{0,0,0,0,0,0,10,0,0,0,0,0,0,9,9,0,0,0,0,0,0,0,0,0,10,10,0,0,0,0,0,9,0,0,0,0,0,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,0,0,0,0,0,9,9,0,0,0,0,0,0,0,0,0,10,10,0,0,0,0,0,9,0,0,0,0,0},
			{0,0,0,0,0,0,9,0,0,0,0,0,0,9,9,0,0,0,0,0,0,0,0,0,9,9,0,0,0,0,0,9,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,9,9,0,0,0,0,0,0,0,0,0,9,9,0,0,0,0,0,9,0,0,0,0,0},
			{0,0,0,0,0,0,9,0,0,0,0,0,0,9,9,0,0,0,0,0,0,0,0,0,9,9,0,0,0,0,10,9,10,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,10,0,0,0,0,0,9,0,0,0,0,0,0,9,9,0,0,0,0,0,0,0,0,0,9,9,0,0,0,0,10,9,10,0,0,0,0},
			{0,10,10,0,0,10,9,10,0,0,0,0,0,9,9,0,10,10,10,0,0,0,0,0,9,9,0,0,0,0,9,9,9,0,0,10,10,10,9,10,0,0,0,0,0,0,0,0,9,10,10,0,0,10,9,10,0,0,0,0,0,9,9,0,10,10,10,0,0,0,0,0,9,9,0,0,0,0,9,9,9,0,0,10,10},
			{0,9,9,0,0,9,9,9,0,0,10,10,0,9,9,0,9,9,9,0,0,0,0,0,9,9,0,0,0,0,9,9,9,0,0,9,9,9,9,9,0,0,10,10,0,0,0,0,9,9,9,0,0,9,9,9,0,0,10,10,0,9,9,0,9,9,9,0,0,0,0,0,9,9,0,0,0,0,9,9,9,0,0,9,9},
			{0,9,9,0,0,9,9,9,0,0,9,9,0,9,9,0,9,9,9,0,0,0,0,0,9,9,0,0,0,0,9,9,9,0,0,9,9,9,9,9,0,0,9,9,0,0,0,0,9,9,9,0,0,9,9,9,0,0,9,9,0,9,9,0,9,9,9,0,0,0,0,0,9,9,0,0,0,0,9,9,9,0,0,9,9},
			{0,9,9,10,10,9,9,9,10,10,9,9,10,9,9,10,9,9,9,10,10,10,10,10,9,9,10,10,10,10,9,9,9,10,10,9,9,9,9,9,10,10,9,9,10,10,10,10,9,9,9,10,10,9,9,9,10,10,9,9,10,9,9,10,9,9,9,10,10,10,10,10,9,9,10,10,10,10,9,9,9,10,10,9,9},
			{10,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
	};

	/** Game score */
	private int score;
	
	/** User lives left */
	private int livesLeft;
	
	/** Did the player die in this game cycle */
	private boolean dying;
		
	/** Default constructor */
	public Board() {
		initBoard();
	}

	/** 
	 * Initialises the game board.<br>
	 * Add an addKeyListener method for key processing.<br>
	 * Calls startLevel() 
	 */
	private void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		ingame = true;
		score = 0;
		livesLeft = 3;
		currentLevel = 0;
		worldX = 0;
		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		
		Sound.init();	// Initialise sounds
		// Sound.MUSIC.play();	
		
		craft = new Craft(ICRAFT_X, ICRAFT_Y);
		//System.out.println(Arrays.deepToString(craft.getTransparencyArr()));
		
		startLevel();
	}

	/**
	 * Initialise / reinitialise game objects for the current level. <br>
	 * Called on initial start of game and on restart after the player dies.<br>
	 * Allows game to resume at current level without impacting score, numbers lives, etc.<br>
	 * On restarting a level ensures player craft is in clear space.
	 */ 
	private void startLevel() {
		initAliens(currentLevel, 0);
		initMeteors(currentLevel, 0);
		initTiles(currentLevel, 0);
		initRockets(currentLevel, 0);
		initTripods(currentLevel, 0);
		initFuel(currentLevel, 0);
		initExplosions();		
				
		// Reset craft location		
		switch(currentLevel) {
        case 3:
        	craft.x = 8;
    		craft.y = 64;
        	break;
        case 4:
        	craft.x = 40;
    		craft.y = 96;
        	break;
        case 5:
        	craft.x = 8;
    		craft.y = 100;
        	break;
        default:
    		craft.x = ICRAFT_X;
    		craft.y = ICRAFT_Y;
        	break;
		};
		// Fill fuel tank
		craft.setCurrentFuel(craft.getMaxFuel());
	}
	
	/**
	 * Override addNotify() method.<br>
	 * This is automatically called to notify this component that it now has a
	 * parent component.<br>
	 * Sets the chain of parent components up with KeyboardAction event
	 * listeners.<br>
	 * We are using it to start our thread for the game animation.
	 */
	 @Override
	 public void addNotify() {
	     super.addNotify();
	     animator = new Thread(this);
	     animator.start();
	 }
	
		
	/**
	 * Our class implements the Runnable interface which requires us to
	 * implement the inherited abstract method Runnable.run().<br>
	 * Animating objects using a thread should be more effective than calling
	 * methods on a defined periodic basis using a timer (java.util.Timer or
	 * javax.Swing.Timer)<br>
	 * The Game loop runs continuously until we terminate the application. 
	 */
	@Override
	public void run() {
		
		long beforeTime, timeDiff, sleep;
        
		/*
		 * Get current time correct to millisecond Be aware that very old
		 * operating systems may not support this level of precision
		 */
		beforeTime = System.currentTimeMillis(); 
		
        // Thread will loop continuously as it is always true
        while (true) {       
        	updateGame();   // Update the game state
            repaint(); 		// Paint the current Game state to UI. Calls paintComponent()            
            worldX++;		// increment world x counter. Used to load new levels just in time
            updateLevel();  // Load next level once craft nears end of previous level
                        
			/*
			 * Determine how long each game update loop takes to allow us to
			 * maintain our desired frame rate via a sleep variable
			 */
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = GAME_DELAY - timeDiff;
      
			/*
			 * Set a minimum sleep period of 2 milliseconds if our time in the
			 * loop takes us outside our desired fps rate
			 */
            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
        }		
	}
		
	/**
	 * Our Game world is defined and loaded to memory in a series of levels.<br>
	 * This method checks a players progress within the game world and calls the
	 * loading of objects for the next level once we near the end of the
	 * previous level.
	 */
	private void updateLevel() {
		// Triggered when the next level is one screen (BOARD_WIDTH) away
		if ((worldX + BOARD_WIDTH) % GAME_WORLD_WIDTH == 0) {
			currentLevel++;
			// System.out.println("Calling initiators. Level (" + currentLevel + "). worldX: " + worldX);
			initTiles(currentLevel, 1);
			initRockets(currentLevel, 1);
			initTripods(currentLevel, 1);
			initFuel(currentLevel, 1);
			initAliens(currentLevel, 1);
			initMeteors(currentLevel, 0);	// clear meteors array and reload as they loop within the level
		}
	}

	/**
	 * Creates or appends to the list of alien objects.<br>
	 * The aliens take their initial positions from the alienCordinates array
	 * for the level.
	 * 
	 * @param level
	 *            the int representing the level to initialise
	 * @param continuationLevel
	 *            the int representing whether to add the objects to an empty or new / array
	 */
	public void initAliens(int level, int continuationLevel) {
		int[][] alienArray;
		tempAliens.clear(); // ensure temporary array for objects is always

		switch (level) {
		case 0:
			alienArray = levelZeroAlienCordinates;
			break;
		case 1:
			alienArray = levelOneAlienCordinates;
			break;
		case 2:
			alienArray = levelTwoAlienCordinates;
			break;
		case 3:
			alienArray = levelThreeAlienCordinates;
			break;
		case 4:
			alienArray = levelFourAlienCordinates;
			break;
		case 5:
			alienArray = levelFiveAlienCordinates;
			break;
		default:
			alienArray = levelZeroAlienCordinates;
			break;
		}

		// add objects to temporary array
		for (int[] a : alienArray) {
			alienX = (continuationLevel * (BOARD_WIDTH - 1)) + a[0];
			alienY = a[1];
			tempAliens.add(new Alien(alienX, alienY));
		}

		// Add aliens after fully loading temporary array
		if (continuationLevel == 0) {
			// Add to a new / empty array for initial level (or restarting after
			// dying)
			aliens.clear();
			aliens.addAll(tempAliens);
			System.out.println("addeded extra aliens - continuation level = 0");
		} else {
			// Add to existing tile array when loading the next level
			aliens.addAll(tempAliens);
			System.out.println("addeded extra aliens - continuation level = 1");
		}
	}

	/**
	 * Creates or appends to the list of meteor objects. <br>
	 * The meteors take their initial positions from the meteorCordinates array.
	 * 
	 * @param level the int representing the level to initialise
	 * 
	 * @param continuationLevel
	 *            the int representing whether to add the objects to an empty or
	 *            new / array
	 */
	public void initMeteors(int level, int continuationLevel) {
		int[][] meteorArray;
		tempMeteors.clear();	// ensure temporary array for objects is always empty
		
		switch(level) {
        case 0:
        	meteorArray = levelZeroMeteorCordinates;
        	break;
        case 1:
        	meteorArray = levelOneMeteorCordinates;
        	break;
        case 2:
        	meteorArray = levelTwoMeteorCordinates;
        case 3:
        	meteorArray = levelThreeMeteorCordinates;
        	break;
        case 4:
        	meteorArray = levelFourMeteorCordinates;
        	break;
        case 5:
        	meteorArray = levelFiveMeteorCordinates;
        	break;
        default:
        	meteorArray = levelZeroMeteorCordinates;
        	continuationLevel = 0;
        	break;
		}
	
		// add objects to temporary array
		for (int[] m : meteorArray) {			
			meteorX = (continuationLevel * (BOARD_WIDTH - 1)) + m[0];
			meteorY = m[1];
			tempMeteors.add(new Meteor(meteorX, meteorY));
		}
		
		// Add meteors after fully loading temporary array 		
		if (continuationLevel == 0){
			// Add to a new / empty array for initial level (or restarting after dying)
			meteors.clear();
			meteors.addAll(tempMeteors);
			System.out.println("addeded extra meteors - continuation level = 0");		
		} else {
			//  Add to existing tile array when loading the next level
			meteors.addAll(tempMeteors);
			System.out.println("addeded extra meteors - continuation level = 1");
		}	
	}
	
	
	/**
	 * Creates or appends to the list of rocket objects.<br>
	 * Rockets take their initial positions from the rocketCordinates array.
	 * 
	 * @param level
	 *            the int representing the level to initialise
	 * 
	 * @param continuationLevel
	 *            the int representing whether to add the objects to an empty or
	 *            new / array
	 */
	public void initRockets(int level, int continuationLevel) {				
		int[][] rocketArray;		
		tempRockets.clear();	// ensure temporary array for objects is always empty
		
		switch(level) {
        case 0:
        	rocketArray = levelZeroRocketCordinates;
        	break;
        case 1:
        	rocketArray = levelOneRocketCordinates;
        	break;
        case 2:
        	rocketArray = levelTwoRocketCordinates;
        	break;
        case 3:
        	rocketArray = levelThreeRocketCordinates;
        	break;
        case 4:
        	rocketArray = levelFourRocketCordinates;
        	break;
        case 5:
        	rocketArray = levelFiveRocketCordinates;        	
        	break;
        default:
        	rocketArray = levelZeroRocketCordinates;
        	break;
		}

		// add rockets to temporary array
		for (int[] r : rocketArray) {
			rocketX = (continuationLevel * (BOARD_WIDTH - 1)) + + r[0];
			rocketY = r[1];
			tempRockets.add(new Rocket(rocketX, rocketY));
		}
		
		// Add rockets after fully loading temporary rocket array 		
		if (continuationLevel == 0){
			// Add to a new / empty array for initial level (or restarting after dying)
			rockets.clear();
			rockets.addAll(tempRockets);
			System.out.println("addeded extra rockets - continuation level = 0");		
		} else {
			//  Add to existing tile array when loading the next level
			rockets.addAll(tempRockets);
			System.out.println("addeded extra rockets - continuation level = 1");
		}		
	}
	
	
	/**
	 * Creates or appends to the list of tripod objects.<br>
	 * The tripods take their initial positions from the tripodCordinates array.
	 * 
	 * @param level the int representing the level to initialise
	 * 
	 * @param continuationLevel
	 *            the int representing whether to add the objects to an empty or
	 *            new / array
	 */
	public void initTripods(int level, int continuationLevel) {
		int[][] tripodArray;
		tempTripods.clear();	// ensure temporary array for objects is always empty
		
		switch(level) {
        case 0:
        	tripodArray = levelZeroTripodCordinates;
        	break;
        case 1:
        	tripodArray = levelOneTripodCordinates;
        	break;
        case 2:
        	tripodArray = levelTwoTripodCordinates;
        	break;
        case 3:
        	tripodArray = levelThreeTripodCordinates;
        	break;
        case 4:
        	tripodArray = levelFourTripodCordinates;
        	break;
        case 5:
        	tripodArray = levelFiveTripodCordinates;
        	break;
        default:
        	tripodArray = levelZeroTripodCordinates;
        	break;
		};
	
		// add objects to temporary array
		for (int[] t : tripodArray) {			
			tripodX = (continuationLevel * (BOARD_WIDTH - 1)) + t[0];
			tripodY = t[1];
			tempTripods.add(new Tripod(tripodX, tripodY));
		}
		
		// Add tripods after fully loading temporary array 		
		if (continuationLevel == 0){
			// Add to a new / empty array for initial level (or restarting after dying)
			tripods.clear();
			tripods.addAll(tempTripods);
			System.out.println("addeded extra tripods - continuation level = 0");		
		} else {
			//  Add to existing tile array when loading the next level
			tripods.addAll(tempTripods);
			System.out.println("addeded extra tripods - continuation level = 1");
		}	
	}
	

	/**
	 * Creates or appends to the list of fuel tank objects.<br>
	 * The fuel tanks take their initial positions from the fuelCordinates array.
	 * 
	 * @param level the int representing the level to initialise
	 * 
	 * @param continuationLevel
	 *            the int representing whether to add the objects to an empty or
	 *            new / array
	 */
	public void initFuel(int level, int continuationLevel) {
		int[][] fuelArray;
		tempFuelTanks.clear();	// ensure temporary array for objects is always empty
		
		switch(level) {
        case 0:
        	fuelArray = levelZeroFuelCordinates;
        	break;
        case 1:
        	fuelArray = levelOneFuelCordinates;
        	break;
        case 2:
        	fuelArray = levelTwoFuelCordinates;
        	break;
        case 3:
        	fuelArray = levelThreeFuelCordinates;
        	break;
        case 4:
        	fuelArray = levelFourFuelCordinates;
        	break;
        case 5:
        	fuelArray = levelFiveFuelCordinates;
        	break;
        default:
        	fuelArray = levelZeroFuelCordinates;
        	break;
		};
	
		// add objects to temporary array
		for (int[] t : fuelArray) {			
			fuelX = (continuationLevel * (BOARD_WIDTH - 1)) + t[0];
			fuelY = t[1];
			tempFuelTanks.add(new Fuel(fuelX, fuelY));
		}
		
		// Add fuels after fully loading temporary array 		
		if (continuationLevel == 0){
			// Add to a new / empty array for initial level (or restarting after dying)
			fuelTanks.clear();
			fuelTanks.addAll(tempFuelTanks);
			System.out.println("addeded extra fuels - continuation level = 0");		
		} else {
			//  Add to existing tile array when loading the next level
			fuelTanks.addAll(tempFuelTanks);
			System.out.println("addeded extra fuels - continuation level = 1");
		}	
	}
	
	
	/**
	 * Creates or appends to the list of tile objects.<br>
	 * Tiles take their initial positions from the world level array of arrays.<br>
	 * Each array has 12 elements (rows) of 85 columns.<br>
	 * Tiles are positioned top down, left to right.<br>
	 * 
	 * @param level the int representing the level to initialise
	 * 
	 * @param continuationLevel
	 *            the int representing whether to add the objects to an empty or
	 *            new / array
	 */
	public void initTiles(int level, int continuationLevel) {
		int[][] tileArray;
		tempTiles.clear();	// ensure temporary array for objects is always empty
		
		switch(level) {
        case 0:
        	tileArray = levelZeroTiles;      	
        	break;
        case 1:
        	tileArray = levelOneTiles;
        	break;
        case 2:
        	tileArray = levelTwoTiles;
        	break;
        case 3:
        	tileArray = levelThreeTiles;
        	break;
        case 4:
        	tileArray = levelFourTiles;
        	break;
        case 5:
        	tileArray = levelFiveTiles;
        	break;
        default:
        	tileArray = levelZeroTiles;
        	break;
		}
				
		// for each row get array of tiles
		rowNumber = 0;
		for (int[] row : tileArray) {
			colNumber = 0;
			// for each column (tile) in the row
			for (int tileNum : row) {		
				// We only add non-empty tiles, i.e. tile number > zero 
				if (tileNum > 0){
					/*
					 * The appending of tiles for a continuing level happens when there is one board width in the level left. 
					 * So tile x location for continuing levels is shifted right by the board width 
					 */
					tileX = (continuationLevel * (BOARD_WIDTH - 1)) + (colNumber * TILE_WIDTH);
					tileY = (rowNumber * TILE_HEIGHT) + BORDER_HEIGHT;	// shift tiles down
					tempTiles.add(new Tile(tileX, tileY, tileNum));
				}				
				colNumber += 1; // increment column number
			};
			rowNumber += 1;  // increment row number
		}
		// Add tiles after fully loading temporary tile array 		
		if (continuationLevel == 0){
			// Add to a new / empty array for initial level (or restarting after dying)
			tiles.clear();
			tiles.addAll(tempTiles);
			System.out.println("addeded extra tiles - continuation level = 0");		
		} else {
			//  Add to existing tile array when loading the next level
			tiles.addAll(tempTiles);
			System.out.println("addeded extra tiles - continuation level = 1");
		}
	}
	
			
	/**
	 * Creates a list of explosion objects.<br> 
	 * Initially empty. Explosions added when aliens are destroyed.
	 */
	public void initExplosions() {
		explosions = new ArrayList<>();
	}
	
	/**
	 * Override paintComponent.<br> 
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ingame) {
			drawObjects(g); // draw game sprites
		} else {
			if (won) {
				drawGameWon(g); // draw game won
			} else {
				drawGameLost(g); // draw game lost
			}			
		}
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * Draw the game Sprites and info
	 * @param g
	 *            the graphic object
	 */
	private void drawObjects(Graphics g) {

		// Draw players craft
		if (craft.isVisible()) {
			g.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);			
		}

		// Draw craft's missiles
		ArrayList<Missile> missiles = craft.getMissiles();
		for (Missile m : missiles) {
			if (m.isVisible()) {
				g.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}
		}
		
		// Draw craft's bombs
		ArrayList<Bomb> bombs = craft.getBombs();
		for (Bomb b : bombs) {
			if (b.isVisible()) {
				g.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
		}

		// Draw all aliens not previously destroyed - checked by the isVisible() method
		for (Alien a : aliens) {
			if (a.isVisible()) {
				g.drawImage(a.getImage(), a.getX(), a.getY(), this);
			}
		}
		
		// Draw all meteors not previously destroyed - checked by the isVisible() method
		for (Meteor m : meteors) {
			if (m.isVisible()) {
				g.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}
		}
		
		// Draw all rockets not previously destroyed - checked by the isVisible() method
		for (Rocket r : rockets) {
			if (r.isVisible()) {
				g.drawImage(r.getImage(), r.getX(), r.getY(), this);
			}
		}
		
		// Draw all tripods not previously destroyed - checked by the isVisible() method
		for (Tripod t : tripods) {
			if (t.isVisible()) {
				g.drawImage(t.getImage(), t.getX(), t.getY(), this);
			}
		}
		
		// Draw all fuel tanks not previously destroyed - checked by the isVisible() method
		for (Fuel f : fuelTanks) {
			if (f.isVisible()) {
				g.drawImage(f.getImage(), f.getX(), f.getY(), this);
			}
		}
		
		// Draw all "visible" tiles. Not all with be shown on screen
		for (Tile t : tiles) {
			if (t.isVisible()) {
				g.drawImage(t.getImage(), t.getX(), t.getY(), this);
			}
		}		

		// Draw all explosions not yet fully rendered - checked by the isVisible() method
		for (Explosion e : explosions) {
			if (e.isVisible()) {
				g.drawImage(e.getImage(), e.getX(), e.getY(), this);
			}
		}
		
		/* Draw Header consisting of 3 zones
		 * Left 25% - Score
		 * Mid 50% - World location bar
		 * Right 25% - High score 
		 */
				
		// set display font
		Font displayFont = new Font("Helvetica", Font.BOLD, 14);
		g.setFont(displayFont);
		
		// get metrics for the display font so we can centre text in display
		FontMetrics displayFontMetrics = g.getFontMetrics(displayFont);
    		
		// Draw score		
        g.setColor(Color.yellow);
              
        int paddingLeft = 0;	// padding to center text 
        int stageLeft = 0;		// start at left of board
                
        paddingLeft = (BOARD_WIDTH/4 - displayFontMetrics.stringWidth("SCORE:"))/2;        
        g.drawString("SCORE:", stageLeft+paddingLeft, BORDER_HEIGHT / 2 + 3);
        
        paddingLeft = (BOARD_WIDTH/4 - displayFontMetrics.stringWidth(Integer.toString(score)))/2;
    	g.drawString(Integer.toString(score), stageLeft+paddingLeft, BORDER_HEIGHT / 2 + 18);
    	
		// Draw high score
    	highScore = score;
    	stageLeft = 3*BOARD_WIDTH/4;	// start three quarters of way across board
    	paddingLeft = (BOARD_WIDTH/4 - displayFontMetrics.stringWidth("HIGH SCORE:"))/2;
    	g.drawString("HIGH SCORE:", stageLeft+paddingLeft, BORDER_HEIGHT / 2 + 3);
    	paddingLeft = (BOARD_WIDTH/4 - displayFontMetrics.stringWidth(Integer.toString(highScore)))/2;
    	g.drawString(Integer.toString(highScore), stageLeft+paddingLeft, BORDER_HEIGHT / 2 + 18);
        
        // Draw world location bar
        Font progress = new Font("Helvetica", Font.BOLD, 10);
				
		// get metrics from the graphics
		FontMetrics metrics = g.getFontMetrics(progress);
        String stages[] = {"1ST", "2ND", "3RD", "4TH", "5TH", "BASE"};		
 		g.setFont(progress);
             
    	// loop through each game stage and draw
        int j=0;
        paddingLeft = 0;
        stageLeft = BOARD_WIDTH/4;	// start a quarter of the way across the board
        int stageRectangleWidth = BOARD_WIDTH/12;	// size to fit 6 rectangles across 50% of the screen 
		for (String stage : stages) {		
	      	// Add empty rectangles
			g.setColor(Color.yellow);
           	g.draw3DRect(stageLeft+(j*stageRectangleWidth), 2, stageRectangleWidth, 16, true);  //row 1           	
           	g.draw3DRect(stageLeft+(j*stageRectangleWidth), 16, stageRectangleWidth, 14, true);  // row 2
           	
           	// Add Stage text
           	// Calculate padding to centre stage name
           	paddingLeft = (stageRectangleWidth - metrics.stringWidth(stage))/2;
           	g.setColor(Color.red);
           	g.drawString(stage, stageLeft + (j*stageRectangleWidth) + paddingLeft, 13);
        	
        	// Add Stage progress rectangles
           	if (j <= currentLevel){
           		g.setColor(Color.red);
           	} else {
           		g.setColor(Color.yellow);
           	}           	
           	g.fill3DRect(stageLeft+(j*stageRectangleWidth), 16, stageRectangleWidth, 16, true);
           	j++;
		}
		
		/* Draw Footer consisting of 2 zones
		 * Left 25% - Score
		 * Right 25% - High score 
		 */
		
		// Draw fuel tank bar
		g.setFont(displayFont);
        g.setColor(Color.yellow);
 		
 		stageLeft = 0;
 		paddingLeft = (BOARD_WIDTH/4 - displayFontMetrics.stringWidth("FUEL:"))/2;
    	g.drawString("FUEL:", stageLeft+paddingLeft, BOARD_HEIGHT - (BORDER_HEIGHT/2) + 5);
     		 		
 		// loop through 128 bar fuel gauge and draw full / empty bars
    	// 128 bars * 2 pixels = 50% of screen
        double currentFuel = craft.getCurrentFuel();
        int fuelLeftPadding = BOARD_WIDTH/4;
        int fuelRectangleWidth = 2;
		for (int i = 0; i < 128; i++) {        	
			
        	// Add Fuel tank rectangles
           	if (i <= currentFuel){
           		g.setColor(Color.yellow);
           	} else {
           		g.setColor(Color.red);
           	}           	
          	g.fill3DRect(fuelLeftPadding+(i*fuelRectangleWidth), BOARD_HEIGHT - 3*BORDER_HEIGHT/4, fuelRectangleWidth, BORDER_HEIGHT/2, true);
           	i++;
		}
		
    	// Draw lives remaining
    	ImageIcon ii = new ImageIcon("src/resources/ship.png");
    	Image life = ii.getImage();
        for (int i = 0; i < livesLeft; i++) {
        	g.drawImage(life, BOARD_WIDTH - ((i+1) * 32), BOARD_HEIGHT - BORDER_HEIGHT, this);
        }
		
	}

	/**
	 * Paint Game Over screen
	 * 
	 * @param g
	 *            the graphic object
	 */
	private void drawGameLost(Graphics g) {
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);
		g.setColor(Color.white);
		g.setFont(small);
		String msg = "GAME OVER";
		g.drawString(msg, (BOARD_WIDTH - fm.stringWidth(msg)) / 2, BOARD_HEIGHT / 2);
	}
	
	/**
	 * Paint Game Won screen
	 * 
	 * @param g
	 *            the graphic object
	 */
	private void drawGameWon(Graphics g) {
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);
		g.setColor(Color.white);
		g.setFont(small);
		String msg = "CONGRATULATIONS";
		g.drawString(msg, (BOARD_WIDTH - fm.stringWidth(msg)) / 2, BOARD_HEIGHT / 2);
		String msg2 = "YOU HAVE WON";
		g.drawString(msg2, (BOARD_WIDTH - fm.stringWidth(msg2)) / 2, (BOARD_HEIGHT / 2) + 20 );
	}

	/**
	 * Update Game state.<br>
	 * This method is called repeatedly by the animation cycle / thread.<br>
	 * The games logic is contained in the specific methods called.<br>
	 * The order the methods are called is important, for example, move your
	 * ships position before determining if there is a collision.
	 */
	public void updateGame() {
		if (ingame) {
			dying = false;	// reset at start of each cycle. 
			updateCraft();
			updateMissiles();
			updateBombs();
			updateAliens();
			updateMeteors();
			updateRockets();
			updateTripods();
			updateFuelTanks();
			updateTiles();
			updateExplosions();
			checkProximity();
			checkDeath();
			checkWon();
		}
	}

	/**
	 * Check and exit if game over (player reached a certain point) 
	 */
	private void checkWon() {
		if (worldX == (GAME_WORLD_WIDTH*6)-BOARD_WIDTH) {
			ingame = false;
			won = true;
			return;
		}		
	}

	/**
	 * Update players craft.<br>
	 * If the players craft is visible (i.e. alive) move the craft and consume
	 * fuel.
	 */
	private void updateCraft() {
		if (craft.isVisible()) {
			craft.move();	// move
			craft.setCurrentFuel(craft.getCurrentFuel() - 0.0512); // use fuel
		}
	}

	/**
	 * Loop through all the crafts active missiles and either move or remove
	 * them from the container.
	 */
	private void updateMissiles() {
		ArrayList<Missile> missiles = craft.getMissiles();
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = (Missile) missiles.get(i);
			if (m.isVisible()) {
				m.move();
			} else {
				missiles.remove(i);
			}
		}
	}
	
	/**
	 * Loop through all bombs and either move or remove them from the
	 * container.
	 */
	private void updateBombs() {
		ArrayList<Bomb> bombs = craft.getBombs();
		for (int i = 0; i < bombs.size(); i++) {
			Bomb b = (Bomb) bombs.get(i);
			if (b.isVisible()) {
				b.move();
			} else {
				bombs.remove(i);
			}
			// when a bomb reaches the bottom of the board mark for removal next time round
			if (b.getY() > BOARD_HEIGHT) {
				b.setVisible(false);
			}
		}
	}

	/**
	 * Loop through all aliens and either move or remove them from the
	 * container.
	 */
	private void updateAliens() {
		for (int i = 0; i < aliens.size(); i++) {
			Alien a = aliens.get(i);
			if (a.isVisible()) {
				a.move();
			} else {
				aliens.remove(i);
			}
		}
	}
	
	/**
	 * Loop through the meteors and move or remove them
	 */
	private void updateMeteors() {
		for (int i = 0; i < meteors.size(); i++) {
			Meteor m = meteors.get(i);
			if (m.isVisible()) {
				m.move();
			} else {
				meteors.remove(i);
			}			
		}
	}	
	
	/**
	 * Loop through the rocket objects and move or remove them.<br>
	 * Rockets are only moved vertically (i.e. launched) once the players craft
	 * is in range.
	 */
	private void updateRockets() {
		for (int i = 0; i < rockets.size(); i++) {
			Rocket r = rockets.get(i);			
			if (r.isVisible()) {
				// always move rocket so it scrolls even if not launched
				r.move();
				if (!r.isLaunched()){
					// if rocket not yet launched check whether to launch
					if (r.getX() - craft.getX() < ROCKET_LAUNCH_PROXIMITY) {
						// launch rocket
						r.setLaunched(true);
					}					
				}				
			// remove if not visible
			} else {				
				rockets.remove(i);
			}
			
			// when a rocket scrolls off the top of the board mark for removal next time round
			if (r.getY() < -31) {
				r.setVisible(false);
			}			
		}
	}	
	
	
	/**
	 * Loop through the tripods and move or remove them
	 */
	private void updateTripods() {
		for (int i = 0; i < tripods.size(); i++) {
			Tripod t = tripods.get(i);
			if (t.isVisible()) {
				t.move();				
			} else {
				tripods.remove(i);
			}			
		}
	}	
	
	/**
	 * Loop through the fuel tanks and move or remove them
	 */
	private void updateFuelTanks() {
		for (int i = 0; i < fuelTanks.size(); i++) {
			Fuel f = fuelTanks.get(i);
			if (f.isVisible()) {
				f.move();				
			} else {
				fuelTanks.remove(i);
			}			
		}
	}	
	
	/**
	 * Loop through the levels tiles and move or remove them.<br>
	 * Tiles are removed once they scroll off screen to free up memory.
	 */
	private void updateTiles() {
		for (int i = 0; i < tiles.size(); i++) {
			Tile t = tiles.get(i);
			if (t.isVisible()) {
				t.move();
			} else {
				tiles.remove(i);
			}
			// when a tile completely scrolls off screen mark for removal next time round
			if (t.getX() < -TILE_WIDTH) {
				t.setVisible(false);
			}		
		}
	}
		
	/**
	 * Loop through the explosions and move or remove them
	 */
	private void updateExplosions() {
		for (int i = 0; i < explosions.size(); i++) {
			Explosion e = explosions.get(i);
			if (e.isVisible()) {
				e.move();				
			} else {
				explosions.remove(i);
			}
		}
	}
	
	/**
	 * Initial check for collisions of interest.<br>
	 * If sprites rectangles intersect call checkCollisions() to look for an
	 * actual collision.
	 */
	public void checkProximity() {		
		Rectangle craftRectangle = craft.getBounds();
		
		// does the craft object intersect (collide) with any of the alien objects
		for (Alien alien : aliens) {
			Rectangle spriteRectangle = alien.getBounds();
			if (craftRectangle.intersects(spriteRectangle)) {				
				checkCollisions(craft, alien);				
			}
		}
		
		// does the craft object intersect (collide) with any of the meteor objects
		for (Meteor meteor : meteors) {
			Rectangle spriteRectangle = meteor.getBounds();
			if (craftRectangle.intersects(spriteRectangle)) {				
				checkCollisions(craft, meteor);				
			}
		}
		
		// does the craft object intersect (collide) with any of the rocket objects
		for (Rocket rocket : rockets) {
			Rectangle spriteRectangle = rocket.getBounds();
			if (craftRectangle.intersects(spriteRectangle)) {				
				checkCollisions(craft, rocket);				
			}
		}
		
		// does the craft object intersect (collide) with any of the tripod objects
		for (Tripod tripod : tripods) {
			Rectangle spriteRectangle = tripod.getBounds();
			if (craftRectangle.intersects(spriteRectangle)) {				
				checkCollisions(craft, tripod);				
			}
		}
		
		// does the craft object intersect (collide) with any of the fuel tank objects
		for (Fuel fuelTank : fuelTanks) {
			Rectangle spriteRectangle = fuelTank.getBounds();
			if (craftRectangle.intersects(spriteRectangle)) {				
				checkCollisions(craft, fuelTank);				
			}
		}

		// does the craft object intersect (collide) with any of the tile objects
		for (Tile tile : tiles) {
			Rectangle spriteRectangle = tile.getBounds();
			if (craftRectangle.intersects(spriteRectangle)) {
				checkCollisions(craft, tile);
			}
		}
				
		/**
		 * Loop through the crafts weapons fired (missiles and bombs) and check
		 * for collision with game sprites we can interact with, e.g. aliens,
		 * rockets, etc.<br>
		 * If we hit a sprite destroy it and the weapon that hit it, add an explosion and update score.
		 */
		// TODO: Pixel level check for collisions? Support smaller rectangles for bombs? 
		
		ArrayList<Sprite> weaponsFired = new ArrayList<Sprite>();
		weaponsFired.addAll(craft.getMissiles());
		weaponsFired.addAll(craft.getBombs());
		
		for (Sprite s : weaponsFired) {
			Rectangle weaponRectangle = s.getBounds();
			for (Alien alien : aliens) {
				Rectangle alienRectangle = alien.getBounds();
				if (weaponRectangle.intersects(alienRectangle)) {
					s.setVisible(false);
					alien.setVisible(false);
	        		// add explosion sprite
	        		explosions.add(new Explosion(alien.x, alien.y));
	        		// update score	
					score += 100;
				}
			}			
			for (Rocket rocket : rockets) {
				Rectangle rocketRectangle = rocket.getBounds();
				if (weaponRectangle.intersects(rocketRectangle)) {
					s.setVisible(false);
					rocket.setVisible(false);
	        		// add explosion sprite
	        		explosions.add(new Explosion(rocket.x, rocket.y));
	        		// update score	
					score += 50;
				}
			}
			for (Tripod tripod : tripods) {
				Rectangle tripodRectangle = tripod.getBounds();
				if (weaponRectangle.intersects(tripodRectangle)) {
					s.setVisible(false);
					tripod.setVisible(false);					
	        		// add explosion sprite with a with random score
					Random random = new Random();
					int  randomScore = (random.nextInt(3)+1)*100;					
	        		explosions.add(new Explosion(tripod.x, tripod.y, randomScore));
	        		// update score	        		
					score += randomScore;
				}
			}
			for (Fuel fuelTank : fuelTanks) {
				Rectangle fuelRectangle = fuelTank.getBounds();
				if (weaponRectangle.intersects(fuelRectangle)) {
					s.setVisible(false);
					fuelTank.setVisible(false);					
	        		// add explosion sprite with a with random score
	        		explosions.add(new Explosion(fuelTank.x, fuelTank.y));
	        		// update score	        		
					score += 150;
					// add fuel to craft
					craft.setCurrentFuel(craft.getCurrentFuel() + 14); // add fuel
				}
			}
			for (Tile tile : tiles) {
				Rectangle tileRectangle = tile.getBounds();
				if (weaponRectangle.intersects(tileRectangle)) {
					s.setVisible(false);				
	        		// add explosion sprite above tile
					explosions.add(new Explosion(tile.x, tile.y-32));	        	
				}
			}			
		}
	}

	
	
	/**
	 * Check for definite non-transparent pixel collision between the craft and
	 * other Sprites e.g. Alien, Tile object.<br>
	 * 
	 * If we have a collision remove the sprite (if destroyable and call death of craft processing.
	 * 
	 * @param craft
	 *            The Craft object representing the player.
	 * @param sprite
	 *            The Sprite object to check collision with
	 */
	public void checkCollisions(Craft craft, Sprite sprite){
		
		// row (y) shift 
		int rowShift = craft.y - sprite.y;
		int rowCount = Math.abs(rowShift);
		
		// column (x) shift
		int colShift = craft.x - sprite.x;
		int colCount = Math.abs(colShift);
		
		// Get 2D Arrays of non-transparent pixels
		int[][] craftMask = craft.getTransparencyArr();
		int[][] spriteMask = sprite.getTransparencyArr();
		//System.out.println(Arrays.deepToString(spriteMask));
		
		// TODO: Fix underlying issue. We shouldn't be able to have a rowshift or colshift > 31 as checkCollisions shouldn't be called in the first place
		// Exception avoidance - stop processing here and resume
		if (rowCount>31 || colCount>31) {			
			return;
		}
				
		// Initialise 2D Array to contain shifted craft array
		int[][] craftShiftedMask = craftMask.clone();
		
		// Blank array line
		final int[] transparentRow = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; 
				
		// Shift craft 2D  Array to match potentially colliding Sprites location 
		
		// FIRST Pass - row shifting
		
		// row (y) shift 
		// Negative rowShift (craft above sprite)
		if (rowShift <= 0){
			// ignore the number of rows indicated by rowshift in the craft pixel array
			// move next rows up until row 32 added
			// add transparent rows until 32 rows reach						
			for (int row = 0; row < craftMask.length; row++){
				if(row < craftMask.length-rowCount){
					//System.out.println("Add -ve shifted row:" + row + ", rowCount:" + rowCount);
					craftShiftedMask[row] = craftMask[row + rowCount];
					//System.out.println(Arrays.toString(craftShiftedMask[row]));
				} else {
					//System.out.println("Add transparent row:" + row + ", rowCount:" + rowCount);
					craftShiftedMask[row] = transparentRow;
					//System.out.println(Arrays.toString(craftShiftedMask[row]));
				}
			}
		// positive rowShift (craft below)
		} else {
			// add transparent rows until we reach number indicated by rowshift
			// add craft rows up until row 32 added
			for (int row = 0; row < craftMask.length; row++){				
				if(row < rowCount){
					//System.out.println("Add transparent row:" + row + ", rowCount:" + rowCount);
					craftShiftedMask[row] = transparentRow;
					//System.out.println(Arrays.toString(craftShiftedMask[row]));
				} else {
					//System.out.println("Add +ve shifted row:" + row + ", rowCount:" + rowCount);
					craftShiftedMask[row] = craftMask[row - rowCount];
					//System.out.println(Arrays.toString(craftShiftedMask[row]));					
				}
			}
		}
		
		// SECOND Pass - col shifting
		// Shift the partially updated craft mask created in the first pass
		// Initialise 2D Array to contain shifted craft array
		int[][] craftShiftedMask2 = craftShiftedMask.clone();
		
		// col (x) shift 
		// Negative colShift (craft to left)
		if (colShift <= 0){
			// discard the columns before col shift
			// i.e. shift columns to left starting with first column after col shift
			// use copyOfRange "to" pad with zeros until we reach 32 columns
			for (int row = 0; row < craftShiftedMask2.length; row++){				
				//System.out.println("second pass. Discard some, then Shift rest Left. row:" + row + ", colCount:" + colCount);
				//System.out.println("Arr before: \n" + Arrays.toString(craftShiftedMask[row]));		
				craftShiftedMask2[row] = Arrays.copyOfRange(craftShiftedMask[row], colCount, colCount + craftShiftedMask2[row].length);
				//System.out.println("Arr after: \n" + Arrays.toString(craftShiftedMask2[row]));				
			}			
		} else {
			// Positive colShift (craft to right)
			for (int row = 0; row < craftShiftedMask2.length; row++){
				
				// need to add correct number of transparent pixels in columns before adding craft pixels 
				//System.out.println("second pass. Shift starting cols right. row: " + row + ", colCount:" + colCount);
				//System.out.println("Arr before: \n" + Arrays.toString(craftShiftedMask[row]));
				
				// need to add shift to an array of zeros otherwise we may have ones that won't be overwritten
				// blank array to be overwritten first
				craftShiftedMask2[row] = transparentRow.clone();
				
				// arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
				System.arraycopy(craftShiftedMask[row], 0, craftShiftedMask2[row], colCount, craftShiftedMask2.length - colCount);
				//System.out.println("Arr after: \n" + Arrays.toString(craftShiftedMask2[row]));
			}
		}
		
		/* 
		 * Compare shifted craft and sprite for actual collision.
		 * A collision happened if there are two non-transparent cells that coincide 
		 */
		for (int i = 0; i < craftShiftedMask2.length; i++) {
	        for (int j = 0; j < craftShiftedMask2[i].length; j++) {
	        	if( craftShiftedMask2[i][j] + spriteMask[i][j] >1){
	        		
					/*
					 * Only remove sprites collided with if they are
					 * "destroyable" implemented at Sprite object level to avoid
					 * conditional programming based on the type of sprite
					 * object, e.g. sprite.getClass().getSimpleName()
					 */
	        		if (sprite.isDestroyable()) {
	        			// remove sprite
		        		sprite.setVisible(false);
	        		}
	        		// call death of craft processing
	        		dying = true;
	        		return;
	        	}	        		
	        }
	    }
	}
	
	/**
	 * Create Listener object by extending the abstract KeyAdapter class for
	 * receiving keyboard events and overriding the methods for the events of
	 * interest.<br>
	 * To use this object it needs to be registered with a component using the
	 * component's addKeyListener method. When a key is pressed, released, or
	 * typed, the relevant method in the listener object is invoked, and the
	 * KeyEvent is passed to it.<br>
	 * Here, the overridden methods of the KeyAdapter class delegate the 
	 * processing to the methods of the Craft class. 
	 */
	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			craft.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			craft.keyPressed(e);
		}
	}
	
	/**
	 * Called at end of game cycle.<br>
	 * Handles a player losing a life within a game cycle. If player has run out
	 * of lives end game else restart the level and continue.<br>
	 * Done at end of game cycle so that we do not get a
	 * ConcurrentModificationException by changing a ArrayList Collection whilst
	 * we are still iterating through the collection. For example, by reseting
	 * the world tile array to level zero whilst still part way iterating
	 * through it checking for collisions.
	 */
	private void checkDeath() {
		if (dying) {
			livesLeft--;
			if (livesLeft == 0) {
				craft.setVisible(false);
				ingame = false;
			} else {
				// restart world (at current level)
				//	worldX = 0;	// restart x location counter
				//	currentLevel = 0; // restart at level 0
				
				// reset x location counter to start of current level. Used to determine when game won
				worldX = currentLevel * GAME_WORLD_WIDTH;				
				startLevel();	// restart
			}
		}
	}

	
}
