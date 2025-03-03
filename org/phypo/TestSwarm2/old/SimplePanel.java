package org.phypo.PPg.PPgTest;




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

			System.out.println( "SimplePanel.mousePressed" );

			cGrepping = !cGrepping; 
		}
		//-------------------------- 
		public void mouseReleased( MouseEvent pEv ) {

			System.out.println( "SimplePanel.mouseReleased" );
		}		

		// -----------------------------
		public void mouseClicked( MouseEvent pEv ) {

				System.out.println( "SimplePanel.mouseClicked" );

		}
		//-------------------------------------
		public void mouseDragged( MouseEvent pEv ){

				System.out.println( "SimplePanel.mouseDragged" );
		}
		//-------------------------------------
		public void mouseMoved( MouseEvent pEv ){

				if( !cGrepping )
						return ;
						
				//				System.out.println( "SimplePanel.mouseMoved" );
				Point2D.Double lPoint = new Point2D.Double( pEv.getX(), pEv.getY() );

				getGamerHuman().actionMouseMoved( lPoint, cFlagCtrl, cFlagShift, cFlagAlt );
		}
		//------------------------------------------
		//------------------------------------------ 
		//------------------------------------------ 
		static Paint sGradientPaint = null;

		public void paint(){

				if( World.IsPause()) {						
						cGC.drawString( "PAUSE", 100, 100 );
						return ;
				}

				// inutile si pixmap ou autre 
				//				cGC.setColor( Color.black );
				//				cGC.fillRect( 0, 0, (int)cSize.getWidth(),   (int)cSize.getHeight() );

				if( sGradientPaint== null	) {
						//	cGC.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

						//						sGradientPaint = new GradientPaint(0, y, Color.cyan,
						//																							 0, y +width, Color.blue);
						sGradientPaint = new GradientPaint(0, y, new Color( 50, 50, 200 ),
																							 0, y +width, new Color( 5, 5, 20 ));
				}

        cGC.setPaint(sGradientPaint);
        cGC.fillRect(0, 0,width, height);




		
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















