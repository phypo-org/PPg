package org.phypo.PPg.PPgRts;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;

 import org.phypo.PPg.PPgGame.*;


//*************************************************
// Gere l'affichage 2D du monde modelise sous forme d'une carte

public class RenderMiniMap extends RenderBase{

		WorldRts    cWorld = null;
		GroundMap   cMap = null;
		double      cMagnify=1.0;
		public double getMagnify() { return cMagnify; }

		//------------------------------------------------

		public RenderMiniMap(GamerHuman pGamer, PanelBox pPanel ){
				super( pGamer, pPanel );
				
		// Variables renseignées/calculées au moment du paint !
		}
		//---------------------
		public void paint( Graphics2D pGC ) 
		{
				if( World.sFurtifMode == true )
						return ;


				cGC   = pGC;

				// Keep the current world
				cWorld = (WorldRts)World.Get();

				// Keep the map
				cMap =cWorld.getMap();



				cGC.setColor( cWorld.cBackgroundColor);
				cGC.fillRect( 0, 0,  (int)cMyPanel.cSize.getWidth(),   (int)cMyPanel.cSize.getHeight());

				Point2D.Double lPosView = cGamer.getViewPoint();

						
				double lScaleX = ((double)cMyPanel.cSize.getWidth())/((double)cMap.getWidth());
				double lScaleY = ((double)cMyPanel.cSize.getHeight())/((double)cMap.getHeight());


				
				cMagnify = Math.min( lScaleX, lScaleY );


				//				System.out.println( "RenderMiniMap.paint <<<<<<<<<<<< " +lScaleX + " " +lScaleY  + " --> " + cMagnify );

				//		if( World.sFurtifMode == false ){					 						
				{	
						
						/////				cGC.fillRect( 0, 0, (int)cSize.getWidth(),   (int)cSize.getHeight() );

						/* AFFICHER L'IMAGE EN MODE REDUIT // IL FAUDRA LA (RE)CONSTRUIRE A L'INIT (RESIZE) DU PANEL OU DE LA LECTURE DU .INI
						if( cMap.getBackgroundImg() != null ) {
								cGC.drawImage( cMap.getBackgroundImg().getImage(),
															 0, 0, (int)cMyPanel.cSize.getWidth(), (int)cMyPanel.cSize.getHeight(),
															 
															 (int)(lPosView.getX()*cMagnify-cMyPanel.cSize.getWidth()/2),
															 (int)(lPosView.getY()*cMagnify-cMyPanel.cSize.getHeight()/2),
															 (int)(lPosView.getX()*cMagnify+cMyPanel.cSize.getWidth()/2),
															 (int)(lPosView.getY()*cMagnify+cMyPanel.cSize.getHeight()/2),
															 cMap.getBackgroundColor(),
															 cMap.getBackgroundImg().getImageObserver() );													
						}
						*/

						for( int y=0; y<cMap.getHeight(); y++){
								for( int x=0; x< cMap.getWidth(); x++){
										GroundSquare lSquare = cMap.get(x,y);
										if( lSquare != null )
												cGC.setColor( lSquare.getProto().getColor() );
										
										cGC.fillRect( (int)(x*cMagnify), (int)(y*cMagnify) , (int)(cMagnify), (int)(cMagnify) );	
								}
						}
						
						//						drawPathCarte();
												
						// Dessin du cadre de la fentre principale
				}

				Point2D.Double lSizeView = cGamer.getSizeView();

				cGC.setColor( Color.red );
				double lPosX = (lPosView.getX()*cMap.cSizeSquareInv);
				double lPosY = (lPosView.getY()*cMap.cSizeSquareInv);


				cGC.drawRect( (int)((lPosX-lSizeView.getX()/2)*cMagnify), 
											(int)((lPosY-lSizeView.getY()/2)*cMagnify),
											(int)((lSizeView.getX())*cMagnify),
											(int)((lSizeView.getY())*cMagnify) );

				cGC.drawRect( (int)((lPosX-lSizeView.getX()/2)*cMagnify-1), 
											(int)((lPosY-lSizeView.getY()/2)*cMagnify-1),
											(int)((lSizeView.getX())*cMagnify+2),
											(int)((lSizeView.getY())*cMagnify+2) );

				//				System.out.println( "RenderMiniMap.paint >>>>>>>>>>>>>>" );




				//				cGC.drawRect( (int)((lPosView.getX()-lSizeView.getX()/2)*cMagnify*World.GetSquareSizeInv()),
				//											(int)((lPosView.getY()-lSizeView.getY()/2)*cMagnify*World.GetSquareSizeInv()),
				//											(int)((lSizeView.getX())*cMagnify*World.GetSquareSizeInv()),
				//											(int)((lSizeView.getY())*cMagnify*World.GetSquareSizeInv()) );
		}
}
//*************************************************
