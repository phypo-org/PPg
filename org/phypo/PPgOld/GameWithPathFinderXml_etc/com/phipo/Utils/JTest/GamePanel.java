
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.DecimalFormat;



//*************************************************

public class GamePanel extends JPanel implements Runnable
{
		//  private static final int PWIDTH = 500;   // size of panel
		//  private static final int PHEIGHT = 400; 

  // private static long MAX_STATS_INTERVAL = 1000000000L;
  private static long MAX_STATS_INTERVAL = 1000L;
    // record stats every 1 second (roughly)

  private static final int NO_DELAYS_PER_YIELD = 16;
  /* Number of frames with a delay of 0 ms before the animation thread yields
     to other running threads. */

  private static int MAX_FRAME_SKIPS = 5;   // was 2;
    // no. of frames that can be skipped in any one animation loop
    // i.e the games state is updated but not rendered

  private static int NUM_FPS = 10;
     // number of FPS values stored to get an average


  // used for gathering statistics
  private long statsInterval = 0L;    // in ms
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


  private Thread animator;           // the thread that performs the animation
  private volatile boolean running = false;   // used to stop the animation thread
  private volatile boolean isPaused = false;

  private int period;                // period between drawing in _ms_



  // used at game termination
  private volatile boolean gameOver = false;
  private int score = 0;
  private Font font;
  private FontMetrics metrics;

  // off screen rendering
  private Graphics2D dbg; 
  private Image dbImage = null;


  private static final int PWIDTH = 500;   // size of panel
  private static final int PHEIGHT = 400; 
		
		//------------------------------------------------
		public GamePanel() {
				
				this.period = period;
				
				setBackground(Color.white);
				setPreferredSize( new Dimension(PWIDTH, PHEIGHT));
				
				setFocusable(true);
				requestFocus();    // the JPanel now has focus, so receives key events

				readyForTermination();
				
				
				addMouseListener( new MouseAdapter() {
								public void mousePressed(MouseEvent e)
								{ testMousePress( e ); }
						});
				addMouseMotionListener( new MouseMotionAdapter() {
								public void mouseMoved(MouseEvent e)
								{ testMouseMove( e); }
						});
						
				// set up message font
				font = new Font("SansSerif", Font.BOLD, 24);
				metrics = this.getFontMetrics(font);
				
				// initialise timing elements
				fpsStore = new double[NUM_FPS];
				upsStore = new double[NUM_FPS];
				for (int i=0; i < NUM_FPS; i++) {
						fpsStore[i] = 0.0;
						upsStore[i] = 0.0;
				}
		} 		
		//------------------------------------------------		
		private void readyForTermination() {
		
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
		}		
		//------------------------------------------------
		// wait for the JPanel to be added to the JFrame before starting
		
		public void addNotify(){
				
				super.addNotify();   // creates the peer
				startGame();    // start the thread
		}		
		//------------------------------------------------		
		private void testMouseEvent( MouseEvent pEv) {
				
				System.out.println("testPress");
				
		}		
		//------------------------------------------------
		
		private void testMouseMove( MouseEvent pEv ) {
								
				System.out.println("testMove");				
		}		

		// ----------------------------------------------
		// initialise and start the thread 

		private void startGame() {
				
				if (animator == null || !running) {
						animator = new Thread(this);
						animator.start();
				}
		}
    
		// ------------- game life cycle methods ------------
		// called by the JFrame's window listener methods
		
		
		// called when the JFrame is activated / deiconified
		public void resumeGame() { isPaused = false;  } 
		
		
		// called when the JFrame is deactivated / iconified
		public void pauseGame() { isPaused = true;   } 
		
		
		// called when the JFrame is closing
		public void stopGame() {  running = false;   }
		
		// ----------------------------------------------
		
		
		public void run() {
				/* The frames of the animation are drawn inside the while loop. */

				long beforeTime, afterTime, timeDiff, sleepTime;
				int overSleepTime = 0;
				int noDelays = 0;
				int excess = 0;
				Graphica2D g;
				
				gameStartTime = System.currentTimeMillis();
				prevStatsTime = gameStartTime;
				beforeTime = gameStartTime;
				
				running = true;
				
				while(running) {
						gameUpdate(); 
						gameRender();   // render the game to a buffer
						paintScreen();  // draw the buffer on-screen
						
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
				
				printStats();
				System.exit(0);   // so window disappears
		} // end of run()
		
		//------------------------------------------------
		private void gameUpdate() {
				if (!isPaused && !gameOver){
				}
		} 
				
		//------------------------------------------------
		private void gameRender(){
  
				if (dbImage == null){
						dbImage = createImage(PWIDTH, PHEIGHT);
						if (dbImage == null) {
								System.out.println("dbImage is null");
								return;
						}
						else
								dbg = dbImage.getGraphics();
				}
				
				// clear the background
				dbg.setColor(Color.white);
				dbg.fillRect (0, 0, PWIDTH, PHEIGHT);
				
				dbg.setColor(Color.blue);
				dbg.setFont(font);
				
				// report frame count & average FPS and UPS at top left
				// dbg.drawString("Frame Count " + frameCount, 10, 25);
				dbg.drawString("Average FPS/UPS: " + df.format(averageFPS) + ", " +
                                df.format(averageUPS), 20, 25);  // was (10,55)
				
				dbg.setColor(Color.black);				

		} 


		//------------------------------------------------
		// center the game-over message in the panel
		
		private void gameOverMessage(Graphics2D g){
		
				String msg = "Game Over. Your Score: " + score;
				int x = (PWIDTH - metrics.stringWidth(msg))/2; 
				int y = (PHEIGHT - metrics.getHeight())/2;
				g.setColor(Color.red);
				g.setFont(font);
				g.drawString(msg, x, y);
		}  // end of gameOverMessage()
		

		//------------------------------------------------
		
		// use active rendering to put the buffered image on-screen
		private void paintScreen() {
				Graphic2Ds g;
				try {
						g = (Graphics2D) this.getGraphics();
						if ((g != null) && (dbImage != null))
								g.drawImage(dbImage, 0, 0, null);
						Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
						g.dispose();
				}
				catch (Exception e)
						{ System.out.println("Graphics error: " + e);  }
		} // end of paintScreen()

}  // end of GamePanel class


//*************************************************
