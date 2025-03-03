package org.phypo.PPgEdImg;


import java.awt.*;
import java.util.*;


import javax.swing.ImageIcon;


import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import java.util.regex.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;

import java.awt.geom.Area;
 
import org.phypo.PPg.PPgWin.*;

import org.phypo.PPg.PPgImg.*;


//*************************************************
public class PPgCanvasEdImg extends PPgCanvas  implements MouseMotionListener, MouseListener, KeyListener {
		
		public EdImgInst cMyInst = null;



		public BufferedImage getBufferImg()                     { return cMyInst.cLayerGroup.getCurrentLayer().getBufferImg(); }

		/*		public void          setBufferImg( BufferedImage pBuf ) {
				if( pBuf != null ) {						
						cBufImg = pBuf;
						cIsChanged = true;
						setPreferredSize( new Dimension( cBufImg.getWidth(), cBufImg.getHeight()) );				
						actualize();
				}
		}
		*/

		boolean cIsChanged = false ;
		public boolean isChanged()              { return cIsChanged; }
		public void    setChanged(boolean pChg) { cIsChanged = pChg; }


		
		public boolean cFlagGrid = false;
		public int     cGridSize = 10;

		public double  cZoom = 1.0;
		//------------------------------------------------

		public void saySize() {		
				setPreferredSize( cMyInst.cLayerGroup.getSize( cZoom));	
				setMaximumSize( cMyInst.cLayerGroup.getSize( cZoom) );				
				revalidate(); // TRES IMPORTANT !!!!
		}
		//------------------------------------------------
		public PPgCanvasEdImg( EdImgInst pMyInst ){

				cMyInst = pMyInst;
				cMyInst.setCanvas( this );
				cMyInst.setDefault();

				//			setDefaultProps();
				//				cMyInst.cCurrentGrafUtil = cMyInst.cPencil;  // par defaut

				saySize();
					
	
				Graphics2D lGraf = cMyInst.cLayerGroup.getGraphics();
				lGraf.scale( cZoom, cZoom );
				cMyInst.cLayerGroup.draw( lGraf );//, this );						
						

				addMouseListener( this );		
				addMouseMotionListener( this );		
				addKeyListener( this );		
										
				//				cView.addMouseMotionListener( this );		
				//				cView.addKeyListener( this );
		}
		//------------------------------------------------
		public void changeZoom( double pZoom ){
				if( pZoom < 0.1 )
						pZoom = 0.1;

				cZoom = pZoom;
				saySize();

				cMyInst.cFrame.actualize();
		}																		
		//------------------------------------------------
		public Graphics2D getUserSpaceGraphics(){

				Graphics2D  lGraf = (Graphics2D) getGraphics();
				lGraf.scale( cZoom, cZoom );
				// Mettre d'au transformation s'il y a lieu

				// s'il y a un Shape faire un setClip( cCurrentShape );  A FAIRE 

				return lGraf;
		}
		//------------------------------------------------
		// A EVITER !
		public Graphics2D forceImmediatelyDraw(){

			 Graphics2D lG	= (Graphics2D) getGraphics();
			 draw( lG );

			 return lG;
		}
		//------------------------------------------------
		 public void paint( Graphics pG ){
					
				 draw( pG );

				 Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
						
				 pG.dispose();
		 }
		//------------------------------------------------
		protected void draw( Graphics pG ){

				System.out.println( "********** CANVAS DRAW *************" );
				
				Graphics2D  lGraf = (Graphics2D) pG;

				AffineTransform lMemTransform = lGraf.getTransform();

				//	lGraf.setClip( 0, 0, cMyInst.cCanvas.getWidth(), cMyInst.cCanvas.getHeight() );

				lGraf.scale( cZoom, cZoom );

				try {

						//========= Fond "transparent" ==========
						Paint lMemPaint = lGraf.getPaint();

						lGraf.setPaint( PPgMotif.sBackgroundTransparentMotif16x16.getPaint() );
						lGraf.fillRect( 0, 0, 
														cMyInst.cLayerGroup.getWidth(), 
														cMyInst.cLayerGroup.getHeight() );

						lGraf.setPaint( lMemPaint );
						//========================================




						//========= Ecriture des layers =========

						cMyInst.cLayerGroup.draw( lGraf );//, this );	 			

						//=======================================
						

						

						//============= SELECT ===================

						cMyInst.cSelectZone.drawZone( lGraf );

						//========================================


						lGraf.setTransform( lMemTransform  );

						cMyInst.cSelectZone.drawSelect( lGraf, cZoom );

						//============== Grille ==================
						if( cFlagGrid ){
								System.out.println( "paint grid 2 " +cFlagGrid );
								
								lGraf.setColor( Color.black );
								for( double x= cGridSize*cZoom ; x< cMyInst.cLayerGroup.getWidth()*cZoom; x += cGridSize*cZoom )
										lGraf.drawLine( (int)x, 0, (int)x, (int)(cMyInst.cLayerGroup.getHeight()*cZoom) );
								
								for( double y= cGridSize*cZoom ; y< cMyInst.cLayerGroup.getHeight()*cZoom; y += cGridSize*cZoom )
										lGraf.drawLine( 0, (int)y, (int)(cMyInst.cLayerGroup.getWidth()*cZoom), (int)y );
						}
						//========================================


				}	catch (Exception e) {
						System.out.println("Graphics error: " + e); 
				}
				
		}
		
		
		//------------------------------------------
		//----------------- MOUSE ------------------
		//------------------------------------------

