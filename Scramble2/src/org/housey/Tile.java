package org.housey;

/**
 * Tile sprite
 */
public class Tile extends Sprite implements Common{

	public Tile(int x, int y) {
		super(x, y);
		initTile(0);  // assign default tile
	}
	
	
	public Tile(int x, int y, int tileNumber) {
		super(x, y);
		initTile(tileNumber);
		destroyable = false;	// tiles won't be destroyed in a collision with the craft
	}

	private void initTile(int tileNumber) {
		
		switch(tileNumber) {
        case 0:
           	loadImage("src/resources/empty.png");
        	break;
        case 1:
           	loadImage("src/resources/ground.png");
        	break;
        case 2:
           	loadImage("src/resources/ground-slope-up.png");
        	break;
        case 3:
           	loadImage("src/resources/ground-slope-down.png");
        	break;
        case 4:
           	loadImage("src/resources/roof-slope-down.png");
        	break;
        case 5:
           	loadImage("src/resources/roof-slope-up.png");
        	break;
        case 6:
        	loadImage("src/resources/peak.png");
        	break;
        case 7:
        	loadImage("src/resources/ground-ripple.png");
        	break;
        case 8:
        	loadImage("src/resources/roof.png");
        	break;
        case 9:
        	loadImage("src/resources/city-ground.png");
        	break;
        case 10:
        	loadImage("src/resources/city-floor.png");
        	break;
        case 11:
        	loadImage("src/resources/city-roof.png");
        	break;
        case 12:
        	loadImage("src/resources/city-slope-up.png");
        	break;
        case 13:
        	loadImage("src/resources/city-logo-1.png");
        	break;
        case 14:
        	loadImage("src/resources/city-logo-2.png");
        	break;
        case 20:
        	loadImage("src/resources/tripod.png");
        	break;
        case 21:
        	loadImage("src/resources/fuel.png");
        	break;       
        default:
        	loadImage("src/resources/empty.png");
        	break;
		};
		getImageDimensions();
	}

	public void move() {
		// old - Tiles return on the right after they have completely disappeared on the left (inc. TILE WIDTH)
//		if (x < -TILE_WIDTH) {
//			x = GAME_WORLD_WIDTH - (2*TILE_WIDTH);
//		}
		
		x -= SCROLL_SPEED;
	}

}
