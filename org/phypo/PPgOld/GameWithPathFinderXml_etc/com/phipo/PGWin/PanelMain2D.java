package com.phipo.GLib;



import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.Robot;


import java.util.*;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;


/**
 ** Sous fentre permettant de voir les application et les connexions 
 ** sous forme graphique.
 ** 
 */


//***********************************
final public class PanelMain2D extends PanelBox
{
	

		
		int cMemClickX=0;
		int cMemClickY=0;

		int cMemClickX2=0;
		int cMemClickY2=0;

		Rectangle cRectSelect=null;
		

		//--------------------------
		public PanelMain2D( Component pDrawComponent, GamerHuman pGamer, 
												Render2D pRender, 
												int pX, int pY, int pWidth, int pHeight) { 		
				
 				super( pDrawComponent, pGamer, pRender, pX, pY, pWidth, pHeight );

		}  																					
		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 

		// Convertit un position ecran en une position carte 

		void convert2CartePosition( Point2D.Double lPointDest ){
				
				Point2D.Double lPosView  = cRender.getGamerHuman().getViewPoint(); // position du centre 
				
				double lX = ((double)lPointDest.getX()-getX()) - getWidth()/2.0;
				double lY = ((double)lPointDest.getY()-getY()) - getHeight()/2.0;
				
				//				double lTailleFinal = World.sDecorCarte.sSizeCase*cRender.getMagnify();
				double lTailleFinal = cRender.getMagnify();

				lX /= lTailleFinal;
				lY /= lTailleFinal;
				
				lX += lPosView.getX();
				lY += lPosView.getY();
				
				lPointDest.setLocation( lX, lY );
		}

		//------------------------------------------ 

		public void mousePressed( MouseEvent pEv ) {
			System.out.println( "PanelMain2D.mousePressed" );

			//			cMousePressed = true;
			cMemClickX = cMemClickX2 = pEv.getX();
			cMemClickY = cMemClickY2 = pEv.getY();			


			// Deplacement du point de vue
			/*
			if( SwingUtilities.isRightMouseButton( pEv ) == true){

					Point2D.Double lPointDest = new Point2D.Double( pEv.getX(), pEv.getY() );
					Point2D.Double lPos = cDisplayGamer.getPosition();
				
							
					double lDecalX = ((cCurrentBufSize.getWidth()/2) -pEv.getX())/(World.GetCaseSize()*cRender.getMagnify());
					double lDecalY = ((cCurrentBufSize.getHeight()/2)-pEv.getY())/(World.GetCaseSize()*cRender.getMagnify());

					lPos.setLocation( lPos.getX()-lDecalX, lPos.getY()-lDecalY );


					actualize();
			}
					
			*/		
		}
		//-------------------------- 
		public void mouseReleased( MouseEvent pEv ) {
			System.out.println( "PanelMain2D.mouseReleased" );


			if( SwingUtilities.isLeftMouseButton( pEv ) == true){

					if( Math.abs( cMemClickX - cMemClickX2 ) >= 3 
							&&  Math.abs( cMemClickY - cMemClickY2 ) >= 3 ){

							
							
							int lCtrl  = InputEvent.CTRL_DOWN_MASK; 
							int lShift = InputEvent.SHIFT_DOWN_MASK;
							int lAlt   = InputEvent.ALT_DOWN_MASK;
							boolean lFlagCtrl  = false;
							boolean lFlagShift = false;
							
							if( (pEv.getModifiersEx() &  lCtrl) == lCtrl){
									lFlagCtrl = true;
									System.out.println( "CTRL" );
							}
							if( (pEv.getModifiersEx() & lShift) == lShift ){
									lFlagShift = true;
									System.out.println( "SHIFT" );
							}
							if( (pEv.getModifiersEx() & lAlt) == lAlt ){
									System.out.println( "ALT" );
							}
							
							
							Point2D.Double lPointMin = new Point2D.Double( Math.min( cMemClickX, cMemClickX2 ),  Math.min( cMemClickY, cMemClickY2 ));
							Point2D.Double lPointMax = new Point2D.Double( Math.max( cMemClickX, cMemClickX2 ),  Math.max( cMemClickY, cMemClickY2 ));
							
							convert2CartePosition( lPointMin  );
							convert2CartePosition( lPointMax );
							
							cGamer.sendMessage( new GamerMessage(  pEv, GamerMessage.Order.SELECT_RECT, 
																										 new Rectangle2D.Double( lPointMin.getX(), lPointMin.getY(), 
																																						 lPointMax.getX()-lPointMin.getX(),
																																						 lPointMax.getY()-lPointMin.getY()),
																										 lFlagCtrl, lFlagShift ));
							
							cMemClickX2 = cMemClickX;
							cMemClickY2 = cMemClickY;
					}
			}
		}		

