
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.awt.image.BufferStrategy;



//*************************************************

public class WT extends JFrame implements Runnable
{
		private static final int NUM_BUFFERS = 2;    // used for page flipping
		
		private static int DEFAULT_FPS = 100;
		
		private static long MAX_STATS_INTERVAL = 1000000000L;
    // record stats every 1 second (roughly)

		private static final int NO_DELAYS_PER_YIELD = 16;
		/* Number of frames with a delay of 0 ms before the animation thread yields
			 to other running threads. */
		
		private static int MAX_FRAME_SKIPS = 5;   // was 2;
    // no. of frames that can be skipped in any one animation loop
    // i.e the games state is updated but not rendered
		
		private static int NUM_FPS = 10;    
    // number of FPS values stored to get an average
		
		
		private int pWidth, pHeight;     // panel dimensions
		
		// used for gathering statistics
		private long statsInterval = 0L;    // in ns
		private long prevStatsTime;   
		private long totalElapsedTime = 0L;
		private long gameStartTime;
		private int timeSpentInGame = 0;    // in seconds
		
		private long frameCount = 0;
		private double fpsStore[];
		private long statsCount = 0;
		private double averageFPS = 0.0;
		
		private long framesSkipped = 0L;
		private long totalFramesSkipped = 0L;
		private double upsStore[];
		private double averageUPS = 0.0;
		
		
		private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp
		private DecimalFormat timedf = new DecimalFormat("0.####");  // 4 dp
		
		
		private Thread animator;            // the thread that performs the animation
		private volatile boolean running = false;    // used to stop the animation thread
		
		private long period;                 // period between drawing in _nanosecs_
		
		private int boxesUsed = 0;
		
		
		// used at game termination
		private volatile boolean gameOver = false;
		private int score = 0;
		private Font font;
		private FontMetrics metrics;
		private boolean finishedOff = false;
		
		// used by quit 'button'
		private volatile boolean isOverQuitButton = false;
		private Rectangle quitArea;
		
		// used by the pause 'button'
		private volatile boolean isOverPauseButton = false;l
		private Rectangle pauseArea;
		private volatile boolean isPaused = false;
		
		
		// used for full-screen exclusive mode  
		private GraphicsDevice gd;
		private Graphics gScr;
		private BufferStrategy bufferStrategy;
		
		
		boolean cFlagFullScreen = false;

		//------------------------------------------------
		public WT(long period) {
		
				super("Worm Chase");



				
				this.period = period;
				if( initFullScreen() == false ){
						//				initWindows();
				}
				
				readyForTermination();
				
				// create game components
				
				addMouseListener( new MouseAdapter() {
								public void mousePressed(MouseEvent e)
								{ testPress(e.getX(), e.getY()); }
						});

				addMouseMotionListener( new MouseMotionAdapter() {
								public void mouseMoved(MouseEvent e)
								{ testMove(e.getX(), e.getY()); }
						});
				
				// set up message font
				font = new Font("SansSerif", Font.BOLD, 24);
				metrics = this.getFontMetrics(font);
				
				// specify screen areas for the buttons
				pauseArea = new Rectangle(pWidth-100, pHeight-45, 70, 15);
				quitArea = new Rectangle(pWidth-100, pHeight-20, 70, 15);
				
				// initialise timing elements
				fpsStore = new double[NUM_FPS];
				upsStore = new double[NUM_FPS];
				for (int i=0; i < NUM_FPS; i++) {
						fpsStore[i] = 0.0;
						upsStore[i] = 0.0;
				}
				
				gameStart();
		}
		//----------------------------------------
		private boolean initFullScreen() {
		
				System.out.println("initFullScreen");
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				gd = ge.getDefaultScreenDevice();


				setUndecorated(true);    // no menu bar, borders, etc. or Swing components
				setIgnoreRepaint(true);  // turn off all paint events since doing active rendering
				setResizable(false);
				
				
				if (!gd.isFullScreenSupported()) {
						System.out.println("Full-screen exclusive mode not supported");
						cFlagFullScreen = false;
				}
				else 		{

						cFlagFullScreen = true;

						gd.setFullScreenWindow(this); // switch on full-screen exclusive mode
						// we can now adjust the display modes, if we wish
						showCurrentMode();
						
						// setDisplayMode(800, 600, 8);   // or try 8 bits
						// setDisplayMode(1280, 1024, 32);
				}
				
				
				
				pWidth = getBounds().width;
				pHeight = getBounds().height;

				System.out.println("initFullScreen bounds:"+pWidth+","+pHeight);
	
				setBufferStrategy();
				return true;
		} 		
		//------------------------------------------------
		// Switch on page flipping: NUM_BUFFERS == 2 so
		//	 there will be a 'primary surface' and one 'back buffer'.
		//		 
		//	 The use of invokeAndWait() is to avoid a possible deadlock
		//	 with the event dispatcher thread. Should be fixed in J2SE 1.5 
		//	 
		//	 createBufferStrategy) is an asynchronous operation, so sleep
		//	 a bit so that the getBufferStrategy() call will get the
		//	 correct details.
		
