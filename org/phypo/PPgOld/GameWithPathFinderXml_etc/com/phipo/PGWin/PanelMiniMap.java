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
final public class PanelMiniMap extends PanelBox {


		GamerHuman        cGamer = null;

		
		public PanelMiniMap( Component pDrawComponent, GamerHuman pGamer, RenderMiniMap pRender, 
												 int pX, int pY, int pWidth, int pHeight){

				super( pDrawComponent, pGamer, pRender, pX, pY, pWidth, pHeight);

		}


		//------------------------------------------ 

		// Convertit un position ecran en une position carte 

		void convert2CartePosition( Point2D.Double lPointDest ){
				
				double lPosX = ((double)lPointDest.getX()-getX()) /cRender.getMagnify();
				double lPosY = ((double)lPointDest.getY()-getY()) /cRender.getMagnify();
				
				
				if( lPosX < 0 ) { 
						lPosX = 0;
				}
				else
						if( lPosX > World.sDecorCarte.getWidth()) {								
								lPosX = World.sDecorCarte.getWidth();
						}
				
				if( lPosY < 0 )  {
						lPosY = 0;
				}
				else
						if( lPosY > World.sDecorCarte.getHeight()) {
								lPosY = World.sDecorCarte.getHeight();
						}
				
				
				lPosX *= DecorCarte.sSizeCase;
				lPosY *= DecorCarte.sSizeCase;

				lPointDest.setLocation( lPosX, lPosY );
		}
		// -----------------------------
		public void mouseClicked( MouseEvent pEv ) {
				
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

				if( SwingUtilities.isMiddleMouseButton( pEv ) == true){
																
						cRender.getGamerHuman().getViewPoint().setLocation( lPointDest.getX(), lPointDest.getY() ); // deplacement du point de vue !
						
				}
				else if( SwingUtilities.isRightMouseButton( pEv ) == true){
						cGamer.sendMessage( new GamerMessage( pEv, GamerMessage.Order.ORDER_AT, lPointDest, lFlagCtrl, lFlagShift ) );
				}			 
		}
		//-------------------------------------
		public void mouseDragged( MouseEvent pEv ){

				// Deplacement dans la carte
				if( SwingUtilities.isMiddleMouseButton( pEv ) == true){
						Point2D.Double lPointDest = new Point2D.Double( pEv.getX(), pEv.getY() );
						convert2CartePosition( lPointDest );
						cRender.getGamerHuman().getViewPoint().setLocation( lPointDest.getX(), lPointDest.getY() ); // deplacement du point de vue !						
				}
		}
}
//***********************************