		public void mousePressed( MouseEvent pEv ) {
			 	System.out.println( "mousePressed " + pEv.getClickCount());	

				//			Verifier par rapport au shape de clipping courant en espace utilisateur s'il exite !


				PPgWinUtils.SetMemMouse( pEv, cZoom);


				//================
				if( SwingUtilities.isLeftMouseButton( pEv )  ){ //&& pEv.getClickCount() == 1){
						//	System.out.println( "mousePressed left/right ");

						if( PPgWinUtils.sFlagShift && cMyInst.cCurrentGrafUtil.usePickColor() ){
								
								cMyInst.cOpProps.cmdSetColor( OpGrafDropper.PickColor( cMyInst, PPgWinUtils.sMemPoint )); //, OpGrafDropper.WitchColor() );

						} else 						
						cMyInst.cCurrentGrafUtil.cmdBeginOp( new Point( PPgWinUtils.sMemPoint ) );		
						
				}else 	if( SwingUtilities.isRightMouseButton( pEv ) ){
						System.out.println( "mouseClicked Right");	
						cMyInst.cCurrentGrafUtil.cmdMenuOp( pEv );
				}
				//================
		}
		//------------------------------------------
		public void mouseReleased( MouseEvent pEv ) {
				//			System.out.println( "mousePressed" );	
				//			Verifier par rapport au shape de clipping courant en espace utilisateur s'il exite !

				System.out.println( "mouseReleased" );	
							
				PPgWinUtils.SetMemMouse( pEv, cZoom);

				//================
				if( SwingUtilities.isLeftMouseButton( pEv )) {
						
											 	System.out.println( "mouseReleased left ");	
												cMyInst.cCurrentGrafUtil.cmdFinalizeOp(  new Point( PPgWinUtils.sMemPoint ));						
				} 
				//================
				//================
		}
		//------------------------------------------
		public void mouseClicked( MouseEvent pEv ) {			
				
				PPgWinUtils.SetMemMouse( pEv, cZoom);

		}
		//-------------------------- 
		public void mouseEntered( MouseEvent pEv ) {
				System.out.println( "mouseEntered" );
		}		
		//-------------------------- 
		public void mouseExited( MouseEvent pEv ) {
				System.out.println( "mouseExited" );
		}
		//-------------------------------------
		public void mouseDragged( MouseEvent pEv ){
				//			Verifier par rapport au shape de clipping courant en espace utilisateur s'il exite !

				System.out.println( "mouseDragged" );	
							
				PPgMain.SetFootText( "x:"+ PPgWinUtils.sMemPoint.x + " y:"+ PPgWinUtils.sMemPoint.y );

				PPgWinUtils.SetMemMouse( pEv, cZoom);

				//================
				if( SwingUtilities.isLeftMouseButton( pEv ) ){
	
						cMyInst.cCurrentGrafUtil.cmdMoveOp(  new Point(  PPgWinUtils.sMemPoint ));	
				}
				//================
		}
		//-------------------------------------
		public void mouseMoved( MouseEvent pEv ){

				PPgWinUtils.SetMemMouse( pEv, cZoom);
				PPgMain.SetFootText( "x:"+ PPgWinUtils.sMemPoint.x + " y:"+ PPgWinUtils.sMemPoint.y );

		}
		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 
		