		private void setBufferStrategy() {
				
				
				System.out.println("setBufferStrategy");
				
				try {
						EventQueue.invokeAndWait( new Runnable() {
										public void run() 
										{ createBufferStrategy(NUM_BUFFERS);  }
								});
				}
				catch (Exception e) {  
						System.out.println("Error while creating buffer strategy : " + e);  
						System.exit(0);
				}
				
				try {  // sleep to give time for the buffer strategy to be carried out
						Thread.sleep(500);  // 0.5 sec
				}
				catch(InterruptedException ex){}
				
				bufferStrategy = getBufferStrategy();  // store for later
		} 
		//------------------------------------------------

		private void readyForTermination() {
				
				System.out.println("readyForTermination");
				
				addKeyListener( new KeyAdapter() {
								// listen for esc, q, end, ctrl-c on the canvas to
								// allow a convenient exit from the full screen configuration
								public void keyPressed(KeyEvent e)
								{ int keyCode = e.getKeyCode();
								if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q) ||
										(keyCode == KeyEvent.VK_END) ||
										((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
										running = false;
								}
								}
						});
				
				// for shutdown tasks
				// a shutdown may not only come from the program
				Runtime.getRuntime().addShutdownHook(new Thread() {
								public void run()
								{ running = false;   
								finishOff();
								}
						});
		} 	
		//------------------------------------------------
		
		private void gameStart() {
				// initialise and start the thread 
				
				System.out.println("gameStart");
				
				if (animator == null || !running) {
						animator = new Thread(this);
						animator.start();
				}
		}
		//------------------------------------------------
		// Deal with pause and quit buttons.
		// Also, is (x,y) near the head, or should an obstacle be added?
		
		private void testPress(int x, int y) {
				
				System.out.println("testPress");
				
				if (isOverPauseButton)
						isPaused = !isPaused;     // toggle pausing
				else if (isOverQuitButton)
						running = false;
				else {
						if (!isPaused && !gameOver) {
								gameOver = true;
								score =  (40 - timeSpentInGame) + (40 - boxesUsed);    
								// hack together a score
						}
				}
		}		
		//------------------------------------------------
		// is (x,y) over the pause or quit buttons?
		
		private void testMove(int x, int y) {
				
				
				System.out.println("testMove");
				
				if (running) {   // stops problems with a rapid move after pressing 'quit'
						isOverPauseButton = pauseArea.contains(x,y) ? true : false;
						isOverQuitButton = quitArea.contains(x,y) ? true : false;
				}
		}		
		//------------------------------------------------
		// called from Obstacles object
		
		public void setBoxNumber(int no) {
				boxesUsed = no;  
		}		
		//------------------------------------------------
		// The frames of the animation are drawn inside the while loop.
		
