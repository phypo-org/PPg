package com.phipo.GLib;


import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;

//***********************************
public class GamePanel extends JPanel 
		implements MouseMotionListener, MouseListener,  ActionListener, ComponentListener, KeyListener {

		InterfaceDisplayGamer cDisplayGamer;

		BufferedImage  cCurrentBufImg = null;
		Graphics2D     cCurrentBufGraf = null;
		Dimension      cBufSize = null;

		BufferedImage cBufImgA = null;
		BufferedImage cBufImgB = null;
		Graphics2D    cBufGrafB = null;
		Graphics2D    cBufGrafA = null;

		FrameGamer2D cFrame;

		//-------------------------- 
		public GamePanel( InterfaceDisplayGamer pDisplayGamer, FrameGamer2D pFrame,  int pWidth, int pHeight, Color pColorBackground ) {				
				
				super();
				setBackground(pColorBackground);
				setPreferredSize( new Dimension( pWidth, pHeight));

				//				System.out.println( ">>>>>>>>>>>>> W:" + pWidth + " H:" + pHeight);
				//				System.exit(0);
						

				setFocusable(true);
				requestFocus();    // the JPanel now has focus, so receives key events

				cDisplayGamer = pDisplayGamer;
				cFrame = pFrame;

				addMouseListener( this );		
				addMouseMotionListener( this );		
				addKeyListener( this );		
				//				addComponentListener( this );	
		}
		//-------------------------- 
		void initBufferImage(){
				
				cBufSize = getSize();
				if( cBufSize.getWidth() <= 0 || cBufSize.getHeight() <=0 ) {
						System.out.println( "*** GamePanel.initBufferImage : ERROR bad size : " + cBufSize.getWidth() + " " + cBufSize.getHeight()  );
						return;
				}

				cBufImgA =  new BufferedImage( (int)cBufSize.getWidth(),  (int) cBufSize.getHeight(),
																				 BufferedImage.TYPE_BYTE_INDEXED );
				cBufImgB =  new BufferedImage( (int)cBufSize.getWidth(),  (int) cBufSize.getHeight(),
																			 BufferedImage.TYPE_BYTE_INDEXED );
				cBufGrafA = cBufImgA.createGraphics();
				cBufGrafB = cBufImgB.createGraphics();
				cBufGrafA.setColor( Color.white );
				cBufGrafB.setColor( Color.white );
				cBufGrafA.fillRect(0, 0,  (int)cBufSize.getWidth(),   (int)cBufSize.getHeight());				
				cBufGrafB.fillRect(0, 0,  (int)cBufSize.getWidth(),   (int)cBufSize.getHeight());				
				
				swapBuffer();
		}
		//-------------------------------------
		void swapBuffer(){

				if( cBufSize == null ) {
						System.out.println( "*** GamePanel.swapBuffer : ERROR buffer is null"  );
						return;
				}

				if( cCurrentBufImg == cBufImgA ){
						
						cCurrentBufImg = cBufImgB;
						cCurrentBufGraf = cBufGrafB;

						//						actualize(); 
						cDisplayGamer.setDrawReady( cBufGrafA );
				}
				else {

						cCurrentBufImg  = cBufImgA;
						cCurrentBufGraf = cBufGrafA;

						//						actualize();
						cDisplayGamer.setDrawReady( cBufGrafB );
				}

				//				System.out.println( "GamePanel.swapBuffer" );
				paintScreen();
		}
		//-------------------------------------
		void actualize(){
				repaint( new Rectangle( 0, 0, 20000, 20000 ));
		}	
  																					
		//------------------------------------------
		
		public void paintScreen( ){
				
				//				System.out.println( "GamePanel.paintScreen" );

				if( cCurrentBufImg == null )
						initBufferImage();

				Graphics2D g;
				try {
						g = (Graphics2D) this.getGraphics();
						if ((g != null) && ( cCurrentBufImg != null))
								g.drawImage( cCurrentBufImg, 0, 0, this );		
						Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
						g.dispose();
				}
				catch (Exception e)
						{ System.out.println("Graphics error: " + e);  }				
		}
		//------------------------------------------
		public void mousePressed( MouseEvent pEv ) {
			System.out.println( "GamePanel.mousePressed" );

				PanelBox lPanelBox = cFrame.findPanelBox(  pEv.getX(),  pEv.getY());
				if( lPanelBox != null ){
						lPanelBox.mousePressed( pEv );
				}
		}
		public void mouseReleased( MouseEvent pEv ) {
			System.out.println( "GamePanel.mouseRealsed" );
				PanelBox lPanelBox = cFrame.findPanelBox(  pEv.getX(),  pEv.getY());
				if( lPanelBox != null ){
						lPanelBox.mouseReleased( pEv );
				}
		}
		public void mouseClicked( MouseEvent pEv ) {
			System.out.println( "GamePanel.Clicked" );
				PanelBox lPanelBox = cFrame.findPanelBox(  pEv.getX(),  pEv.getY());
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
				PanelBox lPanelBox = cFrame.findPanelBox(  pEv.getX(),  pEv.getY());
				if( lPanelBox != null ){
						lPanelBox.mouseDragged( pEv );
				}	
		}
		//-------------------------------------
		public void mouseMoved( MouseEvent pEv ){
				/////			System.out.println( "GamePanel.mouseMoved" );		
						
				PanelBox lPanelBox = cFrame.findPanelBox(  pEv.getX(),  pEv.getY());				
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
						
						((GamerHuman)cDisplayGamer.getGamer()).sendMessage( new GamerMessage( pEv, GamerMessage.Order.CANCEL, 
																									(Point2D.Double)null, false, false ));
						break;
				}
		}
		//------------------------------------------
		//--------- ComponentListener -------------- 
		//------------------------------------------ 
		public void componentResized(ComponentEvent e){
				System.out.println( "GamePanel.componentResized" );	
				
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
}
//***********************************