		public void actionPerformed( ActionEvent pEvv ){		
				System.out.println( "GamePanel.actionPerformed" );								
		}
		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 

		public	void keyPressed( KeyEvent pEv ){
				// TESTER PAUSE QUIT ... 

				System.out.println( "keyPressed :" + pEv );

				PPgWinUtils.ReadModifier( pEv );
				
			  int lKeyCode = pEv.getKeyCode();
				
				System.out.println( "KeyCode:" + lKeyCode );


				switch( lKeyCode ){
						/*
				case KeyEvent.VK_Z:
						if( PPgWinUtils.sFlagCtrl )
								cMyInst.cOpControl.cmdHistoUndo();		
						break;
						
				case KeyEvent.VK_C:
						if( PPgWinUtils.sFlagCtrl 
								&& cMyInst.cSelectZone.isActive() ){
								cMyInst.cSelectZone.setSelectPixels( cMyInst.cLayerGroup.getClipAreaImage(  cMyInst.cSelectZone.getArea() ) );
								// A FAIRE HISTO
								cMyInst.cCanvas.actualize();			
						}
						break;
						
				case KeyEvent.VK_V:
	
								if( PPgWinUtils.sFlagCtrl
										&& cMyInst.cSelectZone.isActive() &&  cMyInst.cSelectZone.getSelectPixels() != null ){ 
										cMyInst.cOpLayers.cmdPasteSelection();										
										cMyInst.cCanvas.actualize();
							}
						break;
				case KeyEvent.VK_X:
			
								if( PPgWinUtils.sFlagCtrl 
										&& cMyInst.cSelectZone.isActive() ){ 

										// On recupere la zone dans SelectPixels
										cMyInst.cSelectZone.setSelectPixels(  cMyInst.cLayerGroup.getClipAreaImage(  cMyInst.cSelectZone.getArea()));
										// A FAIRE HISTO
										//On efface la zone
										cMyInst.cOpLayers.cmdClearVisibleAll( cMyInst.cSelectZone.getArea() );
										cMyInst.cCanvas.actualize();
								}
						break;
						*/
						/*
				case KeyEvent.VK_PLUS:
						changeZoom( cZoom * 1.10 );
						break;
				case KeyEvent.VK_MINUS:
						changeZoom( cZoom * 0.90 );
						break;
						*/
				case KeyEvent.VK_Q:
						System.out.println( "---> Q" );
						System.exit(0);
						break;
				case KeyEvent.VK_P:
						System.out.println( "---> P" );
						break;
				case KeyEvent.VK_ESCAPE:
						System.out.println( "---> ESCAPE" );
							cMyInst.cCurrentGrafUtil.cmdCancelOp();
						break;
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
		//--------- ComponentListener -------------- 
		//------------------------------------------ 
		/*
		public void componentResized(ComponentEvent e){
				
				
				System.out.println( ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GamePanel.componentResized" );	
				
		}
		public void componentMoved(ComponentEvent e){
		}
		public void componentShown(ComponentEvent e){
		}		
		public void componentHidden(ComponentEvent e){
		}	
		*/	
		
		//------------------------------------------------

		//------------------------------------------------


}