		public void run() {
				
				System.out.println("run");
				
				long beforeTime, afterTime, timeDiff, sleepTime;
				long overSleepTime = 0L;
				int noDelays = 0;
				long excess = 0L;
				
				gameStartTime = System.currentTimeMillis();
				prevStatsTime = gameStartTime;
				beforeTime = gameStartTime;
				
				
				//gameStartTime = J3DTimer.getValue();
				//    prevStatsTime = gameStartTime;
				//    beforeTime = gameStartTime;
				
				running = true;
				
				while(running) {
						//	  gameUpdate(); 
						if( cFlagFullScreen )
								fullScreenUpdate();
						else
								windowUpdate();

						//paintScreen();  // draw the buffer on-screen
						
						//      afterTime = J3DTimer.getValue();
						//      timeDiff = afterTime - beforeTime;
						//      sleepTime = (period - timeDiff) - overSleepTime;  
						
						afterTime = System.currentTimeMillis();
						timeDiff = afterTime - beforeTime;
						sleepTime = (period - timeDiff) - overSleepTime;  
						
						if (sleepTime > 0) {   // some time left in this cycle
								try {
										Thread.sleep(sleepTime);  // already in ms
								}
								catch(InterruptedException ex){}
								overSleepTime = (int)((System.currentTimeMillis() - afterTime) - sleepTime);
						}
						else {    // sleepTime <= 0; the frame took longer than the period
								excess -= sleepTime;  // store excess time value
								overSleepTime = 0;
								
								if (++noDelays >= NO_DELAYS_PER_YIELD) {
										Thread.yield();   // give another thread a chance to run
										noDelays = 0;
								}
						}
						
						beforeTime = System.currentTimeMillis();
						
						
						
						
						/*
							
						if (sleepTime > 0) {   // some time left in this cycle
						try {
						Thread.sleep(sleepTime/1000000L);  // nano -> ms
						}
						catch(InterruptedException ex){}
						overSleepTime = (J3DTimer.getValue() - afterTime) - sleepTime;
						}
						else {    // sleepTime <= 0; the frame took longer than the period
						excess -= sleepTime;  // store excess time value
						overSleepTime = 0L;
						
						if (++noDelays >= NO_DELAYS_PER_YIELD) {
						Thread.yield();   // give another thread a chance to run
						noDelays = 0;
						}
						}
						
						beforeTime = J3DTimer.getValue();
						*/
						
						/* If frame animation is taking too long, update the game state
							 without rendering it, to get the updates/sec nearer to
							 the required FPS. */
						int skips = 0;
						while((excess > period) && (skips < MAX_FRAME_SKIPS)) {
								excess -= period;
								gameUpdate();    // update state but don't render
								skips++;
						}
						framesSkipped += skips;
						
						storeStats();
				}
				finishOff();
		}		
		//------------------------------------------------		
		private void gameUpdate() {
				
				//if (!isPaused && !gameOver)
				//      fred.move();
		}		
		//------------------------------------------------
		// use active rendering
		
		private void fullScreenUpdate() {
				
				System.out.println("fullScreenUpdate");
				
				try {
						gScr = bufferStrategy.getDrawGraphics();
						gameRender(gScr);
						gScr.dispose();
						if (!bufferStrategy.contentsLost())
								bufferStrategy.show();
						else
								System.out.println("Contents Lost");
						// Sync the display on some systems.
						// (on Linux, this fixes event queue problems)
						Toolkit.getDefaultToolkit().sync();
				}
				catch (Exception e) 
						{ e.printStackTrace();  
      running = false; 
						} 
		}
		//------------------------------------------------
		// use active rendering
		
