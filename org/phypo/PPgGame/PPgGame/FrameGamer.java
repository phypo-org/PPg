package org.phypo.PPgGame.PPgGame;




import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.WindowConstants;
import java.util.*;

import org.phypo.PPg.PPgUtils.*;


//***********************************
// La Frame ou le joueur interagit
//***********************************

//public class FrameGamer extends JFrame implements InterfaceDisplayGamer,  WindowListener {
//public class FrameGamer extends JDialog implements InterfaceDisplayGamer,  WindowListener {
public class FrameGamer implements InterfaceDisplayGamer,  WindowListener, MouseMotionListener, MouseListener,  ActionListener, ComponentListener, KeyListener{

		JDialog cMyDialog;
		JFrame  cMyFrame;

		Window  cMyWindow;
		InterfaceDisplayGamer cDisplayGamer;

		public Window getWindow() { return cMyWindow;}
		public JFrame getJFrame() { return  cMyFrame;}

		GamerHuman cGamerHuman;

    static FrameGamer sTheFrameGamer = null;
		static public FrameGamer Get() { return sTheFrameGamer; }
		
		Color      cMyBackgroundColor = Color.black;
		public void setMyBackgroundColor( Color pColor ) {
				cMyBackgroundColor= pColor;
				cMyWindow.setBackground( pColor );
		}

		boolean cTranslucent = false;
		public void setTranslucent(){
				cTranslucent = true;
				cMyBackgroundColor = new Color( 0,0,0,0 );
		}
		

	// 	String   cName = null;

	 	protected int cWidth  = 800;
	 	protected int cHeight = 600;

  	ArrayList<PanelBox> cVectPanelBox = new ArrayList<PanelBox>();


		// BUFFERS
		BufferedImage  cCurrentBufImg = null;
		Graphics2D     cCurrentBufGraf = null;
		Dimension      cBufSize = null;

