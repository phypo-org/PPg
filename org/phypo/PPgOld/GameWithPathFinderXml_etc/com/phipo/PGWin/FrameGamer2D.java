package com.phipo.GLib;



import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.WindowConstants;
import java.util.*;


//***********************************
public class FrameGamer2D extends JFrame
		implements InterfaceDisplayGamer {


		GamerHuman cGamer;
		public Gamer getGamer() { return cGamer; }


		Render2D         cRenderMain;        // Ce qui fait l'affichage !
		RenderMiniMap    cRenderMini;        // Ce qui fait l'affichage !
		RenderInfoSelect cRenderInfoSelect;
		RenderOrder      cRenderOrder;


		public  Render2D         getRenderMain()        { return cRenderMain; }
		public  RenderMiniMap    getRenderMini()        { return cRenderMini; }
		public  RenderInfoSelect getRenderInfoSelect()  { return cRenderInfoSelect; }
		public  RenderOrder      getRenderOrder()       { return cRenderOrder; }

		PanelBox cPanelMain = null;
		PanelBox cPanelMini = null;
		PanelBox cPanelInfoSelect = null;
		PanelBox cPanelOrder = null;

		GamePanel  cGamePanel = null;


		String   cName = null;

		int cWidth  = 800;
		int cHeight = 600;

		ArrayList<PanelBox> cVectPanelBox = new ArrayList<PanelBox>();

		//-------------------------- 
		public PanelBox findPanelBox( int x, int y ){
				for( PanelBox lPanel:cVectPanelBox ){
						if( lPanel.contains( x, y ) )
								return lPanel;
				}
				return null;
		}
		//-------------------------- 
		public void addPanelBox( PanelBox pPanelBox ){
				cVectPanelBox.add( pPanelBox );
		}


	//-------------------------------------
		public FrameGamer2D( GamerHuman pGamer, 
												 Point2D.Double pPosViewInit, boolean  pFlagFullScreen, int pWidth, int pHeight, int pDepth ){
				super( pGamer.getName() );				
				
				//				setViewPoint( pPosViewInit );


				setUndecorated(true);    // no menu bar, borders, etc. or Swing components
				setIgnoreRepaint(true);  // turn off all paint events since doing active rendering
				//				setResizable(false);

				
				//				if( pFlagFullScreen == false || initFullScreen( pWidth, pHeight, pDepth ) == false ){

						cWidth  = pWidth;
						cHeight = pHeight;

						//						cWidth  = 800;
						//						cHeight = 600;

						setSize( cWidth, cHeight );
						setContentPane( (cGamePanel= new GamePanel( this, this, cWidth, cHeight, World.sBagroundColor )));
						pack();
						setVisible(true);					

						//						addWindowListener( this );
						//				}
						//				else {
						/*
						addMouseListener( new MouseAdapter() {
										public void mousePressed(MouseEvent e)
										{ testMouseEvent( e); }
								});
						
						addMouseMotionListener( new MouseMotionAdapter() {
										public void mouseMoved(MouseEvent e)
										{ testMouseMove( e); }
								});			
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
						*/ 			

				
				// for shutdown tasks
				// a shutdown may not only come from the program
						/*
						Runtime.getRuntime().addShutdownHook(new Thread() {
										public void run()
										{ 
												World.Stop();   
												finishOff();
										}
								});
						*/
						
						//		}

				cRenderMain = new Render2D( pGamer, 1.0 );
				cRenderMini = new RenderMiniMap(pGamer );
				cRenderInfoSelect = new RenderInfoSelect(pGamer);
				cRenderOrder = new RenderOrder(pGamer );

				cGamer = pGamer;
				pGamer.setDisplayRender( this  );



				int lWDiv=4;
				int lHDiv=4;

				int lWidthDiv  = cWidth/lWDiv;

				int lHeightDiv = cHeight/lHDiv;


				
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

				
				setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );



				cGamePanel.initBufferImage();
	}	
		//-------------------------------------
		//-------------------------------------
		//-------------------------------------
		// Implementation InterfaceDisplayGamer
		
		public void displayBuffer(){
				cGamePanel.swapBuffer();
		}
 		//--------------------------
	  public void drawBuffer() {
				
				for( 	PanelBox lPanelBox: cVectPanelBox ){
						if( lPanelBox.getRender().isRedrawReady() ){

								//								System.out.println( "FrameGamer2D.drawBuffer" );

								lPanelBox.display();
								lPanelBox.getRender().setRedrawFinish();  // plus de paint possible !														
						}
				}				
		}
 		//--------------------------

		public void setDrawReady( Graphics2D pGC  ){
				
				//				System.out.println( "******************** FrameGamer2D.setDrawReady ********************" );

				for( 	PanelBox lPanelBox: cVectPanelBox ){
						lPanelBox.getRender().setRedrawReady( lPanelBox,  lPanelBox.getSize(), pGC);
				}		
		}
 		//--------------------------
		public boolean isReadyToDraw() { 
				for( 	PanelBox lPanelBox: cVectPanelBox ){
						//						System.out.println("FrameGamer2D.isReadyToDraw " + lPanelBox.getRender().isRedrawReady() );
						if( lPanelBox.getRender().isRedrawReady() == false )
								return false;
				}		
				return true;
		}
		//-------------------------- 
		public void actionPerformed( ActionEvent pEv ){		
				//				System.out.println( "actionPerformed:" +  pEv.getActionCommand());								
				
				Point2D.Double lPointDest = new 	Point2D.Double(0,0);
				cGamer.sendMessage( new GamerMessage(  pEv, GamerMessage.Order.ACTION_EVENT, 
																							 lPointDest, false, false ));					
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
				
			 
				cGd.setFullScreenWindow(this); // switch on full-screen exclusive mode
				// we can now adjust the display modes, if we wish
				DisplayMode lDm = cGd.getDisplayMode();
				//				System.out.println("Current Display Mode: (" + 
				//                           lDm.getWidth()    + "," + lDm.getHeight() + "," +
				//                           lDm.getBitDepth() + "," + lDm.getRefreshRate() + ")  " );		

				
				if( pW != 0 && pH != 0 && pDepth != 0 ){
						setDisplayMode( pW, pH, pDepth); 
				}

												
				cWidth  = getBounds().width;
				cHeight = getBounds().height;

	
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
}