		private void windowUpdate() {
				
				System.out.println("windowUpdate");
				
				try {
						gScr = bufferStrategy.getDrawGraphics();
						gameRender(gScr);
						gScr.dispose();
						if (!bufferStrategy.contentsLost())
								bufferStrategy.show();
						else
								System.out.println("Contents Lost");
						// Sync the display on some systems.
						// (on Linux, this fixes event queue problems)
						Toolkit.getDefaultToolkit().sync();
				}
				catch (Exception e) 
						{ e.printStackTrace();  
      running = false; 
						} 
		}
		//------------------------------------------------		
		private void gameRender(Graphics gScr) {
				
				System.out.println("gameRender");
				
				// clear the background
				gScr.setColor(Color.white);
				gScr.fillRect (0, 0, pWidth, pHeight);
				
				gScr.setColor(Color.blue);
				gScr.setFont(font);
				
				// report frame count & average FPS and UPS at top left
				// gScr.drawString("Frame Count " + frameCount, 10, 25);
				gScr.drawString("Average FPS/UPS: " + df.format(averageFPS) + ", " +
												df.format(averageUPS), 20, 25);  // was (10,55)
				
				// report time used and boxes used at bottom left
				gScr.drawString("Time Spent: " + timeSpentInGame + " secs", 10, pHeight-15);
				gScr.drawString("Boxes used: " + boxesUsed, 260, pHeight-15);
				
				// draw the pause and quit 'buttons'
				drawButtons(gScr);

				gScr.setColor(Color.black);
				
				// draw game elements: the obstacles and the worm
				//    obs.draw(gScr);
				//    fred.draw(gScr);
				
				if (gameOver)
						gameOverMessage(gScr);
		} 		
		//------------------------------------------------		
		private void drawButtons(Graphics g) {

				System.out.println("gameRender");
				
				g.setColor(Color.black);

				// draw the pause 'button'
				if (isOverPauseButton)
						g.setColor(Color.green);
				
				g.drawOval( pauseArea.x, pauseArea.y, pauseArea.width, pauseArea.height);
				if (isPaused)
						g.drawString("Paused", pauseArea.x, pauseArea.y+10);
				else
						g.drawString("Pause", pauseArea.x+5, pauseArea.y+10);
				
				if (isOverPauseButton)
						g.setColor(Color.black);
				
				
				// draw the quit 'button'
				if (isOverQuitButton)
						g.setColor(Color.green);
				
				g.drawOval(quitArea.x, quitArea.y, quitArea.width, quitArea.height);
				g.drawString("Quit", quitArea.x+15, quitArea.y+10);
				
				if (isOverQuitButton)
						g.setColor(Color.black);
		}		
		//------------------------------------------------
		// center the game-over message in the panel

		private void gameOverMessage(Graphics g) {

				System.out.println("gameRender");

				String msg = "Game Over. Your Score: " + score;
				int x = (pWidth - metrics.stringWidth(msg))/2; 
				int y = (pHeight - metrics.getHeight())/2;
				g.setColor(Color.red);
				g.setFont(font);
				g.drawString(msg, x, y);
		} 		
		//------------------------------------------------
		/* The statistics:
       - the summed periods for all the iterations in this interval
			 (period is the amount of time a single frame iteration should take), 
			 the actual elapsed time in this interval, 
			 the error between these two numbers;
			 
       - the total frame count, which is the total number of calls to run();
			 
       - the frames skipped in this interval, the total number of frames
			 skipped. A frame skip is a game update without a corresponding render;
			 
       - the FPS (frames/sec) and UPS (updates/sec) for this interval, 
			 the average FPS & UPS over the last NUM_FPSs intervals.
			 
			 The data is collected every MAX_STATS_INTERVAL  (1 sec).
		*/		

