package org.phypo.PPgGame.PPgRts;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.Image;

import javax.swing.*;

import org.phypo.PPg.PPgGame.*;


//*************************************************
// Gete l'affichage 2D du monde modelise

public class Render2D extends RenderBase{


		Font cPolice    = new Font( "Monospaced", Font.PLAIN, 16 );
		
		Color cColorInConstruct        = new Color( 0.8f, 0.8f, 0.0f, 0.8f );
		Color cColorWorkingConstruct   = new Color( 0.9f, 0.9f, 0.0f, 0.8f );

		Color cColorSelection          = new Color( 1f, 1f, 1f, 0.8f );
		Color cColorForbiden           = new Color( 1f, 0f, 0f, 0.5f );

			
		double      cMagnify = 1.0;
		public double getMagnify() { return cMagnify; }

		// Variables renseignées/calculées au moment du paint !
		double      cDecalX=0;
		double      cDecalY=0;
		WorldRts    cWorld = null;
		GroundMap   cMap = null;
		int         cHeightFont =10;

		//---------------------
		public Render2D( GamerHuman pGamer,  PanelBox pPanel, double pMagnify ){
				super( pGamer, pPanel);

				cMagnify = pMagnify;;
		}
		
		//---------------------
		/*
		void drawActor( Actor pActor ){

				
				double cMagnify = getMagnify(); 					 				

				int lEcranX = (int)(pEntity.getX()*cMagnify -cDecalX);
				int lEcranY = (int)(pEntity.getY()*cMagnify -cDecalY);
			 
				pActor.draw( lEcranX, lEcranY, cMagnify );
		}
		*/

		//------------------------------------------------

		void drawMap( ){

					 			
				//				System.out.println( "Render2D.drawMap 1" );


				// la taille d'un bout de decor
				double lSizeSquareDraw = cMap.cSizeSquare*cMagnify;

				// la position du joueur dans l'univers 
				Point2D.Double lPosView = cGamer.getViewPoint();

				//				System.out.println( "=== Size:" + cMyPanel.cSize.getWidth() +":"+ cMyPanel.cSize.getHeight() 
				//													+ " " + cMap.cSizeSquare + ": "+ cMap.cSizeSquareInv);


				// Calcul du nombre de case affichable
				double lNbSquareX = (cMyPanel.cSize.getWidth()  * cMap.cSizeSquareInv) / cMagnify;
				double lNbSquareY = (cMyPanel.cSize.getHeight() * cMap.cSizeSquareInv) / cMagnify;

				
				
				//				lNbSquareX--;
				//				lNbSquareY--;

				// On les met a disposition pour RenderMiniMap
				cGamer.setSizeView( new Point2D.Double( lNbSquareX, lNbSquareY ) );

				
				// On calcule les plus petite et plus grande coordonnees affichable 
				int lMinX = (int)(lPosView.getX()*cMap.cSizeSquareInv - lNbSquareX/2);
				int lMaxX = (int)(lPosView.getX()*cMap.cSizeSquareInv + lNbSquareX/2);
				int lMinY = (int)(lPosView.getY()*cMap.cSizeSquareInv - lNbSquareY/2);
				int lMaxY = (int)(lPosView.getY()*cMap.cSizeSquareInv + lNbSquareY/2);

				if( lMinX < 0 )
						lMinX = 0;
				if( lMinY < 0 )
						lMinY = 0;
				//		
				//				System.out.println( "lNbSquareX:" +lNbSquareX + " lNbSquareY:" + lNbSquareY );
								//					System.out.println( "lMinX:"+lMinX+" lMaxX:"+lMaxX+ " lMinY:" + lMinY + " lMaxY:" +lMaxY );

				//								System.out.println( "Render2D.drawMap 2" );


				if( World.sFurtifMode == false ) {

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
				}
				//
				//				else
				for( int y=lMinY; y<= lMaxY; y++){
						for( int x=lMinX; x<= lMaxX; x++){

								
								if( x < 0 || x >= cMap.getWidth() || y < 0 || y >= cMap.getHeight() ){
										//							System.out.println( "X:" + x + " Y:" + y + " HORS" );
								}
								else {
										GroundSquare lSquare = cMap.get(x,y);
										cGC.setColor( lSquare.getProto().getColor() );

										int lEcranX = (int)(x*lSizeSquareDraw -cDecalX);
										int lEcranY = (int)(y*lSizeSquareDraw -cDecalY);

										//					System.out.println( "X:" + x + " Y:" + y + "  eX:" + lEcranX + " eY:" + lEcranY );


										if( World.sFurtifMode == false ) {

												// A OPTIMIZER LARGEMENT !!!
												if( lSquare.getProto().getImgBack() != null ){
														lSquare.getProto().getImgBack().paintIcon( null, cGC, lEcranX, lEcranY );													
												}
												if( lSquare.getProto().getImg() != null ){
														lSquare.getProto().getImg().paintIcon( null, cGC, lEcranX, lEcranY );													
												}
										} else {												
										//										cGC.drawRect( lEcranX, lEcranY, (int)lSizeSquareDraw,  (int)lSizeSquareDraw );
										cGC.drawLine(  lEcranX, lEcranY,  lEcranX+(int)lSizeSquareDraw, lEcranY+(int)lSizeSquareDraw ); 
										cGC.drawLine(  lEcranX, lEcranY+(int)lSizeSquareDraw,  lEcranX+(int)lSizeSquareDraw, lEcranY ); 
										}
			
										//		cGC.setColor( Color.white );
										// cGC.drawString( "("+lSquare.getX()+":"+lSquare.getY()+")", lEcranX+5, lEcranY+15 );
								}
						}
				}
				//				System.out.println( "Render2D.drawMap 3" );
		}



