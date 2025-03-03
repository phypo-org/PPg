import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.awt.image.BufferStrategy;



//*************************************************

public class JTest extends JFrame  implements WindowListener, Runnable {
	

		private static final int NUM_BUFFERS = 2;    // used for page flipping
	
		static boolean sFlagFullScreen = true;
		static JTest   sTheAppli= null;
		static String  sTheName = null;

		
		GamePanel cGamePanel = null;



		//------------------------------------------------

		public JTest( String pName) {
				super( pName );

				sTheName = pName;
				sTheAppli = this;

				
				if( sFlagFullScreen == false || initFullScreen() == false ){

						cGamePanel = new GamePanel();
						
						setContentPane( cGamePanel );
						addWindowListener( this );
						pack();
						//						setResizable(false);
						setVisible(true);
				}
				else {
						readyForTermination();
						
						// create game components
						
						addMouseListener( new MouseAdapter() {
										public void mousePressed(MouseEvent e)
										{ testMouseEvent( e); }
								});
						
						addMouseMotionListener( new MouseMotionAdapter() {
										public void mouseMoved(MouseEvent e)
										{ testMouseMove( e); }
								});
						
						// set up message font
						font = new Font("SansSerif", Font.BOLD, 12);
						metrics = this.getFontMetrics(font);
						
						// initialise timing elements
						fpsStore = new double[NUM_FPS];
						upsStore = new double[NUM_FPS];
						for (int i=0; i < NUM_FPS; i++) {
								fpsStore[i] = 0.0;
								upsStore[i] = 0.0;
						}
						
						gameStart();	
				}
		}
		//--------------------------------------------------------------------------		
		public void windowActivated(WindowEvent e)   {  cGamePanel.resumeGame(); }		
		public void windowDeactivated(WindowEvent e) {  cGamePanel.pauseGame();  }
		
		public void windowDeiconified(WindowEvent e) {  cGamePanel.resumeGame(); }		
		public void windowIconified(WindowEvent e)   {  cGamePanel.pauseGame();  }
		
		
		public void windowClosing(WindowEvent e) {  cGamePanel.stopGame();  }
		
		public void windowClosed(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}

		//----------------------------------------
		//        FULL SCREEN
		//----------------------------------------

		private Thread cAnimator;            // the thread that performs the animation
		private volatile boolean cRunning = false;    // used to stop the animation thread



		private long period;                 // period between drawing in _nanosecs_
		private static final int NO_DELAYS_PER_YIELD = 16;
		private static int MAX_FRAME_SKIPS = 5;   // was 2;
		private static int NUM_FPS = 10;    

		private static long MAX_STATS_INTERVAL = 1000000000L;

		// used for gathering statistics
		private long statsInterval = 0L;    // in ns
		private long prevStatsTime;   
		private long totalElapsedTime = 0L;
		private long gameStartTime;
		
		private long frameCount = 0;
		private double fpsStore[];
		private long statsCount = 0;
		private double averageFPS = 0.0;
		
		private long framesSkipped = 0L;
		private long totalFramesSkipped = 0L;
		private double upsStore[];
		private double averageUPS = 0.0;
		
		// used at game termination
		private volatile boolean cGameOver = false;
		private volatile boolean cIsPaused = false;
		private int score = 0;
		private Font font;
		private FontMetrics metrics;
		private boolean finishedOff = false;
		

		// used for full-screen exclusive mode  
		private GraphicsDevice cGd;
		private BufferStrategy cBufferStrategy;

		private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp
		private DecimalFormat timedf = new DecimalFormat("0.####");  // 4 dp

		private int cWidth, cHeight;     // panel dimensions