		private void storeStats() {

				frameCount++;
				statsInterval += period;
				
				if (statsInterval >= MAX_STATS_INTERVAL) {     // record stats every MAX_STATS_INTERVAL
						long timeNow = System.currentTimeMillis();
						timeSpentInGame = (int) ((timeNow - gameStartTime)/1000L);  // ms --> secs
						
						long realElapsedTime = timeNow - prevStatsTime;   // time since last stats collection
						totalElapsedTime += realElapsedTime;
						
						double timingError = 
								((double)(realElapsedTime - statsInterval) / statsInterval) * 100.0;
						
						totalFramesSkipped += framesSkipped;
						
						double actualFPS = 0;     // calculate the latest FPS and UPS
						double actualUPS = 0;
						if (totalElapsedTime > 0) {
								actualFPS = (((double)frameCount / totalElapsedTime) * 1000000000L);
								actualUPS = (((double)(frameCount + totalFramesSkipped) / totalElapsedTime) 
														 * 1000000000L);
						}
						
						// store the latest FPS and UPS
						fpsStore[ (int)statsCount%NUM_FPS ] = actualFPS;
						upsStore[ (int)statsCount%NUM_FPS ] = actualUPS;
						statsCount = statsCount+1;
						
						double totalFPS = 0.0;     // total the stored FPSs and UPSs
						double totalUPS = 0.0;
						for (int i=0; i < NUM_FPS; i++) {
								totalFPS += fpsStore[i];
								totalUPS += upsStore[i];
						}
						
						if (statsCount < NUM_FPS) { // obtain the average FPS and UPS
								averageFPS = totalFPS/statsCount;
								averageUPS = totalUPS/statsCount;
						}
						else {
								averageFPS = totalFPS/NUM_FPS;
        averageUPS = totalUPS/NUM_FPS;
						}
						/*
							System.out.println(timedf.format( (double) statsInterval/1000000000L) + " " + 
							timedf.format((double) realElapsedTime/1000000000L) + "s " + 
			        df.format(timingError) + "% " + 
							frameCount + "c " +
							framesSkipped + "/" + totalFramesSkipped + " skip; " +
							df.format(actualFPS) + " " + df.format(averageFPS) + " afps; " + 
							df.format(actualUPS) + " " + df.format(averageUPS) + " aups" );
						*/
						framesSkipped = 0;
						prevStatsTime = timeNow;
						statsInterval = 0L;   // reset
				}
		}
		//------------------------------------------------
		/* Tasks to do before terminating. Called at end of run()
			 and via the shutdown hook in readyForTermination().
			 
			 The call at the end of run() is not really necessary, but
			 included for safety. The flag stops the code being called
			 twice.
		*/
		
		private void finishOff() {
				
				System.out.println("finishOff");

				if (!finishedOff) {
						finishedOff = true;
						printStats();
						restoreScreen();
						System.exit(0);
				}
		} 				
		//------------------------------------------------		
		private void printStats() {
				
				System.out.println("Frame Count/Loss: " + frameCount + " / " + totalFramesSkipped);
				System.out.println("Average FPS: " + df.format(averageFPS));
				System.out.println("Average UPS: " + df.format(averageUPS));
				System.out.println("Time Spent: " + timeSpentInGame + " secs");
				System.out.println("Boxes used: " + boxesUsed);
		} 		
		//------------------------------------------------
		// Switch off full screen mode. This also resets the
		// display mode if it's been changed. 
		
		private void restoreScreen(){
				
				Window w = gd.getFullScreenWindow();
				if (w != null)
						w.dispose();
				gd.setFullScreenWindow(null);
		} 
		// ------------------ display mode methods -------------------
		// attempt to set the display mode to the given width, height, and bit depth
		
		private void setDisplayMode(int width, int height, int bitDepth) {
								
				System.out.println("setDisplayMode");
				
				if (!gd.isDisplayChangeSupported()) {
						System.out.println("Display mode changing not supported");
						return;
				}
				
				if (!isDisplayModeAvailable(width, height, bitDepth)) {
						System.out.println("Display mode (" + width + "," +
															 height + "," + bitDepth + ") not available");
						return;
				}
				
				DisplayMode dm = new DisplayMode(width, height, bitDepth, 
																				 DisplayMode.REFRESH_RATE_UNKNOWN);   // any refresh rate
				try {
						gd.setDisplayMode(dm);
						System.out.println("Display mode set to: (" + width + "," +
															 height + "," + bitDepth + ")");
				}
				catch (IllegalArgumentException e) 
						{  System.out.println("Error setting Display mode (" + width + "," +
																	height + "," + bitDepth + ")");  }
				
				try {  // sleep to give time for the display to be changed
						Thread.sleep(1000);  // 1 sec
				}
				catch(InterruptedException ex){}
		}  
		