		//---------------------
		public void paint( Graphics2D pGC ) 
		{							
				cGC   = pGC;

				// Keep the current world
				cWorld = (WorldRts)World.Get();

				// Keep the map
				cMap =cWorld.getMap();


				//				System.out.println( "Render2D::paint" );

				
				if( World.IsPause()) {						
						cGC.drawString( "PAUSE", 100, 100 );
						return ;
				}

				Point2D.Double lPosView = cGamer.getViewPoint();


				cHeightFont = cGC.getFontMetrics().getMaxAscent() + cGC.getFontMetrics().getMaxDescent();


				if( World.sFurtifMode == false )
						cGC.setColor( cWorld.cBackgroundColor);
				else
						cGC.setColor( Color.white );
	
				//		if( cMap.getBackgroundImg() == null ) {
						// inutile si pixmap ou autre 
						cGC.fillRect( 0, 0, (int)cMyPanel.cSize.getWidth(),   (int)cMyPanel.cSize.getHeight() );
						//		}

				// On a deja tenu compte de la position de la fentre lors de la creation du GC

				cDecalX = lPosView.getX()*cMagnify - cMyPanel.cSize.getWidth()/2;    
				cDecalY = lPosView.getY()*cMagnify - cMyPanel.cSize.getHeight()/2;

				drawMap( );
				//				drawPathCarte( );

				

				// Le curseur soft
				cGC.setColor( Color.orange );

				//				System.out.println( "X:" + cGamer.getLastPoint().getX() + " Y:" + cGamer.getLastPoint().getY() );

				int lEcranX = (int)(cGamer.getLastPoint().getX()*cMagnify -cDecalX);
				int lEcranY = (int)(cGamer.getLastPoint().getY()*cMagnify -cDecalY);

				cGC.drawLine( lEcranX-50, (int)lEcranY-50, (int)lEcranX+50, (int)lEcranY+50 );
				cGC.drawLine( (int)lEcranX-50, (int)lEcranY+50, (int)lEcranX+50, (int)lEcranY-50 );			
	
							
				/*		
				double [] cRessourcesVal = cGamer.getRessources();
				int lInter = (int)(cMyPanelBox.getWidth()/cRessourcesVal.length);
				
		

				int lY = lHeightFont;
				int lX = 50;

				for( int i=0; i<cRessourcesVal.length; i++){
						Ressource lRessource = Ressource.GetRessource(i);

						cGC.setColor( Color.black ); 
						cGC.drawString(" "+(int)cRessourcesVal[i], lX,  lY ); //+lRessource.getTinyIcon().getIconWidth(),
						cGC.setColor( Color.white ); 
						cGC.drawString(" "+(int)cRessourcesVal[i], lX+1,  lY+1 ); //+lRessource.getTinyIcon().getIconWidth(),
						lRessource.getTinyIcon().paintIcon( null, cGC, lX,  lY+1);	
						lX += lInter;
				}
				*/

				int lX = 50;
				int lY = cHeightFont;

				for(int i=0;i< World.sVectFpsTime.length; i++){
				     cGC.setColor( Color.black ); 
				     cGC.drawString(""+(int)(World.sVectFpsTime[i]*1000), lX,  lY ); 
				     cGC.setColor( Color.white ); 
				     cGC.drawString(""+(int)(World.sVectFpsTime[i]*1000), lX+1,  lY+1 ); 	
				     lY += cHeightFont;

				}
				
		}
}
//*************************************************
