package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;

 

//*************************************************
// Gete l'affichage 2D du monde modelise

public class RenderMiniMap extends RenderBase{

 
		//------------------------------------------------

		public RenderMiniMap(GamerHuman pGamer ){
				super( pGamer );
		}

		//---------------------
		void drawPathCarte( ){
				
				PathCarte lCarte = World.sPathCarte;
				Point2D.Double lPosView = cGamer.getViewPoint();

				double lScaleX = ((double)cSize.getWidth())/((double)lCarte.getWidth());
				double lScaleY = ((double)cSize.getHeight())/((double)lCarte.getHeight());
				
				double lMagnify = Math.min( lScaleX, lScaleY );


				for( int y=0; y<lCarte.getHeight(); y++){
						for( int x=0; x< lCarte.getWidth(); x++){
								PathCase lCase = lCarte.get(x,y);

								if( lCase.getCurrentEntityZone() != null ){
										if( cGamer.isFriend(lCase.getCurrentEntityZone()) )
												cGC.setColor( Color.green );
										else if( cGamer.isEnemy(lCase.getCurrentEntityZone()) )
												cGC.setColor( Color.red );
										else
												cGC.setColor( Color.white );
										
										cGC.fillRect( (int)(x*lMagnify), (int)(y*lMagnify) , (int)(lMagnify+1), (int)(lMagnify+1) );	
								}
						}
				}
		}
		//---------------------
		public void paint() 
		{
				cGC.setColor( World.sBagroundColor);
				cGC.fillRect( 0, 0, (int)cSize.getWidth(),   (int)cSize.getHeight() );

				DecorCarte lCarte = World.sDecorCarte;
				Point2D.Double lPosView = cGamer.getViewPoint();

						
				double lScaleX = ((double)cSize.getWidth())/((double)lCarte.getWidth());
				double lScaleY = ((double)cSize.getHeight())/((double)lCarte.getHeight());
				
				
				cMagnify = Math.min( lScaleX, lScaleY );
				
				if( World.sFurtifMode == false ){					 						
						
						
						/////				cGC.fillRect( 0, 0, (int)cSize.getWidth(),   (int)cSize.getHeight() );
												
						
						for( int y=0; y<lCarte.getHeight(); y++){
								for( int x=0; x< lCarte.getWidth(); x++){
										DecorCase lCase = lCarte.get(x,y);
										cGC.setColor( lCase.getProto().getColor() );
										
										cGC.fillRect( (int)(x*cMagnify), (int)(y*cMagnify) , (int)(cMagnify+1), (int)(cMagnify+1) );	
								}
						}
						
						drawPathCarte();
												
						// Dessin du cadre de la fentre principale
				}
				Point2D.Double lSizeView = cGamer.getSizeView();

				cGC.setColor( Color.red );
				double lPosX = (lPosView.getX()*lCarte.sSizeCaseInv);
				double lPosY = (lPosView.getY()*lCarte.sSizeCaseInv);


				cGC.drawRect( (int)((lPosX-lSizeView.getX()/2)*cMagnify), 
											(int)((lPosY-lSizeView.getY()/2)*cMagnify),
											(int)((lSizeView.getX())*cMagnify),
											(int)((lSizeView.getY())*cMagnify) );

				cGC.drawRect( (int)((lPosX-lSizeView.getX()/2)*cMagnify-1), 
											(int)((lPosY-lSizeView.getY()/2)*cMagnify-1),
											(int)((lSizeView.getX())*cMagnify+2),
											(int)((lSizeView.getY())*cMagnify+2) );

				//				cGC.drawRect( (int)((lPosView.getX()-lSizeView.getX()/2)*cMagnify*World.GetCaseSizeInv()),
				//											(int)((lPosView.getY()-lSizeView.getY()/2)*cMagnify*World.GetCaseSizeInv()),
				//											(int)((lSizeView.getX())*cMagnify*World.GetCaseSizeInv()),
				//											(int)((lSizeView.getY())*cMagnify*World.GetCaseSizeInv()) );
		}
}
//*************************************************