		//------------------------------------------------
		// Check that a displayMode with this width, height, bit depth is available.
    // We don't care about the refresh rate, which is probably 
    // REFRESH_RATE_UNKNOWN anyway. 

		
		private boolean isDisplayModeAvailable(int width, int height, int bitDepth) {

				DisplayMode[] modes = gd.getDisplayModes();
				showModes(modes);
				
				for(int i = 0; i < modes.length; i++) {
						if (width == modes[i].getWidth() && height == modes[i].getHeight() &&
								bitDepth == modes[i].getBitDepth())
								return true;
				}
				return false;
		} 		
		//------------------------------------------------
		// pretty print the display mode information in modes
		
		private void showModes(DisplayMode[] modes) {
				
				System.out.println("Modes");
				
				for(int i = 0; i < modes.length; i++) {
						System.out.print("(" + modes[i].getWidth() + "," +
														 modes[i].getHeight() + "," +
														 modes[i].getBitDepth() + "," +
														 modes[i].getRefreshRate() + ")  " );
						if ((i+1)%4 == 0)
								System.out.println();
				}
				System.out.println();
		}	 		
		//------------------------------------------------
		// print the display mode details for the graphics device
		
		private void showCurrentMode() {
				
				DisplayMode dm = gd.getDisplayMode();
				System.out.println("Current Display Mode: (" + 
                           dm.getWidth() + "," + dm.getHeight() + "," +
                           dm.getBitDepth() + "," + dm.getRefreshRate() + ")  " );
		}	 
		//----------------------------------------		
		//----------------------------------------		
		//----------------------------------------		
		static private void ReportCapabilities( GraphicsDevice lGd ) {
				
				GraphicsConfiguration gc = lGd.getDefaultConfiguration();
				
				// Image Capabilities
				ImageCapabilities imageCaps = gc.getImageCapabilities();
				System.out.println("Image Caps. isAccelerated: " + imageCaps.isAccelerated() );
				System.out.println("Image Caps. isTrueVolatile: " + imageCaps.isTrueVolatile());
				
				// Buffer Capabilities
				BufferCapabilities bufferCaps = gc.getBufferCapabilities();
				System.out.println("Buffer Caps. isPageFlipping: " + bufferCaps.isPageFlipping());

				System.out.println("Buffer Caps. Full-screen Required: " + 
													 bufferCaps.isFullScreenRequired());
				System.out.println("Buffer Caps. MultiBuffers: " + bufferCaps.isMultiBufferAvailable());

				if (bufferCaps.getFlipContents() == null)
						return "false";
				else if (flip == BufferCapabilities.FlipContents.UNDEFINED)
						return "Undefined";
				else if (flip == BufferCapabilities.FlipContents.BACKGROUND)
						return "Background";
				else if (flip == BufferCapabilities.FlipContents.PRIOR)
						return "Prior";
				else // if (flip == BufferCapabilities.FlipContents.COPIED)
						return "Copied";
		} 		
	
		// -----------------------------------------

		public static void main(String args[]) {

				GraphicsEnvironment lGe = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice      lGd = lGe.getDefaultScreenDevice();

				WT.ReportCapabilities( lGd );
				/*

				int fps = DEFAULT_FPS;
				if (args.length != 0)
						fps = Integer.parseInt(args[0]);

				long period = (long) 1000.0/fps;
				System.out.println("fps: " + fps + "; period: " + period + " ms");
				new WT(period*1000000L);    // ms --> nanosecs
				*/
  } 
} 

//*************************************************