		//------------------------------------------------
		private boolean initFullScreen( ) {
		
				//				System.out.println("initFullScreen");

				setUndecorated(true);    // no menu bar, borders, etc. or Swing components
				setIgnoreRepaint(true);  // turn off all paint events since doing active rendering
				setResizable(false);
				
				GraphicsEnvironment lGe = GraphicsEnvironment.getLocalGraphicsEnvironment();
				cGd	= lGe.getDefaultScreenDevice();
				
			 
				cGd.setFullScreenWindow(this); // switch on full-screen exclusive mode
				// we can now adjust the display modes, if we wish
				DisplayMode lDm = cGd.getDisplayMode();
				//				System.out.println("Current Display Mode: (" + 
				//                           lDm.getWidth()    + "," + lDm.getHeight() + "," +
				//                           lDm.getBitDepth() + "," + lDm.getRefreshRate() + ")  " );
				
				// setDisplayMode(800, 600, 8);   // or try 8 bits
				// setDisplayMode(1280, 1024, 32);
								
				
				cWidth = getBounds().width;
				cHeight = getBounds().height;

	
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
				
				cBufferStrategy = getBufferStrategy();  // store for later
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
										cRunning = false;
								}
								}
						});
				
				// for shutdown tasks
				// a shutdown may not only come from the program
				Runtime.getRuntime().addShutdownHook(new Thread() {
								public void run()
								{ cRunning = false;   
								finishOff();
								}
						});
		} 	
		//------------------------------------------------
		
		private void gameStart() {
				// initialise and start the thread 
				
				System.out.println("gameStart");
				
				if (cAnimator == null || !cRunning) {
						cAnimator = new Thread(this);
						cAnimator.start();
				}
		}
		//------------------------------------------------		
		private void testMouseEvent( MouseEvent pEv) {
				
				System.out.println("testPress");
				// World.cGamer
				
		}		
		//------------------------------------------------
		
		private void testMouseMove( MouseEvent pEv ) {
								
				System.out.println("testMove");				
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
				
				cRunning = true;
				
				while(cRunning) {
						screenUpdate();

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
				
				if (!cIsPaused && !cGameOver){
				// fonctionnement du jeu sans affichage !!
				}
		}		
		//------------------------------------------------
		// use active rendering
		
		private void screenUpdate() {
				
				
				try {
						Graphics2D lGScr = (Graphics2D) cBufferStrategy.getDrawGraphics();
						
						gameRender(lGScr);

						lGScr.dispose();
						if (!cBufferStrategy.contentsLost())
								cBufferStrategy.show();
						else
								System.out.println("Contents Lost");

						// Sync the display on some systems.
						// (on Linux, this fixes event queue problems)

						Toolkit.getDefaultToolkit().sync();
				}
				catch (Exception e) {
						e.printStackTrace();  
						cRunning = false; 
				} 
		}	
		//------------------------------------------------		
		private void gameRender(Graphics2D gScr) {
				
				//				System.out.println("gameRender");

				
				// clear the background
				gScr.setColor(Color.white);
				gScr.fillRect (0, 0, cWidth, cHeight);

				// si besoin afficher image background !

				// APPELER L'AFICHAGE DU JEU !!!
				// World.draw( temps ! );

				gScr.setColor(Color.blue);
				gScr.setFont(font);
				
				// report frame count & average FPS and UPS at top left
			  gScr.drawString("Frame Count " + frameCount + " Average FPS/UPS: " + df.format(averageFPS) + ", " +
												df.format(averageUPS), 10, 25);
		} 		
		//------------------------------------------------
		// center the game-over message in the panel

		private void gameOverMessage(Graphics2D g) {

				//				System.out.println("gameRender");

				String msg = "Game Over. Your Score: " + score;
				int x = (cWidth - metrics.stringWidth(msg))/2; 
				int y = (cHeight - metrics.getHeight())/2;
				g.setColor(Color.red);
				g.setFont(font);
				g.drawString(msg, x, y);
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
		// Switch off full screen mode. This also resets the
		// display mode if it's been changed. 
		
		private void restoreScreen(){
				
				Window w = cGd.getFullScreenWindow();
				if (w != null)
						w.dispose();
				cGd.setFullScreenWindow(null);
		} 

		//--------------------------------------------------------------------------		
		//--------------------------------------------------------------------------		
		//--------------------------------------------------------------------------		
		static private void ReportCapabilities( GraphicsDevice lGd ) {
				
				GraphicsConfiguration gc = lGd.getDefaultConfiguration();
				
				System.out.println("Full-screen exclusive supported: " + lGd.isFullScreenSupported());

				// Image Capabilities
				ImageCapabilities imageCaps = gc.getImageCapabilities();
				System.out.println("Image Caps. isAccelerated: " + imageCaps.isAccelerated() );
				System.out.println("Image Caps. isTrueVolatile: " + imageCaps.isTrueVolatile());
				
				// Buffer Capabilities
				BufferCapabilities bufferCaps = gc.getBufferCapabilities();

				System.out.println("Buffer Caps. isPageFlipping: " + bufferCaps.isPageFlipping());				
				System.out.println("Buffer Caps. Full-screen Required: " +  bufferCaps.isFullScreenRequired());
				System.out.println("Buffer Caps. MultiBuffers: " + bufferCaps.isMultiBufferAvailable());
		} 

//------------------------------------------------

  static String GetParamString( String[] args, String p_prefix, String pDefault ){
		
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
								{
										return arg.substring( l );
								}
				}
				return pDefault;
		}
		//---------------------------------
		static boolean ExistParam( String[] args, String p_prefix){
				
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
								{
										return true;
								}
				}
				return false;
		}
		//---------------------------------
		
		static Integer GetParamInt( String[] args, String p_prefix, Integer pDefault){
				
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
								{
										try{
												return new Integer( arg.substring(l));
										}catch(NumberFormatException ex){
												System.out.println( "Mauvais format pour commande "+p_prefix);
												return null;
										}					
								}
				}
				return pDefault;
		}
		//---------------------------------
		static boolean GetParam( String[] args, String p_prefix ){
				
				int l = p_prefix.length();
				
				for( int i=0; i<  args.length; i++){
						
						String arg = args[i];
						
						if( arg.startsWith( p_prefix ))
										return true;
				}
				return false;
		}		
		//---------------------------------
		//---------------------------------
		//            MAIN
		//---------------------------------
		//---------------------------------

			public static void main(String args[]) {
					
					GraphicsEnvironment lGe = GraphicsEnvironment.getLocalGraphicsEnvironment();
					GraphicsDevice      lGd = lGe.getDefaultScreenDevice();
					
					if( JTest.ExistParam( args, "-W" ) || lGd.isFullScreenSupported() == false ) { // on veut une fenetre
							JTest.sFlagFullScreen = false;
					}

					new JTest( "JTest");
					
					JTest.ReportCapabilities( lGd );				
			}
}


//*************************************************