		BufferedImage cBufImgA = null;
		BufferedImage cBufImgB = null;
		Graphics2D    cBufGrafB = null;
		Graphics2D    cBufGrafA = null;
		//-------------------------- 
		public Dimension getSize() { return new Dimension( cWidth, cHeight ); }
		//-------------------------- 
		void initBufferImage(){
					System.out.println( "*** GamePanel.initBufferImage ");

				cBufSize = getSize();
				if( cBufSize.getWidth() <= 0 || cBufSize.getHeight() <=0 ) {
						//						System.out.println( "*** GamePanel.initBufferImage : ERROR bad size : " + cBufSize.getWidth() + " " + cBufSize.getHeight()  );
						return;
				}

				cBufImgA =  new BufferedImage( (int)cBufSize.getWidth(),  (int) cBufSize.getHeight(),
																			 //															 BufferedImage.TYPE_INT_ARGB_PRE);
																								 BufferedImage.TYPE_BYTE_INDEXED );
				cBufImgB =  new BufferedImage( (int)cBufSize.getWidth(),  (int) cBufSize.getHeight(),
																			 //																 BufferedImage.TYPE_INT_ARGB_PRE);
														 BufferedImage.TYPE_BYTE_INDEXED );
				cBufGrafA = cBufImgA.createGraphics();
				cBufGrafB = cBufImgB.createGraphics();
				if( cTranslucent == false ){
						//			System.out.println( "GamePanel.initBufferImage  fillRect");

						cBufGrafA.setColor( cMyBackgroundColor );
						cBufGrafB.setColor( cMyBackgroundColor );
						cBufGrafA.fillRect(0, 0,  (int)cBufSize.getWidth(),   (int)cBufSize.getHeight());				
						cBufGrafB.fillRect(0, 0,  (int)cBufSize.getWidth(),   (int)cBufSize.getHeight());				
				} else {

						//		System.out.println( "GamePanel.initBufferImage  Translucent");

						AlphaComposite lCompositeClear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f );

						Composite lMemComposite = cBufGrafA.getComposite();
						cBufGrafA.setComposite(lCompositeClear);
						//			cBufGrafA.setColor( new Color( 0, 0, 0, 0)); // ????
						
						cBufGrafA.fillRect(0, 0, (int)cBufSize.getWidth(), (int)cBufSize.getHeight());
						cBufGrafA.setComposite(lMemComposite);

						lMemComposite = cBufGrafB.getComposite();
						cBufGrafB.setComposite(lCompositeClear);
						//			cBufGrafB.setColor( new Color( 0, 0, 0, 0)); // ????
						
						cBufGrafB.fillRect(0, 0, (int)cBufSize.getWidth(), (int)cBufSize.getHeight());
						cBufGrafB.setComposite(lMemComposite);
				}

				
				swapBuffer();
		}
		//-------------------------------------
		void swapBuffer(){

				if( cBufSize == null ) {
						//						System.out.println( "*** GamePanel.swapBuffer : ERROR buffer is null"  );
						return;
				}

				if( cCurrentBufImg == cBufImgA ){
						
						cCurrentBufImg = cBufImgB;
						cCurrentBufGraf = cBufGrafB;

						//						actualize(); 
						//						System.out.println( "GamePanel.swapBuffer setDrawReady A"  );
						setDrawReady( cBufGrafA );
				}
				else {

						cCurrentBufImg  = cBufImgA;
						cCurrentBufGraf = cBufGrafA;

						//						actualize();
						//						System.out.println( "GamePanel.swapBuffer setDrawReady B"  );
						setDrawReady( cBufGrafB );
				}

				//				System.out.println( "GamePanel.swapBuffer" );
				paintScreen();
		}
		//------------------------------------------	
		public void paintScreen( ){
				
				//				System.out.println( "GamePanel.paintScreen" );

				if( cCurrentBufImg == null )
						initBufferImage();
				
				Graphics2D g;
				try {
						g = (Graphics2D) cMyWindow.getGraphics();
						if ((g != null) && ( cCurrentBufImg != null)){		

								AlphaComposite lComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f ); // EXP
								g.setComposite(lComposite); // EXP
								
								g.drawImage( cCurrentBufImg, 0, 0, null );
									//		g.setColor( Color.green );  // for debug
									//		g.fillRect( 0, 0, 150, 150 );
						}		
						Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
						g.dispose();
				}
				catch (Exception e)
						{ System.out.println("Graphics error: " + e);  }				
		}
		//-------------------------- 


		//-------------------------- 
		//		public void setBackground( Color pColor ) {
		//				super.setBackground( pColor );
		//		}
	 	//-------------------------- 
  	public PanelBox findPanelBox( int x, int y ){

				int lSz = cVectPanelBox.size()-1;
				for( int i =lSz; i >=0 ; i--) {
						
						//	 			for( PanelBox lPanel:cVectPanelBox ){
						PanelBox lPanel = cVectPanelBox.get(i);
	 					if( lPanel.contains( x, y ) )
	 							return lPanel;
	 			}
	 			return null;
	 	}
		//-------------------------- 
		public void addPanelBox( PanelBox pPanelBox ){

				System.out.println( "+++++++++++++++++ FrameGamer.addPanelBox" );

	 			cVectPanelBox.add( pPanelBox );
		}
		
		//-------------------------------------
		public void setGamerHuman( GamerHuman pGamer ) {
				cGamerHuman = pGamer;
				cGamerHuman.setDisplayGamer( this  );
				
		}
		
		//-------------------------------------
		// FAIRE UN typedef <JDialog> ou <JFrame>

		public FrameGamer( JDialog pDialog, JFrame pFrame, String pName, 	Point2D.Double pPosViewInit, boolean  pFlagFullScreen, boolean pFlagDeco, int pWidth, int pHeight, int pDepth ){
				if( pDialog != null)
						cMyWindow = cMyDialog = pDialog;
				else if( pFrame!= null )
						cMyWindow = cMyFrame = pFrame;
				//super( pName );				

				cMyWindow.createBufferStrategy( 1 );

				init( pPosViewInit, pFlagFullScreen, pFlagDeco, pWidth, pHeight, pDepth );
		}
		//-------------------------------------
		public FrameGamer( JDialog pDialog, JFrame pFrame, String pName, String[] args  ){
				//	super( pName );		
					if( pDialog != null)
						cMyWindow = cMyDialog = pDialog;
				else if( pFrame != null)
						cMyWindow = cMyFrame = pFrame;
	
				initFromParam( args );
		}
		//-------------------------------------
		//-------------------------------------

		public void initFromParam( String[] args  ){
		
				World.sVerbose  = PPgParam.GetInt( args, "-v", 0 );				
				World.sDataPath = PPgParam.GetString( args, "-D", World.sDataPath );
				World.sLevel    = PPgParam.GetString( args, "-L", World.sLevel );

				///				IniParam.ReadIni(lIniObj);
				World.sFurtifMode     = PPgParam.ExistParam( args, "--F" );
				World.sDebugMode      = PPgParam.GetInt( args, "--G", 0 );
				World.sTraceMode      = PPgParam.GetInt( args, "--T", 0 );


				System.out.println( ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Furtif:" + World.sFurtifMode
														+ " Debug:"   + World.sDebugMode
														+ " Trace:"   + World.sTraceMode
														+ " Verbose:" + World.sVerbose );
				
	
				int lW      = PPgParam.GetInt( args, "-w", 800 );
				int lH      = PPgParam.GetInt( args, "-h", 600 );


				int lDepth  = 0;

				boolean lFlagDeco = !PPgParam.ExistParam( args, "-D" );
				

				boolean lFlagFullScreen = false;
				String lStrFullScreen = PPgParam.GetString( args, "-S", null );


				if(lStrFullScreen != null ){

						lFlagFullScreen = true;
						if( lStrFullScreen.compareTo( "800x600-8" )==0 ){
								lW = 800;
								lH = 600;
								lDepth = 8;
						}
						else
						if( lStrFullScreen.compareTo( "1280x1024-32" )==0 ){
								lW = 1280;
								lH = 1024;
								lDepth = 32;
						}
						else {
								// garde le mode courant !!!
								lW=0;
								lH=0;
								lDepth = 0;
						}								
				}

				init( new Point2D.Double( 200, 200 ), lFlagFullScreen, lFlagDeco, lW, lH, lDepth );								
		}
		//-------------------------------------
		public void init(	Point2D.Double pPosViewInit, boolean  pFlagFullScreen, boolean pFlagDeco, int pWidth, int pHeight, int pDepth ){

				sTheFrameGamer = this;
				//				setViewPoint( pPosViewInit );
				
				if( pFlagDeco == false ) {

						if( cMyFrame != null ) {								
								cMyFrame.setUndecorated(true);    // no menu bar, borders, etc. or Swing components
								cMyFrame.setResizable(false); ///// setResizable(false);
						}
						else  {								
								cMyDialog.setUndecorated(true);    // no menu bar, borders, etc. or Swing components
								cMyDialog.setResizable(false); ///// setResizable(false);
						}
						
						cMyWindow.setIgnoreRepaint(true);  // turn off all paint events since doing active rendering
				}
				
				
				
				//				if( pFlagFullScreen == false || initFullScreen( pWidth, pHeight, pDepth ) == false ){
				
				cWidth  = pWidth;
				cHeight = pHeight;
				cMyWindow.setPreferredSize( new Dimension( pWidth, pHeight));
		
				cMyWindow.setFocusable(true);
				cMyWindow.requestFocus();    // the JPanel now has focus, so receives key events

				//						cWidth  = 800;
				//						cHeight = 600;
				
				cMyWindow.setSize( cWidth, cHeight );
				
				//				if( cMyFrame != null ) {				cMyFrame.setContentPane( (cGamePanel= new GamePanel( this, this, cWidth, cHeight ))); }
				//			else  {				cMyDialog.setContentPane( (cGamePanel= new GamePanel( this, this, cWidth, cHeight ))); }

				cMyWindow.pack();
				cMyWindow.setVisible(true);	

		
				
				cMyWindow.addMouseListener( this );		
				cMyWindow.addMouseMotionListener( this );		 
				cMyWindow.addKeyListener( this );		

				/*
				
				int lHMain2d = lHeightDiv*3;
				cVectPanelBox.add( (cPanelMain = new PanelMain2D(  (cGamePanel==null?this:cGamePanel),
																													 pGamer, cRenderMain, 
																													0, 0,
																													cWidth, lHMain2d )));
				
				
				cVectPanelBox.add( (cPanelMini = new PanelMiniMap( (cGamePanel==null?this:cGamePanel),
																													 pGamer, cRenderMini, 
																													 0, lHMain2d, lWidthDiv, lHeightDiv )));
				
				
				cVectPanelBox.add( (cPanelInfoSelect = new PanelInfoSelect( (cGamePanel==null?this:cGamePanel),
																																		pGamer, cRenderInfoSelect,
																																		lWidthDiv, lHMain2d,
																																		lWidthDiv*2, lHeightDiv )));
				
													 
				cVectPanelBox.add( (cPanelOrder      = new PanelOrder( (cGamePanel==null?this:cGamePanel),
																															 pGamer, cRenderOrder,
																															 lWidthDiv*3, lHMain2d,
																															 lWidthDiv, lHeightDiv )));

				*/			

				cMyWindow.addWindowListener( this );
	
				if( cMyFrame != null ) { cMyFrame.setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE ); }
				else { cMyDialog.setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE ); }


				///////		cGamePanel.initBufferImage();
	}	
		//-------------------------------------
		//-------------------------------------
		//-------------------------------------
		// Implementation InterfaceDisplayGamer
 		//--------------------------
		public void initDisplayBuffer(){
				initBufferImage();
		}
 		//--------------------------
		public void displayBuffer(){

				//				System.out.println( "========== displayBuffer call of swapBuffer" );

				swapBuffer();
		}
 		//--------------------------
	  public void drawBuffer() {
				
				//	System.out.println( "=============== FrameGamer.drawBuffer Debut =================" );

				for( 	PanelBox lPanelBox: cVectPanelBox ){
						if( lPanelBox.isRedrawReady() ){

								
								lPanelBox.display();
								lPanelBox.setRedrawFinish();  // plus de paint possible !														
						}
				}				
				//	System.out.println( "--------------- FrameGamer.drawBuffer Fin   -----------------" );
		}
 		//--------------------------

		public void setDrawReady( Graphics2D pGC  ){
				
				//								System.out.println( "******************** FrameGamer.setDrawReady ******************** PanelBox -> A VERIFIER" );

				for( 	PanelBox lPanelBox: cVectPanelBox ){	
						//						System.out.println(" FrameGamer.setDrawReady call lPanelBox.setRedrawReady  -> A VERIFIER" );
						lPanelBox.setRedrawReady( lPanelBox,  lPanelBox.getSize(), pGC);
				}		
		}
 		//--------------------------
		public boolean isReadyToDraw() { 
				for( 	PanelBox lPanelBox: cVectPanelBox ){
						//						System.out.println("FrameGamer.isReadyToDraw " + lPanelBox.getRender().isRedrawReady() );
						if( lPanelBox.isRedrawReady() == false )
								return false;
				}		
				return true;
		}
		boolean cFlagSelectPosition = false;
		//-------------------------------------
		public void beginSelectionPosition(){
				cFlagSelectPosition = true;
		}		

		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------
		//---------- FULL SCREEN FUNCTIONS ---------------
		//------------------------------------------------
		//------------------------------------------------
		//------------------------------------------------

		private BufferStrategy cBufferStrategy = null;
		GraphicsDevice cGd = null;



		private boolean initFullScreen( int pW, int pH, int pDepth) {
						
				GraphicsEnvironment lGe = GraphicsEnvironment.getLocalGraphicsEnvironment();
				cGd	= lGe.getDefaultScreenDevice();
				
			 
				cGd.setFullScreenWindow( cMyWindow); // switch on full-screen exclusive mode

				// we can now adjust the display modes, if we wish
				DisplayMode lDm = cGd.getDisplayMode();
				//				System.out.println("Current Display Mode: (" + 
				//                           lDm.getWidth()    + "," + lDm.getHeight() + "," +
				//                           lDm.getBitDepth() + "," + lDm.getRefreshRate() + ")  " );		

				
				if( pW != 0 && pH != 0 && pDepth != 0 ){
						setDisplayMode( pW, pH, pDepth); 
				}

												
				cWidth  = cMyWindow.getBounds().width;
				cHeight = cMyWindow.getBounds().height;

	
				setBufferStrategy();

				return true;
		} 		
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

		// ------------------ display mode methods -------------------
		
		
		private void setDisplayMode(int width, int height, int bitDepth)
				// attempt to set the display mode to the given width, height, and bit depth
		{
				if (!cGd.isDisplayChangeSupported()) {
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
						cGd.setDisplayMode(dm);
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
		//			 REFRESH_RATE_UNKNOWN anyway. 
		
		private boolean isDisplayModeAvailable(int width, int height, int bitDepth) {

				DisplayMode[] modes = cGd.getDisplayModes();
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

		private void showModes(DisplayMode[] modes)		{
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
		
		private void showCurrentMode()		{
				DisplayMode dm = cGd.getDisplayMode();
				System.out.println("Current Display Mode: (" + 
                           dm.getWidth() + "," + dm.getHeight() + "," +
                           dm.getBitDepth() + "," + dm.getRefreshRate() + ")  " );
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
		private static final int NUM_BUFFERS = 2;    // used for page flipping

		
		private void setBufferStrategy() {			 				
				
				try {
						EventQueue.invokeAndWait( new Runnable() {
										public void run() 
										{ cMyWindow.createBufferStrategy(NUM_BUFFERS);  }
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
				
				cBufferStrategy = cMyWindow.getBufferStrategy();  // store for later
		} 
		//------------------------------------------------
		// Tasks to do before terminating. Called at end of run()
		//	 and via the shutdown hook in readyForTermination().
			 
		//	 The call at the end of run() is not really necessary, but
		//	 included for safety. The flag stops the code being called
		//	 twice.
		
				private boolean finishedOff = false;

		private void finishOff() {
				
				System.out.println("finishOff");

				if (!finishedOff) {
						finishedOff = true;
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



		public void 	windowActivated(WindowEvent e) {
		}
		public void 	windowClosed(WindowEvent e) {
		}
		public void 	windowClosing(WindowEvent e) {
		}
		public void 	windowDeactivated(WindowEvent e) {
		}
		public void 	windowDeiconified(WindowEvent e) {
		}
		public void 	windowIconified(WindowEvent e) {
		}
		public void 	windowOpened(WindowEvent e)  {
		}



		//------------------------------------------
		public void mousePressed( MouseEvent pEv ) {
				System.out.println( "GamePanel.mousePressed" );

				PanelBox lPanelBox = findPanelBox(  pEv.getX(),  pEv.getY());
				if( lPanelBox != null ){
						lPanelBox.mousePressed( pEv );
				}
		}
		public void mouseReleased( MouseEvent pEv ) {
			System.out.println( "GamePanel.mouseRealsed" );
				PanelBox lPanelBox = findPanelBox(  pEv.getX(),  pEv.getY());
				if( lPanelBox != null ){
						lPanelBox.mouseReleased( pEv );
				}
		}
		public void mouseClicked( MouseEvent pEv ) {
			System.out.println( "GamePanel.Clicked" );
				PanelBox lPanelBox = findPanelBox(  pEv.getX(),  pEv.getY());
				if( lPanelBox != null ){
						lPanelBox.mouseClicked( pEv );
				}				
		}

		//-------------------------- 
		public void mouseEntered( MouseEvent pEv ) {
							System.out.println( "GamePanel.mouseEntered" );
		}		
		//-------------------------- 
		public void mouseExited( MouseEvent pEv ) {
			System.out.println( "GamePanel.mouseExited" );
		}
		//-------------------------- 
		public void actionPerformed( ActionEvent pEvv ){		
			System.out.println( "GamePanel.actionPerformed" );								
		}
		//-------------------------------------
		public void mouseDragged( MouseEvent pEv ){
				PanelBox lPanelBox = findPanelBox(  pEv.getX(),  pEv.getY());
				if( lPanelBox != null ){
						lPanelBox.mouseDragged( pEv );
				}	
		}

		//-------------------------------------
		public void mouseMoved( MouseEvent pEv ){
				/////			System.out.println( "GamePanel.mouseMoved" );		
						
				PanelBox lPanelBox = findPanelBox(  pEv.getX(),  pEv.getY());				
				if( lPanelBox != null ){						
						lPanelBox.mouseMoved( pEv );
				}				
		}
		//----------------------
		public	void keyReleased( KeyEvent e ){
				System.out.println( "KeyReleased" );
		}
		//----------------------
		public	void keyTyped( KeyEvent e ){
				System.out.println( "KeyTyped"  );
		}
		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 
		
		public	void update( Graphics g ){
				//			System.out.println( "Update" );
		}	

		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 

		public	void keyPressed( KeyEvent pEv ){
				// TESTER PAUSE QUIT ... 
				
			  int lKeyCode = pEv.getKeyCode();
				
				System.out.println( "KeyCode:" + lKeyCode );
				
				switch( lKeyCode ){
				case KeyEvent.VK_Q:
						System.out.println( "---> Q" );
						System.exit(0);
						break;
				case KeyEvent.VK_P:
						World.FlipPause();
						System.out.println( "---> P" );
						break;
				case KeyEvent.VK_ESCAPE:
						System.out.println( "---> ESCAPE" );
						//							World.Escape
						/////				cDisplayGamer.getHumanGamer().keyPressed( pEv);
						break;
				}
		}
	
		//------------------------------------------
		//--------- ComponentListener -------------- 
		//------------------------------------------ 
		public void componentResized(ComponentEvent e){


				System.out.println( ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GamePanel.componentResized" );	
				
				cBufSize = getSize();
				if( cBufSize.getWidth() <= 0 || cBufSize.getHeight() <=0 ) return;

				initBufferImage();
				//				actualize();
		}
		public void componentMoved(ComponentEvent e){
		}
		public void componentShown(ComponentEvent e){
		}		
		public void componentHidden(ComponentEvent e){
		}

		//-------------------------------------
		void actualize(){
				cMyWindow.repaint(); // new Rectangle( 0, 0, 20000, 20000 ));
		}	
}
