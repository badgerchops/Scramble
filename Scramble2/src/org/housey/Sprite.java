package org.housey;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
//import java.util.Arrays;

/**
 * This is the base class for all Sprites in the game.<br>
 * Contains all code and variables common to all Sprites (Alien, Craft, Missile, Map tiles, etc.)<br>
 * Extended by all the sprites in the game.
 * 
 * @author Paul House
 * @version 1.0  
 */
public class Sprite implements Common {

	// Instance Variables / Fields
   	
	/** The x coordinate of the sprite on the game panel */
	protected int x;
	
	/** The y coordinate of the sprite on the game panel */
	protected int y;
	
	/** The width in pixels of the sprite */
	protected int width;
	
	/** The height in pixels of the sprite */
	protected int height;
	
	/** Is the sprite visible or not. Sprites are made invisible before being removed in the next game cycle */
	protected boolean vis;
	
	/** The image representing the sprite in the game panel */
	protected Image image;
	
	/** Is the sprite destroyable when involved in a collision */
	protected boolean destroyable;
		
	/** 
	 * 2D integer array representing the solid and empty parts of the sprite.
	 * Zeroes represent empty (transparent) pixels which cannot be involved in a
	 * collision with another sprite. Ones represent solid (non-transparent)
	 * pixels which can be involved in a collision with another sprite.
	 */
	protected int[][] transparencyArr; 

	/** 
	 * Current animation frame being displayed - animated sprites only
	 */
	protected int currentFrame = 0;
	
	/**
	 * Creates a new visible Sprite object at the coordinates passed in. Default
	 * visibility of the sprite is true. Default destroyable of the sprite is
	 * true.
	 * 
	 * @param x
	 *            the int initial x coordinate of the craft
	 * @param y
	 *            the int initial y coordinate of the craft
	 * 
	 */	
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
		vis = true;
		destroyable = true;
	}

	/**
	 * Get image and calculate the transparency array for pixel level collision
	 * calculations.
	 * 
	 * @param imageName
	 *            the String representing the image location
	 */
	protected void loadImage(String imageName) {
		ImageIcon ii = new ImageIcon(imageName);
		image = ii.getImage();
		setTransparencyArr(image);	// determine non transparent pixels for collision calcs
	}

	/**
	 * Get image from sprite sheet that corresponds to the frame passed in.<br>
	 * We don't recalculate the transparency array for pixel level collision
	 * calculations as it was generating an error and is unnecessary overhead
	 * anyway (done in loadImage).
	 * Assumes all frames are the same size (TILE_WIDTH = 32 pixels)
	 *  
	 * @param imageName
	 *            the String representing the image location
	 * @param frame
	 *            the int representing the frame to retrieve
	 */
	protected void loadSubImage(String imageName, int frame) {
		ImageIcon ii = new ImageIcon(imageName);
		image = ii.getImage();
		
		// Convert to buffered image so we can use getSubimage to retrieve frame of interest
		BufferedImage bufferedImg = toBufferedImage(image);
		
		// Calculate x,y co-ords of rectangle containing required sprite image  
		int srcX = frame * TILE_WIDTH;
        int srcY = 0;
           
		image = bufferedImg.getSubimage(srcX, srcY, TILE_WIDTH, TILE_HEIGHT);
			
	//	setTransparencyArr(image);
				
	}
	
	/**
	 * Get image from sprite sheet that corresponds to the frame passed in.<br>
	 * We don't recalculate the transparency array for pixel level collision
	 * calculations as it was generating an error and is unnecessary overhead
	 * anyway (done in loadImage). Supports frames that aren't the standard size
	 * (TILE_WIDTH = 32 pixels)
	 * 
	 * @param imageName
	 *            the String representing the image location
	 * @param frame
	 *            the int representing the frame to retrieve
	 * @param frameWidth
	 *            the int representing the width of the frames in pixels
	 */
	protected void loadSubImage(String imageName, int frame, int frameWidth) {
		ImageIcon ii = new ImageIcon(imageName);
		image = ii.getImage();

		// Convert to buffered image so we can use getSubimage to retrieve frame
		// of interest
		BufferedImage bufferedImg = toBufferedImage(image);

		// Calculate x,y co-ords of rectangle containing required sprite image
		int srcX = frame * frameWidth;
		int srcY = 0;

		image = bufferedImg.getSubimage(srcX, srcY, frameWidth, frameWidth);

		// setTransparencyArr(image);

	}

	/** Retrieve the dimensions of the image */
	protected void getImageDimensions() {
		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getCurrentFrame(){
		return currentFrame;
	}
	
	public void setCurrentFrame(int frame){
		currentFrame = frame;
	}
	
	public boolean isVisible() {
		return vis;
	}

	public void setVisible(Boolean visible) {
		vis = visible;
	}
	
	public boolean isDestroyable() {
		return destroyable;
	}

	public void setDestroyable(Boolean destroy) {
		destroyable = destroy;
	}
		
	
	/**
	 * Returns the bounding rectangle of the sprite image to allow collision
	 * detection.
	 * 
	 * @return Rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	/**
	 *  Create integer array to identify all non transparent pixels in the sprite
	 *  Rule is Sprites can't collide with transparent pixels
	 *  Assumption - all sprites used will have an alpha (transparency) channel
	 *  Adapted from: https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
	 *  
	 *  @param img Image to analyse
	 *    
	 */	 
	public void setTransparencyArr(Image img) {
		// Get buffered image
		BufferedImage bufferedImg = toBufferedImage(img);

		final int pixelLength;
		boolean hasAlphaChannel = bufferedImg.getAlphaRaster() != null;
		if (hasAlphaChannel){
			pixelLength = 4; //number of bytes used to represent a pixel if alpha value present
		} else {
			pixelLength = 3; //number of bytes used to represent a pixel if alpha value not present
		}		
        int height = bufferedImg.getHeight();
        int width = bufferedImg.getWidth();
        final byte[] imgPixels = ((DataBufferByte) bufferedImg.getRaster().getDataBuffer()).getData();
    
        int[][] imgArr = new int[height][width];
        
        for (int pixel = 0, row = 0, col = 0; pixel < imgPixels.length; pixel = pixel + pixelLength) {

        	int alpha = 0;
        	alpha = (((int) imgPixels[pixel] & 0xff) << 24); //getting the alpha channel only for the pixel        	
        	// some images giving me more than 32 rows!
        	if (alpha==0){
        		// imgArr[row][col] = 0;
        		if (row < 32) imgArr[row][col] = 0;
        	} else{
        		// imgArr[row][col] = 1;
        		if (row < 32) imgArr[row][col] = 1;
        	}
        	col++;
        	if (col == width) {
        		col = 0;
        		row++;
        	}
        }
        
//        for( int i = 0; i < width; i++ ) {
//        	for( int j = 0; j < height; j++ ){        		
//		        int alpha = -16777216; // 255 alpha
//		        if (hasAlphaChannel)
//		        {
//	                int pixel = bufferedImg.getRGB(i, j);
//	                alpha = (pixel >> 24) & 0xff;		
//		        }
//	        	if (alpha==0){
//	        		imgArr[i][j] = 0;        	
//	        	} else {	        		
//	        		imgArr[i][j] = 1;	        		
//        		}	   	        	
//        	}               
//        }
      
        transparencyArr = imgArr;
        // System.out.println(Arrays.deepToString(imgArr));
	}
	
	public int[][] getTransparencyArr(){
		return transparencyArr;
	}
	
	/**
	 * Converts a given Image into a BufferedImage
	 * 
	 * @param img
	 *            The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
		
}