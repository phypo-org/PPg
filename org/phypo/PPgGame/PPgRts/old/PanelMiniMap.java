package org.phypo.PPg.PPgRts;



import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.Robot;


import java.util.*;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.phypo.PPg.PPgGame.*;
import org.phypo.PPg.PPgUtils.*;


//***********************************
public class PanelMiniMap extends PanelBox {


		GamerHuman        cGamer = null;

		
		public PanelMiniMap( GamerHuman pGamer, 
												 int pX, int pY, int pWidth, int pHeight){

				super( pGamer, pX, pY, pWidth, pHeight);
				
				setRender( new RenderMiniMap( pGamer, this ) );
	}
		//------------------------------------------ 
		
		// Convertit un position ecran en une position carte 

		void convert2CartePosition( Point2D.Double lPointDest ){
				
				
				// On enleve la position de la PanelBox des coordonnees de la souris
				double lPosX = ((double)lPointDest.getX()-getX())/cMyRender.getMagnify();
				double lPosY = ((double)lPointDest.getY()-getY())/cMyRender.getMagnify();

				
				
				GroundMap lMap = ((WorldRts)World.Get()).getMap();				

				lPosX *= lMap.getSizeSquare();
				lPosY *= lMap.getSizeSquare();


				// On verifie que l'on ne depasse pas les limites du monde
				if( lPosX < 0 ) { 
						lPosX = 0;
				}
				else
						if( lPosX > World.Get().getWidth()) {							
								lPosX = World.Get().getWidth();
						}
				
				if( lPosY < 0 )  {
						lPosY = 0;
				}
				else
						if( lPosY > World.Get().getHeight()) {
								lPosY = World.Get().getHeight();
						}

				lPointDest.setLocation( lPosX, lPosY );
		}
		// -----------------------------
		public void mouseClicked( MouseEvent pEv ) {
				
				// calcul de la position de la souris dans la carte 
				Point2D.Double lPointDest = new Point2D.Double( pEv.getX(), pEv.getY() );
				convert2CartePosition( lPointDest );
				
				if( SwingUtilities.isMiddleMouseButton( pEv ) == true){
																
						getGamerHuman().setViewPoint( lPointDest.x, lPointDest.y); // deplacement du point de vue !

				}
				//			else if( SwingUtilities.isRightMouseButton( pEv ) == true){
				//						cGamer.sendMessage( new GamerMessage( pEv, GamerMessage.Order.ORDER_AT, lPointDest, lFlagCtrl, lFlagShift ) );
				//				}			 
		}
		//-------------------------------------
		public void mouseDragged( MouseEvent pEv ){

				// Deplacement dans la carte
				if( SwingUtilities.isMiddleMouseButton( pEv ) == true){
						Point2D.Double lPointDest = new Point2D.Double( pEv.getX(), pEv.getY() );
						convert2CartePosition( lPointDest );

						getGamerHuman().setViewPoint( lPointDest.getX(), lPointDest.getY() ); // deplacement du point de vue !
				}
		}
}
//***********************************
