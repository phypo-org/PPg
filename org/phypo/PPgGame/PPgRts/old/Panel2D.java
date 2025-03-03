package org.phypo.PPg.PPgRts;




import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.Robot;

import java.awt.geom.Point2D;

import java.util.*;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;




import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgUtils.*;

		

/**
 ** Donne un rendu 2D "classique" du monde
 ** 
 ** 
 */


//***********************************
public class Panel2D extends PanelBox
{	
		int cMemMoveX=0;
		int cMemMoveY=0;
 		
		int cMemClickX=0;
		int cMemClickY=0;
		
		int cMemClickX2=0;
		int cMemClickY2=0;
		
		Rectangle cRectSelect=null;


		static boolean sFlagAutoscroll = false;
		static int     sFlagAutoscrollMaxScroll = 20;
		static int     sFlagAutoscrollSeuil = 100;

		//		double cMagnify;
		//		public double getMagnify() { return cMagnify; }
		//		public void   setMagnify(double pMagnify) { cMagnify = pMagnify; }

		//		Font   cPolice    = new Font( "Monospaced", Font.PLAIN, 16 );

		//		double cDecalX=0;
		//		double cDecalY=0;
		

		//--------------------------
		public Panel2D( GamerHuman pGamer, 
												int pX, int pY, int pWidth, int pHeight) { 		
				
 				super( pGamer, pX, pY, pWidth, pHeight );		

				setRender( new Render2D( pGamer, this, 1.0 ) );
		}  																					
		//------------------------------------------
		// Convertit un position ecran en une position carte 
		void convert2CartePosition( Point2D.Double pPointDest ){
				
				
				// On passe en coordonnées par rapport au centre de la PanelBox
				double lX = ((double)pPointDest.getX()-getX()) - getWidth()/2.0;
				double lY = ((double)pPointDest.getY()-getY()) - getHeight()/2.0;
				
				//				double lTailleFinal = World.sDecorCarte.sSizeCase*cRender.getMagnify();
				double lMagnify= cMyRender.getMagnify();

				// On applique le grossisement
				lX /= lMagnify;
				lY /= lMagnify;
				
				// On redecale par rapport a la position du point de vue courant
				Point2D.Double lPosView  = getGamerHuman().getViewPoint(); // position du centre 
				lX += lPosView.getX();
				lY += lPosView.getY();
				
				pPointDest.setLocation( lX, lY );
		}
		//------------------------------------------
		// Convertit un position ecran en une position carte 
		void convert2CartePosition( Point2D.Double pPointDest, Point2D.Double pPosView ){
								
				// On passe en coordonnées par rapport au centre de la PanelBox
				double lX = ((double)pPointDest.getX()-getX()) - getWidth()/2.0;
				double lY = ((double)pPointDest.getY()-getY()) - getHeight()/2.0;
				
				//				double lTailleFinal = World.sDecorCarte.sSizeCase*cRender.getMagnify();
				double lMagnify= cMyRender.getMagnify();

				// On applique le grossisement
				lX /= lMagnify;
				lY /= lMagnify;
				
				// On redecale par rapport a la position du point de vue courant
				lX += pPosView.getX();
				lY += pPosView.getY();
				
				pPointDest.setLocation( lX, lY );
		}
		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 

