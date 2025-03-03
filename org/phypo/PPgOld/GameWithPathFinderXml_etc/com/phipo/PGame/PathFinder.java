package com.phipo.GLib;

import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.geom.*;

//*************************************************
public class PathFinder{

		static final int CHEMIN=9999;

		//------------------------------
		static public boolean GetPath( PathCarte pCarte, Entity lEntity, Point2D.Double pPoint, ArrayList<PathCase> pPath ){

				// METTRE DES OPTIMISATION : verif depart arrive, dernier getpath ...
								
				PathCase lCaseSrc  = World.sPathCarte.getMeter( lEntity.getMX(), lEntity.getMY());
				PathCase lCaseDest = World.sPathCarte.getMeter( (int)pPoint.getX(), (int)pPoint.getY() );
				
				if( lCaseSrc != null && lCaseDest != null  ){
						if( lCaseSrc != lCaseDest )
								return GetShortlessPath( pCarte, lEntity, lCaseSrc, lCaseDest, pPath);
						else
								return true;
				}
				return false;				
		}
		//--------------------------------
		static public boolean GetShortlessPath( PathCarte pCarte, Entity pEntity, PathCase pSrc, PathCase pDest, ArrayList<PathCase> lResult ) {

				pCarte.razPath();

				boolean lFound = DoAStar( pCarte, pEntity, pSrc, pDest );

				PathCase lCur = pDest;

				if( lFound )
						lResult.add( lCur );

				// On va remonter a partir de la destination en prenant toujours le chemin 
				while( lCur != pSrc )
						{
								double val = 99999;			
								double fval = 9999999;
								PathCase lMin=null;
								
								for( int x = -1; x <=1; x++)
										for( int y =-1; y<=1; y++) {

												if( x ==0 && y== 0) // la case courante 
														continue;
														
												PathCase lCase = pCarte.get( lCur.cX+x, lCur.cY+y );

												if( lCase != null )
														if( lCase.cPathFinderMemo != 0 && lCase.cPathFinderMemo <= val  ) {
																if( lCase.cPathFinderMemo != val || lCase.cPathFinderDist < fval ) {
																		lMin = lCase;
																		val  = lCase.cPathFinderMemo;
																		fval = lCase.cPathFinderDist;
																}
														}
										}
								
								if( lMin == null || lMin == lCur )
										return false;
								
								lCur = lMin;
								//////								lCur.cPathFinderMemo2 = lCur.cPathFinderMemo;
								lCur.cPathFinderMemo = CHEMIN; // pour ne pas boucler

								if( lResult.size() > 100000 )
										return false;
								
								lResult.add( lCur );
						}
				
				System.out.println( "PAthFinder Result size:" + lResult.size() );
				return lFound;
		}
		//--------------------------------
		static boolean DoAStar( PathCarte pCarte, Entity pEntity, PathCase pSrc, PathCase pDest  ){

				TreeMap<Double,PathCase> lMap = new TreeMap<Double,PathCase>();
	

				pSrc.cPathFinderMemo = 1;
				lMap.put( new Double(pSrc.cPathFinderMemo = 1), pSrc);
	
					
				while( lMap.size() != 0 ) {

						if(  lMap.size() > 100000 )
								return false;

						Double lKey = lMap.firstKey();
						PathCase lCur = lMap.remove( lKey );
						
						
						if( lCur == null )
								return false;
						
						if( lCur == pDest )
								return true;
						
						for( int x = -1; x <=1; x++)
								for( int y =-1; y<=1; y++){
										
										if( x ==0 && y== 0)
												continue;

										double pas = 1.41;
										if( x==0 || y== 0) {
												pas = 1;
										}
												
										
										PathCase lCase = pCarte.get( lCur.cX+x, lCur.cY+y );
										if( lCase == null )
												continue; // pour ne pas boucler
										

										if( lCase.cPathFinderMemo == 0 ) {
												
												lCase.cPathFinderMemo = lCur.cPathFinderMemo+pas;
												
												long dx = pDest.cX - lCase.cX;
												long dy = pDest.cY - lCase.cY;
												
												double lCoef = pEntity.getPrototype().getMoving( lCase ); 

												
												if( lCase.isEmpty(pEntity) == false ){
														lCoef = 0;
														//														if( lListEntity.size() > 5 ){
														//																lCoef = 0;
														//														}
														//														else
														//																for( Entity lEntity:lListEntity ){
														//																		if( lEntity.getPrototype().isMobil() == false ){
														//																				lCoef = 0;
														//																				break;
														//																		}																				
														//																}
												}


												//		lCoef = 1.0;
												
												if( lCoef == 0 ){
														lCase.cPathFinderMemo = 99999; 
														lCoef = 999999;
														lCase.cPathFinderDist = 99999;														
												}
												else 
														lCoef = 100.0/lCoef;
												
										
												lCase.cPathFinderDist =  (dx*dx+dy*dy)*lCoef;  // calcul de la case en fonction de sa distance 
												// On peut ajouter au calcul un coef suivant la nature  de la case
												
												lMap.put( new Double( lCase.cPathFinderDist), lCase ); //lMap[ lCase.cPathFinderDist ] = lCase; 
										}
								}	
				}
				
				return false;
		} 
};
//*************************************************