		// -----------------------------
		public void mouseClicked( MouseEvent pEv ) {

			System.out.println( "PanelMain2D.mouseClicked" );

				// calcul de la position de la souris dans la carte 
				Point2D.Double lPointDest = new Point2D.Double( pEv.getX(), pEv.getY() );
				convert2CartePosition( lPointDest );
				
				int lCtrl  = InputEvent.CTRL_DOWN_MASK; 
				int lShift = InputEvent.SHIFT_DOWN_MASK;
				int lAlt   = InputEvent.ALT_DOWN_MASK;
				boolean lFlagCtrl  = false;
				boolean lFlagShift = false;
				
				if( (pEv.getModifiersEx() &  lCtrl) == lCtrl){
						lFlagCtrl = true;
						System.out.println( "CTRL" );
				}
				if( (pEv.getModifiersEx() & lShift) == lShift ){
						lFlagShift = true;
						System.out.println( "SHIFT" );
				}
				if( (pEv.getModifiersEx() & lAlt) == lAlt ){
						System.out.println( "ALT" );
				}
				
				// Selection d'entite
				if( SwingUtilities.isLeftMouseButton( pEv ) == true){	
						if( lFlagCtrl )
								cGamer.sendMessage( new GamerMessage(  pEv, GamerMessage.Order.MENU_AT, 
																											 lPointDest, lFlagCtrl, lFlagShift ));
						else
								cGamer.sendMessage( new GamerMessage(  pEv, GamerMessage.Order.SELECT_AT, 
																											 lPointDest, lFlagCtrl, lFlagShift ));
				}
				else if( SwingUtilities.isRightMouseButton( pEv ) == true){
						cGamer.sendMessage( new GamerMessage( pEv, GamerMessage.Order.ORDER_AT, lPointDest, lFlagCtrl, lFlagShift ) );
				}			 
		}
		//-------------------------------------
		public void mouseDragged( MouseEvent pEv ){

				//			System.out.println( "PanelMain2D.mouseDragged" );

				if( SwingUtilities.isLeftMouseButton( pEv ) == true){
						
						cMemClickX2 = pEv.getX();
						cMemClickY2 = pEv.getY();										
				}
				else
				// Deplacement dans la carte
				if( SwingUtilities.isMiddleMouseButton( pEv ) == true){
						
						Point2D.Double lPosView = cRender.getGamerHuman().getViewPoint();
													
						double lDecalX = (cMemClickX - pEv.getX()) /(cRender.getMagnify());
						double lDecalY = (cMemClickY - pEv.getY()) /(cRender.getMagnify());
												
						lDecalX *=3;  // mettre une variable dans les preferences
						lDecalY *=3;
						
						double lPosX = lPosView.getX()-lDecalX;
						double lPosY = lPosView.getY()-lDecalY;


						// En cas de depassement de la carte
						boolean lFlagMouseMoveX = false;
						boolean lFlagMouseMoveY = false;

						if( lPosX < 0 ) { 
								lPosX = 0;
								lFlagMouseMoveX = true;
						}
						else
						if( lPosX > World.sTheWorld.getWidth() ) {								
								lPosX = World.sTheWorld.getWidth();
								lFlagMouseMoveX = true;
						}

						if( lPosY < 0 )  {
								lPosY = 0;
								lFlagMouseMoveY = true;
						}
						else
						if( lPosY > World.sTheWorld.getHeight() ) {
								lPosY = World.sTheWorld.getHeight();
								lFlagMouseMoveY = true;
						}


						cRender.getGamerHuman().getViewPoint().setLocation( lPosX, lPosY ); // deplacement du point de vue !
						//						cDisplayGamer.getViewPoint().setLocation( lPosX, lPosY ); // deplacement du point de vue !

						
						Point lPt = new Point( pEv.getX(), pEv.getY() );

						// Pour "paralyser la souris" !
						if( lFlagMouseMoveX || lFlagMouseMoveY ) {
								
								if( lFlagMouseMoveX == false)
										cMemClickX2 = cMemClickX = (int)pEv.getX();

								if( lFlagMouseMoveY == false)
										cMemClickY2 = cMemClickY = (int)pEv.getY();
								
								lPt.setLocation( cMemClickX, cMemClickY );

								
								Point lPtScreen = new Point( lPt);							 
								SwingUtilities.convertPointToScreen( lPtScreen, cDrawComponent );
								
								try{
										Robot lRobot = new Robot();
										
										
										lRobot.mouseMove( (int)lPtScreen.getX(), (int)lPtScreen.getY() );
								}
								catch( Exception e )
										{
										}
							 
						}					
						cMemClickX2 = cMemClickX = (int)lPt.getX();
						cMemClickY2 = cMemClickY = (int)lPt.getY();													
				}
		}
		//-------------------------------------
		public void mouseMoved( MouseEvent pEv ){

				//				System.out.println( "PanelMain2D.mouseMoved" );

				Point2D.Double lPointDest = new Point2D.Double( pEv.getX(), pEv.getY() );
				convert2CartePosition( lPointDest );
				
				int lCtrl  = InputEvent.CTRL_DOWN_MASK; 
				int lShift = InputEvent.SHIFT_DOWN_MASK;
				int lAlt   = InputEvent.ALT_DOWN_MASK;
				
				boolean lFlagCtrl  = false;
				boolean lFlagShift = false;
				
				if( (pEv.getModifiersEx() &  lCtrl) == lCtrl){
						lFlagCtrl = true;
						//						System.out.println( "CTRL" );
				}
				if( (pEv.getModifiersEx() & lShift) == lShift ){
						lFlagShift = true;
						//						System.out.println( "SHIFT" );
				}
				if( (pEv.getModifiersEx() & lAlt) == lAlt ){
						//						System.out.println( "ALT" );
				}

				cGamer.sendMessage( new GamerMessage(  pEv, GamerMessage.Order.CURSOR_AT, 
																							 lPointDest, lFlagCtrl, lFlagShift ));
		}
		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 
		
		public void paint(){
				

				if( Math.abs( cMemClickX - cMemClickX2 ) >= 3 
						&&  Math.abs( cMemClickY - cMemClickY2 ) >= 3 ){
						int lMinX = Math.min( cMemClickX, cMemClickX2 );
						int lMaxX = Math.max( cMemClickX, cMemClickX2 );

						int lMinY = Math.min( cMemClickY, cMemClickY2 );
						int lMaxY = Math.max( cMemClickY, cMemClickY2 );

						cRender.getGC().setColor( Color.green );
						cRender.getGC().drawRect( lMinX, lMinY, lMaxX - lMinX, lMaxY - lMinY );
				}
						
		}
}
		//***********************************















