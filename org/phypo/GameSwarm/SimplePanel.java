package org.phypo.GameSwarm;




import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.Robot;

import java.awt.geom.Point2D;

import java.util.*;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;




import org.phypo.PPgGame.PPgGame.*;


/**
 ** Sous fentre permettant de voir les application et les connexions 
 ** sous forme graphique.
 ** 
 */


//***********************************
final public class SimplePanel extends PanelBox
{ 		
		int cMemClickX=0;
		int cMemClickY=0;
		
		int cMemClickX2=0;
		int cMemClickY2=0;

		long cMemWhen = 0;

		boolean cDrag = false;
		
		Rectangle cRectSelect=null;
		
		boolean cGrepping = true;

		//--------------------------
		public SimplePanel( GamerHuman pGamer, 
												int pX, int pY, int pWidth, int pHeight) { 		
				
 				super( pGamer, pX, pY, pWidth, pHeight );				
		}  																					
		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 

		public void mousePressed( MouseEvent pEv ) {

				evalueModifier( pEv );

				cMemClickX = cMemClickX2 = pEv.getX();
				cMemClickY = cMemClickY2 = pEv.getY();	

				Point2D.Double lPoint = new Point2D.Double( pEv.getX(), pEv.getY()  );

				if( SwingUtilities.isLeftMouseButton( pEv ) == true){						
						getGamerHuman().actionButton1Pressed( lPoint, cFlagCtrl, cFlagShift, cFlagAlt ); 
				}
				else 	if( SwingUtilities.isRightMouseButton( pEv ) == true){						
						getGamerHuman().actionButton2Pressed( lPoint,  cFlagCtrl, cFlagShift, cFlagAlt ); 
				}
				else	if( SwingUtilities.isMiddleMouseButton( pEv ) == true){						
						cMemClickX = pEv.getX();
						cMemClickY = pEv.getY();
						getGamerHuman().actionButton3Pressed( lPoint, cFlagCtrl, cFlagShift, cFlagAlt ); 
					 	cDrag = true;
				}
				
		}
		//-------------------------- 
		public void mouseReleased( MouseEvent pEv ) {

				evalueModifier( pEv );

				Point2D.Double lPoint = new Point2D.Double( pEv.getX(), pEv.getY()  );
				cMemClickX2 = pEv.getX();
				cMemClickY2 = pEv.getY();

				if( SwingUtilities.isLeftMouseButton( pEv ) == true){				 
						getGamerHuman().actionButton1Released( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
					}				
				else	if( SwingUtilities.isRightMouseButton( pEv ) == true){		
						getGamerHuman().actionButton2Released( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
				}
				else	if( SwingUtilities.isMiddleMouseButton( pEv ) == true){	
						cDrag = false;
							
						getGamerHuman().actionSelectRect( new Point2D.Double( cMemClickX,cMemClickY), 
																							new Point2D.Double( cMemClickX2,cMemClickY2),
																							cFlagCtrl, cFlagShift, cFlagAlt );
			}	
		}		

		// -----------------------------
		public void mouseClicked( MouseEvent pEv ) {

				//				System.out.println( "SimplePanel.mouseClicked" );

		}
		//-------------------------------------
		public void mouseDragged( MouseEvent pEv ){

				evalueModifier( pEv );

				if( cDrag )
						return;

				Point2D.Double lPoint = new Point2D.Double( pEv.getX(), pEv.getY() );
								

				getGamerHuman().actionMouseMoved( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
		}
		//-------------------------------------
		public void mouseMoved( MouseEvent pEv ){

				evalueModifier( pEv );

				if( cDrag )
						return;
				
				Point2D.Double lPoint = new Point2D.Double( pEv.getX(), pEv.getY() );
				getGamerHuman().actionMouseMoved( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
		}
		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 
		//------------------------------------------------
		public void clear(){
				super.clear();
			}
		//------------------------------------------------
		static Paint sGradientPaint = null;

		public void paint(){

				if( World.IsPause()) {						
						cGC.drawString( "PAUSE", 100, 100 );
						return ;
				}

				// inutile si pixmap ou autre 
				//				cGC.setColor( Color.black );
				//				cGC.fillRect( 0, 0, (int)cSize.getWidth(),   (int)cSize.getHeight() );

				/*
				if( sGradientPaint== null	) {
						//	cGC.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

						//						sGradientPaint = new GradientPaint(0, y, Color.cyan,
						//																							 0, y +width, Color.blue);
						sGradientPaint = new GradientPaint(0, y, new Color( 50, 50, 200 ),
																							 0, y +width, new Color( 5, 5, 20 ));
				} 

        cGC.setPaint(sGradientPaint);
        cGC.fillRect(0, 0,width, height);
				*/



		
				//				System.out.println( ">>>>>>>>>> SimplePanel.paint" );					
				World.Get().sceneDraw( getGC() );
				/* 
				 ON VA ESSAYER DE SE PASSER DE RENDER
						 EN IMPLEMENTANT DIRECTEMENT LE RENDU DANS LE PANEL !!!!

						 IL VA FALOIR CREER LE GRAPHICS CONTEXT 2D ICI  PUIS PASSER LES OBJETS DE WORLS EN REVUE
				*/
		}
}
		//***********************************















