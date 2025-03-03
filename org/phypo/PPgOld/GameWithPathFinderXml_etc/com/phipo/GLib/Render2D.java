package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.Image;

import javax.swing.*;


//*************************************************
// Gete l'affichage 2D du monde modelise

class Render2D extends RenderBase{


		Font cPolice    = new Font( "Monospaced", Font.PLAIN, 16 );
		
		Color cColorInConstruct        = new Color( 0.8f, 0.8f, 0.0f, 0.8f );
		Color cColorWorkingConstruct   = new Color( 0.9f, 0.9f, 0.0f, 0.8f );

		Color cColorSelection          = new Color( 1f, 1f, 1f, 0.8f );
		Color cColorForbiden           = new Color( 1f, 0f, 0f, 0.5f );
			

		double cDecalX=0;
		double cDecalY=0;
		
		//---------------------
		public Render2D( GamerHuman pGamer, double pMagnify ){
				super( pGamer );

				cMagnify = pMagnify;;
		}
		
		//---------------------
		void drawEntity( Entity pEntity ){
				
				double lMagnify = getMagnify(); 					 				

				int lEcranX = (int)(pEntity.getX()*lMagnify -cDecalX);
				int lEcranY = (int)(pEntity.getY()*lMagnify -cDecalY);
				
				
				/*				switch( pEntity.getGroupId() ){
				case 0: 	cGC.setColor( Color.black ); break;
				case 1: 	cGC.setColor( Color.blue ); break;
				case 2: 	cGC.setColor( Color.red ); break;
				case 3: 	cGC.setColor( Color.green ); break;
				default:  cGC.setColor( Color.yellow ); break;
				}
				cGC.drawRect( lEcranX, lEcranY,
											(int)(pEntity.getWidth()*lMagnify),
											(int)(pEntity.getHeight()*lMagnify) );
				*/
				
				
				try{
						//				lEcranX += (int) (pEntity.getWidth() *lMagnify-pEntity.getImg().getIconWidth()) /2.0;
						//				lEcranY += (int) (pEntity.getHeight()*lMagnify-pEntity.getImg().getIconHeight())/2.0;
						

						if( World.sFurtifMode == false && pEntity.getAnimAll() != null ){	 							

								//								System.out.println( "Draw AnimAll " + pEntity.getAnimAll().getName() );

								int lEcranMX = (int)(pEntity.getMX()*lMagnify -cDecalX);
								int lEcranMY = (int)(pEntity.getMY()*lMagnify -cDecalY);
								

								pEntity.getAnimAll().draw( lEcranMX, lEcranMY, lMagnify,
																					 cGC,
																					 ActionEntity.ActionType.WALK, 
																					 pEntity.getOrientation().getAngle(),																					 
																					 World.sCurrentTime ); // A CHANGER ! (temps du sprite + temps global ? )

								////								ImageIcon lTmpImg = pEntity.getAnimAll().get( ActionEntity.ActionType.WALK, pEntity.getOrientation().getAngle(),  World.sCurrentTime );
								///if( lTmpImg != null ){
								///										lTmpImg.paintIcon( PPAppli.GetAppli(), cGC, lEcranX, lEcranY );							
					  		 ///								}
							 	//								else										
								//										pEntity.getImg().paintIcon( PPAppli.GetAppli(), cGC, lEcranX, lEcranY );							
						}
						else
								pEntity.getImg().paintIcon( null, cGC, lEcranX, lEcranY );	
						
						

						
						if( pEntity.isSelect() ){
								cGC.setColor( cColorSelection ); 
																cGC.drawRect( lEcranX, lEcranY,
																							(int)(pEntity.getWidth()*lMagnify),
																							(int)(pEntity.getHeight()*lMagnify) );
																cGC.drawRect( lEcranX-1, lEcranY-1,
																							(int)(pEntity.getWidth()*lMagnify)+2,
																							(int)(pEntity.getHeight()*lMagnify)+2);
								//								cGC.drawRect( lEcranX-2, lEcranY-2,
								//															(int)(pEntity.getWidth()*lMagnify)+4,
								//															(int)(pEntity.getHeight()*lMagnify)+4);	
						}
						if(  pEntity.getInConstruct() ){
								
								float lOpacity = (float)(0.75 - ((pEntity.cLife/pEntity.getPrototype().cLife)*0.55));
								
								Color cColorInConstruct        = new Color( 0.8f, 0.8f, 0.0f, lOpacity );
								cGC.setColor( cColorInConstruct );
								
								cGC.fillOval( lEcranX, lEcranY,
															(int)((pEntity.getWidth()*1.1)*lMagnify),
															(int)((pEntity.getHeight()*1.1)*lMagnify));								
								
								cGC.setColor( Color.white); 								
								cGC.drawString( (int)pEntity.cLife + "/" + (int)pEntity.getPrototype().cLife,
																lEcranX, lEcranY );
								
						}
						
						
						if(  pEntity.getWorkingConstruct() ){
								cGC.setColor( cColorWorkingConstruct );
								
								cGC.fillOval( lEcranX, lEcranY,
															(int)(5*lMagnify),
															(int)(5*lMagnify));								
						}
						
						if( World.sDrawHealthBar ){
								
								float lSante = (float)(pEntity.cLife/pEntity.getPrototype().cLife);
								if( lSante <= 0.2 )
										cGC.setColor( Color.red );
								else
										if( lSante <= 0.5 )
												cGC.setColor( Color.orange );
								else
										if( lSante <= 0.8 )
												cGC.setColor( Color.yellow );
								else
												cGC.setColor( Color.green );
												
								
								cGC.drawRect( lEcranX, (int)(lEcranY-5*lMagnify),
															(int)(pEntity.getWidth()*lMagnify),
															(int)(3*lMagnify));	

								cGC.fillRect( lEcranX, (int)(lEcranY-5*lMagnify),
															(int)(pEntity.getWidth()*lMagnify*lSante),
															(int)(3*lMagnify));	
						}


						
						
						// ======= DEBUG ======
						if( World.sDebug && World.sDebugVision){
										// Tracer du rayn de l'objet !
										// 								cGC.setColor( Color.white );
								
										// double lmEcranX = pEntity.getMX()*lMagnify -cDecalX;
										// double lmEcranY = pEntity.getMY()*lMagnify -cDecalY;
								
										//								double lmRayon = pEntity.getPrototype().cRayon*lMagnify;
										//								lmEcranX -= lmRayon/2;
										//								lmEcranY -= lmRayon/2;
								
										//								cGC.drawOval( (int)lmEcranX, 
										//															(int)lmEcranY, 
										//															(int)lmRayon,
										//															(int)lmRayon);				
								
										
								cGC.setColor( Color.yellow );
								double lmRayon = pEntity.getPrototype().getRayon()*lMagnify;
								double lmEcranX = pEntity.getMX()*lMagnify -cDecalX;
								double lmEcranY = pEntity.getMY()*lMagnify -cDecalY;
								lmEcranX -= lmRayon/2;
								lmEcranY -= lmRayon/2;
								
								cGC.drawOval( (int)lmEcranX, 
															(int)lmEcranY, 
																	(int)lmRayon,
															(int)lmRayon);				
						}						
						// ======= DEBUG ======
				}
				catch( Exception e ){
						System.out.println( "Render2D.drawEntity Exception for " + pEntity.getPrototype().getName() );
						e.printStackTrace();

				}
				
				

				// ====== DEBUG ========
				if( World.sDebug && World.sDebugPath){
				
						ArrayList<PathCase> lPath = pEntity.getPath();
						if( lPath != null ){
								int i=0;
								for( PathCase lCaseCur: lPath ){
										
										lEcranX = (int)(lCaseCur.getMiddleMeterX()*lMagnify -cDecalX);
										lEcranY = (int)(lCaseCur.getMiddleMeterY()*lMagnify -cDecalY);
										
										cGC.setColor( Color.green );														
										cGC.fillOval( (int)(lEcranX-5*lMagnify), (int)(lEcranY-5*lMagnify), (int)(5*lMagnify), (int)(5*lMagnify));
										
										cGC.setColor( Color.white );														
										cGC.drawString( ""+i, (int)(lEcranX-5*lMagnify), (int)(lEcranY-5*lMagnify) );
										i++;								
								}
						}
				
						PathCase lCase = pEntity.getCurrentCase(); 
						
						lEcranX = (int)(lCase.getMiddleMeterX()*lMagnify -cDecalX);
						lEcranY = (int)(lCase.getMiddleMeterY()*lMagnify -cDecalY);
						cGC.setColor( Color.red );														
						cGC.fillOval( (int)(lEcranX-2*lMagnify), (int)(lEcranY-2*lMagnify), (int)(4*lMagnify), (int)(4*lMagnify));
				}
				// ====== DEBUG ========
				
		}
		//---------------------
		void drawDecorCarte(){

				DecorCarte lCarte = World.sDecorCarte;

				double lMagnify = getMagnify(); 					 				
				double lTailleFinal = DecorCarte.sSizeCase*lMagnify;

				Point2D.Double lPosView = cGamer.getViewPoint();

				//				System.out.println( "Size:" + cSize.getWidth() +":"+ cSize.getHeight() 
				//														+ " " + DecorCarte.sSizeCase + ": "+ DecorCarte.sSizeCaseInv);

				// Calcul du nombre de case affichable
				double lNbCaseX = (cSize.getWidth()*DecorCarte.sSizeCaseInv)  / lMagnify;
				double lNbCaseY = (cSize.getHeight()*DecorCarte.sSizeCaseInv) / lMagnify;

				//				lNbCaseX--;
				//				lNbCaseY--;

				// On les met a disposition pour RenderMiniMap
				cGamer.setSizeView( new Point2D.Double( lNbCaseX, lNbCaseY ) );
				
				
				
				int lMinX = (int)(lPosView.getX()*DecorCarte.sSizeCaseInv - lNbCaseX/2);
				int lMaxX = (int)(lPosView.getX()*DecorCarte.sSizeCaseInv + lNbCaseX/2);
				int lMinY = (int)(lPosView.getY()*DecorCarte.sSizeCaseInv - lNbCaseY/2);
				int lMaxY = (int)(lPosView.getY()*DecorCarte.sSizeCaseInv + lNbCaseY/2);

				if( lMinX < 0 )
						lMinX = 0;
				if( lMinY < 0 )
						lMinY = 0;
				
				//				System.out.println( "lNbCaseX:" +lNbCaseX + " lNbCaseY:" + lNbCaseY );
				//				System.out.println( "lMinX:"+lMinX+" lMaxX:"+lMaxX+ " lMinY:" + lMinY + " lMaxY:" +lMaxY );

				
				for( int y=lMinY; y<= lMaxY; y++){
						for( int x=lMinX; x<= lMaxX; x++){


								if( x < 0 || x >= lCarte.getWidth() || y < 0 || y >= lCarte.getHeight() ){
										//			System.out.println( "X:" + x + " Y:" + y + " HORS" );
								}
								else {
										DecorCase lCase = lCarte.get(x,y);
										cGC.setColor( lCase.getProto().getColor() );

										int lEcranX = (int)(x*lTailleFinal -cDecalX);
										int lEcranY = (int)(y*lTailleFinal -cDecalY);

										//										System.out.println( "X:" + x + " Y:" + y + "  eX:" + lEcranX + " eY:" + lEcranY );


										if( World.sFurtifMode == false ) {

												// A OPTIMIZER LARGEMENT !!!
												if( lCase.getProto().getImgBack() != null ){
														lCase.getProto().getImgBack().paintIcon( null, cGC, lEcranX, lEcranY );													
												}
												if( lCase.getProto().getImg() != null ){
														lCase.getProto().getImg().paintIcon( null, cGC, lEcranX, lEcranY );													
												}
										}




										//										cGC.drawRect( lEcranX, lEcranY, (int)lTailleFinal,  (int)lTailleFinal );
										cGC.drawLine(  lEcranX, lEcranY,  lEcranX+(int)lTailleFinal, lEcranY+(int)lTailleFinal ); 
										cGC.drawLine(  lEcranX, lEcranY+(int)lTailleFinal,  lEcranX+(int)lTailleFinal, lEcranY ); 
																				
										//										cGC.setColor( Color.white );
										//										cGC.drawString( "("+lCase.getX()+":"+lCase.getY()+")", lEcranX+5, lEcranY+15 );
								}
						}
				}
		}
		//---------------------
		void drawPathCarte(){

				PathCarte lCarte = World.sPathCarte;

				double lMagnify = getMagnify(); 					 				
				double lTailleFinal = PathCarte.sSizeCase*lMagnify;

				// Calcul du nombre de case affichable
				double lNbCaseX = (cSize.getWidth()*PathCarte.sSizeCaseInv)  / lMagnify;
				double lNbCaseY = (cSize.getHeight()*PathCarte.sSizeCaseInv) / lMagnify;

				//				lNbCaseX--;
				//				lNbCaseY--;

				// Position du point 0.0 de la fenetre  si l'on convertit en pixel dans le repere de la carte

				Point2D.Double lPosView = cGamer.getViewPoint();
	
				int lMinX = (int)(lPosView.getX()*PathCarte.sSizeCaseInv - lNbCaseX/2);
				int lMaxX = (int)(lPosView.getX()*PathCarte.sSizeCaseInv + lNbCaseX/2);
				int lMinY = (int)(lPosView.getY()*PathCarte.sSizeCaseInv - lNbCaseY/2);
				int lMaxY = (int)(lPosView.getY()*PathCarte.sSizeCaseInv + lNbCaseY/2);

				
				//				System.out.println( "lNbCaseX:" +lNbCaseX + " lNbCaseY:" + lNbCaseY );
				//				System.out.println( "lMinX:"+lMinX+" lMaxX:"+lMaxX+ " lMinY:" + lMinY + " lMaxY:" +lMaxY );

				if( lMinX < 0 )
						lMinX = 0;
				if( lMinY < 0 )
						lMinY = 0;
				
				for( int y=lMinY; y<= lMaxY; y++){
						for( int x=lMinX; x<= lMaxX; x++){

								if( x < 0 || x >= lCarte.getWidth() || y < 0 || y >= lCarte.getHeight() ){
										//			System.out.println( "X:" + x + " Y:" + y + " HORS" );
								} 
								else {
										PathCase lCase = lCarte.get(x,y);
										if( lCase != null ){
												if( lCase.getCurrentEntity() != null )										
														drawEntity( lCase.getCurrentEntity()  );
										

												// ========= DEBUG ========
												if( World.sDebug && World.sDebugZone ){
														if( lCase.getCurrentEntityZone() != null ){
																int lEcranX = (int)(x*lTailleFinal -cDecalX);
																int lEcranY = (int)(y*lTailleFinal -cDecalY);
																cGC.setColor( Color.blue );														
																cGC.fillOval( (int)(lEcranX+PathCarte.sSizeCase-2*lMagnify), 
																							(int)(lEcranY+PathCarte.sSizeCase-2*lMagnify), 
																							(int)(4*lMagnify), (int)(4*lMagnify));
																
																if( lCase.getCurrentEntity() != null ){
																		cGC.setColor( Color.yellow );														
																		cGC.fillOval( (int)(lEcranX+PathCarte.sSizeCase-2.5*lMagnify), 
																									(int)(lEcranY+PathCarte.sSizeCase-2.5*lMagnify), 
																									(int)(5*lMagnify), (int)(5*lMagnify));
																}
														}
												}
												// ========= DEBUG ========												
										}
								}
						}
				}				
		}
		//---------------------
		public void paint( ) 
		{							
				//				System.out.println( "Render2D::paint" );

				
				if( World.IsPause()) {						
						cGC.drawString( "PAUSE", 100, 100 );
						return ;
				}

				Point2D.Double lPosView = cGamer.getViewPoint();

				int cHeightFont = cGC.getFontMetrics().getMaxAscent() + cGC.getFontMetrics().getMaxDescent();


				// inutile si pixmap ou autre 
				cGC.setColor( World.sBagroundColor);
				cGC.fillRect( 0, 0, (int)cSize.getWidth(),   (int)cSize.getHeight() );

				double lMagnify = getMagnify();

				cDecalX = lPosView.getX()*lMagnify - cSize.getWidth()/2;
				cDecalY = lPosView.getY()*lMagnify - cSize.getHeight()/2;

				drawDecorCarte( );
				drawPathCarte( );

				
				cGC.setColor( Color.orange );

				//				System.out.println( "X:" + cGamer.getLastPoint().getX() + " Y:" + cGamer.getLastPoint().getY() );

				int lEcranX = (int)(cGamer.getLastPoint().getX()*lMagnify -cDecalX);
				int lEcranY = (int)(cGamer.getLastPoint().getY()*lMagnify -cDecalY);

				cGC.drawLine( lEcranX-50, (int)lEcranY-50, (int)lEcranX+50, (int)lEcranY+50 );
				cGC.drawLine( (int)lEcranX-50, (int)lEcranY+50, (int)lEcranX+50, (int)lEcranY-50 );				
								

				if( cGamer.getSelectionPositionIcon() != null ){
						
						cGamer.getSelectionPositionIcon().paintIcon( null, cGC, 
																												 (int)(lEcranX-cGamer.getSelectionPositionIcon().getIconWidth()/2.0),
																												 (int)(lEcranY-cGamer.getSelectionPositionIcon().getIconHeight()/2.0) );	
						
						if( World.sPathCarte.testEntityAndTerrain( cGamer.cCurrentPrototype, cGamer.getLastPoint().getX(), cGamer.getLastPoint().getY()) == false ){
								
								cGamer.setSelectionPositionFlag( false );

								cGC.setColor( cColorForbiden );
								cGC.fillRect( (int)(lEcranX-cGamer.getSelectionPositionIcon().getIconWidth()/2.0),
															(int)(lEcranY-cGamer.getSelectionPositionIcon().getIconHeight()/2.0),
															cGamer.getSelectionPositionIcon().getIconWidth(),
															cGamer.getSelectionPositionIcon().getIconHeight());
						}
						else
								cGamer.setSelectionPositionFlag( true );
				}
				
				double [] cRessourcesVal = cGamer.getRessources();
				int lInter = (int)(cSize.getWidth()/cRessourcesVal.length);
				
		

				int lY = cHeightFont;
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

				lX = 0;
				lY = cHeightFont;
				for(int i=0;i<World.sVectFps.length; i++){
				     cGC.setColor( Color.black ); 
				     cGC.drawString(""+(int)(World.sVectFps[i]*1000), lX,  lY ); 
				     cGC.setColor( Color.white ); 
				     cGC.drawString(""+(int)(World.sVectFps[i]*1000), lX+1,  lY+1 ); 	
				     lY += cHeightFont;

				}
				
		}
}
//*************************************************
