package org.housey;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * The entry point of the game.
 *  
 * @author Paul House
 * @version 1.0
 */
public class Application extends JFrame implements Common{

  	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new game
	 */
  	public Application() {        
        initUI();
    }
    
	/**
	 * Initialise the game
	 */
  	private void initUI() {        
        add(new Board());		// Add new Board to the centre of the JFrame container    
        setSize(BOARD_WIDTH, BOARD_HEIGHT);		// Set default size of board
        setResizable(false);
        setTitle("Scramle(ish) Game");
		// Close the app when we click on the close button. It is not the default behaviour
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Call pack to query the preferred size of the parent container
		pack();
		// Passing null will centre the window on the screen.
		// call after pack so window doesn't resize afterwards
		setLocationRelativeTo(null);
    }


	/**
	 * All the Swing (GUI) processing is done in a thread called EDT (Event
	 * Dispatching Thread). If code is slow to complete you can block this
	 * thread (and the GUI). To avoid this we process our calculations within a
	 * different thread, then pass this to the EDT thread to display.
	 * EventQueue.invokeLater posts an event (your Runnable) at the end of
	 * Swings event list and it's processed after all previous GUI events are
	 * processed.
	 * 
	 * @param args a String array for optional command line arguments
	 */
    public static void main(String[] args) {            	
    	EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
            	// Create an instance of our application
            	Application ex = new Application();
            	// make it visible on the screen
                ex.setVisible(true); 
            }
        });
    }
}