		public void mousePressed( MouseEvent pEv ) {

			System.out.println( "RenderPanel.mousePressed" );

			cMemClickX = cMemClickX2 = pEv.getX();
			cMemClickY = cMemClickY2 = pEv.getY();	

			evalueModifier( pEv );

			Point2D.Double lPoint = new Point2D.Double( pEv.getX(), pEv.getY()  );
			convert2CartePosition( lPoint );

			if( SwingUtilities.isLeftMouseButton( pEv ) == true )
					getGamerHuman().actionButton1Pressed( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
			else if( SwingUtilities.isRightMouseButton( pEv ) == true )
					getGamerHuman().actionButton2Pressed( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
			else if(  SwingUtilities.isMiddleMouseButton( pEv ) == true )
					getGamerHuman().actionButton3Pressed( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
		}
		//-------------------------- 
		public void mouseReleased( MouseEvent pEv ) {

			System.out.println( "RenderPanel.mouseReleased" );

			if( SwingUtilities.isLeftMouseButton( pEv ) == true){
					
					if( Math.abs( cMemClickX - cMemClickX2 ) >= 3 
							&&  Math.abs( cMemClickY - cMemClickY2 ) >= 3 ){

							evalueModifier( pEv );

							Point2D.Double lPointMin = new Point2D.Double( Math.min( cMemClickX, cMemClickX2 ),  Math.min( cMemClickY, cMemClickY2 ));
							Point2D.Double lPointMax = new Point2D.Double( Math.max( cMemClickX, cMemClickX2 ),  Math.max( cMemClickY, cMemClickY2 ));
							
							convert2CartePosition( lPointMin );
							convert2CartePosition( lPointMax );

							getGamerHuman().actionSelectRect( lPointMin, lPointMax, cFlagCtrl, cFlagShift, cFlagAlt );
					}
			}
			else	if( SwingUtilities.isRightMouseButton( pEv ) == true){		
					Point2D.Double lPoint = new Point2D.Double( pEv.getX(), pEv.getY()  );
					convert2CartePosition( lPoint );
					evalueModifier( pEv );
					getGamerHuman().actionButton2Released( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
			}
			else	if( SwingUtilities.isMiddleMouseButton( pEv ) == true){	
					Point2D.Double lPoint = new Point2D.Double( pEv.getX(), pEv.getY()  );
					convert2CartePosition( lPoint );
					evalueModifier( pEv );
					getGamerHuman().actionButton3Released( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
			}
			
			cMemClickX2 = cMemClickX;
			cMemClickY2 = cMemClickY;
		}

		// -----------------------------
		public void mouseClicked( MouseEvent pEv ) {

				// il faut empecher les coins de la carte de sortir de l'ecran !
				// faire des test
			 
				evalueModifier( pEv );
				
				Point2D.Double lPoint = new Point2D.Double( pEv.getX(),  pEv.getY() );
				convert2CartePosition( lPoint );

				System.out.println( "RenderPanel.mouseClicked" );

				if( SwingUtilities.isLeftMouseButton( pEv ) == true){	
						getGamerHuman().actionButton1Clicked( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
				}
				else if( SwingUtilities.isRightMouseButton( pEv ) == true){
						getGamerHuman().actionButton2Clicked( lPoint, cFlagCtrl, cFlagShift, cFlagAlt);
				}
				else if( SwingUtilities.isMiddleMouseButton( pEv ) == true){
						getGamerHuman().actionButton3Clicked( lPoint, cFlagCtrl, cFlagShift, cFlagAlt);		 							
				}
		}
		//-------------------------------------
		boolean moveGamerView( double lPosX, double lPosY ) {
				// En cas de depassement de la carte
				boolean lFlagMouseMoveX = false;
				boolean lFlagMouseMoveY = false;


				Point2D.Double lFuturView = new Point2D.Double(  lPosX, lPosY );


				Point2D.Double lZero = new Point2D.Double( 0, 0 );
				Point2D.Double lMax = new Point2D.Double( getWidth(), getHeight() );
				
				System.out.println (  " zeroX=" +  lZero.x + " zeroY="+ lZero.y 
															+ " maxX=" +lMax.x + " maxY=" + lMax.y );
				
				convert2CartePosition( lZero, lFuturView ); // position du point Zero dans l'espace du jeu
				convert2CartePosition( lMax,  lFuturView); // position du point Zero dans l'espace du jeu
				
				System.out.println (  "vx=" +  lPosX  + " vy=" + lPosY 
															+ " zeroX=" +  lZero.x + " zeroY="+ lZero.y 
															+ " maxX=" +lMax.x + " maxY=" + lMax.y );
				
				
				if( lZero.x < 0) {
						lPosX -= lZero.x;								
						lFlagMouseMoveX = true;
				}
				if( lZero.y < 0 ) {
						lPosY -= lZero.y;
						lFlagMouseMoveY = true;
				}								
				
				if( lMax.x >  World.Get().getWidth() ) {
						lPosX -= lMax.x-World.Get().getWidth();
						lFlagMouseMoveX = true;
				}
				if( lMax.y >  World.Get().getHeight() ) {
						lPosY -= lMax.y-World.Get().getHeight();
						lFlagMouseMoveY = true;
				}
				
				
				System.out.println (  "vx=" +  lPosX  + " vy=" + lPosY );
				
				
				getGamerHuman().setViewPoint( lPosX, lPosY ); // deplacement du point de vue !
				
				return lFlagMouseMoveX || lFlagMouseMoveY;
		}
		//-------------------------------------
		public void mouseDragged( MouseEvent pEv ){
				
				boolean lFlagMouseMoveX = false;
				boolean lFlagMouseMoveY = false;

				System.out.println( "RenderPanel.mouseDragged" );

				if( SwingUtilities.isLeftMouseButton( pEv ) == true){
						
						// Pour la selection !
						cMemClickX2 = pEv.getX();
						cMemClickY2 = pEv.getY();										
				}
				else
				// Deplacement dans la carte
				if( SwingUtilities.isMiddleMouseButton( pEv ) == true){
						
						Point2D.Double lPosView = getGamerHuman().getViewPoint();
													
						double lDecalX = (cMemClickX - pEv.getX()) /(cMyRender.getMagnify());
						double lDecalY = (cMemClickY - pEv.getY()) /(cMyRender.getMagnify());
												
						lDecalX *=3;  // mettre une variable dans les preferences
						lDecalY *=3;
						
						double lPosX = lPosView.getX()-lDecalX;
						double lPosY = lPosView.getY()-lDecalY;


						Point lPt = new Point( pEv.getX(), pEv.getY() );


						lFlagMouseMoveX = moveGamerView( lPosX, lPosY );
						

						// Pour "paralyser la souris" !
						if( lFlagMouseMoveX || lFlagMouseMoveY ) {
								
								if( lFlagMouseMoveX == false)
										cMemClickX2 = cMemClickX = (int)pEv.getX();

								if( lFlagMouseMoveY == false)
										cMemClickY2 = cMemClickY = (int)pEv.getY();
								
								lPt.setLocation( cMemClickX, cMemClickY );

								
								Point lPtScreen = new Point( lPt);							 
								SwingUtilities.convertPointToScreen( lPtScreen,  FrameGamer.Get().getWindow() ); 
								
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
				else 	if( SwingUtilities.isRightMouseButton( pEv ) == true){
						
						Point2D.Double lPoint = new Point2D.Double( pEv.getX(), pEv.getY() );

						convert2CartePosition( lPoint );
						evalueModifier( pEv );

						getGamerHuman().actionMouseDraggedButton2( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
				}
		}
		//-------------------------------------
		public void mouseMoved( MouseEvent pEv ){

				Point2D.Double lPoint = new Point2D.Double( pEv.getX(), pEv.getY() );
				convert2CartePosition( lPoint );
				evalueModifier( pEv );


				if( sFlagAutoscroll  ) {
						
						//	System.out.println ( " pEv.getY():" + pEv.getY() + " " + getY() + " " + (getY()+getHeight())+ " " +(getY()+getHeight()-sFlagAutoscrollSeuil));
						
						
						// si l'on est pres des bords on fait un scroll
						if(  pEv.getX() < (getX()+sFlagAutoscrollSeuil) 
								 || pEv.getX() > getX()+getWidth()-sFlagAutoscrollSeuil  
								 || pEv.getY() < (getY()+sFlagAutoscrollSeuil)
								 || pEv.getY() > (getY()+getHeight()-sFlagAutoscrollSeuil) ) {
								
								
								double lDecalX = 0;
								double lDecalY = 0;
								
								
								
								if(  pEv.getX() < (getX()+sFlagAutoscrollSeuil)
										 && (pEv.getX() <= cMemMoveX )) {
										lDecalX = (-sFlagAutoscrollSeuil- (pEv.getX()-getX())) /10.0;
										if( lDecalX < -sFlagAutoscrollMaxScroll )
												lDecalX = -sFlagAutoscrollMaxScroll; 
										
										if( lDecalX > -1 )
												lDecalX = -1;
										
										
								}
								else
										if( pEv.getX() > (getX()+getWidth()-sFlagAutoscrollSeuil)
												&&  pEv.getX() >= cMemMoveX ) {
												lDecalX = (sFlagAutoscrollSeuil + ((getX()+getWidth())-pEv.getX()))/10.0 ;
												if( lDecalX > sFlagAutoscrollMaxScroll )
														lDecalX = sFlagAutoscrollMaxScroll;
												
												if( lDecalX < 1 )
														lDecalX = 1;
										}
								
								
								
								if(  pEv.getY() < (getY()+sFlagAutoscrollSeuil)
										 &&  pEv.getY() <= cMemMoveY ) {
										lDecalY = (-sFlagAutoscrollSeuil- (pEv.getY()-getY()))/10.0;
										if( lDecalY < -sFlagAutoscrollMaxScroll )
												lDecalY = -sFlagAutoscrollMaxScroll; 
										
										if( lDecalY > -1 )
												lDecalY = -1;
										
								}
								else
										if( pEv.getY() > getY()+getHeight()-sFlagAutoscrollSeuil
												&&  pEv.getY() >= cMemMoveY ) {
												lDecalY = (sFlagAutoscrollSeuil + ((getY()+getHeight())-pEv.getY()))/10.0 ;
												if( lDecalY > sFlagAutoscrollMaxScroll )
														lDecalY = sFlagAutoscrollMaxScroll;
												
												if( lDecalY < 1 )
														lDecalY = 1;
										}
								
								
								if( lDecalX != 0  || lDecalY != 0 ) {
										// Point2D.Double lPosView = getGamerHuman().getViewPoint();
										
										// double lPosX = lPosView.getX()+lDecalX;
										// double lPosY = lPosView.getY()+lDecalY;
										
										
										// boolean lFlagMouseMoveX = false;
										// boolean lFlagMouseMoveY = false;
										
										
										// lFlagMouseMoveX = moveGamerView( lPosX, lPosY );
										
										
										
										// // Pour "Paralyser la souris" !
										
										// cMemClickX2 = cMemClickX = (int)pEv.getX();								
										// cMemClickY2 = cMemClickY = (int)pEv.getY();
										
										
										// Point lPtScreen = new Point( cMemMoveX, cMemMoveY );							 
										// SwingUtilities.convertPointToScreen( lPtScreen,  FrameGamer.Get().GetGamePanel() ); 
										
										// try{												Robot lRobot = new Robot();
												
												
										// 		lRobot.mouseMove( (int)lPtScreen.getX(), (int)lPtScreen.get
										//	Y() );
										//				}
										//		catch( Exception e )
										//			{
										//			}
								
										return;
								}						
						}			
				}
				getGamerHuman().actionMouseMoved( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
				
				cMemMoveX = pEv.getX();
				cMemMoveY = pEv.getY();				
		}
		//------------------------------------------------
		
		public void paint(){
				
				super.paint();
			 

				//				System.out.println( ">>>>>>>>>> RenderPanel.paint" );					
				World.Get().sceneDraw( getGC() );
				
				if( Math.abs( cMemClickX - cMemClickX2 ) >= 3 
						&&  Math.abs( cMemClickY - cMemClickY2 ) >= 3 ){
						int lMinX = Math.min( cMemClickX, cMemClickX2 );
						int lMaxX = Math.max( cMemClickX, cMemClickX2 );
						
						int lMinY = Math.min( cMemClickY, cMemClickY2 );
						int lMaxY = Math.max( cMemClickY, cMemClickY2 );
						
						getGC().setColor( Color.green );
						getGC().drawRect( lMinX, lMinY, lMaxX - lMinX, lMaxY - lMinY );
				}
		}
		//------------------------------------------------
		//------------------------------------------------		
		//------------------------------------------------
		static public void Read( PPgIniFile pIni, String pSection ) {

				System.out.println( "Loading Panel2D " );

				sFlagAutoscroll          = pIni.getboolean( pSection, "Autoscroll",       sFlagAutoscroll );
				sFlagAutoscrollMaxScroll = pIni.getint( pSection, "Autoscroll.MaxScroll", sFlagAutoscrollMaxScroll );
				sFlagAutoscrollSeuil     = pIni.getint( pSection, "Autoscroll.Seuil",     sFlagAutoscrollSeuil );
 
		}
}
		//***********************************















